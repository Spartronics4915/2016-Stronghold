package org.usfirst.frc.team4915.stronghold.subsystems;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.SetLauncherHeightCommand;
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
    // Negative speed indicates a wheel spinning inwards and positive speed
    // indicates a wheel spinning outwards.
    private final double INTAKE_SPEED = -1.0;
    private final double LAUNCH_SPEED = 1.0;
    private final double ZERO_SPEED = 0.0;
    private final double LAUNCHER_SERVO_NEUTRAL_POSITION = 0.0;
    private final double LAUNCHER_SERVO_LAUNCH_POSITION = 1.0;

    // in encoder ticks
    private final double AIM_MOTOR_INCREMENT = 1; // TODO
    private final double LAUNCHER_MIN_HEIGHT = 0;
    private final double LAUNCHER_MAX_HEIGHT = 1000; // TODO
    private final double JOYSTICK_SCALE = 1.0; // TODO

    public Joystick aimStick = Robot.oi.getJoystickAimStick();

    // left and right are determined when standing behind the robot

    // These motors control flywheels that collect and shoot the ball
    public CANTalon intakeLeftMotor = RobotMap.intakeLeftMotor;
    public CANTalon intakeRightMotor = RobotMap.intakeRightMotor;

    // This motor adjusts the angle of the launcher for shooting
    public CANTalon aimMotor = RobotMap.aimMotor;

    // limitswitch in the back of the basket that tells the robot when the
    // boulder is secure
    public DigitalInput boulderSwitch = RobotMap.boulderSwitch;

    // limitswitches that tell when the launcher is at the maximum or minumum
    // height
    public DigitalInput launcherBottomSwitch = RobotMap.launcherBottomSwitch;
    public DigitalInput launcherTopSwitch = RobotMap.launcherTopSwitch;

    // servo that pushes the ball into the flywheels
    public Servo launcherServo = RobotMap.launcherServo;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SetLauncherHeightCommand());
    }

    // Sets the speed on the flywheels to suck in the boulder
    public void setSpeedIntake() {
        this.intakeLeftMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeRightMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeLeftMotor.set(INTAKE_SPEED);
        this.intakeRightMotor.set(INTAKE_SPEED);
    }

    // Sets the speed on the flywheels to launch the boulder
    public void setSpeedLaunch() {
        this.intakeLeftMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeRightMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeLeftMotor.set(LAUNCH_SPEED);
        this.intakeRightMotor.set(LAUNCH_SPEED);
    }

    // stops the flywheels
    public void setSpeedAbort() {
        this.intakeLeftMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeRightMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeLeftMotor.set(ZERO_SPEED);
        this.intakeRightMotor.set(ZERO_SPEED);
    }

    // moves the launcher, joystick angle determines speed
    public void moveLauncher() {
        if (!VisionState.getInstance().AutoAimEnabled) {
            aimMotor.changeControlMode(TalonControlMode.Speed);
            aimMotor.set(aimStick.getAxis(Joystick.AxisType.kY) * JOYSTICK_SCALE);
            if ((aimMotor.getSpeed() > 0 && launcherTopSwitch.get()) || (aimMotor.getSpeed() < 0 && launcherBottomSwitch.get())) {
                aimMotor.set(ZERO_SPEED);
            }
        } else {
            if (VisionState.getInstance().TargetY > LAUNCHER_MIN_HEIGHT || VisionState.getInstance().TargetY < LAUNCHER_MAX_HEIGHT) {
                SmartDashboard.putBoolean("Auto-aim target out of range", true);
            } else {
                aimMotor.changeControlMode(TalonControlMode.Position);
                aimMotor.set(VisionState.getInstance().TargetY);
            }
        }
    }

    // changes the launcher height by a small value
    // direction is either 1 or -1
    public void incrementLauncherHeight(int direction) {
        if (!VisionState.getInstance().AutoAimEnabled) {
            aimMotor.changeControlMode(TalonControlMode.Position);
            aimMotor.set(aimMotor.getPosition() + (AIM_MOTOR_INCREMENT * direction));
        }
    }

    public void activateLaunchServo() {
        launcherServo.set(LAUNCHER_SERVO_LAUNCH_POSITION);
    }

    public void retractLaunchServo() {
        launcherServo.set(LAUNCHER_SERVO_NEUTRAL_POSITION);
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
}
