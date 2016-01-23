package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.IntakeLauncher;

public class IntakeBallCommand extends Command {

    IntakeLauncher intake_launcher = Robot.intakeLauncher;

    public IntakeBallCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        intake_launcher.setSpeedIntake();
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        // ends once the ball is in the basket and presses the limit switch
        return intake_launcher.boulderSwitch.get();
    }

    protected void end() {

    }

    protected void interrupted() {
        end();
    }

}
