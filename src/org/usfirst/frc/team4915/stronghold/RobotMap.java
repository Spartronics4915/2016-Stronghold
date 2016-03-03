package org.usfirst.frc.team4915.stronghold;

import java.util.ArrayList;

import org.usfirst.frc.team4915.stronghold.utils.BNO055;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RobotMap {

    // Define channels for the drive train motors
    private static final int DRIVE_TRAIN_RIGHT_FOLLOWER = 13;
    private static final int DRIVE_TRAIN_RIGHT_MASTER = 12;
    private static final int DRIVE_TRAIN_LEFT_FOLLOWER = 11;
    private static final int DRIVE_TRAIN_LEFT_MASTER = 10;

    // Define channels for two speed gear system for the drive train
    private static final int SOLENOID_CHANNEL_PRIMARY = 0;
    private static final int SOLENOID_CHANNEL_SECONDARY = 1;

    // Define channels for IntakeLauncher motors
    private static final int INTAKE_LEFT_MOTOR_ID = 15;
    private static final int INTAKE_RIGHT_MOTOR_ID = 14;
    private static final int AIM_MOTOR_ID = 16;

    // Define port for the boulder switch
    private static final int BOULDER_SWITCH_PORT = 0;

    // Define ports for the launcher servos
    private static final int LAUNCHER_SERVO_LEFT_PORT = 0;
    private static final int LAUNCHER_SERVO_RIGHT_PORT = 1;

    // Define channels for scaling motors
    private static final int SCALING_MOTOR_ID = 18; // TODO
    private static final int SCALING_WINCH_ID = 19; // TODO

    private static final int PHOTONIC_CANNON_ID = 4;

    // private static final int AIMER_P = 0; //TODO uncomment
    // private static final int AIMER_I = 0; //TODO uncomment
    // private static final int AIMER_D = 0; //TODO uncomment

    // Create motor controllers for the driveTrain
    public static CANTalon leftMasterMotor;
    public static CANTalon rightMasterMotor;
    public static CANTalon leftFollowerMotor;
    public static CANTalon rightFollowerMotor;

    // Create solenoid for the drivetrain
    public static DoubleSolenoid doubleSolenoid;

    // Create IMU
    public static BNO055 imu;

    // Create the motor controllers for the IntakeLauncher
    public static CANTalon intakeLeftMotor;
    public static CANTalon intakeRightMotor;
    public static CANTalon aimMotor;

    // Create the boulder switch
    public static DigitalInput boulderSwitch;

    // Create the launcher solenoid
    public static Servo launcherServoLeft;
    public static Servo launcherServoRight;

    // Create the motor controllers for the Scaler
    public static CANTalon scalingMotor;
    public static CANTalon scalingWinch;

    public static final DigitalOutput PHOTONIC_CANNON = new DigitalOutput(PHOTONIC_CANNON_ID);

    // Drive train QuadEncoder calibration
    /**
     * Wheel is connected to 48T sprocket, chain driven by 15T sprocket. 15T
     * sprocket is connected to encoder via 3:1 geartrain (1 turn of 15T
     * sprocket is 3 turns of encoder). Encoder is 256 cycles per revolution.
     * Multiply by 4 to get 1024 counts per revolution at Talon.
     * 
     * 1 wheel rotation = (48 / 15) * 3 * 1024 encoder ticks = 9830.4 encoder
     * ticks per wheel revolution
     * 
     * Verified experimentally by rotating a wheel 360 degrees and comparing
     * before and after tick counts.
     */
    public static final int quadTicksPerWheelRev = 9830;
    public static final double wheelDiameterInInches = 14.0;
    public static final double wheelCircumferenceInInches = wheelDiameterInInches * Math.PI;
    public static final double quadTicksPerInch = quadTicksPerWheelRev / wheelCircumferenceInInches;

    // Initialize the various robot modules
    public static void init() {
        CANProbe cp = new CANProbe();
        ArrayList<String> canDevices = cp.Find();
        System.out.println("RobotMap.init() CAN devices:" + canDevices);
        // conditionally initialize the modules
        if (ModuleManager.DRIVE_MODULE_ON) {

            // STEP 1: instantiate the motor controllers
            leftMasterMotor = new CANTalon(DRIVE_TRAIN_LEFT_MASTER);
            rightMasterMotor = new CANTalon(DRIVE_TRAIN_RIGHT_MASTER);
            leftFollowerMotor = new CANTalon(DRIVE_TRAIN_LEFT_FOLLOWER);
            rightFollowerMotor = new CANTalon(DRIVE_TRAIN_RIGHT_FOLLOWER);


            // Step 2: Configure the follower Talons: left & right back motors
            leftFollowerMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
            leftFollowerMotor.set(leftMasterMotor.getDeviceID());

            rightFollowerMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
            rightFollowerMotor.set(rightMasterMotor.getDeviceID());

            // STEP 3: Setup speed control mode for the master Talons
            leftMasterMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
            rightMasterMotor.changeControlMode(CANTalon.TalonControlMode.Speed);

            // STEP 4: Indicate the feedback device used for closed-loop
            // For speed mode, indicate the ticks per revolution
            leftMasterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
            rightMasterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
            leftMasterMotor.configEncoderCodesPerRev(quadTicksPerWheelRev);
            rightMasterMotor.configEncoderCodesPerRev(quadTicksPerWheelRev);

            // STEP 5: Set PID values & closed loop error
            leftMasterMotor.setPID(0.22, 0, 0);
            rightMasterMotor.setPID(0.22, 0, 0);
            

            // Add ramp up rate
            leftMasterMotor.setVoltageRampRate(48.0); // max allowable voltage
                                                      // change /sec: reach to
                                                      // 12V after 1sec
            rightMasterMotor.setVoltageRampRate(48.0);

            // Add SmartDashboard controls for testing
            // Add SmartDashboard live window
            LiveWindow.addActuator("Drive Train", "Left Master 10", leftMasterMotor);
            LiveWindow.addActuator("Drive Train", "Right Master 12", rightMasterMotor);

            System.out.println("ModuleManager RobotMap Initialized: DriveTrain!");

        }
        
        if (ModuleManager.GEARSHIFT_MODULE_ON) {
            doubleSolenoid = new DoubleSolenoid(SOLENOID_CHANNEL_PRIMARY, SOLENOID_CHANNEL_SECONDARY);
        }

        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            /*
             * here we look for signs that the launcher is present and disable
             * it if signs aren't there.
             */
            boolean intakeFound = false;
            for (int i = 0; i < canDevices.size(); i++) {
                if (canDevices.get(i) == "SRX 14") {
                    intakeFound = true;
                    break;
                }
            }
            if (intakeFound) {
                intakeLeftMotor = new CANTalon(INTAKE_LEFT_MOTOR_ID);
                intakeRightMotor = new CANTalon(INTAKE_RIGHT_MOTOR_ID);
                intakeLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
                intakeRightMotor.changeControlMode(TalonControlMode.PercentVbus);
                intakeLeftMotor.reverseSensor(true);
                aimMotor = new CANTalon(AIM_MOTOR_ID);
                aimMotor.changeControlMode(TalonControlMode.Position);
                boulderSwitch = new DigitalInput(BOULDER_SWITCH_PORT);
                launcherServoLeft = new Servo(LAUNCHER_SERVO_LEFT_PORT);
                launcherServoRight = new Servo(LAUNCHER_SERVO_RIGHT_PORT);

                // setup the motor
                if (aimMotor.isSensorPresent(FeedbackDevice.AnalogPot) != null) {
                    aimMotor.setFeedbackDevice(FeedbackDevice.AnalogPot);
                    aimMotor.enableLimitSwitch(true, true);
                    aimMotor.enableBrakeMode(true);
                    aimMotor.reverseSensor(true);
                    aimMotor.setAllowableClosedLoopErr(15);
                    // aimMotor.setPID(AIMER_P, AIMER_I, AIMER_D); //TODO
                    // uncomment
                }
                LiveWindow.addActuator("IntakeLauncher", "AimMotor", aimMotor);
                System.out.println("ModuleManager RobotMap initialized: IntakeLauncher");
            } else {
                ModuleManager.INTAKELAUNCHER_MODULE_ON = false;
                System.out.println("RobotMap disabled IntakeLauncher (SRX 14 not found)");
            }

        }

        if (ModuleManager.SCALING_MODULE_ON) {
            scalingMotor = new CANTalon(SCALING_MOTOR_ID);
            scalingWinch = new CANTalon(SCALING_WINCH_ID);
            System.out.println("ModuleManager RobotMap Initialized: Scaling");
        }
        if (ModuleManager.IMU_MODULE_ON) {
            imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,
                    BNO055.vector_type_t.VECTOR_EULER);
            // imuLinAcc =
            // BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,
            // BNO055.vector_type_t.VECTOR_LINEARACCEL);
            System.out.println("ModuleManager RobotMap Initialized: IMU");
        }
    }
}
