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
    public double joystickThrottle;

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

    public double modifyThrottle() {
        double modifiedThrottle = 0.40 * (-1 * Robot.oi.getJoystickDrive().getAxis(Joystick.AxisType.kThrottle)) + 0.60;
        if (modifiedThrottle != this.joystickThrottle) {
            this.joystickThrottle = modifiedThrottle;
            setMaxOutput(modifiedThrottle);
        }
        return modifiedThrottle;
    }

    private void setMaxOutput(double topSpeed) {
        SmartDashboard.putNumber("Drivetrain Throttle: ", topSpeed);
        robotDrive.setMaxOutput(topSpeed);
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
    	System.out.println("Autoturning left: " + left);
        if (left) {
            robotDrive.arcadeDrive(0, -1);
            //System.out.println("left");
        } else {
            robotDrive.arcadeDrive(0, 1);
            //System.out.println(+"right");
        }
    }

    public void turnToward(double targetHeading) {
    	boolean turnLeft;
    	//targetHeading = (targetHeading) % 360;
    	double currentHeading = RobotMap.imu.getHeading();
    	//int currentTurns = RobotMap.imu.getTurns();
    	//currentHeading = currentHeading - 360*currentTurns;
    	//currentHeading = (currentHeading+360) % 360;
        double deltaHeading =  targetHeading - currentHeading;
        System.out.println("current: " + currentHeading);
    	System.out.println("target: " + targetHeading);
    	System.out.println("delta: " + deltaHeading);
      	//System.out.println("deltaHeading: " + deltaHeading);
        if (Math.abs(deltaHeading) < 5.0) {
        	System.out.println("Stopping!");
        	/*System.out.println("current: " + currentHeading);
        	System.out.println("target: " + targetHeading);
        	System.out.println("delta: " + deltaHeading);*/
        	//VisionState.getInstance().DriveLockedOnTarget = true;
            this.stop();
            return;
        }
        else if(deltaHeading < 0) {
        	turnLeft = true;
        }
        else {
        	turnLeft = false;
        	// System.out.println(deltaHeading);
        	SmartDashboard.putNumber("Drivetrain DeltaHeading", deltaHeading);
            //this.autoturn(deltaHeading < 0.0);
        }
        
        if(Math.abs(deltaHeading) > 180) {
        	turnLeft = !turnLeft;
        }
       	//System.out.println(deltaHeading);
       	//System.out.println(turnLeft);
        this.autoturn(turnLeft);

    }
}
