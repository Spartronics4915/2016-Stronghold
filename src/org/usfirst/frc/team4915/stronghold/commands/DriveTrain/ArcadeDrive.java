package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import java.util.List;

import org.usfirst.frc.team4915.stronghold.ModuleManager;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.utils.BNO055;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDrive extends Command {

    public Joystick joystickDrive;
    private double joystickX;
    private double joystickY;
    public static List<CANTalon> motors = Robot.driveTrain.motors;
    public double[] oldVelocity = new double[3];
    public double[] distTraveled = new double[3];
    public double distFromOrigin;

    public ArcadeDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).setEncPosition(0);
        }
        /*
         * motors.get(1).setEncPosition(0); System.out.println("motor " + 1 +
         * " reset to " + motors.get(1).getEncPosition());
         * motors.get(3).setEncPosition(0); System.out.println("motor " + 3 +
         * " reset to " + motors.get(3).getEncPosition());
         */
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        this.joystickDrive = Robot.oi.getJoystickDrive();
        this.joystickX = this.joystickDrive.getAxis(Joystick.AxisType.kX);
        this.joystickY = this.joystickDrive.getAxis(Joystick.AxisType.kY);

        VisionState vs = null;
        if (ModuleManager.VISION_MODULE_ON) {
            vs = VisionState.getInstance();
        }

        if (vs != null && vs.wantsControl()) {
            Robot.driveTrain.ignoreThrottle();
        	if(!vs.DriveLockedOnTarget) {
	            if (vs.RelativeTargetingMode == 1) {

	                if (Math.abs(vs.TargetX) < 3) {
	                    Robot.driveTrain.stop(); // close enough
	                }
	                else {
	                    Robot.driveTrain.autoturn(vs.TargetX < 0);
	                }
	            } else {
	                /* absolute autotargeting */
	                Robot.driveTrain.turnToward(vs.TargetX);
	            }
        	}
            else {
                // wait for launcher to shoot and exit auto mode
                // or toggle AutoAim
                Robot.driveTrain.stop(); // needed to keep driveTrain alive
            }
        }
        else {
            Robot.driveTrain.applyThrottle();
            if ((Math.abs(this.joystickX) < 0.075) &&
                    (Math.abs(this.joystickY) < 0.075)) {
                Robot.driveTrain.stop();
            }
            else {
                Robot.driveTrain.arcadeDrive(this.joystickDrive);
            }
            SmartDashboard.putNumber("Drivetrain X", this.joystickX);
            SmartDashboard.putNumber("Drivetrain Y", this.joystickY);
        }
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
