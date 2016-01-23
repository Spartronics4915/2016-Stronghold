
package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.ArcadeDrive;

public class DriveTrain extends Subsystem {

    RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;
    DoubleSolenoid rightDoubleSolenoid = RobotMap.rightDoubleSolenoid;
    DoubleSolenoid leftDoubleSolenoid = RobotMap.leftDoubleSolenoid;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        // set the default command for a new arcade drive
        setDefaultCommand(new ArcadeDrive());
    }

    public void arcadeDrive(Joystick stick) {
        robotDrive.arcadeDrive(stick);
    }

    public void lowSpeedMode(DoubleSolenoid solenoid) {
        // switches the gears from high speed to low speed
        // or turns the gears on and goes to low speed mode?
        rightDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        leftDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void highSpeedMode(DoubleSolenoid solenoid) {
        // switches the gears from low speed to high speed
        // or turns the gears on and goes to high speed mode
        rightDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
        leftDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void stopMotor(DoubleSolenoid solenoid) {
        // turns off the motor
        rightDoubleSolenoid.set(DoubleSolenoid.Value.kOff);
        leftDoubleSolenoid.set(DoubleSolenoid.Value.kOff);
    }
}
