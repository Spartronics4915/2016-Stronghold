package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpinIntakeWheelsInwardCommand extends Command {


    // this command spins the intake flywheels inward to retrieve the ball
    public SpinIntakeWheelsInwardCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        setTimeout(10); // TODO finalize time
        SmartDashboard.putString("Flywheels spinning ", "inward");
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedIntake();
        Robot.intakeLauncher.aimLauncher(); // this command interrupts
                                            // AimLauncherCommand so we make
                                            // sure the launcher always aims
    }

    @Override
    protected boolean isFinished() {
        // ends once the ball is in the basket and presses the limit switch or
        // after 10 seconds
        return (Robot.intakeLauncher.boulderSwitch.get() || isTimedOut());
    }

    @Override
    protected void end() {
        Robot.intakeLauncher.stopWheels();
        System.out.println("Spin Wheels in end");
    }

    @Override
    protected void interrupted() {
        
    }
}
