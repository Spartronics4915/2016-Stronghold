package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;

public class AutoRotateDegrees extends Command {

    private double startingGyroValue;
    RobotDrive robotDrive = DriveTrain.robotDrive;
    private boolean goLeft;
    double robotAngle;

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
        startingGyroValue = Robot.driveTrain.trackGyro();
    }

    @Override
    protected void execute() {
        Robot.driveTrain.turn(goLeft);
        SmartDashboard.putNumber("Robot Angle", robotAngle);
    }

    @Override
    protected boolean isFinished() {
        double gyroDelta = Math.abs(Robot.driveTrain.trackGyro() - startingGyroValue);
        System.out.println("Current Gyro:" + Robot.driveTrain.trackGyro() + "\tDelta: " + gyroDelta + "\tDesired robot angle" + robotAngle);
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
