package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.RobotMap;

public class LightSwitchCommand extends Command {

    private boolean enabled = false;

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        RobotMap.PHOTONIC_CANNON.set(enabled = !enabled);
        System.out.println("Light is now " + enabled);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
