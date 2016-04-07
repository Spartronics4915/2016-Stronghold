package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.ModuleManager;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.AutoRotateDegrees;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Aimer.LauncherGoToPositionForAutoCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.Boulder.AutoLaunchCommand;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoVisionDriveAndAim;
import org.usfirst.frc.team4915.stronghold.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCommand1 extends CommandGroup {

    private Autonomous.Type m_type;
    private Autonomous.Strat m_strat;
    private Autonomous.Position m_position;

    public AutoCommand1(Autonomous.Type type, Autonomous.Strat strat, Autonomous.Position position) {
        this.m_strat = strat;
        this.m_position = position;
        this.m_type = type;

        System.out.println("Autonomous1 construct (strat:" + strat + ")");

        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {

            boolean shouldQuit = false; // means we rely on launcher positioning
            if(m_type == Autonomous.Type.LOWBAR) {
                addSequential(new LauncherGoToPositionForAutoCommand(shouldQuit, LauncherGoToPositionForAutoCommand.NEUTRAL));
            } else {
                addSequential(new LauncherGoToPositionForAutoCommand(shouldQuit, LauncherGoToPositionForAutoCommand.TRAVEL));
            }
        }

        if (ModuleManager.PORTCULLIS_MODULE_ON) {
            // portcullis lifter begin position
            boolean portcullisBeginDown = getPortcullisBeginPosition();
            if (portcullisBeginDown) {
                addSequential(new PortcullisMoveDown());
            } else {
                addSequential(new PortcullisMoveUp());
            }
        }

        double distance = getDistance() + getDistancePastDefense();
        switch (strat) {
            case NONE:
                break;

            case DRIVE_ACROSS:
                System.out.println("Starting Drive Across (New)");
                addSequential(new AutoDriveStraight(distance, getSpeed()));
                break;

            case DRIVE_SHOOT_VISION: // sets us up to use vision to shoot a high
                // goal
                System.out.println("Starting Drive Shoot Vision (untested)");
                addSequential(new AutoDriveStraight(distance, getSpeed()));
                addSequential(new AutoRotateDegrees(getTurnAngle()));
                addSequential(new AutoAimControlCommand(true, true));
                addSequential(new AutoVisionDriveAndAim());
                addSequential(new AutoLaunchCommand());
                break;
        
            case DRIVE_SHOOT_NO_VISION:
                System.out.println("Starting Drive Shoot No Vision (untested)");
                addSequential(new AutoDriveStraight(distance, getSpeed()));
                addSequential(new AutoRotateDegrees(getTurnAngle()));
                break;
    
        }
    }

    public void initialize() {
        System.out.println("Autonomous1 initialize (strat:" + m_strat + ")");
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

    
 
    public boolean getPortcullisBeginPosition() {
        boolean liftdown; // tells if portcullis needs to be down
        switch (m_type) {
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
                liftdown = true;
                break;
            case RAMPARTS:
                liftdown = false;
            default:
                liftdown = false;
        }
        return liftdown;
    }

    public double getAimAngle() {
        double angle = 0;
        switch (m_position) {
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

    public double getTurnAngle() {
        double degrees;
        switch (m_position) {
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

    public boolean getStrategy() {
        boolean vision = true;
        switch (m_strat) {
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
        }
        return vision;
    }

    // getting the distance to go after the barrier for launching
    // andalucia's math
    public double getDistancePastDefense() {
        double distance;
        switch (m_position) {
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

    public int getDistance() {
        int distance; // in inches
        switch (m_type) {
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
                distance = 140;
                break;
            case RAMPARTS:
                distance = 150;
                break;
            default:
                distance = 145;
        }
        return distance;
    }

    public int getSpeed() {
        int speed; // in inches
        switch (m_type) {
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
            case RAMPARTS:
                speed = -50;
                break;
            default:
                speed = 35;
        }
        return speed;
    }

}
