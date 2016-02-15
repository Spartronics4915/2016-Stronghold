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

    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedLaunch();
        SmartDashboard.putString("Intake Flywheels",
                "Right: " + Double.toString(Robot.intakeLauncher.getIntakeRightMotor().getBusVoltage()) + " Left: "
                        + Double.toString(Robot.intakeLauncher.getIntakeLeftMotor().getBusVoltage()));
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
        end();
    }
}
