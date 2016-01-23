package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.Intake_Launcher;

public class IntakeBallCommand extends Command {

    Intake_Launcher Flywheels = Robot.intake_launcher;

    public IntakeBallCommand() {
        requires(Robot.intake_launcher);
    }

    protected void initialize() {
        Flywheels.setSpeedIntake();
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }

    protected void interrupted() {
        end();
    }

}
