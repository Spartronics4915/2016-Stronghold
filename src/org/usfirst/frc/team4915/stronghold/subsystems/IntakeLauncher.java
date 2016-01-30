package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4915.stronghold.RobotMap;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative speed indicates a wheel spinning inwards and positive speed
    // indicates a wheel spinning outwards.
    public static final double INTAKE_SPEED = -0.5;
    public static final double LAUNCH_SPEED = 1.0;
    public static final double ZERO_SPEED = 0.0;
    public static final double ELEVATOR_SPEED = 0.25;
    public static final double ELEVATOR_MAX_HEIGHT = 0.0;
    public static final double ELEVATOR_MIN_HEIGHT = 0.0;
    public static final double JOYSTICK_SCALE = 1.0;

    // left and right are determined when standing behind the robot

    // These motors collect and shoot the ball
    public CANTalon intakeLeftMotor = RobotMap.intakeLeftMotor;
    public CANTalon intakeRightMotor = RobotMap.intakeRightMotor;

    // These motors elevate the launcher for shooting
    public CANTalon launcherLeftMotor = RobotMap.launcherLeftMotor;
    public CANTalon launcherRightMotor = RobotMap.launcherRightMotor;

    // limitswitch in the back of the basket that tells the robot when the
    // boulder is secure
    public DigitalInput boulderSwitch = RobotMap.boulderSwitch;

    // this solenoid activates the pneumatic compressor that pushes the boulder
    // into the launcher flywheels
    public Solenoid launcherSolenoid = RobotMap.launcherSolenoid;
    public Compressor launcherCompressor = RobotMap.launcherCompressor;

    public IntakeLauncher() {
        this.launcherLeftMotor.changeControlMode(TalonControlMode.Speed);
        this.launcherRightMotor.changeControlMode(TalonControlMode.Speed);
        this.launcherLeftMotor.set(ELEVATOR_SPEED);
        this.launcherRightMotor.set(ELEVATOR_SPEED);
    }

    @Override
    protected void initDefaultCommand() {

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
    public void setElevatorHeightWithJoystick(Joystick joystick) {
        double height = joystick.getAxis(Joystick.AxisType.kY) * JOYSTICK_SCALE;
        if (height >= ELEVATOR_MIN_HEIGHT && height <= ELEVATOR_MAX_HEIGHT) {
            this.launcherRightMotor.changeControlMode(TalonControlMode.Position);
            this.launcherLeftMotor.changeControlMode(TalonControlMode.Position);
            this.launcherRightMotor.set(height);
            this.launcherLeftMotor.set(height);
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

    public void toggleLauncherClosedLoopControl() {
        if (this.launcherCompressor.getClosedLoopControl()) {
            this.launcherCompressor.setClosedLoopControl(false);
        } else {
            this.launcherCompressor.setClosedLoopControl(true);
        }
    }
}
