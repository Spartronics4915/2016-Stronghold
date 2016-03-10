package org.usfirst.frc.team4915.stronghold.subsystems;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.ArcadeDrive;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

    public final static double DEFAULT_SPEED_MAX_OUTPUT = 100.0;         // 100.0 == ~13 ft/sec interpolated from observations
    public final static double MAXIMUM_SPEED_MAX_OUTPUT = 150.0;         // 150.0 == ~20 ft/sec interpolated from observations

    public static RobotDrive robotDrive =
            new RobotDrive(RobotMap.leftMasterMotor, RobotMap.rightMasterMotor);

    private double maxSpeed = 0;
    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        System.out.println("ArcadeDrive getControlModes: " +
        			RobotMap.leftMasterMotor.getControlMode() + "  " +
        			RobotMap.rightMasterMotor.getControlMode());
        setDefaultCommand(new ArcadeDrive());

        robotDrive.setSafetyEnabled(true);
        robotDrive.stopMotor();
    }

    public void arcadeDrive(double driveYstick, double driveXstick) {
        System.out.println("Arcade drive y: " + driveYstick + ", x " + driveXstick);
        robotDrive.arcadeDrive(driveYstick, driveXstick);
        System.out.println("Arcade drive get speed = " + RobotMap.leftMasterMotor.getSpeed());
    }

    public void resetEncoders(){
        RobotMap.leftMasterMotor.setEncPosition(0);
        RobotMap.rightMasterMotor.setEncPosition(0);
    }

    /*
     * Methods to get/set maximum top speed for our robot
     */
    public double getMaxOutput() {

        if (maxSpeed == 0) {   /* not initialized, yet */
            return DEFAULT_SPEED_MAX_OUTPUT;
        }
        else {
            return maxSpeed;
        }
    }


    public void setMaxOutput(double maxOutput) {

        if (maxOutput > MAXIMUM_SPEED_MAX_OUTPUT) {
            maxSpeed = MAXIMUM_SPEED_MAX_OUTPUT;
        }
        else {
            maxSpeed = maxOutput;
        }

        robotDrive.setMaxOutput(maxSpeed);
    }

    public void stop() {
        robotDrive.stopMotor();
    }

    public void driveStraight(double speed) {
        RobotMap.leftMasterMotor.set(speed);
        RobotMap.rightMasterMotor.set(-speed);
    }

    public void turn(boolean left, double speed) {
        if (left) {
            RobotMap.leftMasterMotor.set(-0.5*speed);
            RobotMap.rightMasterMotor.set(-0.5*speed);
        } else {
            System.out.println("Turn right: " + speed);
            RobotMap.leftMasterMotor.set(0.5*speed);
            RobotMap.rightMasterMotor.set(0.5*speed);
        }
    }

    // autoturn is just a gentler version of (joystick) turn.
    public void autoturn(boolean left) {
    	//System.out.println("autoturning left: " + left);
        if (left) {
            robotDrive.arcadeDrive(0, -.55);
        } else {
            robotDrive.arcadeDrive(0, .55);
        }
    }

    public void turnToward(double target) {
        double heading = RobotMap.imu.getNormalizedHeading();
        double delta =  target - heading;
        /*System.out.println("target: " + target +
                           " heading: " + heading +
                           " delta: " + delta);*/
        SmartDashboard.putNumber("Vision Delta", delta);
        if (Math.abs(delta) < 5.0) {
            this.stop();
        } else {
            this.autoturn(delta > 0.0 /*turn left*/ );
        }
    }
}
