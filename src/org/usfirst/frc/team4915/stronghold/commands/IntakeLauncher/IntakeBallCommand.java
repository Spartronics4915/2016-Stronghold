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
        Robot.intakeLauncher.setSpeedIntake();
    }

    @Override
    protected void execute() {
        SmartDashboard.putString("Intake Flywheels", "Right: " + Double.toString(Robot.intakeLauncher.getIntakeRightMotor().getSpeed()) + " Left: "
                + Double.toString(Robot.intakeLauncher.getIntakeLeftMotor().getSpeed()));
    }

    @Override
    protected boolean isFinished() {
        // ends once the ball is in the basket and presses the limit switch
        SmartDashboard.putBoolean("Boulder in Basket", Robot.intakeLauncher.boulderSwitch.get());
        return Robot.intakeLauncher.boulderSwitch.get();
        
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        end();
    }

}
