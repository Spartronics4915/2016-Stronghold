package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;

import java.util.ArrayList;
import java.util.List;

public class MoveStraightPositionModeCommand extends Command {

    public static List<CANTalon> motors = Robot.driveTrain.motors;
    public double inputDistanceInches;
    private DriveTrain driveTrain = Robot.driveTrain;
    private List<Double> desiredTicksValue;
    private double driveStraightValue = 0.7;

    public MoveStraightPositionModeCommand(double inputDistanceInches) {

        requires(this.driveTrain);

        System.out.println("***MoveStraightPositionModeCommand inputDistance: " + inputDistanceInches + "*******");

        this.inputDistanceInches = inputDistanceInches;

        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time

    /**
     * This initializes the variables for the distance calculator.
     */
    @Override
    protected void initialize() {
        this.desiredTicksValue = new ArrayList<Double>();

        //double ticksToMove = this.inputDistanceInches * 1000 / (14 * Math.PI);
        double ticksToMove = (this.inputDistanceInches * 256 * 4) / (14 * Math.PI);

        //double startingTickValue;
        //double endValue;
        //reset encoders
        
        // get the starting encoder value
        // move motors and read new encoder value
        for (int i = 0; i < motors.size(); i++) {
            motors.get(i).setEncPosition(0);
            System.out.println("motor " + i + " reset to " + motors.get(i).getEncPosition());
            // All the motors are inverted/backwards. The ticks are moving down
            // when moving forward
            this.desiredTicksValue.add(ticksToMove);

        }
    }

    /**
     * This uses the wheel circumference and the number of rotations to compute
     * the distance traveled.
     */

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (this.inputDistanceInches < 0) {
            this.driveTrain.driveStraight(this.driveStraightValue);
        } else {
            this.driveTrain.driveStraight(-this.driveStraightValue);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // Checking if all the motors have reached the desired tick values
        return isMotorFinished(0) || isMotorFinished(1) || isMotorFinished(2) || isMotorFinished(3);
    }

    private boolean isMotorFinished(int i) {
        boolean finished = false;
        double desiredPosition = this.desiredTicksValue.get(i);

        double currentPosition = motors.get(i).getEncPosition();
        System.out.println("Motor " + i + ": current position: " + currentPosition + ", desired position " + desiredPosition);

        // All motors are inverted
        if (this.inputDistanceInches < 0) {
            finished = currentPosition <= desiredPosition;
        } else {
            finished = currentPosition >= desiredPosition;
        }

        return finished;

    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        this.driveTrain.robotDrive.stopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
