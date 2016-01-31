package org.usfirst.frc.team4915.stronghold.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.stronghold.Robot;

public class AutoDriveStraight extends Command {

    // variable
    Encoder encoderLeft;
    Encoder encoderRight;

    public AutoDriveStraight(Encoder encoderLeft, Encoder encoderRight) {

        this.encoderLeft = encoderLeft;
        this.encoderRight = encoderRight;
    }

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        /*
         * How we got 7,468: Distance that robot needs to move to get into
         * position for AutoRotateDegree Our estimated distance (d) is 130
         * inches. Our circumference of the 14 inch diameter wheel is 14π. The
         * number of ticks per rotation (14π) is 256 ticks. 
         * 130 ft/14π = 29.17 rotations. 29.17 rotations*256 ticks = 7468 ticks needed to reach destination
         */
        while (this.encoderLeft.get() <= 7468 && this.encoderRight.get() <= 7468) {
            System.out.println("Drive Work");
            Robot.driveTrain.driveStraight(.7);

        }
        Robot.driveTrain.stop();

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
