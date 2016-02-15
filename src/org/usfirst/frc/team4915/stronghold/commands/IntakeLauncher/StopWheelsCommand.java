package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class StopWheelsCommand extends Command {

    // this command stops the intake flywheels
    public StopWheelsCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        Robot.intakeLauncher.setWheelsFinished(true);
    }

    @Override
    protected void execute() {
        SmartDashboard.putString("Flywheels spinning ", "nowhere");
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
