package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AimerGoToAngleCommand extends Command {

    private double setPoint;

    public AimerGoToAngleCommand(double setPoint) {
        this.setPoint = setPoint;
        SmartDashboard.putNumber("Aimer setPoint", this.setPoint);
    }

    protected void initialize() {
        Robot.intakeLauncher.setSetPoint(setPoint);
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
