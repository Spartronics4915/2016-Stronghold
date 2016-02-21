package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetSetPointFromSmartDashboardCommand extends Command {

    public SetSetPointFromSmartDashboardCommand() {
    
    }

    protected void initialize() {
        Robot.intakeLauncher.setSetPoint(SmartDashboard.getNumber("Launcher Set Point: "));
    }

    protected void execute() {
    
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    
    }

    protected void interrupted() {
    
    }
}
