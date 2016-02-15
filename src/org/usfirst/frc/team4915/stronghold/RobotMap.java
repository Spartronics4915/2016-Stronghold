package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team4915.stronghold.utils.BNO055;

public class RobotMap {

    // Define channels for the drive train motors
    private static final int DRIVE_TRAIN_RIGHT_BACK_MOTOR_ID = 13;
    private static final int DRIVE_TRAIN_RIGHT_FRONT_MOTOR_ID = 12;
    private static final int DRIVE_TRAIN_LEFT_BACK_MOTOR_ID = 11;
    private static final int DRIVE_TRAIN_LEFT_FRONT_MOTOR_ID = 10;

    // Define channels for two speed gear system for the drive train
    private static final int SOLENOID_CHANNEL_PRIMARY = 0;
    private static final int SOLENOID_CHANNEL_SECONDARY = 1;

    // Define port for the gyro
    public final static int GYRO_PORT = 0;

    // Define channels for IntakeLauncher motors
    private static final int INTAKE_LEFT_MOTOR_ID = 14;
    private static final int INTAKE_RIGHT_MOTOR_ID = 15;
    private static final int AIM_MOTOR_ID = 16;

    // Define port for the boulder switch
    private static final int BOULDER_SWITCH_PORT = 2;
    
    // Define port for the launcher pneumatic
    private static final int LAUNCHER_SOLENOID_PORT = 47; //TODO

    // Define channels for scaling motors
    private static final int SCALING_MOTOR_ID = 18; // TODO
    private static final int SCALING_WINCH_ID = 19; // TODO

    private static final int AIMER_P = 0;
    private static final int AIMER_I = 0;
    private static final int AIMER_D = 0;
    
    
    // Create motor controllers for the driveTrain
    public static CANTalon leftBackMotor;
    public static CANTalon rightBackMotor;
    public static CANTalon leftFrontMotor;
    public static CANTalon rightFrontMotor;

    // Create solenoid for the drivetrain
    public static DoubleSolenoid doubleSolenoid;

    // Create the gyro
    public static AnalogGyro gyro;
    
    //Create IMU
    public static BNO055 imu;

    // Create the motor controllers for the IntakeLauncher
    public static CANTalon intakeLeftMotor;
    public static CANTalon intakeRightMotor;
    public static CANTalon aimMotor;

    // Create the boulder switch
    public static DigitalInput boulderSwitch;
    
    // Create the launcher solenoid
    public static Solenoid launcherSolenoid;
    
    // Create the motor controllers for the Scaler
    public static CANTalon scalingMotor;
    public static CANTalon scalingWinch;

    // Initialize the various robot modules
    public static void init() {
        // conditionally initialize the modules
        if (ModuleManager.DRIVE_MODULE_ON) {
            leftBackMotor = new CANTalon(DRIVE_TRAIN_LEFT_BACK_MOTOR_ID);
            rightBackMotor = new CANTalon(DRIVE_TRAIN_RIGHT_BACK_MOTOR_ID);
            leftFrontMotor = new CANTalon(DRIVE_TRAIN_LEFT_FRONT_MOTOR_ID);
            rightFrontMotor = new CANTalon(DRIVE_TRAIN_RIGHT_FRONT_MOTOR_ID);
            // TODO: Invert motors here if needed: someMotor.setInverted(true)
            /*
             * TODO: Initialize the Talon drive motors 1. establish follower
             * mode: we have 4 motor controls, but need to give commands to two
             * of them 2. establish speed control mode 3. on motors w/ encoders
             * set feedbackdevice to quadEncoder 4. optional: if driving jerky,
             * set PID values
             */
            // THe front motors are the follower motors
            // follower mode for right side
            rightFrontMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
            rightFrontMotor.set(rightBackMotor.getDeviceID());
            // follow mode for left side
            leftFrontMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
            leftFrontMotor.set(leftBackMotor.getDeviceID());

            System.out.println("ModuleManager RobotMap Initialize: DriveTrain Nothing to initalize... Moving on!");
        }
        if (ModuleManager.GEARSHIFT_MODULE_ON) {
            doubleSolenoid = new DoubleSolenoid(SOLENOID_CHANNEL_PRIMARY, SOLENOID_CHANNEL_SECONDARY);
        }

        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            intakeLeftMotor = new CANTalon(INTAKE_LEFT_MOTOR_ID);
            intakeRightMotor = new CANTalon(INTAKE_RIGHT_MOTOR_ID);
            aimMotor = new CANTalon(AIM_MOTOR_ID);
            intakeLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
            intakeRightMotor.changeControlMode(TalonControlMode.PercentVbus);
            aimMotor.changeControlMode(TalonControlMode.Position);
            boulderSwitch = new DigitalInput(BOULDER_SWITCH_PORT);
            launcherSolenoid = new Solenoid(LAUNCHER_SOLENOID_PORT);
            System.out.println("ModuleManager RobotMap initialized: IntakeLauncher");

            // setup the motor
            if (aimMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != null) {
                aimMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
                aimMotor.enableLimitSwitch(true, true);
                aimMotor.enableBrakeMode(true);
                aimMotor.setPID(AIMER_P, AIMER_I, AIMER_D);
            }
            LiveWindow.addActuator("IntakeLauncher", "AimMotor", aimMotor);
        }

        if (ModuleManager.GYRO_MODULE_ON) {
            System.out.println("ModuleManager RobotMap initalize. Initialize Gyro!");
            gyro = new AnalogGyro(GYRO_PORT);
        }

        if (ModuleManager.SCALING_MODULE_ON) {
            System.out.println("ModuleManager RobotMap Initialize: Scaling");
            scalingMotor = new CANTalon(SCALING_MOTOR_ID);
            scalingWinch = new CANTalon(SCALING_WINCH_ID);
        }
        if (ModuleManager.IMU_MODULE_ON) {
            System.out.println("ModuleManager RobotMap Initialize: IMU");
            imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER);
        }
    }
}
