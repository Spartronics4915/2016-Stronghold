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
    //creates new IMU variable
    BNO055 imuEuler = RobotMap.imuEuler;

    // autonomous rotate command
    public AutoRotateDegrees(boolean left, double robotAngle) {
        System.out.println("Auto Rotate degrees");
        requires(Robot.driveTrain);
        goLeft = left;
        this.robotAngle = robotAngle;
        System.out.println(robotAngle);
    }

    @Override
    protected void initialize() {
        robotDrive.setMaxOutput(1.0);
        startingGyroValue = imuEuler.getHeading();
    }

    @Override
    public void execute() {

        Robot.driveTrain.turn(goLeft);
        SmartDashboard.putNumber("Robot Angle", robotAngle);
    }

    @Override
    protected boolean isFinished() {
        double gyroDelta = Math.abs(imuEuler.getHeading() - startingGyroValue);
        System.out.println("Current IMU heading:" + imuEuler.getHeading() + "\tDelta: " + gyroDelta + "\tDesired robot angle" + robotAngle);
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
