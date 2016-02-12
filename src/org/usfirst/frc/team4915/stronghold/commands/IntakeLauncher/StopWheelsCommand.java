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
        Robot.intakeLauncher.setSpeedAbort();
    }

    @Override
    protected void execute() {
        SmartDashboard.putString("Intake Flywheels", "Right: " + Double.toString(Robot.intakeLauncher.getIntakeRightMotor().getSpeed()) + " Left: "
                + Double.toString(Robot.intakeLauncher.getIntakeLeftMotor().getSpeed()));
    }

    @Override
    protected boolean isFinished() {
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
