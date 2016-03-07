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
        System.out.println("stop wheels command");
        SmartDashboard.putString("Flywheels spinning ", "nowhere");
        Robot.intakeLauncher.stopWheels();
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
