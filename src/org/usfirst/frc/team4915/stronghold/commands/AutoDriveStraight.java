package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDriveStraight extends Command {

    private StringBuilder _sb = new StringBuilder();

    public double AUTOSPEED;      // ~3-4 ft/sec

    private double desiredDistanceTicks;

    private boolean isInitialized;

    private int initializeRetryCount;
    private final static int MAX_RETRIES = 50;

    public AutoDriveStraight(double desiredDistanceInches, double speed) {
        this.AUTOSPEED = speed;
        requires(Robot.driveTrain);
        desiredDistanceTicks = inchesToTicks(desiredDistanceInches);
    }

    private int inchesToTicks(double inches) {
        return (int)(inches * RobotMap.quadTicksPerInch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.init();
        Robot.intakeLauncher.aimMotor.disableControl();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	updateSB();

    	if (!isInitialized){
    		isInitialized = (RobotMap.leftMasterMotor.getEncPosition() == 0 &&
                             RobotMap.rightMasterMotor.getEncPosition() == 0);
    		initializeRetryCount++;
    		
    	} else if (desiredDistanceTicks != 0) {
            Robot.driveTrain.driveStraight(AUTOSPEED); //speed can be positive or negative
        } else {
            SmartDashboard.putString("AutoDriveStraight: ", "No Ticks");
        }
    }

    private void updateSB() {
    	_sb.setLength(0);
        _sb.append("Left motor ticks: ");
        _sb.append(RobotMap.leftMasterMotor.getEncPosition());
        _sb.append(", control mode: ");
        _sb.append(RobotMap.leftMasterMotor.getControlMode());
        _sb.append(", speed: ");
        _sb.append(RobotMap.leftMasterMotor.getSpeed());

        _sb.append(", Right motor ticks: ");
        _sb.append(RobotMap.rightMasterMotor.getEncPosition());
        _sb.append(", control mode: ");
        _sb.append(RobotMap.rightMasterMotor.getControlMode());
        _sb.append(", speed: ");
        _sb.append(RobotMap.rightMasterMotor.getSpeed());

        _sb.append(", initialized? " + isInitialized);
        _sb.append(", retry count " + initializeRetryCount);

        SmartDashboard.putString("AutoDriveStraight: ", _sb.toString());
    }


    // Make this return true when this Command no longer needs to run execute()
    // We are finished when either encoder has moved the requested autonomous distance (in ticks)
    // TODO: It may be wise to set a timer to time out in case our encoders are broken or our drivetrain is stuck?
    //       Maybe look for a few seconds of time in which no encoder changes happen? Then return true to stop us...
    protected boolean isFinished() {
    	if(!isInitialized) {
    		if(initializeRetryCount >= MAX_RETRIES) {
    			SmartDashboard.putString("AutoDriveStraight: ", "INITIALIZE FAILED, MAXED OUT RETRIES");
    			System.out.println("AutoDriveStraight: INITIALIZE FAILED, MAXED OUT RETRIES");
    			return true;
    		}
    		return false;
    	} else if ((Math.abs(RobotMap.leftMasterMotor.getEncPosition()) >= desiredDistanceTicks) ||
                (Math.abs(RobotMap.rightMasterMotor.getEncPosition()) >= desiredDistanceTicks)) {
            return true;
        }
        else {
            return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
