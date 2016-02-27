package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class SpinIntakeWheelsOutCommand extends Command {

    // this command spins the launch wheels outwards so they will launch the
    // ball

    public SpinIntakeWheelsOutCommand() {
        requires(Robot.intakeLauncher); 
    }

    @Override
    protected void initialize() {
        setTimeout(10); // TODO
        SmartDashboard.putString("Flywheels spinning ", "outward");
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
        System.out.println("End Spin Wheels Out");
    }

    @Override
    protected void interrupted() {
        
    }
}
