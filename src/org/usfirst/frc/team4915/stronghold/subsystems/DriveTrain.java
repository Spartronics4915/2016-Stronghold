package org.usfirst.frc.team4915.stronghold.subsystems;
import org.usfirst.frc.team4915.stronghold.Robot;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.ArcadeDrive;
import org.usfirst.frc.team4915.stronghold.utils.IMUPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

    public final static double DEFAULT_SPEED_MAX_OUTPUT = 100.0;  // 100.0 == ~13 ft/sec interpolated from observations
    public final static double MAXIMUM_SPEED_MAX_OUTPUT = 150.0;  // 150.0 == ~20 ft/sec interpolated from observations
    public final static double MAXIMUM_TURN_SPEED = 40.0;  //3-4 ft per sec
    public double turnMultiplier = MEDIUM_TURN; 
    public static final double FAST_TURN = -1;
    public static final double MEDIUM_TURN = -.75;
    public static final double SLOW_TURN = -.4;

    public static RobotDrive robotDrive =
            new RobotDrive(RobotMap.leftMasterMotor, RobotMap.rightMasterMotor);

    private double maxSpeed = 0;

    // support for pid-based turning
    private PIDController m_turnPID;
    private IMUPIDSource m_imu;
    private static final double turnKp = 0.1;
    private static final double turnKi = 0;
    private static final double turnKd = 0.30;
    private static final double turnKf = 0.001;

    public DriveTrain() {
        // TODO: would be nice to migrate stuff from RobotMap here.

        // m_turnPID is used to improve accuracy during auto-turn operations.
        m_imu = new IMUPIDSource();
        m_turnPID = new PIDController(turnKp, turnKi, turnKd, turnKf,
                                      m_imu,
                                      new PIDOutput() {
                    public void pidWrite(double output) {
                        // output is [-1, 1]... we need to
                        // convert this to a speed...
                        Robot.driveTrain.turn(output * MAXIMUM_TURN_SPEED);
                        // robotDrive.tankDrive(-output, output);
                    }
                });
        m_turnPID.setOutputRange(-1, 1);
        m_turnPID.setInputRange(-180, 180);
        m_turnPID.setPercentTolerance(2);
        // m_turnPID.setContuous?
    }

    public void init() {
        this.setMaxOutput(this.getMaxOutput());
        // reset encoders
        RobotMap.leftMasterMotor.setEncPosition(0);
        RobotMap.rightMasterMotor.setEncPosition(0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        System.out.println("DriveTrain getControlModes: " +
        			RobotMap.leftMasterMotor.getControlMode() + "  " +
        			RobotMap.rightMasterMotor.getControlMode());
        setDefaultCommand(new ArcadeDrive());

        robotDrive.setSafetyEnabled(true);
        robotDrive.stopMotor();
    }

    public void arcadeDrive(double driveYstick, double driveXstick) {
        // robotDrive applies maxSpeed, but direct "set" doesn't
        robotDrive.arcadeDrive(driveYstick, driveXstick);
    }

    public double getCurrentHeading() {
        return m_imu.getHeading();
    }

    public void stop() {
        robotDrive.stopMotor();
    }

    public void driveStraight(double speed) {
        // nb: maxspeed isn't applied via set so
        //  speed is supposedly measured in rpm but this depends on our
        //  initialization establishing encoding ticks per revolution.
        //  This is approximate so we rely on the observed values above.
        //  (DEFAULT_SPEED_MAX_OUTPUT)
        RobotMap.leftMasterMotor.set(speed);
        RobotMap.rightMasterMotor.set(-speed);
    }

    // turn takes a speed, not an angle...
    // A negative speed is interpretted as turn left.
    // Note that we bypass application of setMaxOutput Which
    // only applies to calls to robotDrive.
    public void turn(double speed) {
        // In order to turn left, we want the right wheels to go
        // forward and left wheels to go backward (cf: tankDrive)
        // Oddly, our right master motor is reversed, so we compensate here.
        //  speed < 0:  turn left:  rightmotor(negative) (forward),
        //                          leftmotor(negative)  (backward)
        //  speed > 0:  turn right: rightmotor(positive) (backward)
        //                          leftmotor(positive) (forward)
        RobotMap.leftMasterMotor.set(speed);
        RobotMap.rightMasterMotor.set(speed);
    }

    public void startAutoTurn(double degrees) {
        m_turnPID.reset();
        m_turnPID.setSetpoint(degrees);
        m_turnPID.enable();
        // Timer.delay(.2);
        System.out.println("start turning from "
                + roundToHundredths(m_imu.getHeading())
                + " to setpoint " + degrees);
    }

    public boolean isAutoTurning() {
        return m_turnPID.isEnabled();
    }

    public boolean isAutoTurnFinished() {
        System.out.println("is turn done: " + m_turnPID.onTarget()
                + " deg:" + roundToHundredths(m_imu.getHeading())
                + " out:" + roundToHundredths(m_turnPID.get())
                + " err:" + roundToHundredths(m_turnPID.getError()));
        return m_turnPID.onTarget();
    }

    public void endAutoTurn() {
        if(m_turnPID.isEnabled())
            m_turnPID.disable();
    }

    /*
     * Methods to get/set maximum top speed for our robot.
     * Note that this value is only applied by calls to
     * robotDrive that assume [-1, 1] like arcadedrive, tankdrive, etc.
     * When we issue direct set calls (this.driveStraight, etc),
     * maxSpeed is ignored/bypassed.
     */
    private double getMaxOutput() {
        if (maxSpeed == 0) {   /* not initialized, yet */
            return DEFAULT_SPEED_MAX_OUTPUT;
        }
        else {
            return maxSpeed;
        }
    }

    private void setMaxOutput(double maxOutput) {
        if (maxOutput > MAXIMUM_SPEED_MAX_OUTPUT) {
            maxSpeed = MAXIMUM_SPEED_MAX_OUTPUT;
        }
        else {
            maxSpeed = maxOutput;
        }
        robotDrive.setMaxOutput(maxSpeed);
    }


    private double roundToHundredths(double x) {
        if(x > 0)
            return Math.floor(x * 100 + .5) / 100.0;
        else
            return Math.floor(x * 100 - .5) / 100.0;
    }

}
