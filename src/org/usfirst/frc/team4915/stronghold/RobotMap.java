package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;

public class RobotMap {
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

    public static final int INTAKE_LEFT_MOTOR_PORT = -1; //TODO
    public static final int INTAKE_RIGHT_MOTOR_PORT = -1; //TODO
    public static final int LAUNCHER_LEFT_MOTOR_PORT = -1; //TODO
    public static final int LAUNCHER_RIGHT_MOTOR_PORT = -1; //TODO

    public static final int BOULDER_SWITCH_PORT = -1; //TODO
    public static final int LAUNCHER_BOTTOM_SWITCH_PORT = -1; //TODO
    public static final int LAUNCHER_TOP_SWITCH_PORT = -1; //TODO

    public static final int LAUNCHER_ANGLE_ENCODER_PORT_1 = -1; //TODO
    public static final int LAUNCHER_ANGLE_ENCODER_PORT_2 = -1; //TODO

    public static final int LAUNCHER_SOLENOID_PORT = -1; //TODO
    public static final int LAUNCHER_COMPRESSOR_PORT = -1; //TODO
    // not actual port values

    public static CANTalon intakeLeftMotor = new CANTalon(INTAKE_LEFT_MOTOR_PORT);
    public static CANTalon intakeRightMotor = new CANTalon(INTAKE_RIGHT_MOTOR_PORT);
    public static CANTalon launcherLeftMotor = new CANTalon(LAUNCHER_LEFT_MOTOR_PORT);
    public static CANTalon launcherRightMotor = new CANTalon(LAUNCHER_RIGHT_MOTOR_PORT);

    public static DigitalInput boulderSwitch = new DigitalInput(BOULDER_SWITCH_PORT);
    public static DigitalInput launcherTopSwitch = new DigitalInput(LAUNCHER_TOP_SWITCH_PORT);
    public static DigitalInput launcherBottomSwitch = new DigitalInput(LAUNCHER_BOTTOM_SWITCH_PORT);

    public static Encoder launcherAngleEndoder = new Encoder(LAUNCHER_ANGLE_ENCODER_PORT_1, LAUNCHER_ANGLE_ENCODER_PORT_2);

    public static Solenoid launcherSolenoid = new Solenoid(LAUNCHER_SOLENOID_PORT);
    public static Compressor launcherCompressor = new Compressor(LAUNCHER_COMPRESSOR_PORT);
}
