package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;

public class AutoRotateDegrees extends Command {

    RobotDrive robotDrive = DriveTrain.robotDrive;
    private boolean goLeft;
    double robotAngle;

    // autonomous rotate command
    public AutoRotateDegrees(boolean left, double robotAngle) {
        requires(Robot.driveTrain);
        goLeft = left;
        this.robotAngle = robotAngle;
        System.out.println(robotAngle);

    }

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub
        Robot.driveTrain.calibrateGyro();
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        Robot.driveTrain.turn(goLeft);
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return (Math.abs(Robot.driveTrain.trackGyro()) >= robotAngle);
    }

    @Override
    protected void end() {
        // TODO Auto-generated method stub
        robotDrive.stopMotor();
    }

    @Override
    protected void interrupted() {
        // TODO Auto-generated method stub
        end();

    }

}
