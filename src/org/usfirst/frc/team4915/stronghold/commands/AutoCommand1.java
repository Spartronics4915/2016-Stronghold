package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.AutoRotateDegrees;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.MoveStraightPositionModeCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimerGoToAngleCommand;
import org.usfirst.frc.team4915.stronghold.subsystems.Autonomous;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoAimControlCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCommand1 extends CommandGroup {

    private final Autonomous.Type type;
    private final Autonomous.Strat strat;
    private final Autonomous.Position position;

    public AutoCommand1(Autonomous.Type type, Autonomous.Strat strat, Autonomous.Position position) {
        this.strat = strat;
        this.position = position;
        this.type = type;
        System.out.println("ANgle: " + position + "Field Position " + position + "strategy " + strat + "Obstacle " + type);
        
        addSequential(new MoveStraightPositionModeCommand(getDistance(type)));
        addSequential(new AutoRotateDegrees(getLeft(position), getDegrees(position))); // these two commands can move you across barriers and roughly turn an angle towards the goal
        switch(strat){
        	case DRIVE_SHOOT_VISION: //sets us up to use vision to shoot a high goal. 
        		addSequential(new AutoAimControlCommand(true, true));
        		break;
        	default:
        		addSequential(new AutoAimControlCommand(false, true));
        		addSequential(new AimerGoToAngleCommand(300));
        		break;
        }
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
                left = true;
                break;
            case FIVE:
                left = true;
                break;
            default:
                left = true;
        }
        return left;
    }

    public static int getDegrees(Autonomous.Position position) {
        int degrees;
        System.out.println(position);
        switch (position) {
            case ONE:
                degrees = 45;
                break;
            case TWO:
                degrees = 15;
                break;
            case THREE:
                degrees = 15;
                break;
            case FOUR:
                degrees = 15;
                break;
            case FIVE:
                degrees = 15;
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
        int distance;
        System.out.println(type);
        switch (type) {
            case LOWBAR:
                distance = 200;
                break;
            case CHEVAL_DE_FRISE:
                distance = 168;
                break;
            case MOAT:
                distance = 168;
                break;
            case RAMPARTS:
                distance = 168;
                break;
            case ROUGH_TERRAIN:
                distance = 168;
                break;
            case ROCK_WALL:
                distance = 168;
                break;
            default:
                distance = 0;
        }
        return distance;
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
