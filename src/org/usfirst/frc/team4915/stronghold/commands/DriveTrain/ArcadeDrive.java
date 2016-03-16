package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {

    private double scaledThrottle;
    private double joystickX;
    private double joystickY;

    public ArcadeDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveTrain.init();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Joystick joystickDrive = Robot.oi.getJoystickDrive();
        this.joystickX = joystickDrive.getAxis(Joystick.AxisType.kX) * Robot.driveTrain.turnMultiplier;
        this.joystickY = joystickDrive.getAxis(Joystick.AxisType.kY) *-1;

        VisionState vs = VisionState.getInstance();

        if (vs == null || !vs.wantsControl()) {
            // endAutoTurn is harmless when not needed but required
            //  if the driver changes her mind after initiating auto-targeting..
            Robot.driveTrain.endAutoTurn();

            this.scaledThrottle = Robot.driveTrain.scaleThrottle(joystickDrive.getAxis(Joystick.AxisType.kThrottle));
            if ((Math.abs(this.joystickX) < 0.075) &&
                    (Math.abs(this.joystickY) < 0.075)) {
                Robot.driveTrain.stop();
            }
            else {
                Robot.driveTrain.arcadeDrive(joystickY * scaledThrottle, joystickX * scaledThrottle);
            }
        } else {
            Robot.driveTrain.trackVision();
        }
    }
    
    // scaleThrottle was moved to driveTrain so it can be shared...

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }

    // Call every 100th of a sec

}
