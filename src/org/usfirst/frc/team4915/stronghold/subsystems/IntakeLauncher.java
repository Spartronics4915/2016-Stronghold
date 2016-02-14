package org.usfirst.frc.team4915.stronghold.subsystems;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.SetLauncherHeightCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.ZeroAimerCommand;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative speed indicates a wheel spinning inwards and positive speed
    // indicates a wheel spinning outwards.
    private final double FULL_SPEED_REVERSE = 1.0 / 1.0 ;
    private final double FULL_SPEED_FORWARD = -1.0 / 1.0;
    private final double ZERO_SPEED = 0.0;
    private final double LAUNCHER_SERVO_NEUTRAL_POSITION = 0.0;
    private final double LAUNCHER_SERVO_LAUNCH_POSITION = -5.0;

    /*
     * in encoder ticks Step 1: Find number of encoder ticks per cycle:
     * 4(Quadrature Encoder) * 7(Cycles per revolution) = 28 Step 2: Find the
     * planetary gear ratio(Ask your electrical team): 188:1 Step 2.5: Find
     * other gear ratios: 56:14 = 4:1 Step 3: Convert ticks to degrees, multiply
     * all ratios together: 28 * 188 * 4 = 21056 ticks per 360 degrees Step 3.5:
     * Find ticks per 1 degree, divide by 360: 21056 / 360 = 58.489
     */
    private final int TICKS_PER_CYCLE = 4 * 7;
    private final int PLANETARY_GEAR_RATIO = 188 / 1;
    private final int GEAR_RATIO = 56 / 14;
    private final int TICKS_PER_360_DEGREES = TICKS_PER_CYCLE * PLANETARY_GEAR_RATIO * GEAR_RATIO;
    private final double TICKS_PER_DEGREE = TICKS_PER_360_DEGREES / 360;

    private final double AIM_MOTOR_INCREMENT = 20 * TICKS_PER_DEGREE; // increment
                                                                     // 1 degree
    private final int LAUNCHER_MIN_HEIGHT = 0;
    private final int LAUNCHER_MAX_HEIGHT = 5264; // 90 degrees * ticks per
                                                  // degree

    /*
     * Joystick Scale calculations: at max speed the joystick should go from 0
     * to 90 in 2 seconds The talon set method units are in ticks per 10 ms when
     * in speed mode In 10 ms we should move .45 degrees, which is 26.32 ticks
     * The equation is motion range / time in ms * 10
     */
    private final int TIME_IN_MS_FOR_FULL_MOTION = 2000;
    private final double JOYSTICK_SCALE = (LAUNCHER_MAX_HEIGHT - LAUNCHER_MIN_HEIGHT) / TIME_IN_MS_FOR_FULL_MOTION * 10; //

    private boolean ballLaunched = false;

    // left and right are determined when standing behind the robot

    // These motors control flywheels that collect and shoot the ball
    public CANTalon intakeLeftMotor = RobotMap.intakeLeftMotor;
    public CANTalon intakeRightMotor = RobotMap.intakeRightMotor;

    // This motor adjusts the angle of the launcher for shooting
    public CANTalon aimMotor = RobotMap.aimMotor;

    // limitswitch in the back of the basket that tells the robot when the
    // boulder is secure
    public DigitalInput boulderSwitch = RobotMap.boulderSwitch;

    // servo that pushes the ball into the flywheels
    public Servo launcherServo = RobotMap.launcherServo;

    // lowers the aimer so the encoder can zero when the robot turns on
    // method commented for now so we can test
    public IntakeLauncher() {
        aimMotor.setEncPosition(0);
        //Command initCommand = new ZeroAimerCommand();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SetLauncherHeightCommand());
    }

    // Sets the speed on the flywheels to suck in the boulder
    public void setSpeedIntake() {
        this.intakeLeftMotor.set(FULL_SPEED_REVERSE);
        this.intakeRightMotor.set(-FULL_SPEED_REVERSE);
    }

    // Sets the speed on the flywheels to launch the boulder
    public void setSpeedLaunch() {
        this.intakeLeftMotor.set(FULL_SPEED_FORWARD);
        this.intakeRightMotor.set(-FULL_SPEED_FORWARD);
    }

    // stops the flywheels
    public void stopWheels() {
        this.intakeLeftMotor.set(ZERO_SPEED);
        this.intakeRightMotor.set(ZERO_SPEED);
    }

    // moves the launcher, joystick angle determines speed
    public void moveLauncher() {
        
        /*if (!VisionState.getInstance().AutoAimEnabled) {
            aimMotor.changeControlMode(TalonControlMode.Speed);
            aimMotor.set(Robot.oi.aimStick.getAxis(Joystick.AxisType.kY) * JOYSTICK_SCALE);
        } else {
            double targetY = VisionState.getInstance().TargetY * TICKS_PER_DEGREE;
            if (targetY < LAUNCHER_MIN_HEIGHT || targetY > LAUNCHER_MAX_HEIGHT) {
                SmartDashboard.putBoolean("Auto-aim target out of range", true);
            } else {
                aimMotor.changeControlMode(TalonControlMode.Position);
                aimMotor.set(targetY);
            }
        }
        if (aimMotor.isRevLimitSwitchClosed()) {
            aimMotor.setEncPosition(LAUNCHER_MIN_HEIGHT);
        }
        */
    }

    // changes the launcher height by a small value
    // direction is either 1 or -1
    public void incrementLauncherHeight(int direction) {
        /*if (!VisionState.getInstance().AutoAimEnabled) {
            System.out.println("Increment new height: " + (aimMotor.getPosition() + (AIM_MOTOR_INCREMENT * direction)));
            aimMotor.changeControlMode(TalonControlMode.Position);
            aimMotor.set(aimMotor.getPosition() + (AIM_MOTOR_INCREMENT * direction));
        }
        */
        System.out.println("in move launcher");
        aimMotor.changeControlMode(TalonControlMode.PercentVbus);
        aimMotor.set(0.5 * direction);
    }

    public void activateLaunchServo() {
        launcherServo.set(LAUNCHER_SERVO_LAUNCH_POSITION);
        System.out.println(launcherServo.get());
  
    }

    public void retractLaunchServo() {
        launcherServo.set(LAUNCHER_SERVO_NEUTRAL_POSITION);
        ballLaunched = true;
    }

    public void zeroEncoder() {
        aimMotor.setEncPosition(0);
    }

    public void initAimer() {
        if (Robot.intakeLauncher.aimMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != null) {
            aimMotor.changeControlMode(TalonControlMode.PercentVbus);
            aimMotor.set(FULL_SPEED_REVERSE / 2);
        }
    }

    public CANTalon getIntakeLeftMotor() {
        return intakeLeftMotor;
    }

    public CANTalon getIntakeRightMotor() {
        return intakeRightMotor;
    }

    public CANTalon getLauncherAimMotor() {
        return aimMotor;
    }

    public DigitalInput getBoulderSwitch() {
        return boulderSwitch;
    }

    public Servo getLauncherServo() {
        return launcherServo;
    }

    public CANTalon getAimMotor() {
        return aimMotor;
    }

    public boolean getBallLaunched() {
        return ballLaunched;
    }

    public void setBallLaunched(boolean ballLaunched) {
        this.ballLaunched = ballLaunched;
    }
}
