
package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.commands.AutoRotateDegrees;
import org.usfirst.frc.team4915.stronghold.commands.MoveStraightPositionModeCommand;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;
import org.usfirst.frc.team4915.stronghold.subsystems.GearShift;
import org.usfirst.frc.team4915.stronghold.subsystems.IntakeLauncher;

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
    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotMap.init();        // 1. Initialize RobotMap prior to initializing modules
        
        // 2. conditionally create the modules
        if (ModuleManager.DRIVE_MODULE_ON) {
            driveTrain = new DriveTrain();
            gearShift= new GearShift();
            System.out.println("ModuleManager initialized: DriveTrain");
        }
        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            intakeLauncher = new IntakeLauncher();
            SmartDashboard.putString("Module Manager", "IntakeLauncher Initialized");
            System.out.println("ModuleManager initialized: IntakeLauncher");
        }
        if (ModuleManager.GYRO_MODULE_ON) {
            SmartDashboard.putString("Module Manager", "FIX GYRO INITIALIZATION!");
            System.out.println("ModuleManager TODO: Initialize Gyro!");  
        }

        oi = new OI();      // 3. Construct OI after subsystems created
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        // schedule the autonomous command
        autonomousCommand = new AutoRotateDegrees(true, 90);    // in inches
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
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}
