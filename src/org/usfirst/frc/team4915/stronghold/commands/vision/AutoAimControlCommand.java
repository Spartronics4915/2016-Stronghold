package org.usfirst.frc.team4915.stronghold.commands.vision;

import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.command.Command;

public class AutoAimControlCommand extends Command {

    private boolean m_toggleEnable = false;
    private boolean m_toggleTarget = false;

    public AutoAimControlCommand(boolean toggleEnable, boolean toggleTarget) {
        this.m_toggleEnable = toggleEnable;
        this.m_toggleTarget = toggleTarget;
    }

    @Override
    protected void initialize() {
        // Do nothing
    }

    @Override
    protected void execute() {
        VisionState vs = VisionState.getInstance();
        vs.toggleAimState(this.m_toggleEnable, this.m_toggleTarget);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        // Do nothing
    }

    @Override
    protected void interrupted() {
        // nothing to interrupt
    }
}
