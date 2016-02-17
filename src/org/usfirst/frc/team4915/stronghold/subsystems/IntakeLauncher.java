package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
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

    private final int LAUNCHER_MAX_HEIGHT_DEGREES = 70; // TODO, in degrees from
                                                        // horizontal
    private final int LAUNCHER_MIN_HEIGHT_DEGREES = -10; // TODO, in degrees
                                                         // from horizontal

    private final double LAUNCHER_MAX_HEIGHT_VOLTS = 1023; // TODO, in
                                                           // potentiometer
                                                           // volts
    private final double LAUNCHER_MIN_HEIGHT_VOLTS = 0; // TODO, in
                                                        // potentiometer volts

    private final double JOYSTICK_SCALE = 1.0; // TODO

    private double setPoint; // in potentiometer volts
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

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveToSetPointCommand());
    }

    // Sets the speed on the flywheels to suck in the boulder
    public void setSpeedIntake() {
        this.intakeLeftMotor.set(FULL_SPEED_REVERSE);
        this.intakeRightMotor.set(FULL_SPEED_REVERSE);
    }

    // Sets the speed on the flywheels to launch the boulder
    public void setSpeedLaunch() {
        this.intakeLeftMotor.set(FULL_SPEED_FORWARD);
        this.intakeRightMotor.set(FULL_SPEED_FORWARD);
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

    public void readSetPoint() {
        setPoint = aimMotor.getPosition();
    }

    public void setSetPoint(double newSetPoint) {
        setPoint = newSetPoint;
    }

    public void offsetSetPoint(double offset) {
        readSetPoint();
        setPoint += offset;
    }

    public void offsetSetPoint() {
        double joystickY = Robot.oi.aimStick.getAxis((Joystick.AxisType.kY));
        if (Math.abs(joystickY) > .1) {
            offsetSetPoint(joystickY * JOYSTICK_SCALE);
        }
    }

    public void moveToSetPoint() {
        keepSetPointInRange();
        aimMotor.changeControlMode(TalonControlMode.Position);
        aimMotor.set(setPoint);
    }

    public void keepSetPointInRange() {
        if (setPoint > LAUNCHER_MAX_HEIGHT_VOLTS) {
            setPoint = LAUNCHER_MAX_HEIGHT_VOLTS;
        }
        if (setPoint < LAUNCHER_MIN_HEIGHT_VOLTS) {
            setPoint = LAUNCHER_MIN_HEIGHT_VOLTS;
        }
    }

    public void aimWithDashboard() {
        setSetPoint(SmartDashboard.getNumber("Launcher Set Point: "));
        moveToSetPoint();
    }

    public boolean isLauncherAtBottom() {
        return aimMotor.isRevLimitSwitchClosed();
    }

    public double degreesToVolts(int degrees) {
        double heightRatio = (degrees - LAUNCHER_MIN_HEIGHT_DEGREES) / (LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES);
        return LAUNCHER_MIN_HEIGHT_VOLTS + (LAUNCHER_MAX_HEIGHT_VOLTS - LAUNCHER_MIN_HEIGHT_VOLTS) * heightRatio;
    }

    public int voltsToDegrees(double volts) {
        double heightRatio = (volts - LAUNCHER_MIN_HEIGHT_VOLTS) / (LAUNCHER_MAX_HEIGHT_VOLTS - LAUNCHER_MIN_HEIGHT_VOLTS);
        return LAUNCHER_MIN_HEIGHT_DEGREES + (int) ((LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES) * heightRatio);
    }
}
