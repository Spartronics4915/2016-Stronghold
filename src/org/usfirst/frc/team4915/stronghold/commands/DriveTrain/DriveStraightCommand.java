package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

// This class is a test of whether the IMU can be relied upon to
// assist the driver (or autonomous) to drive straight over
// complex barriers like the ramparts.  
// Note: this is experimental and assumes that the command is
//  bound to a whilePressed joystick button.
public class DriveStraightCommand extends Command {
    private double m_kp = .3; // correction strength
    private double m_initialHeading;
    private boolean m_isRunning = false;

    public DriveStraightCommand() {
        requires(Robot.driveTrain);
        setInterruptible(true);
    }

    protected void initialize() {
        if (!m_isRunning){
            m_initialHeading = Robot.driveTrain.getCurrentHeading(); 
            m_isRunning = true;
        }
        RobotMap.leftMasterMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
        RobotMap.rightMasterMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
        System.out.println("DriveStraightCommand:init");
        Robot.driveTrain.init();
    }

    protected void execute() {
        Joystick joystickDrive = Robot.oi.getJoystickDrive();
        double deltaHeading = Robot.driveTrain.getCurrentHeading() - m_initialHeading;
        double joystickY = -joystickDrive.getAxis(Joystick.AxisType.kY);
        double outputMagnitude = .35;
                //Robot.driveTrain.scaleThrottle(joystickY);
        double curve = deltaHeading * - m_kp;
        System.out.println(deltaHeading);
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
        System.out.println("interrupted");
        m_isRunning = true;
        // called when button is released...
        end(); // XXX: we assume that ArcadeDrive will be re-introduced
    }
}
