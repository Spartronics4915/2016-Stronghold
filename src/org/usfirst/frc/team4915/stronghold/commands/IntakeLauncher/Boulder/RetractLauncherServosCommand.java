package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Boulder;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RetractLauncherServosCommand extends Command {

    public RetractLauncherServosCommand() {
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.retractLauncherServos();
        System.out.println("Retract Launcher Servos Command");
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        System.out.println("End Retract Launcher Servos");
    }

    protected void interrupted() {
        end();
    }
}
