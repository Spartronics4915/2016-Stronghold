package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class SpinLaunchWheelsOutCommand extends Command {

    // this command spins the launch wheels outwards so they will launch the
    // ball
    public SpinLaunchWheelsOutCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        Robot.intakeLauncher.setSpeedLaunch();
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

    }

    @Override
    protected void interrupted() {

    }
}
