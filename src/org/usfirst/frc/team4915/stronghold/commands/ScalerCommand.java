package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.subsystems.Scaler;
import org.usfirst.frc.team4915.stronghold.subsystems.Scaler.State;

public class ScalerCommand extends Command {

    public static final double MOTOR_SPEED = 0.7;
    public static final double WINCH_SPEED = -0.7;
    private final Scaler.State target;

    public ScalerCommand(State target) {
        this.target = target;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        switch (target) {
            case RETRACTED:
                RobotMap.SCALING_WINCH.set(WINCH_SPEED);
                break;
            case EXTENDED:
                RobotMap.SCALING_MOTOR.set(MOTOR_SPEED);
                break;
            default:
                System.out.println("Invalid scaler state " + target);
        }
    }

    @Override
    protected boolean isFinished() {
        return RobotMap.SCALING_BOTTOM_SWITCH.get() || RobotMap.SCALING_TOP_SWITCH.get();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
