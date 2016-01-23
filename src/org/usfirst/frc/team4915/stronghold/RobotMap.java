package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;

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
    public static int driveTrainLeftFrontMotor = 10;
    public static int driveTrainRightFrontMotor = 12;
    public static int driveTrainRightBackMotor = 13;
    public static int driveTrainLeftBackMotor = 11;
    // defining motor channels
    public static RobotDrive driveTrainRobotDrive;

    public static DoubleSolenoid leftDoubleSolenoid;
    public static DoubleSolenoid rightDoubleSolenoid;
    // there are two double solenoids, one for each side. They control the
    // gearbox and help switch between the two speeds
}
