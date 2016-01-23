package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4915.stronghold.RobotMap;

public class Intake_Launcher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative speed indicates a wheel spinning inwards and positive speed
    // indicates a wheel spinning outwards.
    public static final double INTAKE_SPEED = -0.5;
    public static final double LAUNCH_SPEED = 1.0;
    public static final double ZERO_SPEED = 0.0;
    public static final double ELEVATOR_SPEED = 0.25;
    public static final double ELEVATOR_MAX_HEIGHT = 0.0;
    public static final double ELEVATOR_MIN_HEIGHT = 0.0;

    // left and right are determined when standing behind the robot

    // These motors collect and shoot the ball
    public CANTalon intakeLeftMotor = RobotMap.intakeLeftMotor;
    public CANTalon intakeRightMotor = RobotMap.intakeRightMotor;

    // These motors elevate the launcher for shooting
    public CANTalon launcherLeftMotor = RobotMap.launcherLeftMotor;
    public CANTalon launcherRightMotor = RobotMap.launcherRightMotor;

    protected void initDefaultCommand() {

    }

    // Sets the speed on the flywheels to suck in the boulder
    public void setSpeedIntake() {
        intakeLeftMotor.changeControlMode(TalonControlMode.Speed);
        intakeRightMotor.changeControlMode(TalonControlMode.Speed);
        intakeLeftMotor.set(INTAKE_SPEED);
        intakeRightMotor.set(INTAKE_SPEED);
    }

    // Sets the speed on the flywheels to launch the boulder
    public void setSpeedLaunch() {
        intakeLeftMotor.changeControlMode(TalonControlMode.Speed);
        intakeRightMotor.changeControlMode(TalonControlMode.Speed);
        intakeLeftMotor.set(LAUNCH_SPEED);
        intakeRightMotor.set(LAUNCH_SPEED);
    }

    // Stops flywheels
    public void setSpeedAbort() {
        intakeLeftMotor.changeControlMode(TalonControlMode.Speed);
        intakeRightMotor.changeControlMode(TalonControlMode.Speed);
        intakeLeftMotor.set(ZERO_SPEED);
        intakeRightMotor.set(ZERO_SPEED);
    }

    // Sets the angle of the elevation motors
    public void setElevatorHeight(double height) {
        launcherRightMotor.changeControlMode(TalonControlMode.Position);
        launcherLeftMotor.changeControlMode(TalonControlMode.Position);
        launcherRightMotor.set(height);
        launcherLeftMotor.set(height);
    }

    // Might be a good idea to add a way to control the elevator with a joystick
    // in the future

}
