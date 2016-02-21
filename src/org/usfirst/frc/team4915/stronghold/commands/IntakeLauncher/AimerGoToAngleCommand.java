package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AimerGoToAngleCommand extends Command {

    private double angle;

    public AimerGoToAngleCommand(double angle) {
        this.angle = angle;
        SmartDashboard.putNumber("Aimer angle: ", this.angle);
    }

    protected void initialize() {
        Robot.intakeLauncher.setSetPoint(Robot.intakeLauncher.degreesToTicks(angle));
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }

    protected void interrupted() {

    }
}
