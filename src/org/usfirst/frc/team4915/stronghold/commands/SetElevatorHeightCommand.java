package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class SetElevatorHeightCommand extends Command {

    private Joystick joystick;
    
    public SetElevatorHeightCommand(Joystick joystick) {
        requires(Robot.intakeLauncher);
        this.joystick = joystick;
    }

    protected void initialize() {
        Robot.intakeLauncher.setElevatorHeightWithJoystick(joystick);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        end();
    }

}
