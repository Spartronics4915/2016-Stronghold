package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpinIntakeWheelsOutCommand extends Command {

    // this command spins the launch wheels outwards so they will launch the
    // ball

    boolean areLaunchWheelsReady;
    int startTime;
    final int WAIT_DURATION = 1500;
    
    public SpinIntakeWheelsOutCommand() {
        requires(Robot.intakeLauncher);
        startTime = (int)System.currentTimeMillis();
 
    }

    @Override
    protected void initialize() {
        System.out.println("Spin wheels out command");
        setTimeout(10); // TODO
        SmartDashboard.putString("Flywheels spinning ", "outward");
        SmartDashboard.putBoolean("Launch Ready ", System.currentTimeMillis() - startTime > WAIT_DURATION);
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
