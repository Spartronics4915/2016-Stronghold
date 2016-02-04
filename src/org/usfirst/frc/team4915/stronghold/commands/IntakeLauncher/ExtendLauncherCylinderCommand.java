package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class ExtendLauncherCylinderCommand extends Command {

    // this command extends the cylinder that pushes the boulder into the
    // flywheels
    public ExtendLauncherCylinderCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        Robot.intakeLauncher.punchBall();
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        SmartDashboard.putBoolean("Piston Extended", true);
        return true;
    }

    @Override
    protected void end() {
        isFinished();
    }

    @Override
    protected void interrupted() {
        
    }

}
