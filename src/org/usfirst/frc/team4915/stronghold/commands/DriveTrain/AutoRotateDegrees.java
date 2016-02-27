package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;
import org.usfirst.frc.team4915.stronghold.utils.BNO055;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoRotateDegrees extends Command {

    private double startingGyroValue;
    RobotDrive robotDrive = DriveTrain.robotDrive;
    private boolean goLeft;
    double robotAngle;
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
        robotDrive.setMaxOutput(1.0); // max the throttle
        this.startingGyroValue = imu.getHeading();
    }

    @Override
    public void execute() {
    	System.out.println("Executing the rotate degrees");
        Robot.driveTrain.turn(goLeft);
    }

    @Override
    protected boolean isFinished() {
    	// TODO: will this logic work if robotAngle is negative?
    	double gyroDelta = 0;
    	double heading = imu.getHeading();
    	if (heading <= 180)
    		gyroDelta = Math.abs(heading - startingGyroValue);
    	else 
    		gyroDelta = Math.abs((360 - heading) - startingGyroValue);
        System.out.println("Current IMU heading:" + imu.getHeading() + "\tDelta: " + gyroDelta + "\tDesired robot angle" + robotAngle);
        return gyroDelta >= robotAngle;
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
