package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

// This class is a test of whether the IMU can be relied upon to
// assist the driver (or autonomous) to drive straight over
// complex barriers like the ramparts.  
// Note: this is experimental and assumes that the command is
//  bound to a whilePressed joystick button.
public class DriveStraightCommand extends Command {
    private double m_kp = .03; // correction strength
    
    public DriveStraightCommand() {
	requires(Robot.driveTrain);
    }

    protected void initialize() {
	System.out.println("DriveStraightCommand:init");
	Robot.driveTrain.init();
    }

    protected void execute() {
	Joystick joystickDrive = Robot.oi.getJoystickDrive();
	double heading = Robot.driveTrain.getCurrentHeading();
	double joystickY = -joystickDrive.getAxis(Joystick.AxisType.kY);
	double outputMagnitude = Robot.driveTrain.scaleThrottle(joystickY);
	double curve = -heading * m_kp;
	Robot.driveTrain.drive(outputMagnitude, curve);
    }

    protected boolean isFinished() {
	return true; // since we're "whileHeld", isFinished is slippery
    }

    protected void end() {
	System.out.println("DriveStraightCommand:end");
    }

    @Override
    protected void interrupted() {
	// called when button is released...
	end(); // XXX: we assume that ArcadeDrive will be re-introduced
    }
}
