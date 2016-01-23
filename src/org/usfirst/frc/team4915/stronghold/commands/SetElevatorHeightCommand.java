package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.Intake_Launcher;

public class SetElevatorHeightCommand extends Command {

    Intake_Launcher Flywheels = Robot.intake_launcher;

    private double height;

    public SetElevatorHeightCommand(double height) {
        requires(Robot.intake_launcher);
        this.height = height;
    }

    protected void initialize() {
        Flywheels.setElevatorHeight(height);
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
