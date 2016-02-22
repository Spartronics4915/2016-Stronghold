package org.usfirst.frc.team4915.stronghold.subsystems;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team4915.stronghold.ModuleManager;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.ArcadeDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

    // Constructor for SpeedControllers: frontLeftMotor, rearLeftMotor,
    // frontRightMotor, rearRightMotor
    public static RobotDrive robotDrive =
            new RobotDrive(RobotMap.leftBackMotor, RobotMap.rightBackMotor);
    public double joystickThrottle;

    // For Gyro
    public static Gyro gyro = RobotMap.gyro;
    public double deltaGyro = 0;
    public double gyroHeading = 0;
    public double startingAngle = 0;

    // motors
    public static List<CANTalon> motors =
            Arrays.asList(RobotMap.leftFrontMotor, RobotMap.leftBackMotor, RobotMap.rightFrontMotor, RobotMap.rightBackMotor);

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        System.out.println("INFO: Initializing the ArcadeDrive");

        setDefaultCommand(new ArcadeDrive());
        /*
         * FIXME: robotDrive static field access instead of:
         * robotDrive.setSafetyEnabled(true); do (remove this):
         * robotDrive.setSafetyEnabled(true)
         */
        robotDrive.setSafetyEnabled(true);
        // inverting motors
        robotDrive.setInvertedMotor(MotorType.kRearLeft, true);

        robotDrive.setInvertedMotor(MotorType.kRearRight, true);
        robotDrive.stopMotor();
        // checking to see the encoder values
        // this can be removed later. Used to debug
        if (motors.size() > 0) {
            for (int i = 0; i < motors.size(); i++) {
            }
        }
    }

    public double modifyThrottle() {
        // 255 is the max number on the throttle
        double modifiedThrottle = 0.40 * (-1 * Robot.oi.getJoystickDrive().getAxis(Joystick.AxisType.kThrottle)) + 0.60;
        if (modifiedThrottle != this.joystickThrottle) {
            SmartDashboard.putNumber("Throttle: ", modifiedThrottle);
        }
        setMaxOutput(modifiedThrottle);
        return modifiedThrottle;
    }

    private void setMaxOutput(double topSpeed) {
        robotDrive.setMaxOutput(topSpeed);
    }

    public void arcadeDrive(Joystick stick) {

        robotDrive.arcadeDrive(stick);
        // checking to see the encoder values
        // this can be removed later. Used to debug
        if (motors.size() > 0) {
            for (int i = 0; i < motors.size(); i++) {
                SmartDashboard.putNumber("Encoder Value for Motor" + i, motors.get(i).getEncPosition());
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
            robotDrive.arcadeDrive(0, -.2);
        } else {
            robotDrive.arcadeDrive(0, .2);
        }
    }

    public void turnToward(double heading) {
        double deltaHeading = RobotMap.imu.getHeading() - heading;
        if (Math.abs(deltaHeading) < 1.0)
            this.stop();
        else
            this.autoturn(deltaHeading < 0.0);
    }

}
