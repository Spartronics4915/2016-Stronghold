package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class SpinIntakeWheelsOutCommand extends Command {

    // this command spins the launch wheels outwards so they will launch the
    // ball
    double startTime;

    public SpinIntakeWheelsOutCommand() {
        setTimeout(10); // TODO
        requires(Robot.intakeLauncher);
        SmartDashboard.putString("Flywheels spinning ", "outward");
    }

    @Override
    protected void initialize() {
        this.startTime = (double) System.currentTimeMillis();
        SmartDashboard.putNumber("Time Since Wheels Start: ", 0.0);
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedLaunch();
        Robot.intakeLauncher.aimLauncher(); // this command interrupts
                                            // AimLauncherCommand so we make
                                            // sure the launcher always moves
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.intakeLauncher.stopWheels();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
