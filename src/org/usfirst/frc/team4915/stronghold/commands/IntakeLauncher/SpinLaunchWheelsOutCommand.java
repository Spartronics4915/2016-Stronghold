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
        Robot.intakeLauncher.setWheelsFinished(false);
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedLaunch();
        SmartDashboard.putString("Flywheels spinning ", "outward");
    }

    @Override
    protected boolean isFinished() {
        // Ends once stopWheelsCommand is used
        return Robot.intakeLauncher.areWheelsFinished();
    }

    @Override
    protected void end() {
        SmartDashboard.putString("Boulder in Basket: ", "No");
    }

    @Override
    protected void interrupted() {
        end();
    }
}
