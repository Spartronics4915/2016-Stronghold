package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;
import org.usfirst.frc.team4915.stronghold.utils.BNO055;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;

public class AutoRotateDegrees extends Command {

    private double startingGyroValue;
    RobotDrive robotDrive = DriveTrain.robotDrive;
    private boolean goLeft;
    double robotAngle;
    public final static double AUTOSPEED = 30.0;
    // creates new IMU variable
    BNO055 imu = RobotMap.imu;

    // autonomous rotate command
    public AutoRotateDegrees(boolean left, double robotAngle) {
        requires(Robot.driveTrain);
        goLeft = left;
        this.robotAngle = robotAngle;
        System.out.println("Auto Rotate degrees "+robotAngle);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.setMaxOutput(Robot.driveTrain.getMaxOutput());
        this.startingGyroValue = imu.getNormalizedHeading();
    }

    @Override
    public void execute() {
    	System.out.println("Executing the rotate degrees");
        Robot.driveTrain.turn(goLeft, AUTOSPEED);
    }

    @Override
    protected boolean isFinished() {
    	// TODO: will this logic work if robotAngle is negative?
    	double gyroDelta = 0;
    	double heading = imu.getNormalizedHeading();
    	gyroDelta = Math.abs(heading - startingGyroValue); 
        System.out.println("Current IMU heading:" + imu.getNormalizedHeading() + "\tDelta: " + gyroDelta + "\tDesired robot angle" + robotAngle);
        return gyroDelta >= (robotAngle);
    }

    @Override
    protected void end() {
        robotDrive.stopMotor();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
