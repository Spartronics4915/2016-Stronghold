package org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer;

import org.usfirst.frc.team4915.stronghold.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

public class LauncherGoToPositionCommand extends Command {

    String myPosition;
    double myDegrees;
    
    public static final String ANGLE = "angle";
    public static final String NEUTRAL = "neutral";
    public static final String TRAVEL = "travel";
    
    public LauncherGoToPositionCommand(String position) {
        myPosition = position;
        requires(Robot.intakeLauncher);
    }
    
    public LauncherGoToPositionCommand(double degrees) {
        myDegrees = degrees;
        myPosition = ANGLE;
        requires(Robot.intakeLauncher);
    }

    protected void initialize() {
        Robot.intakeLauncher.aimMotor.disableControl();
        Robot.intakeLauncher.aimMotor.changeControlMode(CANTalon.TalonControlMode.Position);
        Robot.intakeLauncher.aimMotor.setPID(0.1, 0.1, 0);
        System.out.println("Launcher go to " + myPosition + " command");
        switch (myPosition) {
            case ANGLE: 
                Robot.intakeLauncher.launcherJumpToAngle(myDegrees); //Known to be nonfunctional 
                break;
            case NEUTRAL:
                Robot.intakeLauncher.launcherSetNeutralPosition();
                break;
            case TRAVEL:
                Robot.intakeLauncher.launcherSetTravelPosition();
                break;
            default:
                break;
        }
        Robot.intakeLauncher.aimMotor.enableControl();
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        switch(myPosition) {
            case NEUTRAL:
               return Robot.intakeLauncher.isLauncherAtNeutral(); 
            case TRAVEL:
               return Robot.intakeLauncher.isLauncherAtTravel(); 
            case ANGLE:
               return Robot.intakeLauncher.isLauncherAtAngle(myDegrees);
            default:
                return true;
        }
    }

    protected void end() {
        Robot.intakeLauncher.aimMotor.disableControl();
 //       Robot.intakeLauncher.aimMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
 //       Robot.intakeLauncher.aimMotor.enableControl();
    }

    protected void interrupted() {
        end();
    }
}
