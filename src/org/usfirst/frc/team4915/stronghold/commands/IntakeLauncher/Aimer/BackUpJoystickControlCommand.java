package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BackUpJoystickControlCommand extends Command {

    public BackUpJoystickControlCommand() {
        requires(Robot.intakeLauncher);
        
    }

    protected void initialize() {
        Robot.intakeLauncher.aimMotor.disableControl();
        Robot.intakeLauncher.aimMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        System.out.println("Back up Joystick Command");
        Robot.intakeLauncher.aimMotor.enableControl();
    }

    protected void execute() {
        Robot.intakeLauncher.backUpJoystickMethod();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.disableControl();
    }

    protected void interrupted() {
        System.out.println("Back Up Joystick Interrupt");
        end();
    }
}
