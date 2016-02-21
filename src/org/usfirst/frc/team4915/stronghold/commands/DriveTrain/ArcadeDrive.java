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
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        this.joystickDrive = Robot.oi.getJoystickDrive();
        this.joystickX = this.joystickDrive.getAxis(Joystick.AxisType.kX);
        this.joystickY = this.joystickDrive.getAxis(Joystick.AxisType.kY);

        if (ModuleManager.GYRO_MODULE_ON) {
            Robot.driveTrain.trackGyro();
        }

        VisionState vs = VisionState.getInstance();
        double heading;
        if (ModuleManager.IMU_MODULE_ON) {
            heading = RobotMap.imu.getHeading();
            SmartDashboard.putNumber("IMU heading", (int)(heading+.5));
            vs.updateIMUHeading(heading);
        } else {
            heading = 0.0;
        }

        Robot.driveTrain.joystickThrottle = Robot.driveTrain.modifyThrottle();

        if (vs.wantsControl()) {
            if (vs.RelativeTargetingMode) {
                if (Math.abs(vs.TargetX) < 3) {
                    Robot.driveTrain.stop(); // close enough
                } else {
                    Robot.driveTrain.autoturn(vs.TargetX < 0);
                }
            } else {
                /* absolute autotargeting */
                Robot.driveTrain.turnToward(vs.TargetX);
            }
        } else {
            if ((Math.abs(this.joystickX) < 0.075) && 
                (Math.abs(this.joystickY) < 0.075)) {
                Robot.driveTrain.stop();
            } else {
                Robot.driveTrain.arcadeDrive(this.joystickDrive);
            }
            SmartDashboard.putNumber("Drive joystick X position", this.joystickX);
            SmartDashboard.putNumber("Drive joystick Y position", this.joystickY);
            
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
