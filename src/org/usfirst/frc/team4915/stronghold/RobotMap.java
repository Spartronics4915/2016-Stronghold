package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;

    // Define channels for the motors
    public static final int driveTrainLeftBackMotor = 21;
    public static final int driveTrainRightBackMotor = 23;
    public static final int driveTrainLeftFrontMotor = 20;
    public static final int driveTrainRightFrontMotor = 22;
    // connecting the motors to the channels
    public static final CANTalon leftBackMotor = new CANTalon(driveTrainLeftBackMotor);
    public static final CANTalon rightBackMotor = new CANTalon(driveTrainRightBackMotor);
    public static final CANTalon leftFrontMotor = new CANTalon(driveTrainLeftFrontMotor);
    public static final CANTalon rightFrontMotor = new CANTalon(driveTrainRightFrontMotor);

}
