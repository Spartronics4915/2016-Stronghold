package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;

    // Define channels for the motors
    public static final int driveTrainLeftBackMotor = 11;
    public static final int driveTrainRightBackMotor = 13;	// inverted 
    public static final int driveTrainLeftFrontMotor = 10;
    public static final int driveTrainRightFrontMotor = 12;

    public static final CANTalon leftBackMotor = new CANTalon(driveTrainLeftBackMotor);
    public static final CANTalon rightBackMotor = new CANTalon(driveTrainRightBackMotor);
    public static final CANTalon leftFrontMotor = new CANTalon(driveTrainLeftFrontMotor);
    public static final CANTalon rightFrontMotor = new CANTalon(driveTrainRightFrontMotor);
 
    public static final int INTAKE_LEFT_MOTOR_PORT = 0;
    public static final int INTAKE_RIGHT_MOTOR_PORT = 0;
    public static final int LAUNCHER_LEFT_MOTOR_PORT = 0;
    public static final int LAUNCHER_RIGHT_MOTOR_PORT = 0;
    // not actual port values

    public static CANTalon intakeLeftMotor = new CANTalon(INTAKE_LEFT_MOTOR_PORT);
    public static CANTalon intakeRightMotor = new CANTalon(INTAKE_RIGHT_MOTOR_PORT);
    public static CANTalon launcherLeftMotor = new CANTalon(LAUNCHER_LEFT_MOTOR_PORT);
    public static CANTalon launcherRightMotor = new CANTalon(LAUNCHER_RIGHT_MOTOR_PORT);

}
