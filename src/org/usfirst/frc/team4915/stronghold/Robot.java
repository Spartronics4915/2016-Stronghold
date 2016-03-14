
package org.usfirst.frc.team4915.stronghold;
import org.usfirst.frc.team4915.stronghold.commands.AutoCommand1;
import org.usfirst.frc.team4915.stronghold.commands.PortcullisMoveUp;
import org.usfirst.frc.team4915.stronghold.subsystems.Autonomous;
import org.usfirst.frc.team4915.stronghold.subsystems.DriveTrain;
import org.usfirst.frc.team4915.stronghold.subsystems.GearShift;
import org.usfirst.frc.team4915.stronghold.subsystems.IntakeLauncher;
import org.usfirst.frc.team4915.stronghold.subsystems.Scaler;
import org.usfirst.frc.team4915.stronghold.utils.BNO055;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
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

    private volatile double lastTime;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotMap.init(); // 1. Initialize RobotMap prior to initializing modules

        // 2. conditionally create the modules
        if (ModuleManager.PORTCULLIS_MODULE_ON){
            new PortcullisMoveUp().start();

        }
        if (ModuleManager.DRIVE_MODULE_ON) {
            driveTrain = new DriveTrain();
            SmartDashboard.putString("Drivetrain Module", "initialized");
        }
        else
            SmartDashboard.putString("Drivetrain Module", "disabled");

        if (ModuleManager.GEARSHIFT_MODULE_ON) {
            gearShift = new GearShift();
            SmartDashboard.putString("Shift Module", "initialized");
        }
        else
        	SmartDashboard.putString("Shift Module", "disabled");

        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
        	/* to prevent module-manager-madness (M-cubed), we
        	 * place try/catch block for exceptions thrown on account of
        	 * missing hardware.
        	 */
        	try {
        		intakeLauncher = new IntakeLauncher();
                SmartDashboard.putString("IntakeLauncher Module", "initialized");
        	}
        	catch (Throwable e) {
                System.out.println("Disabling IntakeLauncher at runtime");
                SmartDashboard.putString("IntakeLauncher Module", "ERROR");
                ModuleManager.INTAKELAUNCHER_MODULE_ON = false;
        	}
        }
        else
            SmartDashboard.putString("IntakeLauncher Module", "disabled");

        if (ModuleManager.SCALING_MODULE_ON) {
            scaler = new Scaler();
            SmartDashboard.putString("Scaling Module", "initialized");
       }
        else
            SmartDashboard.putString("Scaling Module", "disabled");

        if (ModuleManager.IMU_MODULE_ON) {
        	// imu is initialized in RobotMap.init()
            SmartDashboard.putString("IMU Module", "initialized");
            SmartDashboard.putBoolean("IMU present", RobotMap.imu.isSensorPresent());
            updateIMUStatus();
        }
        else
            SmartDashboard.putString("IMU Module", "disabled");

        oi = new OI(); // 3. Construct OI after subsystems created
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {

        // schedule the autonomous command
       autonomousCommand = new AutoCommand1((Autonomous.Type) oi.barrierType.getSelected(), (Autonomous.Strat) oi.strategy.getSelected(),
                (Autonomous.Position) oi.startingFieldPosition.getSelected());
       System.out.println("Autonomous selection Angle: " + oi.startingFieldPosition.getSelected() + "Field Position " + oi.startingFieldPosition.getSelected() + "strategy " + oi.strategy.getSelected() + "Obstacle " + oi.barrierType.getSelected());

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
        periodicStatusUpdate();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
      //set speed

       // RobotMap.rightBackMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
       // RobotMap.leftBackMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
    	System.out.println("entering teleop");
    	Robot.intakeLauncher.aimMotor.enableControl();
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
        periodicStatusUpdate();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
        periodicStatusUpdate();
     }

    public void periodicStatusUpdate() {
        double currentTime = Timer.getFPGATimestamp(); // seconds
        // only update the smart dashboard twice per second to prevent
        // network congestion.
        if(currentTime - this.lastTime > .5) {
        	updateIMUStatus();
        	updateLauncherStatus();
        	updateDrivetrainStatus();
        	this.lastTime = currentTime;
        }
    }

	public void updateIMUStatus() {
 	   if (ModuleManager.IMU_MODULE_ON) {
            BNO055.CalData calData = RobotMap.imu.getCalibration();
            SmartDashboard.putNumber("IMU heading", RobotMap.imu.getNormalizedHeading());
            // SmartDashboard.putNumber("IMU dist to origin", RobotMap.imu.getDistFromOrigin());
            SmartDashboard.putNumber("IMU calibration",
                                 (1000 + (calData.accel * 100) + calData.gyro *10 + calData.mag));
                                 //Calibration values range from 0-3, Right to left: mag, gyro, accel
 	   }
	}

	public void updateLauncherStatus() {
        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            SmartDashboard.putNumber("aimMotor Potentiometer: ", intakeLauncher.getPosition());
	        SmartDashboard.putBoolean("Top Limit Switch: ", intakeLauncher.isLauncherAtTop());
	        SmartDashboard.putBoolean("Bottom Limit Switch: ", intakeLauncher.isLauncherAtBottom());
	        SmartDashboard.putBoolean("Boulder Limit Switch: ", intakeLauncher.boulderSwitch.get());
	        SmartDashboard.putBoolean("Potentiometer might be broken", intakeLauncher.getIsPotentiometerScrewed());
        }
	}
	
	public void toggleSpeed() {
		
	}

	public void updateDrivetrainStatus() {
        if (ModuleManager.DRIVE_MODULE_ON) {

        }
	}
}
