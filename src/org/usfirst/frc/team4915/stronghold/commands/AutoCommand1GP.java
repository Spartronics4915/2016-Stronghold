package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import org.usfirst.frc.team4915.stronghold.ModuleManager;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.AutoRotateDegrees;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer.AimLauncherCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer.AimLauncherNeutralForAutoCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer.AimLauncherTravelForAutoCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Boulder.AutoLaunchCommand;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoVisionDriveAndAim;

import org.usfirst.frc.team4915.stronghold.subsystems.Autonomous;

public class AutoCommand1GP extends CommandGroup {

    private final Autonomous.Type type;
    private final Autonomous.Strat strat;
    private final Autonomous.Position position;

    public AutoCommand1GP(Autonomous.Type type, Autonomous.Strat strat, Autonomous.Position position) {
	this.strat = strat;
	this.position = position;
	this.type = type;
    }

    public void initialize() {
	// Please NOTE:
	// this file is "deprecated"... It represents the strategy we
	// employed during our first event at Glacier Peak and relies
	// on a timer ensure that the we attain travel or neutral
	// launch position before crossing the barrier.
	System.out.println(
		"AutonomousGP init, field position: " + position + "strategy: " + strat + " obstacle: " + type);

	if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
	    boolean launcherWantsTravelPosition = getLauncherBeginPosition(type);
	    boolean blocking = false; // false means we rely on timer
	    if (launcherWantsTravelPosition)
		addSequential(new AimLauncherTravelForAutoCommand(blocking));
	    else
		addSequential(new AimLauncherNeutralForAutoCommand(blocking));
	    addParallel(new AimLauncherCommand());
	    addSequential(new WaitCommand(2.75));
	}

	if (ModuleManager.PORTCULLIS_MODULE_ON) {
	    // portcullis lifter begin position
	    boolean portcullisBeginDown = getPortcullisBeginPosition(type);
	    if (portcullisBeginDown) {
		addSequential(new PortcullisMoveDown());
	    } else {
		addSequential(new PortcullisMoveUp());
	    }
	}

	double distance = getDistance(type) + getDistancePastDefense(position);

	switch (strat) {
	case NONE:
	    break;

	case DRIVE_ACROSS:
	    System.out.println("Starting Drive Across (GP)");
	    addSequential(new AutoDriveStraight(distance, getSpeed(type)));
	    break;

	case DRIVE_SHOOT_VISION: // sets us up to use vision to shoot a high
				 // goal
	    System.out.println("Starting Drive Shoot Vision (untested)");
	    addSequential(new AutoDriveStraight(distance, getSpeed(type)));
	    addSequential(new AutoRotateDegrees(getTurnAngle(position)));
	    addSequential(new AutoAimControlCommand(true, true));
	    addSequential(new AutoVisionDriveAndAim());
	    addSequential(new AutoLaunchCommand());
	    break;

	case DRIVE_SHOOT_NO_VISION:
	    System.out.println("Starting Drive Shoot No Vision (untested)");
	    addSequential(new AutoDriveStraight(distance, getSpeed(type)));
	    addSequential(new AutoRotateDegrees(getTurnAngle(position)));
	}
    }

    protected boolean isFinished() {
	// CommandGroup's implementation looks at done-state of children
	// we can introduce extra code here but we must call super.isFinished()
	// Or we can delete this overridden method and rely on CommandGroup's
	// implementation. Note that graceful shutdown isn't absolutely
	// required, since we cancel the autonomous command when we enter
	// teleop.
	return super.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
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

    public static boolean getPortcullisBeginPosition(Autonomous.Type type) {
	boolean liftdown; // tells if portcullis needs to be down
	switch (type) {
	case LOWBAR:
	    liftdown = true;
	    break;
	case MOAT:
	    liftdown = false;
	    break;
	case ROUGH_TERRAIN:
	    liftdown = false;
	    break;
	case ROCK_WALL:
	    liftdown = false;
	    break;
	case PORTCULLIS:
	    liftdown = false;
	    break;
	default:
	    liftdown = false;
	}
	return liftdown;
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

    public static double getTurnAngle(Autonomous.Position position) {
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
	    speed = 40;
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

}
