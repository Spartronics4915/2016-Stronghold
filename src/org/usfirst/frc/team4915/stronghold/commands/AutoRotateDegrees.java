package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;

public class AutoRotateDegrees extends Command {
    RobotDrive robotDrive = DriveTrain.robotDrive;
    //autonomous rotate command 
    public void AutonomousCommandRotate(int moveValue, int rotateValue){
        robotDrive.arcadeDrive (moveValue, rotateValue );
    }

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void end() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void interrupted() {
        // TODO Auto-generated method stub

    }

}
