package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.ModuleManager;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.ArcadeDrive;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.AutoRotateDegrees;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.MoveStraightPositionModeCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimLauncherCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AutoLaunchCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LauncherGoToAngleCommand;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCommand1 extends CommandGroup {

    private final Autonomous.Type type;
    private final Autonomous.Strat strat;
    private final Autonomous.Position position;

    public AutoCommand1(Autonomous.Type type, Autonomous.Strat strat, Autonomous.Position position) {
        this.strat = strat;
        this.position = position;
        this.type = type;
        System.out.println("Angle: " + position + "Field Position " + position + "strategy " + strat + "Obstacle " + type);

        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
          //  Robot.intakeLauncher.launcherSetNeutralPosition(); // placeholder
                                                               // for setting
                                                               // the launcher
                                                               // to neutral
                                                               // driving
                                                               // position
        }

    	switch (strat) {
		case DRIVE_SHOOT_VISION: // sets us up to use vision to shoot a high
									// goal.
			addSequential(new MoveStraightPositionModeCommand(getDistance(type), getSpeed(type)));
			addSequential(new AutoRotateDegrees(getLeft(position), getDegrees(position)));
			if (ModuleManager.VISION_MODULE_ON) {
				addSequential(new AutoAimControlCommand(true, true));
				addParallel(new ArcadeDrive());
				addParallel(new AimLauncherCommand());
			}
			break;
		case DRIVE_SHOOT_NO_VISION: 
			System.out.println("Starting Move Straight");
			addSequential(new MoveStraightPositionModeCommand(getDistance(type), getSpeed(type)));
			addSequential(new AutoRotateDegrees(getLeft(position), getDegrees(position)));
			if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
				addParallel(new AimLauncherCommand());
				addSequential(new LauncherGoToAngleCommand(getAimAngle(position)));
				addSequential(new AutoLaunchCommand());
			}
			break;
		case DRIVE_ACROSS:
			addSequential(new MoveStraightPositionModeCommand(getDistance(type), getSpeed(type)));
			break;
		default:
			break;
		}
	}
    public static double getAimAngle(Autonomous.Position position) {
        System.out.println(position);
        double angle = 0;
        switch (position) {
            case ONE:
                angle = 40;
                break;
            case TWO:
                angle = 40;
                break;
            case THREE:
            	angle = 30;
                break;
            case FOUR:
                angle = 40;
                break;
            case FIVE:
                angle = 40;
                break;
            default:
                angle = 40;
        }
        return angle;
    }
    
    public static boolean getLeft(Autonomous.Position position) {
        System.out.println(position);
        boolean left = true;
        switch (position) {
            case ONE:
                left = false;
                break;
            case TWO:
                left = false;
                break;
            case THREE:
                break;
            case FOUR:
                left = false;
                break;
            case FIVE:
                left = true;
                break;
            default:
                left = true;
        }
        return left;
    }

    public static double getDegrees(Autonomous.Position position) {
        double degrees;
        System.out.println(position);
        switch (position) {
            case ONE://low bar
                degrees = 80.4;
                break;
            case TWO:
                degrees = 41.08;
                break;
            case THREE:
                degrees = 11.95;
                break;
            case FOUR:
                degrees = 13.12;
                break;
            case FIVE:
                degrees = 57.75;
                break;
            default:
                degrees = 0;
        }
        return degrees;
    }

    public static boolean getStrategy(Autonomous.Strat strat) {
        System.out.println(strat);
        boolean vision = true;
        switch (strat) {
            case NONE:
                break;
            case DRIVE_ACROSS:
                break;
            case DRIVE_SHOOT_VISION:
                vision = true;
                break;
            case DRIVE_SHOOT_NO_VISION:
                vision = false;
                break;
            default:
                vision = true;
        }
        return vision;
    }

    public static int getDistance(Autonomous.Type type) {
        int distance; //in inches
        System.out.println(type);
        switch (type) {
            case LOWBAR:
                distance = 120; 
                break;
            case MOAT:
                distance = 120; 
                break;
            case RAMPARTS:
                distance = 120; 
                break;
            case ROUGH_TERRAIN:
                distance = 150; 
                break;
            case ROCK_WALL:
                distance = 120; 
                break;
            default:
                distance = 25;
        }
        return distance;
    }
    
    public static double getSpeed(Autonomous.Type type) {
        double speed;
        System.out.println(type);
        switch (type) {
            case LOWBAR:
                speed = 0.6;
                break;
            case MOAT:
                speed = 0.65; 
                break;
            case RAMPARTS:
                speed = 0.6;
                break;
            case ROUGH_TERRAIN:
                speed = 0.65;
                break;
            case ROCK_WALL:
                speed = 0.65;
                break;
            default:
                speed = 0;
        }
        return speed;
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
