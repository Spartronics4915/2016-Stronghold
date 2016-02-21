package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimLauncherCommand;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative values indicate a wheel spinning outwards and positive values
    // indicate a wheel spinning inwards.
    private final double FULL_SPEED_REVERSE = 1.0;
    private final double FULL_SPEED_FORWARD = -1.0;
    private final double ZERO_SPEED = 0;

    private final int LAUNCHER_MAX_HEIGHT_DEGREES = 70; // TODO, in degrees from
                                                        // horizontal
    private final int LAUNCHER_MIN_HEIGHT_DEGREES = -10; // TODO, in degrees
                                                         // from horizontal

    private final double LAUNCHER_MAX_HEIGHT_TICKS = 900.0; // TODO, in
                                                            // potentiometer
                                                            // volts
    private final double LAUNCHER_MIN_HEIGHT_TICKS = 100.0; // TODO, in
                                                            // potentiometer
                                                            // volts

    private final double JOYSTICK_SCALE = 10.0; // TODO

    private final double MIN_JOYSTICK_MOTION = 0.1;

    private final double SERVO_LAUNCH_POSITION = 1.0;
    private final double SERVO_NEUTRAL_POSITION = 1.0;

    private double setPoint; // in potentiometer volts
    private boolean forceLauncherNeutral = false;

    // left and right are determined when standing behind the robot
    // These motors control flywheels that collect and shoot the ball
    private CANTalon intakeLeftMotor = RobotMap.intakeLeftMotor;
    private CANTalon intakeRightMotor = RobotMap.intakeRightMotor;

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

    public void stopWheels() {
        this.intakeLeftMotor.set(ZERO_SPEED);
        this.intakeRightMotor.set(ZERO_SPEED);
    }

    public void activateLauncherServos() {
        this.launcherServoLeft.set(SERVO_LAUNCH_POSITION);
        this.launcherServoRight.set(SERVO_LAUNCH_POSITION);
    }

    public void retractLauncherServos() {
        this.launcherServoLeft.set(SERVO_NEUTRAL_POSITION);
        this.launcherServoRight.set(SERVO_NEUTRAL_POSITION);
    }

    public void readSetPoint() { // TODO rename
        setPoint = getPosition();
    }

    // changes the set point to a value
    public void setSetPoint(double newSetPoint) {
        setPoint = newSetPoint;
    }

    // changes the set point based on an offset
    public void offsetSetPoint(double offset) {
        readSetPoint();
        setPoint += offset;
    }

    // sets the set point with the joystick and moves to set point
    public void trackJoystick() {
        moveLauncherWithJoystick();
        moveToSetPoint();
    }

    // sets the set point with vision and moves to set point
    public void trackVision() {
        moveLauncherWithVision();
        moveToSetPoint();
    }

    // changes the set point based on vision
    public void moveLauncherWithVision() {
        offsetSetPoint(VisionState.getInstance().TargetY);
    }

    // changes the set point based on the joystick
    public void moveLauncherWithJoystick() {
        double joystickY = Robot.oi.aimStick.getAxis((Joystick.AxisType.kY));
        if (Math.abs(joystickY) > MIN_JOYSTICK_MOTION) {
            offsetSetPoint(joystickY * JOYSTICK_SCALE);
        }
    }

    // sets the launcher position to the current set point
    public void moveToSetPoint() {
        keepSetPointInRange();
        aimMotor.changeControlMode(TalonControlMode.Position);
        aimMotor.set(setPoint);
    }

    // Checks to see if joystick control or vision control is needed and
    // controls motion
    public void aimLauncher() {
        // if (VisionState.getInstance().wantsControl()) {
        // trackVision();
        // } else {
        trackJoystick();
        // }
    }

    // makes sure the set point doesn't go outside its max or min range
    public void keepSetPointInRange() {
        if (setPoint > LAUNCHER_MAX_HEIGHT_TICKS) {
            setPoint = LAUNCHER_MAX_HEIGHT_TICKS;
        }
        if (setPoint < LAUNCHER_MIN_HEIGHT_TICKS) {
            setPoint = LAUNCHER_MIN_HEIGHT_TICKS;
        }
    }

    public void aimWithDashboard() {
        setSetPoint(SmartDashboard.getNumber("Launcher Set Point: "));
        moveToSetPoint();
    }

    public boolean isLauncherAtBottom() {
        return aimMotor.isRevLimitSwitchClosed();
    }

    public boolean isLauncherAtTop() {
        return aimMotor.isFwdLimitSwitchClosed();
    }

    public double degreesToVolts(int degrees) {
        double heightRatio = (degrees - LAUNCHER_MIN_HEIGHT_DEGREES) / (LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES);
        return LAUNCHER_MIN_HEIGHT_TICKS + (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS) * heightRatio;
    }

    public int voltsToDegrees(double volts) {
        double heightRatio = (volts - LAUNCHER_MIN_HEIGHT_TICKS) / (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS);
        return LAUNCHER_MIN_HEIGHT_DEGREES + (int) ((LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES) * heightRatio);
    }

    public boolean getForceLauncherNeutral() {
        return forceLauncherNeutral;
    }

    public void setForceLauncherNeutral(boolean forceLauncherNeutral) {
        this.forceLauncherNeutral = forceLauncherNeutral;
    }

    public void backUpJoystickMethod() {
        aimMotor.changeControlMode(TalonControlMode.PercentVbus);
        aimMotor.set(Robot.oi.aimStick.getAxis((Joystick.AxisType.kY)));
    }

    public double getPosition() {
        return aimMotor.getPosition();
    }

    public double getSetPoint() {
        return setPoint;
    }
}
