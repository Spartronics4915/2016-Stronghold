package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeBallCommand extends Command {

    // this command spins the intake flywheels inward to retrieve the ball
    public IntakeBallCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        System.out.println("Intake Ball Command");
        setTimeout(10); //TODO finalize time
        Robot.intakeLauncher.retractLauncherServos();
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedIntake();
    }

    @Override
    protected boolean isFinished() {
        // ends once the ball is in the basket and presses the limit switch or
        // the stop wheels button is pressed
        return (Robot.intakeLauncher.boulderSwitch.get() || isTimedOut());
    }

    @Override
    protected void end() {
        Robot.intakeLauncher.stopWheels();
        //Robot.intakeLauncher.launcherSetNeutralPosition();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
