package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class IntakeBallCommand extends Command {

    // this command spins the intake flywheels inward to retrieve the ball
    public IntakeBallCommand() {
        requires(Robot.intakeLauncher);
    }

    @Override
    protected void initialize() {
        System.out.println("Intake Ball Command");
    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setSpeedIntake();
        SmartDashboard.putString("Flywheels spinning ", "inward"); 
    }

    @Override
    protected boolean isFinished() {
        // ends once the ball is in the basket and presses the limit switch
        return(Robot.intakeLauncher.boulderSwitch.get() || Robot.intakeLauncher.areWheelsFinished());
    }

    @Override
    protected void end() {
        SmartDashboard.putString("Boulder in Basket: ", "Yes");
    }

    @Override
    protected void interrupted() {

    }
}
