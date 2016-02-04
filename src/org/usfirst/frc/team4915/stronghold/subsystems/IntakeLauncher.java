package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.SetElevatorHeightCommand;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative speed indicates a wheel spinning inwards and positive speed
    // indicates a wheel spinning outwards.
    // Numbers are not correct
    private static final double INTAKE_SPEED = -0.5; //TODO
    private static final double LAUNCH_SPEED = 1.0; //TODO
    private static final double ZERO_SPEED = 0.0; //TODO
    private static final double JOYSTICK_SCALE = 1.0; //TODO
    public static final double ELEVATOR_MIN_HEIGHT = 0; //TODO

    public Joystick aimStick = Robot.oi.aimStick;
    
    public SmartDashboard smartDashboard = Robot.smartDashboard;

    // left and right are determined when standing behind the robot

    // These motors control flywheels that collect and shoot the ball
    public CANTalon intakeLeftMotor = RobotMap.intakeLeftMotor;
    public CANTalon intakeRightMotor = RobotMap.intakeRightMotor;

    // This motor adjust the angle of the launcher for shooting
    public CANTalon aimMotor = RobotMap.aimMotor;

    // limitswitch in the back of the basket that tells the robot when the
    // boulder is secure
    public DigitalInput boulderSwitch = RobotMap.boulderSwitch;

    // limitswitches that tell when the launcher is at the maximum or minumum
    // height
    public DigitalInput launcherBottomSwitch = RobotMap.launcherBottomSwitch;
    public DigitalInput launcherTopSwitch = RobotMap.launcherTopSwitch;

    // this solenoid activates the pneumatic compressor that pushes the boulder
    // into the launcher flywheels
    public Solenoid launcherSolenoid = RobotMap.launcherSolenoid;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SetElevatorHeightCommand(aimStick.getAxis(Joystick.AxisType.kY)));
    }

    // Sets the speed on the flywheels to suck in the boulder
    public void setSpeedIntake() {
        this.intakeLeftMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeRightMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeLeftMotor.set(INTAKE_SPEED);
        this.intakeRightMotor.set(INTAKE_SPEED);
    }

    // Sets the speed on the flywheels to launch the boulder
    public void setSpeedLaunch() {
        this.intakeLeftMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeRightMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeLeftMotor.set(LAUNCH_SPEED);
        this.intakeRightMotor.set(LAUNCH_SPEED);
    }

    // stops the flywheels
    public void setSpeedAbort() {
        this.intakeLeftMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeRightMotor.changeControlMode(TalonControlMode.Speed);
        this.intakeLeftMotor.set(ZERO_SPEED);
        this.intakeRightMotor.set(ZERO_SPEED);
    }

    // Sets the angle of the elevation motors
    // If the current angle is not within acceptable paramaters then set the
    // speed to move towards the intended angle
    // if either limiting limitswitch has been pressed or the target angle has
    // been reached stop the elevator from moving
    public void changeElevatorHeight(double speed) {
        if (!launcherBottomSwitch.get() && !launcherTopSwitch.get()) {
            aimMotor.changeControlMode(TalonControlMode.Speed);
            aimMotor.set(speed * JOYSTICK_SCALE);
        } else {
            aimMotor.set(ZERO_SPEED);
        }
    }

    // pneumatically extends the arm to shove the boulder into the launch
    // flywheels
    public void punchBall() {
        this.launcherSolenoid.set(true);
    }

    // brings back the launch cylinder so it doesn't get in the way
    public void retractCylinder() {
        this.launcherSolenoid.set(false);
    }

    public CANTalon getIntakeLeftMotor() {
        return intakeLeftMotor;
    }

    public CANTalon getIntakeRightMotor() {
        return intakeRightMotor;
    }

    public CANTalon getLauncherAimMotor() {
        return aimMotor;
    }

    public DigitalInput getBoulderSwitch() {
        return boulderSwitch;
    }

    public Solenoid getLauncherSolenoid() {
        return launcherSolenoid;
    }
}
