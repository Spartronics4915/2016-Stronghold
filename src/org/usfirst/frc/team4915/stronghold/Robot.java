
package org.usfirst.frc.team4915.stronghold;

import org.usfirst.frc.team4915.stronghold.commands.AutoCommand1;
import org.usfirst.frc.team4915.stronghold.subsystems.Autonomous;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;
import org.usfirst.frc.team4915.stronghold.subsystems.GearShift;
import org.usfirst.frc.team4915.stronghold.subsystems.IntakeLauncher;
import org.usfirst.frc.team4915.stronghold.subsystems.Scaler;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static DriveTrain driveTrain;
    public static IntakeLauncher intakeLauncher;
    public static OI oi;
    public static GearShift gearShift;
    public static Scaler scaler;

    Command autonomousCommand;
    SendableChooser autonomousProgramChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotMap.init(); // 1. Initialize RobotMap prior to initializing modules

        // 2. conditionally create the modules
        if (ModuleManager.DRIVE_MODULE_ON) {
            driveTrain = new DriveTrain();
            gearShift = new GearShift();
            System.out.println("ModuleManager initialized: DriveTrain");
        }
        if (ModuleManager.GEARSHIFT_MODULE_ON) {
            SmartDashboard.putString("Gear shift", "Initialized");
            gearShift = new GearShift();
        }
        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            intakeLauncher = new IntakeLauncher();
            intakeLauncher.readSetPoint();
            SmartDashboard.putNumber("Launcher Set Point: ", intakeLauncher.getEncoderPosition());
            intakeLauncher.readSetPoint();
            SmartDashboard.putString("Module Manager", "IntakeLauncher Initialized");
            System.out.println("ModuleManager initialized: IntakeLauncher");
        }
        if (ModuleManager.GYRO_MODULE_ON) {
            RobotMap.gyro.initGyro();
            // Sensitivity in VEX Yaw Rate Gyro data sheet: 0.0011
            RobotMap.gyro.setSensitivity(0.0011);
            RobotMap.gyro.calibrate();
            SmartDashboard.putString("Module Manager", "initialize gyro");
            System.out.println("ModuleManager initialize gyro: " + RobotMap.gyro.getAngle());
            RobotMap.gyro.reset();
        }
        if (ModuleManager.SCALING_MODULE_ON) {
            scaler = new Scaler();
        }
        if (ModuleManager.IMU_MODULE_ON) {

            SmartDashboard.putString("Module Manager", "imu Initialized");
            System.out.println("Module Manager initialized: imu");

        }
        oi = new OI(); // 3. Construct OI after subsystems created
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {

        // schedule the autonomous command
        autonomousCommand = new AutoCommand1((Autonomous.Type) oi.barrierType.getSelected(), (Autonomous.Strat)oi.strategy.getSelected(), (Autonomous.Position)oi.startingFieldPosition.getSelected());


        if (this.autonomousCommand != null) {
            this.autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (this.autonomousCommand != null) {
            this.autonomousCommand.cancel();
        }
    }

    /**
     * This function is called when the disabled button is hit. You can use it
     * to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {

    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            SmartDashboard.putNumber("aimMotor Encoder position = ", RobotMap.aimMotor.getEncPosition());
            SmartDashboard.putNumber("Aimer JoystickY Position: ", Robot.oi.aimStick.getAxis((Joystick.AxisType.kY)));
        }

    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}
