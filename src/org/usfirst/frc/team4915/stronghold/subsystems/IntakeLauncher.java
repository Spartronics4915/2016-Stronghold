package org.usfirst.frc.team4915.stronghold.subsystems;

import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimLauncherCommand;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeLauncher extends Subsystem {

    // Ranges -1 to 1, negative values are reverse direction
    // Negative values indicate a wheel spinning outwards and positive values
    // indicate a wheel spinning inwards.
    private final double FULL_SPEED_REVERSE = -.60;
    private final double FULL_SPEED_FORWARD = 1;
    private final double LAUNCH_SPEED_FORWARD_LOW = 0.5; //TODO
    private final double ZERO_SPEED = 0.0;
    private final double AIM_DEGREES_SLOP = 2; // TODO: tune this number

    private final double LAUNCHER_MAX_HEIGHT_DEGREES = 45.0; // in degrees from
                                                             // horizontal
    private final double LAUNCHER_MIN_HEIGHT_DEGREES = -11.0; // in degrees from
                                                              // horizontal
    private final double SERVO_LEFT_LAUNCH_POSITION = .45; // in servo units
    private final double SERVO_RIGHT_LAUNCH_POSITION = .65; // in servo units
    private final double SERVO_LEFT_NEUTRAL_POSITION = .75; // in servo units
    private final double SERVO_RIGHT_NEUTRAL_POSITION = .4; // in servo units

    private double launcherMaxHeightTicks = 829.0; // in potentiometer
                                                   // ticks
    private double launcherMinHeightTicks = 570.0; // in potentiometer
                                                   // ticks
    private double launcherNeutralHeightTicks = 719.0; // in
                                                       // potentiometer
                                                       // ticks
    private double launcherTravelHeightTicks = 585.0; // in
                                                      // potentiometer
                                                      // ticks

    private final double MAX_POTENTIOMETER_ERROR = 20;
    
    private final double APPROXIMATE_DANGER = 50;


    private final double JOYSTICK_SCALE = 50.0; // TODO

    private final double MIN_JOYSTICK_MOTION = 0.1;
    
    private boolean isJoystickIdle = false;

    private final double NO_VISION_TARGET = -1000;
    
    private final int POTENTIOMETER_NEGATIVITY = -1;

    private double setPoint; // in potentiometer ticks
    private boolean isPotentiometerScrewed = false;
    private double visionTarget = NO_VISION_TARGET;

    // left and right are determined when standing behind the robot
    // These motors control flywheels that collect and shoot the ball
    private CANTalon intakeLeftMotor = RobotMap.intakeLeftMotor; // ID 15
    private CANTalon intakeRightMotor = RobotMap.intakeRightMotor; // ID 14

    // This motor adjusts the angle of the launcher for shooting
    public CANTalon aimMotor = RobotMap.aimMotor; // ID 16

    // limitswitch in the back of the basket that tells the robot when the
    // boulder is secure
    public DigitalInput boulderSwitch = RobotMap.boulderSwitch;

    // These servos push the boulder into the launcher flywheels
    public Servo launcherServoLeft = RobotMap.launcherServoLeft; // port 0
    public Servo launcherServoRight = RobotMap.launcherServoRight; // port 1

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new AimLauncherCommand());
        // setDefaultCommand(new BackUpJoystickControlCommand());
    }

    public IntakeLauncher() {
        readSetPoint();
    }

    public boolean IsAlive() {
    	return this.aimMotor.isAlive();
    }

    // Sets the speed on the flywheels to suck in the boulder
    public void setSpeedIntake() {
        this.intakeLeftMotor.set(FULL_SPEED_REVERSE);
        this.intakeRightMotor.set(-FULL_SPEED_REVERSE);
    }

    // Sets the speed on the flywheels to launch the boulder
    public void setSpeedLaunch() {
        this.intakeLeftMotor.set(FULL_SPEED_FORWARD);
        this.intakeRightMotor.set(-FULL_SPEED_FORWARD);
    }
    
    public void setSpeedLaunchLow() {
        this.intakeLeftMotor.set(LAUNCH_SPEED_FORWARD_LOW);
    }

    public void stopWheels() {
        this.intakeLeftMotor.set(ZERO_SPEED);
        this.intakeRightMotor.set(ZERO_SPEED);
    }

    public void activateLauncherServos() {
        this.launcherServoLeft.set(SERVO_LEFT_LAUNCH_POSITION);
        this.launcherServoRight.set(SERVO_RIGHT_LAUNCH_POSITION);
    }

    public void retractLauncherServos() {
        this.launcherServoLeft.set(SERVO_LEFT_NEUTRAL_POSITION);
        this.launcherServoRight.set(SERVO_RIGHT_NEUTRAL_POSITION);
    }

    private void readSetPoint() { // TODO rename
        setPoint = getPosition() * POTENTIOMETER_NEGATIVITY;
    }

    public void setElevationDegrees(double deg) {
        setSetPoint(degreesToTicks(deg));
    }

    public double getElevationDegrees() {
        return ticksToDegrees(getPosition());
    }

    public boolean elevationTargetReached(double targetDegrees) {
        double currentElevation = getElevationDegrees();
        if(Math.abs(this.visionTarget - currentElevation) < AIM_DEGREES_SLOP)
            return true;
        else
            return false;
    }

    // changes the set point to a value
    public void setSetPoint(double newSetPoint) {
        setPoint = newSetPoint;
    }

    // changes the set point based on an offset
    private void offsetSetPoint(double offset) {
        setPoint += offset;
        SmartDashboard.putNumber("Offset: ", offset);
        SmartDashboard.putNumber("Moving to setPoint", getSetPoint());
    }

    // sets the set point with the joystick and moves to set point
    private void trackJoystick() {
        moveLauncherWithJoystick();
        moveToSetPoint();
    }

    // sets the set point with vision and moves to set point
    private void trackVision() {
        VisionState vs = VisionState.getInstance();
        if(vs == null) return;

    	if(!vs.LauncherLockedOnTarget) {
            double currentElevation = getElevationDegrees();
            if(this.visionTarget == NO_VISION_TARGET) {
                this.visionTarget = vs.getTargetElevation(currentElevation);
                setSetPoint(degreesToTicks(this.visionTarget)); // TODO: verify sign
            }
            if(Math.abs(this.visionTarget - currentElevation) < AIM_DEGREES_SLOP) {
    			vs.LauncherLockedOnTarget = true;
                this.visionTarget = NO_VISION_TARGET; // ok since we're locked
    			System.out.println("Stopping aimer!");
    		}
            else {
                moveToSetPoint();
            }
    	}
    	else if (vs.DriveLockedOnTarget) {
    		//shoot
    		//Leave AutoAimMode
    	}
    	else {
    		//Do nothing, waiting for driveTrain to get into position
    	}
    }

    // changes the set point based on the joystick
    private void moveLauncherWithJoystick() {
        double joystickY = Robot.oi.aimStick.getAxis((Joystick.AxisType.kY));
        if (Math.abs(joystickY) > MIN_JOYSTICK_MOTION) {
            if(isJoystickIdle) {
            	aimMotor.enableControl();
            	isJoystickIdle = false;
            	System.out.println("Enabling Aim Control");
            }
        	readSetPoint();
            offsetSetPoint(joystickY * JOYSTICK_SCALE * POTENTIOMETER_NEGATIVITY);
        } else {
        	if(!isJoystickIdle) {
        		aimMotor.disableControl();
        		isJoystickIdle = true;
        		System.out.println("Disabling Aim Control");
        	}
        }
        	//aimMotor.disableControl();
    }

    // aimLauncher is invoked from AimLauncherCommand which is installed
    // by AutoCommand1 when the associated strategy is chosen.
    // Checks to see if joystick control or vision control is needed and
    // controls motion.
    public void aimLauncher() {
    	//System.out.println("aimLauncher called");
        //System.out.println("Potentiometer value: " + getPosition());
        SmartDashboard.putNumber("Launch Angle", (int) ticksToDegrees(getPosition()));
        if (VisionState.getInstance().wantsControl()) {
        	//System.out.println("Tracking vision!");
            trackVision();
        } else {
            trackJoystick();
        }
    }

    // sets the launcher position to the current set point
    public void moveToSetPoint() {
        //keepSetPointInRange();
        calibratePotentiometer();
        aimMotor.changeControlMode(TalonControlMode.Position);
        aimMotor.set(setPoint);
        dangerTest();
    }

    public void launcherSetNeutralPosition() {
        setSetPoint(launcherNeutralHeightTicks * POTENTIOMETER_NEGATIVITY);
    }

    public void launcherSetIntakePosition() {
        setSetPoint(launcherTravelHeightTicks * POTENTIOMETER_NEGATIVITY);
    }

    public void launcherJumpToAngle(double angle) {
        setSetPoint(degreesToTicks(angle) * POTENTIOMETER_NEGATIVITY);
    }

    // makes sure the set point doesn't go outside its max or min range
    private void keepSetPointInRange() {
        if (getSetPoint() < launcherMinHeightTicks - APPROXIMATE_DANGER) {
            setPoint = launcherMinHeightTicks * POTENTIOMETER_NEGATIVITY;
        }
        if(getSetPoint() > launcherMaxHeightTicks + APPROXIMATE_DANGER) {
            setPoint = launcherMaxHeightTicks * POTENTIOMETER_NEGATIVITY;
        }
    }
    
    private void calibratePotentiometer() {
        if(isLauncherAtBottom()) {
            launcherMinHeightTicks = getPosition();
        }
        if(isLauncherAtTop()) {
            launcherMaxHeightTicks = getPosition();
        }
    }

    private void dangerTest() {
        if((isLauncherAtBottom() && Math.abs(getPosition() - launcherMinHeightTicks) > MAX_POTENTIOMETER_ERROR) || (isLauncherAtTop() && Math.abs(getPosition() - launcherMaxHeightTicks) > MAX_POTENTIOMETER_ERROR)) {
            isPotentiometerScrewed = true;
        }
    }

    private double degreesToTicks(double degrees) {
        double heightRatio = (degrees - LAUNCHER_MIN_HEIGHT_DEGREES) / (LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES);
        return launcherMinHeightTicks + (launcherMaxHeightTicks - launcherMinHeightTicks) * heightRatio;
    }

    private double ticksToDegrees(double ticks) {
        double heightRatio = (ticks - launcherMinHeightTicks) / (launcherMaxHeightTicks - launcherMinHeightTicks);
        return LAUNCHER_MIN_HEIGHT_DEGREES + (LAUNCHER_MAX_HEIGHT_DEGREES - LAUNCHER_MIN_HEIGHT_DEGREES) * heightRatio;
    }

    public boolean isLauncherAtTop() {
        return aimMotor.isRevLimitSwitchClosed();
    }

    public boolean isLauncherAtBottom() {
        return aimMotor.isFwdLimitSwitchClosed();
    }

    public boolean isBoulderLoaded() {
        return boulderSwitch.get();
    }

    public double getPosition() {
        return aimMotor.getPosition() * POTENTIOMETER_NEGATIVITY;
    }

    public double getSetPoint() {
        return setPoint * POTENTIOMETER_NEGATIVITY;
    }

    public boolean getIsPotentiometerScrewed() {
        return isPotentiometerScrewed;
    }

    public void backUpJoystickMethod() {
        aimMotor.changeControlMode(TalonControlMode.PercentVbus);
        aimMotor.set(Robot.oi.aimStick.getAxis((Joystick.AxisType.kY)));
    }
}
