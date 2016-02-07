package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class RobotMap {
    // Define channels for the drive train motors
    public static final int driveTrainRightBackMotor = 13;  
    public static final int driveTrainRightFrontMotor = 12;
    
    public static final int driveTrainLeftBackMotor = 11;
    public static final int driveTrainLeftFrontMotor = 10;
    
    

    public static CANTalon leftBackMotor; 
    public static CANTalon rightBackMotor; 
    public static CANTalon leftFrontMotor; 
    public static CANTalon rightFrontMotor;

    // Solenoid for two speed gear system for the drive train
    public static final int SOLENOID_CHANNEL_PRIMARY= 0;
    public static final int SOLENOID_CHANNEL_SECONDARY= 1;
    public static DoubleSolenoid doubleSolenoid;

    /* Gyro specific constants - Initialization takes place in RobotMapInit() */ 
    public final static int GYRO_PORT = 0;
    public static Gyro gyro;

    /* IntakeLauncher specific constants - Initialization takes place in RobotMapInit() */ 
    /* FIXME: Initialize IntakeLauncher's ports */ 
    private static final int INTAKE_LEFT_MOTOR_PORT = 10; 
    private static final int INTAKE_RIGHT_MOTOR_PORT = 11; 
    private static final int AIM_MOTOR_PORT = 12; 
    private static final int LAUNCHER_SERVO_PORT = 13; 
    
    public static CANTalon  intakeLeftMotor;
    public static CANTalon  intakeRightMotor;
    public static CANTalon  aimMotor;
    public static Servo     launcherServo;

    private static final double AIM_MOTOR_FORWARD_SOFT_LIMIT = 99999999.99; // TODO
    private static final double AIM_MOTOR_REVERSE_SOFT_LIMIT = 99999999.99; // TODO

    /* FIXME: to delete as switch connects to Talon's directly */
    private static final int BOULDER_SWITCH_PORT = 20; 
    private static final int LAUNCHER_BOTTOM_SWITCH_PORT = 21;
    private static final int LAUNCHER_TOP_SWITCH_PORT = 22; 

    /* FIXME: to delete as the switches connect directly to Talon */
    public static DigitalInput boulderSwitch;
    public static DigitalInput launcherTopSwitch;
    public static DigitalInput launcherBottomSwitch;

    /* FIXME: to delete as the encoder connect directly to Talon */
    public static Encoder aimEncoder;
    
    /* 
     * Initialize the various robot modules
     */
    public static void init() {
        // conditionally initialize the modules
        if (ModuleManager.DRIVE_MODULE_ON) {
            leftBackMotor = new CANTalon(driveTrainLeftBackMotor);
            rightBackMotor = new CANTalon(driveTrainRightBackMotor);
            leftFrontMotor = new CANTalon(driveTrainLeftFrontMotor);
            rightFrontMotor = new CANTalon(driveTrainRightFrontMotor);
            // TODO: Invert motors here if needed: someMotor.setInverted(true)
            doubleSolenoid = new DoubleSolenoid 
                    (SOLENOID_CHANNEL_PRIMARY, SOLENOID_CHANNEL_SECONDARY);
            /* 
             * TODO: Initialize the Talon drive motors
             * 1. establish follower mode: we have 4 motor controls, but need to give commands to two of them
             * 2. establish speed control mode
             * 3. on motors w/ encoders set feedbackdevice to quadEncoder
             * 4. optional: if driving jerky, set PID values
             */
            
            //follower mode for right side
            
            rightBackMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
            rightBackMotor.set(rightFrontMotor.getDeviceID());
            //follow mode for left side
            leftBackMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
            leftBackMotor.set(leftFrontMotor.getDeviceID());
            
        
            System.out.println("ModuleManager RobotMap Initialize: DriveTrain Nothing to initalize... Moving on!");
        }
        
        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            intakeLeftMotor = new CANTalon(INTAKE_LEFT_MOTOR_PORT);
            intakeRightMotor = new CANTalon(INTAKE_RIGHT_MOTOR_PORT);
            aimMotor = new CANTalon(AIM_MOTOR_PORT);
            launcherServo = new Servo(LAUNCHER_SERVO_PORT);
            // TODO: Initialize intakelauncher motors here, such as limit switches and encoders
            System.out.println("ModuleManager RobotMap initialized: IntakeLauncher");
            
            /* FIXME: to delete as the switches connect directly to Talon */
            boulderSwitch = new DigitalInput(BOULDER_SWITCH_PORT);
            launcherTopSwitch = new DigitalInput(LAUNCHER_TOP_SWITCH_PORT);
            launcherBottomSwitch = new DigitalInput(LAUNCHER_BOTTOM_SWITCH_PORT);

            /* FIXME: to delete as the encoder connect directly to Talon */
            aimEncoder = new Encoder(LAUNCHER_BOTTOM_SWITCH_PORT, LAUNCHER_TOP_SWITCH_PORT, 
                                     false, Encoder.EncodingType.k4X);
            
            // setup the motor
            aimMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
            aimMotor.setForwardSoftLimit(AIM_MOTOR_FORWARD_SOFT_LIMIT);
            aimMotor.setReverseSoftLimit(AIM_MOTOR_REVERSE_SOFT_LIMIT);
            aimMotor.enableForwardSoftLimit(true);
            aimMotor.enableReverseSoftLimit(true);
            aimMotor.ConfigFwdLimitSwitchNormallyOpen(true);
            aimMotor.ConfigRevLimitSwitchNormallyOpen(true);
        }
        
        if (ModuleManager.GYRO_MODULE_ON) {
            System.out.println("ModuleManager RobotMap initalize TODO: TODO Initialize Gyro!");      // gyro instantiation
            gyro = new AnalogGyro(GYRO_PORT);
        }
    }
}
