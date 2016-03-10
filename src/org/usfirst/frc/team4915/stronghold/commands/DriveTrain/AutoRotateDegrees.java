package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.utils.BNO055;

import edu.wpi.first.wpilibj.command.Command;

public class AutoRotateDegrees extends Command {

    public final static double AUTOSPEED = 30.0;

    private double startingGyroValue;
    private boolean goLeft;
    private double robotAngle;
    private BNO055 imu = RobotMap.imu;

    // autonomous rotate command

    public AutoRotateDegrees(double robotAngle) {
        System.out.println("Auto Rotate degrees "+robotAngle);
        requires(Robot.driveTrain);
        this.robotAngle = robotAngle;
    }

    @Override
    protected void initialize() {
        this.startingGyroValue = imu.getNormalizedHeading();
        Robot.driveTrain.init();
        Robot.driveTrain.startAutoTurn(this.robotAngle);
    }

    @Override
    public void execute() {
        // we're under pid control... so nothing to do here..
        //  drivetrain's m_turnPID invokes turn periodically.
        //  also: Robot.periodicStatusUpdate updates IMU
    }

    @Override
    protected boolean isFinished() {
    	// TODO: will this logic work if robotAngle is negative?
    	return Robot.driveTrain.isAutoTurnFinished();
    }

    @Override
    protected void end() {
        Robot.driveTrain.endAutoTurn();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
