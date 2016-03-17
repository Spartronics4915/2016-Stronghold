package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Boulder;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpinIntakeWheelsOutCommand extends Command {

    // this command spins the launch wheels outwards so they will launch the
    // ball

    int startTime;
    private static final int WAIT_DURATION = 1500;
    
    public SpinIntakeWheelsOutCommand() {
        requires(Robot.intakeLauncher);
        startTime = (int)System.currentTimeMillis();
    }

    @Override
    protected void initialize() {
        System.out.println("Spin Intake Out Command");
        setTimeout(10); // TODO
        SmartDashboard.putString("Flywheels spinning ", "outward");
        SmartDashboard.putBoolean("Launch Ready ", System.currentTimeMillis() - startTime > WAIT_DURATION);
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setDesiredWheelSpeed();
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
        System.out.println("Spin Intake Out Interrupt");
    }
}
