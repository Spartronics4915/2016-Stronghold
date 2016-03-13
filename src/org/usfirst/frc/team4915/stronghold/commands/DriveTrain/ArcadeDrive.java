package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {

    private double scaledThrottle;
    private double joystickX;
    private double joystickY;
    private static final double MIN_THROTTLE_SCALE = 0.5;


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
        Joystick joystickDrive= Robot.oi.getJoystickDrive();
        this.joystickX = joystickDrive.getAxis(Joystick.AxisType.kX) * Robot.driveTrain.turnMultiplier;
        this.joystickY = joystickDrive.getAxis(Joystick.AxisType.kY) *-1;

        VisionState vs = VisionState.getInstance();

        if (vs == null || !vs.wantsControl()) {
            // endAutoTurn is harmless when not needed but required
            //  if the driver changes her mind after initiating auto-targeting..
            Robot.driveTrain.endAutoTurn();

            this.scaledThrottle = scaleThrottle(joystickDrive.getAxis(Joystick.AxisType.kThrottle));
            if ((Math.abs(this.joystickX) < 0.075) &&
                    (Math.abs(this.joystickY) < 0.075)) {
                Robot.driveTrain.stop();
            }
            else {
                Robot.driveTrain.arcadeDrive(joystickY * scaledThrottle, joystickX * scaledThrottle);
            }
        } else {
        	if(vs.DriveLockedOnTarget) {
        		// wait for launcher to shoot and exit auto mode or toggle AutoAim
                Robot.driveTrain.stop(); // needed to keep driveTrain alive
        	}
            else {
                if (!Robot.driveTrain.isAutoTurning()) {
                    double h = Robot.driveTrain.getCurrentHeading();
                    double target = vs.getTargetHeading(h);
                    Robot.driveTrain.startAutoTurn(target);
                } else if (Robot.driveTrain.isAutoTurnFinished()) {
                    Robot.driveTrain.endAutoTurn();
                    vs.DriveLockedOnTarget = true;
                } // else allow auto-turn to continue
        	}
        }
    }

    /*
     * Returns a scaled value between MIN_THROTTLE_SCALE and 1.0
     * MIN_THROTTLE_SCALE must be set to the lowest useful scale value through experimentation
     * Scale the joystick values by throttle before passing to the driveTrain
     *     +1=bottom position; -1=top position
     */
    private double scaleThrottle(double raw_throttle_value) {
        /**
         * Throttle returns a double in the range of -1 to 1. We would like to change that to a range of MIN_THROTTLE_SCALE to 1.
         * First, multiply the raw throttle value by -1 to reverse it (makes "up" maximum (1), and "down" minimum (-1))
         * Then, add 1 to make the range 0-2 rather than -1 to +1
         * Then multiply by ((1-MIN_THROTTLE_SCALE)/2) to change the range to 0-(1-MIN_THROTTLE_SCALE)
         * Finally add MIN_THROTTLE_SCALE to change the range to MIN_THROTTLE_SCALE to 1
         *
         * Check the results are in the range of MIN_THROTTLE_SCALE to 1, and clip it in case the math went horribly wrong.
         */
        double scale = ((raw_throttle_value * -1) + 1) * ((1-MIN_THROTTLE_SCALE) / 2) + MIN_THROTTLE_SCALE;

        if (scale < MIN_THROTTLE_SCALE) {
            // Somehow our math was wrong. Our value was too low, so force it to the minimum
            scale = MIN_THROTTLE_SCALE;
        }
        else if (scale > 1) {
            // Somehow our math was wrong. Our value was too high, so force it to the maximum
            scale = 1.0;
        }
        return scale;
        
    }

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
