
package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.ArcadeDrive;

import java.util.Arrays;
import java.util.List;

public class DriveTrain extends Subsystem {

    public static RobotDrive robotDrive = new RobotDrive(RobotMap.leftFrontMotor, RobotMap.leftBackMotor, RobotMap.rightFrontMotor, RobotMap.rightBackMotor);
    public double joystickThrottle;
    // DoubleSolenoid rightDoubleSolenoid = RobotMap.rightDoubleSolenoid;
    // DoubleSolenoid leftDoubleSolenoid = RobotMap.leftDoubleSolenoid;
    // For Gyro
    public static Gyro gyro = RobotMap.gyro;
    public double deltaGyro = 0;
    public double gyroHeading = 0;
    public double startingAngle = 0;
    // motors
    public static List<CANTalon> motors =
            Arrays.asList(RobotMap.leftFrontMotor, RobotMap.leftBackMotor, RobotMap.rightFrontMotor, RobotMap.rightBackMotor);

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        System.out.println("INFO: Initializing the ArcadeDrive");

        setDefaultCommand(new ArcadeDrive());

        this.robotDrive.setSafetyEnabled(true);
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
    }

    public void twistDrive(Joystick stick) {
        Robot.driveTrain.trackGyro();
        this.robotDrive.arcadeDrive(stick, Joystick.AxisType.kY.value, stick, Joystick.AxisType.kZ.value);
    }

    public void lowSpeedMode() {
        // switches the gears from high speed to low speed
        // or turns the gears on and goes to low speed moe
        // rightDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        // leftDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void highSpeedMode() {
        // switches the gears from low speed to high speed
        // or turns the gears on and goes to high speed mode
        // rightDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
        // leftDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void stop() {
        this.robotDrive.arcadeDrive(0, 0);
        // rightDoubleSolenoid.set(DoubleSolenoid.Value.kOff);
        // leftDoubleSolenoid.set(DoubleSolenoid.Value.kOff);
    }

    public void calibrateGyro() {
        gyro.reset();
    }

    // Methods for Gyro
    public double trackGyro() {
        this.gyroHeading = -gyro.getAngle() + this.startingAngle;
        System.out.println("Gyro Angle: " + gyro.getAngle() );
        System.out.println("Gyro heading:" + this.gyroHeading);
        return this.gyroHeading;
    }

    public void driveStraight(double speed) {
        this.robotDrive.arcadeDrive(speed, 0);
    }
}
