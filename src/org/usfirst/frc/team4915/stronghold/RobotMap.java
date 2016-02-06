package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class RobotMap {
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;

    // Define channels for the motors
    public static final int driveTrainLeftBackMotor = 12;
    public static final int driveTrainRightBackMotor = 10;
    public static final int driveTrainLeftFrontMotor = 13;
    public static final int driveTrainRightFrontMotor = 11;

    public static final CANTalon leftBackMotor = new CANTalon(driveTrainLeftBackMotor);
    public static final CANTalon rightBackMotor = new CANTalon(driveTrainRightBackMotor);
    public static final CANTalon leftFrontMotor = new CANTalon(driveTrainLeftFrontMotor);
    public static final CANTalon rightFrontMotor = new CANTalon(driveTrainRightFrontMotor);

    public static final RobotDrive drive = new RobotDrive(0, 1, 2, 3);

    static {
        //drive.setInvertedMotor(MotorType.kRearRight, true);
        //drive.setInvertedMotor(MotorType.kFrontRight, true);

        //drive.setInvertedMotor(MotorType.kRearLeft, true);
        //drive.setInvertedMotor(MotorType.kFrontLeft, true);
    }

    public final static int GYRO_PORT = 0;
    // gyro instantiation
    public final static Gyro gyro = new AnalogGyro(GYRO_PORT);

    private static final int INTAKE_LEFT_MOTOR_PORT = -1; // TODO
    private static final int INTAKE_RIGHT_MOTOR_PORT = -1; //TODO
    private static final int AIM_MOTOR_PORT = -1; // TODO

    private static final int BOULDER_SWITCH_PORT = -1; // TODO
    private static final int LAUNCHER_BOTTOM_SWITCH_PORT = -1; // TODO
    private static final int LAUNCHER_TOP_SWITCH_PORT = -1; // TODO
    
    private static final int LAUNCHER_SERVO_PORT = -1; //TODO
    // not actual port values

    public static CANTalon intakeLeftMotor = new CANTalon(INTAKE_LEFT_MOTOR_PORT);
    public static CANTalon intakeRightMotor = new CANTalon(INTAKE_RIGHT_MOTOR_PORT);
    public static CANTalon aimMotor = new CANTalon(AIM_MOTOR_PORT);

    public static DigitalInput boulderSwitch = new DigitalInput(BOULDER_SWITCH_PORT);
    public static DigitalInput launcherTopSwitch = new DigitalInput(LAUNCHER_TOP_SWITCH_PORT);
    public static DigitalInput launcherBottomSwitch = new DigitalInput(LAUNCHER_BOTTOM_SWITCH_PORT);

    public static final int SOLENOID_CHANNEL_PRIMARY= 0;
    public static final int SOLENOID_CHANNEL_SECONDARY= 1;
    //public static final int RIGHT_SOLENOID_CHANNEL_PRIMARY= 2;
    //public static final int RIGHT_SOLENOID_CHANNEL_SECONDARY= 3;
    
    public static final DoubleSolenoid doubleSolenoid= new DoubleSolenoid 
                                (SOLENOID_CHANNEL_PRIMARY, SOLENOID_CHANNEL_SECONDARY);
    //public static final DoubleSolenoid rightDoubleSolenoid= new DoubleSolenoid
                          //(RIGHT_SOLENOID_CHANNEL_PRIMARY, RIGHT_SOLENOID_CHANNEL_SECONDARY);

    public static Servo launcherServo = new Servo(LAUNCHER_SERVO_PORT);

}
