package org.usfirst.frc.team4915.stronghold.subsystems;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.ArcadeDrive;

import java.util.Arrays;
import java.util.List;

public class DriveTrain extends Subsystem {
    /*
     * FIXME: add RobotDrive initialization and feedback system w/ encoders to
     * RobotMap or DriveTrain constructor
     */

    /* FIXME: the instantiation doesn't follow RobotDrive method parameters */
    public static RobotDrive robotDrive =
            new RobotDrive(RobotMap.leftFrontMotor, RobotMap.leftBackMotor, RobotMap.rightFrontMotor, RobotMap.rightBackMotor);
    public double joystickThrottle;

    // TODO: instead of the analogGyro, we'll be using IMU
    // For Gyro
    public static Gyro gyro = RobotMap.gyro;
    public double deltaGyro = 0;
    public double gyroHeading = 0;
    public double startingAngle = 0;
    DoubleSolenoid doubleSolenoid = RobotMap.doubleSolenoid;
    // DoubleSolenoid leftDoubleSolenoid= RobotMap.leftDoubleSolenoid;

    // motors
    public static List<CANTalon> motors =
            Arrays.asList(RobotMap.leftFrontMotor, RobotMap.leftBackMotor, RobotMap.rightFrontMotor, RobotMap.rightBackMotor);

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        System.out.println("INFO: Initializing the ArcadeDrive");

        setDefaultCommand(new ArcadeDrive());

        this.robotDrive.setSafetyEnabled(true);
        //inverting motors
        this.robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);
        this.robotDrive.setInvertedMotor(MotorType.kRearLeft, true);
        this.robotDrive.setInvertedMotor(MotorType.kFrontRight, true);
        this.robotDrive.setInvertedMotor(MotorType.kRearRight, true);
        
        //checking to see the encoder values
        //this can be removed later. Used to debug
        if (motors.size() > 0){
            for (int i = 0; i < motors.size(); i++){
                System.out.println("The encoder value of motor " + i + " is " + motors.get(i).getEncPosition());
            }
        }
    }

    public double modifyThrottle() {
        double modifiedThrottle = 0.40 * (1.0 * Robot.oi.getJoystickDrive().getAxis(Joystick.AxisType.kThrottle)) + 0.60;
        if (modifiedThrottle != this.joystickThrottle) {
            SmartDashboard.putNumber("Throttle: ", modifiedThrottle);
        }
        setMaxOutput(modifiedThrottle);
        return modifiedThrottle;
    }

    private void setMaxOutput(double topSpeed) {
        this.robotDrive.setMaxOutput(topSpeed);
    }

    public void arcadeDrive(Joystick stick) {
        Robot.driveTrain.trackGyro();
        this.robotDrive.arcadeDrive(stick);
      //checking to see the encoder values
        //this can be removed later. Used to debug
        if (motors.size() > 0){
            for (int i = 0; i < motors.size(); i++){
                System.out.println("The encoder value of motor " + i + " is " + motors.get(i).getEncPosition());
            }
        }

    }

    public void twistDrive(Joystick stick) {
        Robot.driveTrain.trackGyro();
        /*
         * FIXME: should use rotate values rather than twist values 1 to -1 --
         * check the motor mapping correctness
         */
        this.robotDrive.arcadeDrive(stick, Joystick.AxisType.kY.value, stick, Joystick.AxisType.kZ.value);
    }

    public void stop() {
        this.robotDrive.arcadeDrive(0, 0);
    }

    public void calibrateGyro() {
        gyro.reset();
    }

    // Methods for Gyro
    public double trackGyro() {
        this.gyroHeading = -gyro.getAngle() + this.startingAngle;
        System.out.println("Gyro Angle: " + gyro.getAngle());
        System.out.println("Gyro heading:" + this.gyroHeading);
        return this.gyroHeading;
    }

    public void driveStraight(double speed) {
        this.robotDrive.arcadeDrive(speed, 0);
    }

    public void turn(boolean left) {
        System.out.println("public void turn boolean left");
        if (left) {
            System.out.println("turn left");
            robotDrive.arcadeDrive(0, -.5);
        } else {
            System.out.println("turn right");
            robotDrive.arcadeDrive(0, .5);
        }
    }

    public void lowSpeedMode() {
        // switches the gears from high speed to low speed
        // or turns the gears on and goes to low speed mode
        System.out.println("Entering low speed mode");
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        // leftDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Leaving low speed mode");
    }

    public void highSpeedMode() {
        // switches the gears from low speed to high speed
        // or turns the gears on and goes to high speed mode
        System.out.println("Entering high speed mode");
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
        // leftDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
        System.out.println("Leaving high speed mode");
    }
}
