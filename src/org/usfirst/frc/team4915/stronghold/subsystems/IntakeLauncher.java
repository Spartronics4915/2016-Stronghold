package org.usfirst.frc.team4915.stronghold.subsystems;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.BackUpJoystickControlCommand;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimLauncherCommand;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative values indicate a wheel spinning outwards and positive values
    // indicate a wheel spinning inwards.
    private final double FULL_SPEED_REVERSE = .750;
    private final double FULL_SPEED_FORWARD = -1;
    private final double ZERO_SPEED = 0.0;

    private final double LAUNCHER_MAX_HEIGHT_DEGREES = 35.0; // TODO, in degrees
                                                             // from horizontal
    private final double LAUNCHER_MIN_HEIGHT_DEGREES = -16.0; // TODO, in
                                                              // degrees from
                                                              // horizontal
    private final double LAUNCHER_MAX_HEIGHT_TICKS = 550.0; // TODO, in
                                                            // potentiometer
                                                            // ticks
    private final double LAUNCHER_MIN_HEIGHT_TICKS = 0.0; // TODO, in
                                                            // potentiometer
                                                            // ticks
    private final double LAUNCHER_NEUTRAL_HEIGHT_DEGREES = 20.0; // TODO, in
                                                                 // degrees from
                                                                 // horizontal

    private final double JOYSTICK_SCALE = 50.0; // TODO

    private final double MIN_JOYSTICK_MOTION = 0.1;

    private final double SERVO_LEFT_LAUNCH_POSITION = .45;
    private final double SERVO_RIGHT_LAUNCH_POSITION = .65;
    private final double SERVO_LEFT_NEUTRAL_POSITION = .7;
    private final double SERVO_RIGHT_NEUTRAL_POSITION = .4;

    private double setPoint; // in potentiometer volts

    // left and right are determined when standing behind the robot
    // These motors control flywheels that collect and shoot the ball
    private CANTalon intakeLeftMotor = RobotMap.intakeLeftMotorCAN15;
    private CANTalon intakeRightMotor = RobotMap.intakeRightMotorCAN14;

    // This motor adjusts the angle of the launcher for shooting
    public CANTalon aimMotor = RobotMap.aimMotor;

    // limitswitch in the back of the basket that tells the robot when the
    // boulder is secure
    public DigitalInput boulderSwitch = RobotMap.boulderSwitch;

    public boolean boulderLoaded()
    {
        SmartDashboard.putBoolean("Boulder Limit Switch ", boulderSwitch.get()); // TODO Flip polarity
        return boulderSwitch.get();
    }
    
    // These servos push the boulder into the launcher flywheels
    public Servo launcherServoLeft = RobotMap.launcherServoLeft;
    public Servo launcherServoRight = RobotMap.launcherServoRight;

    @Override
    protected void initDefaultCommand() {

        setDefaultCommand(new AimLauncherCommand());
        //setDefaultCommand(new BackUpJoystickControlCommand());
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

    public void launch() {
        setSpeedLaunch();
        activateLauncherServos();
    }

    public void launchEnd() {
        retractLauncherServos();
        stopWheels();
        //launcherSetNeutralPosition();
    }

    private void readSetPoint() { // TODO rename
        setPoint = -getPosition();
    }

    // changes the set point to a value
    public void setSetPoint(double newSetPoint) {
        setPoint = newSetPoint;
    }

    // changes the set point based on an offset
    public void offsetSetPoint(double offset) {
        readSetPoint();
        setPoint += offset;
        SmartDashboard.putNumber("Offset: ", offset);
        SmartDashboard.putNumber("Moving to setPoint", getSetPoint());
        //System.out.println("Offset: " + offset);
        //System.out.println("Current SetPoint: " + getSetPoint());
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
        //System.out.println("aimMotor.set(" + setPoint + ")");
        if (!isOnTarget()) {
            aimMotor.set(setPoint);
        }
        //System.out.println("setted------------------------------------------------------");
    }

    // Checks to see if joystick control or vision control is needed and
    // controls motion
    public void aimLauncher() {
        if (VisionState.getInstance().wantsControl()) {
            trackVision();
        } else {
            trackJoystick();
        }
    }

    // makes sure the set point doesn't go outside its max or min range
    public void keepSetPointInRange() {
        if (getSetPoint() > LAUNCHER_MAX_HEIGHT_TICKS) {
            setPoint = -LAUNCHER_MAX_HEIGHT_TICKS;
        }
        if (getSetPoint() < LAUNCHER_MIN_HEIGHT_TICKS) {
            setPoint = -LAUNCHER_MIN_HEIGHT_TICKS;
        }
    }

    public void aimWithDashboard() {
        setSetPoint(SmartDashboard.getNumber("Launcher Set Point: "));
        moveToSetPoint();
    }

    public boolean isLauncherAtTop() {
        return aimMotor.isRevLimitSwitchClosed();
    }

    public boolean isLauncherAtBottom() {
        return aimMotor.isFwdLimitSwitchClosed();
    }

    public double degreesToTicks(double degrees) {
        double heightRatio = (degrees - LAUNCHER_MIN_HEIGHT_DEGREES) / (LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES);
        return LAUNCHER_MIN_HEIGHT_TICKS + (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS) * heightRatio;
    }

    public double ticksToDegrees(double volts) {
        double heightRatio = (volts - LAUNCHER_MIN_HEIGHT_TICKS) / (LAUNCHER_MAX_HEIGHT_TICKS - LAUNCHER_MIN_HEIGHT_TICKS);
        return LAUNCHER_MIN_HEIGHT_DEGREES + (LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES) * heightRatio;
    }

    public void backUpJoystickMethod() {
        aimMotor.changeControlMode(TalonControlMode.PercentVbus);
        aimMotor.set(Robot.oi.aimStick.getAxis((Joystick.AxisType.kY)));
    }

    public void launcherSetNeutralPosition() {
        setSetPoint(-degreesToTicks(LAUNCHER_NEUTRAL_HEIGHT_DEGREES));
    }

    public double getPosition() {
        //System.out.println("Current Position: " + aimMotor.getPosition());
        return Math.abs(aimMotor.getPosition()); //TODO will explain later
    }

    public double getSetPoint() {
        return Math.abs(setPoint); //TODO will explain later
    }
    
    private boolean isOnTarget() {
        //return (getPosition() < getSetPoint() + 10.0 && getPosition() > getSetPoint() - 10);
        return false;
    }
}
