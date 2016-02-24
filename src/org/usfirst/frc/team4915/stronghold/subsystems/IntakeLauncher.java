package org.usfirst.frc.team4915.stronghold.subsystems;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimLauncherCommand;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative values indicate a wheel spinning outwards and positive values
    // indicate a wheel spinning inwards.
    private final double FULL_SPEED_REVERSE = -.60;
    private final double FULL_SPEED_FORWARD = 1;
    private final double ZERO_SPEED = 0.0;

    private final double LAUNCH_SPEED = 11;

    private final double LAUNCHER_MAX_HEIGHT_DEGREES = 45.0; // in degrees from
                                                             // horizontal
    private final double LAUNCHER_MIN_HEIGHT_DEGREES = -11.0; // in degrees from
                                                              // horizontal
    private double LAUNCHER_MAX_HEIGHT_TICKS = 325.0; // in potentiometer
                                                      // ticks
    private double LAUNCHER_MIN_HEIGHT_TICKS = 110.0; // in potentiometer
                                                      // ticks
    private double LAUNCHER_NEUTRAL_HEIGHT_TICKS = 200.0; // in
                                                          // potentiometer
                                                          // ticks
    private double LAUNCHER_INTAKE_HEIGHT_TICKS = 120.0; // in
                                                         // potentiometer
                                                         // ticks
    private final double JOYSTICK_SCALE = 50.0; // TODO

    private final double MIN_JOYSTICK_MOTION = 0.1;

    private final double SERVO_LEFT_LAUNCH_POSITION = .45;
    private final double SERVO_RIGHT_LAUNCH_POSITION = .65;
    private final double SERVO_LEFT_NEUTRAL_POSITION = .8;
    private final double SERVO_RIGHT_NEUTRAL_POSITION = .4;

    private double setPoint; // in potentiometer ticks
    private boolean autoCalibrate = false;

    // left and right are determined when standing behind the robot
    // These motors control flywheels that collect and shoot the ball
    private CANTalon intakeLeftMotor = RobotMap.intakeLeftMotorCAN15;
    private CANTalon intakeRightMotor = RobotMap.intakeRightMotorCAN14;

    // This motor adjusts the angle of the launcher for shooting
    public CANTalon aimMotor = RobotMap.aimMotor;

    // limitswitch in the back of the basket that tells the robot when the
    // boulder is secure
    public DigitalInput boulderSwitch = RobotMap.boulderSwitch;

    // These servos push the boulder into the launcher flywheels
    public Servo launcherServoLeft = RobotMap.launcherServoLeft;
    public Servo launcherServoRight = RobotMap.launcherServoRight;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new AimLauncherCommand());
        // setDefaultCommand(new BackUpJoystickControlCommand());
    }

    public IntakeLauncher() {
        readSetPoint();
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

    public void stopWheels() {
        this.intakeLeftMotor.set(ZERO_SPEED);
        this.intakeRightMotor.set(ZERO_SPEED);
    }

    public void activateLauncherServos() {
        this.launcherServoLeft.set(SERVO_LEFT_LAUNCH_POSITION);
        this.launcherServoRight.set(SERVO_RIGHT_LAUNCH_POSITION);
    }

    public void retractLauncherServos() {
        this.launcherServoLeft.set(SERVO_LEFT_NEUTRAL_POSITION);
        this.launcherServoRight.set(SERVO_RIGHT_NEUTRAL_POSITION);
    }

    private void readSetPoint() { // TODO rename
        setPoint = -getPosition();
    }

    // changes the set point to a value
    public void setSetPoint(double newSetPoint) {
        setPoint = newSetPoint;
    }

    // changes the set point based on an offset
    private void offsetSetPoint(double offset) {
        setPoint += offset;
        SmartDashboard.putNumber("Offset: ", offset);
        SmartDashboard.putNumber("Moving to setPoint", getSetPoint());
    }

    // sets the set point with the joystick and moves to set point
    private void trackJoystick() {
        moveLauncherWithJoystick();
        moveToSetPoint();
    }

    // sets the set point with vision and moves to set point
    private void trackVision() {
        moveLauncherWithVision();
        moveToSetPoint();
    }

    // changes the set point based on vision
    private void moveLauncherWithVision() {
        offsetSetPoint(degreesToTicks(-VisionState.getInstance().TargetY));
    }

    // changes the set point based on the joystick
    private void moveLauncherWithJoystick() {
        double joystickY = Robot.oi.aimStick.getAxis((Joystick.AxisType.kY));
        if (Math.abs(joystickY) > MIN_JOYSTICK_MOTION) {
            readSetPoint();
            offsetSetPoint(-joystickY * JOYSTICK_SCALE);
        }
    }

    // Checks to see if joystick control or vision control is needed and
    // controls motion
    public void aimLauncher() {
        SmartDashboard.putNumber("Launch Angle", (int) ticksToDegrees(getPosition()));
        if (VisionState.getInstance().wantsControl()) {
            trackVision();
        } else {
            trackJoystick();
        }
    }

    // sets the launcher position to the current set point
    private void moveToSetPoint() {
        keepSetPointInRange();
        aimMotor.changeControlMode(TalonControlMode.Position);
        aimMotor.set(setPoint);
        if (autoCalibrate) {
            autoCalibratePotentiometer();
        }
    }

    public void launcherSetNeutralPosition() {
        setSetPoint(-LAUNCHER_NEUTRAL_HEIGHT_TICKS);
    }

    public void launcherSetIntakePosition() {
        setSetPoint(-LAUNCHER_INTAKE_HEIGHT_TICKS);
    }

    public void launcherJumpToAngle(double angle) {
        setSetPoint(-degreesToTicks(angle));
    }

    // makes sure the set point doesn't go outside its max or min range
    private void keepSetPointInRange() {
        if (getSetPoint() > LAUNCHER_MAX_HEIGHT_TICKS) {
            setPoint = -LAUNCHER_MAX_HEIGHT_TICKS;
        }
        if (getSetPoint() < LAUNCHER_MIN_HEIGHT_TICKS) {
            setPoint = -LAUNCHER_MIN_HEIGHT_TICKS;
        }
    }

    private double degreesToTicks(double degrees) {
        double heightRatio = (degrees - LAUNCHER_MIN_HEIGHT_DEGREES) / (LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES);
        return LAUNCHER_MIN_HEIGHT_TICKS + (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS) * heightRatio;
    }

    private double ticksToDegrees(double ticks) {
        double heightRatio = (ticks - LAUNCHER_MIN_HEIGHT_TICKS) / (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS);
        return LAUNCHER_MIN_HEIGHT_DEGREES + (LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES) * heightRatio;
    }

    private void autoCalibratePotentiometer() {
        double neutralHeightRatio = (LAUNCHER_NEUTRAL_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS) / (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS);
        double intakeHeightRatio = (LAUNCHER_INTAKE_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS) / (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS);
        if (isLauncherAtBottom()) {
            LAUNCHER_MIN_HEIGHT_TICKS = getPosition();
        }
        if (isLauncherAtTop()) {
            LAUNCHER_MAX_HEIGHT_TICKS = getPosition();
        }
        LAUNCHER_NEUTRAL_HEIGHT_TICKS = LAUNCHER_MIN_HEIGHT_TICKS + (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS) * neutralHeightRatio;
        LAUNCHER_INTAKE_HEIGHT_TICKS = LAUNCHER_MIN_HEIGHT_TICKS + (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS) * intakeHeightRatio;

    }

    public boolean isLauncherAtTop() {
        return aimMotor.isRevLimitSwitchClosed();
    }

    public boolean isLauncherAtBottom() {
        return aimMotor.isFwdLimitSwitchClosed();
    }

    public double getPosition() {
        return Math.abs(aimMotor.getPosition());
    }

    public double getSetPoint() {
        return Math.abs(setPoint); // TODO will explain later
    }

    public boolean boulderLoaded() {
        SmartDashboard.putBoolean("Boulder Limit Switch ", boulderSwitch.get()); // TODO
                                                                                 // Flip
                                                                                 // polarity
        return boulderSwitch.get();
    }

    public boolean isLaunchReady() {
        return intakeLeftMotor.getBusVoltage() > LAUNCH_SPEED;
    }

    public CANTalon getIntakeMotorLeft() {
        return intakeLeftMotor;
    }

    public void backUpJoystickMethod() {
        aimMotor.changeControlMode(TalonControlMode.PercentVbus);
        aimMotor.set(Robot.oi.aimStick.getAxis((Joystick.AxisType.kY)));
    }
}
