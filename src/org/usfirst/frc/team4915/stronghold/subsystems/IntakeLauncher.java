package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.MoveToSetPointCommand;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative speed indicates a wheel spinning inwards and positive speed
    // indicates a wheel spinning outwards.
    private final double FULL_SPEED_REVERSE = 1.0;
    private final double FULL_SPEED_FORWARD = -1.0;

    /*
     * in encoder ticks Step 1: Find number of encoder ticks per cycle:
     * 4(Quadrature Encoder) * 7(Cycles per revolution) = 28 Step 2: Find the
     * planetary gear ratio(Ask your electrical team): 188:1 Step 2.5: Find
     * other gear ratios: 56:14 = 4:1 Step 3: Convert ticks to degrees, multiply
     * all ratios together: 28 * 188 * 4 = 21056 ticks per 360 degrees Step 3.5:
     * Find ticks per 1 degree, divide by 360: 21056 / 360 = 58.489 There will
     * be error
     */
    private final int TICKS_PER_CYCLE = 4 * 7;
    private final int PLANETARY_GEAR_RATIO = 188 / 1;
    private final int GEAR_RATIO = 56 / 14;
    private final int TICKS_PER_360_DEGREES = TICKS_PER_CYCLE * PLANETARY_GEAR_RATIO * GEAR_RATIO;
    private final double TICKS_PER_DEGREE = TICKS_PER_360_DEGREES / 360;

    private final int MIN_ANGLE = -10; // deg from horiz
    private final int MAX_ANGLE = 60; // deg from horiz

    private final int LAUNCHER_MIN_HEIGHT = 0; // enc
    private final int ENCODER_RANGE = 4000; // enc
    private final int LAUNCHER_MAX_HEIGHT = LAUNCHER_MIN_HEIGHT + ENCODER_RANGE;

    /*
     * Joystick Scale calculations: at max speed the joystick should go from 0
     * to 90 in 2 seconds The talon set method units are in ticks per 10 ms when
     * in speed mode In 10 ms we should move .45 degrees, which is 26.32 ticks
     * The equation is motion range / time in ms * 10
     */
    private final int TIME_IN_MS_FOR_FULL_MOTION = 2000;
    private final double JOYSTICK_SCALE = (LAUNCHER_MAX_HEIGHT - LAUNCHER_MIN_HEIGHT) / TIME_IN_MS_FOR_FULL_MOTION * 10; //

    private int setPoint;
    private boolean wheelsFinished = false;

    // left and right are determined when standing behind the robot
    // These motors control flywheels that collect and shoot the ball
    private CANTalon intakeLeftMotor = RobotMap.intakeLeftMotor;
    private CANTalon intakeRightMotor = RobotMap.intakeRightMotor;

    // This motor adjusts the angle of the launcher for shooting
    public CANTalon aimMotor = RobotMap.aimMotor;

    // limitswitch in the back of the basket that tells the robot when the
    // boulder is secure
    public DigitalInput boulderSwitch = RobotMap.boulderSwitch;

    public Solenoid launcherSolenoid = RobotMap.launcherSolenoid;

    // lowers the aimer so the encoder can zero when the robot turns on
    // method commented for now so we can test

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveToSetPointCommand());
    }

    // Sets the speed on the flywheels to suck in the boulder
    public void setSpeedIntake() {
        this.intakeLeftMotor.set(FULL_SPEED_REVERSE);
        this.intakeRightMotor.set(-FULL_SPEED_REVERSE); // Right motor spins in
                                                        // the wrong direction
    }

    // Sets the speed on the flywheels to launch the boulder
    public void setSpeedLaunch() {
        this.intakeLeftMotor.set(FULL_SPEED_FORWARD);
        this.intakeRightMotor.set(-FULL_SPEED_FORWARD); // Right motor spins in
                                                        // the wrong direction
    }

    public boolean areWheelsFinished() {
        return wheelsFinished;
    }

    public void setWheelsFinished(boolean wheelsFinished) {
        this.wheelsFinished = wheelsFinished;
    }

    public void activatePneumatic() {
        this.launcherSolenoid.set(true);
    }

    public void retractPneumatic() {
        this.launcherSolenoid.set(false);
    }

    public void initAimer() {
        if (Robot.intakeLauncher.aimMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != null) {
            aimMotor.changeControlMode(TalonControlMode.PercentVbus);
            aimMotor.set(FULL_SPEED_FORWARD);
        }
    }

    public boolean isLauncherAtAngle(double angle) {
        double angleMotorPosition = aimMotor.getEncPosition() * TICKS_PER_DEGREE;
        return angleMotorPosition < angle + 5 && angleMotorPosition > angle - 5;
    }

    public void readSetPoint() {
        setPoint = getEncoderPosition();
    }

    public void setSetPoint(int newSetPoint) {
        setPoint = newSetPoint;
    }

    public void offsetSetPoint(int offset) {
        readSetPoint();
        setPoint += offset;
    }

    public void offsetSetPoint() {
        double joystickY = Robot.oi.aimStick.getAxis((Joystick.AxisType.kY));
        if (Math.abs(joystickY) > .1) {
            offsetSetPoint((int) (joystickY * 1000));
        }
    }

    public void moveToSetPoint() {
        keepSetPointInRange();
        if (isLauncherAtBottom()) {
            setEncoderPosition(0);
        }
        aimMotor.changeControlMode(TalonControlMode.Position);
        aimMotor.set(setPoint);
    }

    public void keepSetPointInRange() {
        if (setPoint > LAUNCHER_MAX_HEIGHT) {
            setPoint = LAUNCHER_MAX_HEIGHT;
        }
        if (setPoint < LAUNCHER_MIN_HEIGHT) {
            setPoint = LAUNCHER_MIN_HEIGHT;
        }
    }

    public void aimWithDashboard() {
        setSetPoint((int) SmartDashboard.getNumber("Launcher Set Point: "));
        moveToSetPoint();
    }

    public boolean isLauncherAtBottom() {
        return aimMotor.isRevLimitSwitchClosed();
    }

    public int degreesToTicks(int degrees) {
        return (int) (degrees * TICKS_PER_DEGREE);
    }

    public void setPointInDegrees(int TargetY) {
        TargetY = degreesToTicks(TargetY);
        setSetPoint(TargetY);
    }

    public int getEncoderPosition() {
        return aimMotor.getEncPosition();
    }

    public void setEncoderPosition(int encoderPosition) {
        aimMotor.setEncPosition(encoderPosition);
    }
}
