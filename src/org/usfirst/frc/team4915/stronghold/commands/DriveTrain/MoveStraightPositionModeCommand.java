package org.usfirst.frc.team4915.stronghold.commands.DriveTrain;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveStraightPositionModeCommand extends Command {

    public static List<CANTalon> motors = Robot.driveTrain.motors;
    public double inputDistanceInches;
    private DriveTrain driveTrain = Robot.driveTrain;
    private List<Double> desiredTicksValue;
    private double driveStraightValue;
    // variables for the ticks to move equation
    private static double cyclesPerRotation = 256;
    private static double approxCircumference = 45;// in inches
    private static double shaftRatio = 3.2;
    private static double gearBoxRatio = 4;
    private static double pulsesPerCycle = 4;

    public MoveStraightPositionModeCommand(double inputDistanceInches, double speed) {

        this.driveStraightValue = speed;
        
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
    public void initialize() {

        this.desiredTicksValue = new ArrayList<Double>();
        // new equation
        double ticksToMove =
                ((this.inputDistanceInches * shaftRatio * gearBoxRatio * cyclesPerRotation * pulsesPerCycle) / (approxCircumference)) ;

        System.out.println("ticksToMove: " + ticksToMove);
        // double startingTickValue;
        // double endValue;

        // get the starting encoder value
        // move motors and read new encoder value
        /*for (int i = 0; i < motors.size(); i++) {
            motors.get(i).setEncPosition(0);
            //System.out.println("motor " + i + " reset to " + motors.get(i).getEncPosition());
            System.out.println("motor " + 1 + " reset to " + motors.get(1).getEncPosition());
            System.out.println("motor " + 3 + " reset to " + motors.get(3).getEncPosition());
            // All the motors are inverted/backwards. The ticks are moving down
            // when moving forward
            
            SmartDashboard.putNumber("Drive Straight: Goal amount of Ticks", ticksToMove);
            System.out.println("Running MoveStraight");

        }*/
        this.desiredTicksValue.add(ticksToMove);
        //prints out the starting encoder value
        motors.get(1).setEncPosition(0);
        System.out.println("motor " + 1 + " reset to " + motors.get(1).getEncPosition());
        motors.get(3).setEncPosition(0);
        System.out.println("motor " + 3 + " reset to " + motors.get(3).getEncPosition());
        this.desiredTicksValue.add(ticksToMove);
        
        motors.get(0).setEncPosition(0);
        System.out.println("motor " + 0 + " reset to " + motors.get(0).getEncPosition());
        motors.get(2).setEncPosition(0);
        System.out.println("motor " + 2 + " reset to " + motors.get(2).getEncPosition());
        SmartDashboard.putNumber("Drive Straight: Goal amount of Ticks", ticksToMove);
        System.out.println("Running MoveStraight");
    }

    /**
     * This uses the wheel circumference and the number of rotations to compute
     * the distance traveled.
     */

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        System.out.println("Executing move straight");
        if (this.inputDistanceInches < 0) {
            this.driveTrain.driveStraight(this.driveStraightValue);
        } else {
            this.driveTrain.driveStraight(-this.driveStraightValue);
        }
        SmartDashboard.putNumber("Drive Straight: Input distance", this.inputDistanceInches);
    }

    @Override
    public boolean isFinished() {
        // Checking if all the motors have reached the desired tick values
        
        //return isAverageMotorFinished();
        return isMotorFinished(1) || isMotorFinished(3) ;
    }
    
    private boolean isAverageMotorFinished(){
        //averages the motors
        System.out.println("in isAverageMotorFinished");
        boolean finished = false;
        double total;
        double average;
        double desiredPosition = this.desiredTicksValue.get(1);// all the same but we didn't want to change everything
        //prints out the motors value
        System.out.println("Motor 1" + motors.get(1).getEncPosition());
        System.out.println("Motor 3" + motors.get(3).getEncPosition());
        total = Math.abs(motors.get(1).getEncPosition()) + Math.abs(motors.get(3).getEncPosition());
        average = total/2;
        System.out.println("Average of motors" + average);
        // drive backwards
        if (this.inputDistanceInches < 0) {
            finished = average <= desiredPosition;
        } else {
            finished = average >= desiredPosition;
        }
        return finished;
    }

    private boolean isMotorFinished(int i) {
        
        boolean finished = false;
        double desiredPosition = this.desiredTicksValue.get(0);
        System.out.println("in isMotorFinished");
        double currentPosition = Math.abs(motors.get(i).getEncPosition());
        System.out.println("Motor " + i + ": current position: " + currentPosition + ", desired position " + desiredPosition);

        // drive backwards
        if (this.inputDistanceInches < 0) {
            finished = currentPosition <= desiredPosition;
        } else {
            finished = currentPosition >= desiredPosition;
        }

        return finished;

    }

    // Called once after isFinished returns true
    @Override
    public void end() {
        this.driveTrain.robotDrive.stopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    public void interrupted() {
        end();
    }
}
