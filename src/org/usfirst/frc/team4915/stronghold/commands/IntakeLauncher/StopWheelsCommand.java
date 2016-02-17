package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StopWheelsCommand extends Command {

    // this command stops the intake flywheels
    public StopWheelsCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        Robot.intakeLauncher.setWheelsFinished(true);
        SmartDashboard.putString("Flywheels spinning ", "nowhere");
    }

    @Override
    protected void execute() {
    
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
