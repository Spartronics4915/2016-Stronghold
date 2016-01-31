package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.IntakeLauncher;

public class IntakeBallCommand extends Command {

    IntakeLauncher intake_launcher = Robot.intakeLauncher;

    // this command spins the intake flywheels inward to retrieve the ball
    public IntakeBallCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        this.intake_launcher.setSpeedIntake();
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        // ends once the ball is in the basket and presses the limit switch
        return this.intake_launcher.boulderSwitch.get();
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        end();
    }

}
