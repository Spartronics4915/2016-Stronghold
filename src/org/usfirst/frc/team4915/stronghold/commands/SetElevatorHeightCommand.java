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

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.intakeLauncher.setElevatorHeightWithJoystick(this.joystick);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        end();
    }

}
