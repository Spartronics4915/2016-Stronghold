package org.usfirst.frc.team4915.stronghold.subsystems;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.ArcadeDrive;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

    // Constructor for SpeedControllers: frontLeftMotor, rearLeftMotor,
    // frontRightMotor, rearRightMotor
    public static RobotDrive robotDrive =
            new RobotDrive(RobotMap.leftBackMotor, RobotMap.rightBackMotor);

    private double lastTopSpeed=-1.;

    // motors
    public static List<CANTalon> motors =
            Arrays.asList(RobotMap.leftFrontMotor, RobotMap.leftBackMotor, RobotMap.rightFrontMotor, RobotMap.rightBackMotor);

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        System.out.println("ArcadeDrive getControlModes: " +
        			RobotMap.leftFrontMotor.getControlMode() + "  " +
        			RobotMap.rightFrontMotor.getControlMode() + "  " +
        			RobotMap.leftBackMotor.getControlMode() + "  " +
        			RobotMap.rightBackMotor.getControlMode());
        setDefaultCommand(new ArcadeDrive());

        robotDrive.setSafetyEnabled(true);
        // inverting motors
        robotDrive.setInvertedMotor(MotorType.kRearLeft, true);

        robotDrive.setInvertedMotor(MotorType.kRearRight, true);
        robotDrive.stopMotor();
    }

    public void applyThrottle() {
        double newThrottle = 0.40 * (-1 * Robot.oi.getJoystickDrive().getAxis(Joystick.AxisType.kThrottle)) + 0.60;
        setMaxOutput(newThrottle);
    }

    public void ignoreThrottle() {
        setMaxOutput(1.0);
    }

    private void setMaxOutput(double topSpeed) {
        if (topSpeed != this.lastTopSpeed) {
        	SmartDashboard.putNumber("Drivetrain Throttle: ", topSpeed);
        	robotDrive.setMaxOutput(topSpeed);
        	this.lastTopSpeed = topSpeed;
        }
    }

    public void arcadeDrive(Joystick stick) {

        robotDrive.arcadeDrive(stick);
        // checking to see the encoder values
        // this can be removed later. Used to debug
        if (motors.size() > 0) {
            for (int i = 0; i < motors.size(); i++) {
                SmartDashboard.putNumber("Drivetrain Encoder " + i,
                					motors.get(i).getEncPosition());
            }
        }
    }

    public void stop() {
        robotDrive.stopMotor();
    }

    public void driveStraight(double speed) {
        robotDrive.arcadeDrive(speed, 0);
    }

    public void turn(boolean left) {
        if (left) {
            robotDrive.arcadeDrive(0, -.7);
        } else {
            robotDrive.arcadeDrive(0, .7);
        }
    }

    // autoturn is just a gentler version of (joystick) turn.
    public void autoturn(boolean left) {
        if (left) {
            robotDrive.arcadeDrive(0, -.55);
        } else {
            robotDrive.arcadeDrive(0, .55);
        }
    }

    public void turnToward(double target) {
        double heading = RobotMap.imu.getNormalizedHeading();
        double delta =  target - heading;
        System.out.println("target: " + target +
                           " heading: " + heading +
                           " delta: " + delta);
        SmartDashboard.putNumber("Vision Delta", delta);
        if (Math.abs(delta) < 5.0) {
            this.stop();
        } else {
            this.autoturn(delta > 0.0 /*turn left*/ );
        }
    }
}
