package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.SetElevatorHeightCommand;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative speed indicates a wheel spinning inwards and positive speed
    // indicates a wheel spinning outwards.
    // Numbers are not correct
    private static final double INTAKE_SPEED = -0.5;
    private static final double LAUNCH_SPEED = 1.0;
    private static final double ZERO_SPEED = 0.0;
    private static final double ELEVATOR_SPEED = 0.15;
    private static final double JOYSTICK_SCALE = 1.0;
    private static final double ELEVATOR_MARGIN_OF_ERROR = .1;
    public static final double ELEVATOR_MIN_HEIGHT = 0;

    // left and right are determined when standing behind the robot

    // These motors control flywheels that collect and shoot the ball
    public CANTalon intakeLeftMotor = RobotMap.intakeLeftMotor;
    public CANTalon intakeRightMotor = RobotMap.intakeRightMotor;

    // These motors adjust the angle of the launcher for shooting
    public CANTalon launcherLeftMotor = RobotMap.launcherLeftMotor;
    public CANTalon launcherRightMotor = RobotMap.launcherRightMotor;

    // limitswitch in the back of the basket that tells the robot when the
    // boulder is secure
    public DigitalInput boulderSwitch = RobotMap.boulderSwitch;

    // limitswitches that tell when the launcher is at the maximum or minumum
    // height
    public DigitalInput launcherBottomSwitch = RobotMap.launcherBottomSwitch;
    public DigitalInput launcherTopSwitch = RobotMap.launcherTopSwitch;

    // encoder that detects the angle the launcher is at
    public Encoder launcherAngleEncoder = RobotMap.launcherAngleEndoder;

    // this solenoid activates the pneumatic compressor that pushes the boulder
    // into the launcher flywheels
    public Solenoid launcherSolenoid = RobotMap.launcherSolenoid;
    public Compressor launcherCompressor = RobotMap.launcherCompressor;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SetElevatorHeightCommand(Robot.oi.launcherStick.getAxis(AxisType.kY) * JOYSTICK_SCALE, launcherAngleEncoder.get()));
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
    public void changeElevatorHeight(double targetHeight) {
        if (!launcherBottomSwitch.get() && !launcherTopSwitch.get()) {
            if (Math.abs(launcherAngleEncoder.get() - targetHeight) <= ELEVATOR_MARGIN_OF_ERROR) {
                this.launcherRightMotor.changeControlMode(TalonControlMode.Speed);
                this.launcherLeftMotor.changeControlMode(TalonControlMode.Speed);
                if (launcherAngleEncoder.get() > targetHeight) {
                    launcherRightMotor.set(ELEVATOR_SPEED);
                    launcherLeftMotor.set(ELEVATOR_SPEED);
                } else {
                    launcherRightMotor.set(-ELEVATOR_SPEED);
                    launcherLeftMotor.set(-ELEVATOR_SPEED);
                }
            } else {
                launcherRightMotor.set(ZERO_SPEED);
                launcherLeftMotor.set(ZERO_SPEED);
            }
        } else {
            launcherRightMotor.set(ZERO_SPEED);
            launcherLeftMotor.set(ZERO_SPEED);
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

    public void zeroLauncherEncoder() {
        launcherAngleEncoder.reset();
    }
}
