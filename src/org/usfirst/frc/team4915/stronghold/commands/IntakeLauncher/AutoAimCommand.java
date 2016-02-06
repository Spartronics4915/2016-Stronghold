package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AutoAimCommand extends Command {

    int position;
    
    public AutoAimCommand(int position) {
        requires(Robot.intakeLauncher);
        this.position = position;
    }

    protected void initialize() {
        Robot.intakeLauncher.AutoAim(position);
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
