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
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.SetLauncherHeightCommand;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative speed indicates a wheel spinning inwards and positive speed
    // indicates a wheel spinning outwards.
    // Numbers are not correct
    private static final double INTAKE_SPEED = -1.0;
    private static final double LAUNCH_SPEED = 1.0;
    private static final double ZERO_SPEED = 0.0;
    private static final double JOYSTICK_SCALE = 1.0; // TODO
    private static final double LAUNCHER_SERVO_NEUTRAL_POSITION = 0.0; // TODO
    private static final double LAUNCHER_SERVO_LAUNCH_POSITION = 1.0; // TODO
    private static final double AIM_MOTOR_INCREMENT = 0; // TODO

    private boolean autoAim = false;

    public Joystick aimStick = Robot.oi.aimStick;

    public SmartDashboard smartDashboard = Robot.smartDashboard;

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

    public Servo launcherServo = RobotMap.launcherServo;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SetLauncherHeightCommand(aimStick.getAxis(Joystick.AxisType.kY)));
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
    public void changeLauncherHeight(double speed) {
        if (!autoAim) {
            if (!launcherBottomSwitch.get() && !launcherTopSwitch.get()) {
                aimMotor.changeControlMode(TalonControlMode.Speed);
                aimMotor.set(speed * JOYSTICK_SCALE);
            } else {
                aimMotor.set(ZERO_SPEED);
            }
        }
    }

    // changes the launcher height by a small value
    // direction is either 1 or -1
    public void incrementLauncherHeight(int direction) {
        aimMotor.changeControlMode(TalonControlMode.Position);
        aimMotor.set(aimMotor.getPosition() + (AIM_MOTOR_INCREMENT * direction));
    }

    public void activateLaunchServo() {
        launcherServo.set(LAUNCHER_SERVO_LAUNCH_POSITION);
    }

    public void retractLaunchServo() {
        launcherServo.set(LAUNCHER_SERVO_NEUTRAL_POSITION);
    }

    // takes a parameter generated from auto aiming and sets the launcher to
    // that parameter
    // if auto aim is on already turns it off
    public void AutoAim() {
        if (autoAim) {
            autoAim = false;
        } else {
            autoAim = true;
        }
    }

    public void setLauncherHeightCommand(double position) {
        aimMotor.changeControlMode(TalonControlMode.Position);
        aimMotor.set(position);
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
}
