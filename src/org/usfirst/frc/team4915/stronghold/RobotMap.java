package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;

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
    public static final int driveTrainLeftBackMotor = 11;
    public static final int driveTrainRightBackMotor = 13; // inverted
    public static final int driveTrainLeftFrontMotor = 10;
    public static final int driveTrainRightFrontMotor = 12;

    public static final CANTalon leftBackMotor = new CANTalon(driveTrainLeftBackMotor);
    public static final CANTalon rightBackMotor = new CANTalon(driveTrainRightBackMotor);
    public static final CANTalon leftFrontMotor = new CANTalon(driveTrainLeftFrontMotor);
    public static final CANTalon rightFrontMotor = new CANTalon(driveTrainRightFrontMotor);

    public static final int SOLENOID_CHANNEL_PRIMARY = 0;
    public static final int SOLENOID_CHANNEL_SECONDARY = 1;
    // public static final int RIGHT_SOLENOID_CHANNEL_PRIMARY= 2;
    // public static final int RIGHT_SOLENOID_CHANNEL_SECONDARY= 3;

    public static final DoubleSolenoid doubleSolenoid = new DoubleSolenoid(SOLENOID_CHANNEL_PRIMARY, SOLENOID_CHANNEL_SECONDARY);
    // public static final DoubleSolenoid rightDoubleSolenoid= new
    // DoubleSolenoid
    // (RIGHT_SOLENOID_CHANNEL_PRIMARY, RIGHT_SOLENOID_CHANNEL_SECONDARY);

}
