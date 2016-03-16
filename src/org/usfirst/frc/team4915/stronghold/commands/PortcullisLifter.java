package org.usfirst.frc.team4915.stronghold.commands;

import org.usfirst.frc.team4915.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PortcullisLifter extends CommandGroup {
    
    public  PortcullisLifter() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        while (!RobotMap.portcullisSwitchBottom.get()){
            RobotMap.portcullisRightMotor.set(RobotMap.PORTCULLIS_SPEED); 
            RobotMap.portcullisLeftMotor.set(RobotMap.PORTCULLIS_SPEED);
    } 
        while (!RobotMap.portcullisSwitchTop.get()){
            RobotMap.portcullisRightMotor.set(-1*RobotMap.PORTCULLIS_SPEED);
            RobotMap.portcullisLeftMotor.set(-1*RobotMap.PORTCULLIS_SPEED);
        }
}
}
