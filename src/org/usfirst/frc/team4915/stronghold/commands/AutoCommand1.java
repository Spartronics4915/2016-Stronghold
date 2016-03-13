package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.ModuleManager;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.ArcadeDrive;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.AutoRotateDegrees;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimLauncherCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AutoLaunchCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LauncherGoToAngleCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LauncherGoToNeutralPositionCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LauncherGoToTravelPositionCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.VisionAimLauncherCommand;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

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
									// goal
		    //if it is low bar launcher will go to travelPosition
		if (getLauncherBeginPosition(type) == true){
		    addSequential(new LauncherGoToTravelPositionCommand());
            addParallel(new AimLauncherCommand());
            addSequential(new WaitCommand(.75));
            addSequential(new AutoDriveStraight(getDistance(type), getSpeed(type)));
            addSequential(new AutoRotateDegrees( getDegrees(position)));
            if (ModuleManager.VISION_MODULE_ON) {
                addSequential(new AutoAimControlCommand(true, true));
                addParallel(new ArcadeDrive());
            }
            break;    
		}
		//false: For all other types of barriers
		//launcher will go to NeutralPosition
		else{
		    addSequential(new LauncherGoToNeutralPositionCommand());
            addParallel(new AimLauncherCommand());
            addSequential(new WaitCommand(.75));
			addSequential(new AutoDriveStraight(getDistance(type), getSpeed(type)));
			addSequential(new AutoRotateDegrees( getDegrees(position)));
			if (ModuleManager.VISION_MODULE_ON) {
				addSequential(new AutoAimControlCommand(true, true));
				addParallel(new ArcadeDrive());
				addParallel(new VisionAimLauncherCommand());
                addSequential(new AutoLaunchCommand());
			}
			break;
		}
		
		case DRIVE_SHOOT_NO_VISION:
		    if (getLauncherBeginPosition(type) == true){
		        addSequential(new LauncherGoToTravelPositionCommand());
                addParallel(new AimLauncherCommand());
                addSequential(new WaitCommand(.75));
		        System.out.println("Starting Move Straight");
		        addSequential(new AutoDriveStraight(getDistance(type), getSpeed(type)));
		        addSequential(new AutoDriveStraight(getDistancePastDefense(position), getSpeed(type)));
		        addSequential(new AutoRotateDegrees(getDegrees(position)));
			if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
				addParallel(new AimLauncherCommand());
				addSequential(new LauncherGoToAngleCommand(getAimAngle(position)));
				addSequential(new AutoLaunchCommand());
			}
			break;
		    }
            
            else{   
                addSequential(new LauncherGoToNeutralPositionCommand());
                addParallel(new AimLauncherCommand());
                addSequential(new WaitCommand(.75));
		        System.out.println("Starting Move Straight");
	            addSequential(new AutoDriveStraight(getDistance(type), getSpeed(type)));
	            addSequential(new AutoDriveStraight(getDistancePastDefense(position), getSpeed(type)));
	            addSequential(new AutoRotateDegrees(getDegrees(position)));
	            if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
	                addSequential(new LauncherGoToAngleCommand(getAimAngle(position)));
	                addSequential(new AutoLaunchCommand());
	            break;    
	            }
            }
		    
	    case DRIVE_ACROSS:
	    	
	    	//if true it is the low bar
            if (getLauncherBeginPosition(type) == true){
                addSequential(new LauncherGoToTravelPositionCommand());
                addParallel(new AimLauncherCommand());
                addSequential(new WaitCommand(2.75));
                addSequential(new AutoDriveStraight(getDistance(type), getSpeed(type)));
                addSequential(new AutoDriveStraight(getDistancePastDefense(position), getSpeed(type)));
			break;

            }
            else{
                addSequential(new LauncherGoToNeutralPositionCommand());
                addParallel(new AimLauncherCommand());
                addSequential(new WaitCommand(2.75));
                addSequential(new AutoDriveStraight(getDistance(type), getSpeed(type)));
                addSequential(new AutoDriveStraight(getDistancePastDefense(position), getSpeed(type)));
            }
            default:
		}
	}


    public static boolean getLauncherBeginPosition(Autonomous.Type type) {
        boolean lowBar; // in inches
        System.out.println(type);
        switch (type) {
            case LOWBAR:
                lowBar = true;
                break;
            case MOAT:
                lowBar = false;
                break;
            case ROUGH_TERRAIN:
                lowBar = false;
                break;
            case ROCK_WALL:
                lowBar = false;
                break;
            case PORTCULLIS:
                lowBar = false;
                break;
            default:
                lowBar = false;
        }
        return lowBar;
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

    public static double getDegrees(Autonomous.Position position) {
        double degrees;
        switch (position) {
            case ONE:// low bar
                degrees = 80.4;
                break;
            case TWO:
                degrees = 41.08;
                break;
            case THREE:
                degrees = 11.95;
                break;
            case FOUR:
                degrees = -13.12; // turn left
                break;
            case FIVE:
                degrees = -57.75;
                break;
            default:
                degrees = 0;
        }
        return degrees;
    }

    public static boolean getStrategy(Autonomous.Strat strat) {
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
                break;
        }
        return vision;
    }

    // getting the distance to go after the barrier for launching
    // andalucia's math
    public static double getDistancePastDefense(Autonomous.Position position) {
        double distance;
        System.out.println(position);
        switch (position) {
            case ONE:// low bar
                distance = 38;
                break;
            case TWO:
                distance = 101.05;
                break;
            case THREE:
                distance = 74.1;
                break;
            case FOUR:
                distance = 75.09;
                break;
            case FIVE:
                distance = 104.97;
                break;
            default:
                distance = 70;
        }
        return distance;
    }

    public static int getDistance(Autonomous.Type type) {
        int distance; // in inches
        System.out.println(type);
        switch (type) {
            case LOWBAR:
                distance = 130;
                break;
            case MOAT:
                distance = 145;
                break;
            case ROUGH_TERRAIN:
                distance = 180;
                break;
            case ROCK_WALL:
                distance = 150;
                break;
            case PORTCULLIS:
                distance = 120;
                break;
            default:
                distance = 145;
        }
        return distance;
    }

    public static int getSpeed(Autonomous.Type type) {
        int speed; // in inches
        System.out.println(type);
        switch (type) {
            case LOWBAR:
                speed = 30;
                break;
            case MOAT:
                speed = 50;
                break;
            case ROUGH_TERRAIN:
                speed = 40;
                break;
            case ROCK_WALL:
                speed = -75;
                break;
            case PORTCULLIS:
                speed = 30;
                break;
            default:
                speed = 35;
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
