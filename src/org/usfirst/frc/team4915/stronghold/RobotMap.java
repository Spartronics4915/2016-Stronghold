package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;

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

    private static final int INTAKE_LEFT_MOTOR_PORT = -1; // TODO
    private static final int INTAKE_RIGHT_MOTOR_PORT = -1; //TODO
    private static final int AIM_MOTOR_PORT = -1; // TODO

    private static final int BOULDER_SWITCH_PORT = -1; // TODO
    private static final int LAUNCHER_BOTTOM_SWITCH_PORT = -1; // TODO
    private static final int LAUNCHER_TOP_SWITCH_PORT = -1; // TODO
    
    private static final int LAUNCHER_SERVO_PORT = -1; //TODO
    // not actual port values
    
    private static final double AIM_MOTOR_FORWARD_SOFT_LIMIT = 99999999.99; //TODO
    private static final double AIM_MOTOR_REVERSE_SOFT_LIMIT = 99999999.99; //TODO

    public static CANTalon intakeLeftMotor = new CANTalon(INTAKE_LEFT_MOTOR_PORT);
    public static CANTalon intakeRightMotor = new CANTalon(INTAKE_RIGHT_MOTOR_PORT);
    public static CANTalon aimMotor = new CANTalon(AIM_MOTOR_PORT);

    public static DigitalInput boulderSwitch = new DigitalInput(BOULDER_SWITCH_PORT);
    public static DigitalInput launcherTopSwitch = new DigitalInput(LAUNCHER_TOP_SWITCH_PORT);
    public static DigitalInput launcherBottomSwitch = new DigitalInput(LAUNCHER_BOTTOM_SWITCH_PORT);
    public static Servo launcherServo = new Servo(LAUNCHER_SERVO_PORT);
    
    public static Encoder aimEncoder = new Encoder(LAUNCHER_BOTTOM_SWITCH_PORT, LAUNCHER_TOP_SWITCH_PORT, false, Encoder.EncodingType.k4X);
    
    public RobotMap() {
        aimMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        aimMotor.setForwardSoftLimit(AIM_MOTOR_FORWARD_SOFT_LIMIT);
        aimMotor.setReverseSoftLimit(AIM_MOTOR_REVERSE_SOFT_LIMIT);
        aimMotor.enableForwardSoftLimit(true);
        aimMotor.enableReverseSoftLimit(true);
        aimMotor.ConfigFwdLimitSwitchNormallyOpen(true);
        aimMotor.ConfigRevLimitSwitchNormallyOpen(true);
    }
}
