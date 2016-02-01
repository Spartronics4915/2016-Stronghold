
package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.ArcadeDrive;

public class DriveTrain extends Subsystem {

    RobotDrive robotDrive = new RobotDrive(RobotMap.leftFrontMotor, RobotMap.leftBackMotor, RobotMap.rightFrontMotor, RobotMap.rightBackMotor);
    public double joystickThrottle;
    DoubleSolenoid doubleSolenoid= RobotMap.doubleSolenoid;
    //DoubleSolenoid leftDoubleSolenoid= RobotMap.leftDoubleSolenoid;


    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	System.out.println("INFO: Initializing the ArcadeDrive");
        setDefaultCommand(new ArcadeDrive());

       	robotDrive.setSafetyEnabled(true);
    }

    public double modifyThrottle() {
        double modifiedThrottle = 0.40 * (1.0 * Robot.oi.getJoystickDrive().getAxis(Joystick.AxisType.kThrottle)) + 0.60;
        if (modifiedThrottle != joystickThrottle) {
            SmartDashboard.putNumber("Throttle: ", modifiedThrottle);
        }
        setMaxOutput(modifiedThrottle);
        return modifiedThrottle;
    }

    private void setMaxOutput(double topSpeed) {
        // TODO Auto-generated method stub
        robotDrive.setMaxOutput(topSpeed);	
 
    }

    public void arcadeDrive(Joystick stick) {
        // TODO Auto-generated method stub
        robotDrive.arcadeDrive(stick);
    }

    public void stop() {
        // TODO Auto-generated method stub
        robotDrive.arcadeDrive(0, 0);
    }
    
    public void lowSpeedMode() {
        //switches the gears from high speed to low speed
        //or turns the gears on and goes to low speed mode
        System.out.println("Entering low speed mode");
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        //leftDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Leaving low speed mode");
    }
    public void highSpeedMode() {
        //switches the gears from low speed to high speed
        //or turns the gears on and goes to high speed mode
        System.out.println("Entering high speed mode");
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
        //leftDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
        System.out.println("Leaving high speed mode");
    }
}
