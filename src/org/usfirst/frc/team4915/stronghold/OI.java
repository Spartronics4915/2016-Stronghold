package org.usfirst.frc.team4915.stronghold;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.usfirst.frc.team4915.stronghold.commands.GearShiftCommand;
import org.usfirst.frc.team4915.stronghold.commands.ScalerCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IncrementLauncherHeightCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IntakeBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LaunchBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.subsystems.Scaler.State;
import org.usfirst.frc.team4915.stronghold.vision.robot.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class handles the "operator interface", or the interactions between the
 * driver station and the robot code.
 */
public class OI {

    // create joysticks for driving and aiming the launcher
    public Joystick driveStick;
    public Joystick aimStick;
    public static final int DRIVE_STICK_PORT = 0;

    // Drive train two speed controls
    public JoystickButton speedUpButton;
    public JoystickButton slowDownButton;
    
    //Gearbox speed buttons on the driving joystick
    public static final int HIGH_SPEED_DRIVE_BUTTON = 4;
    public static final int LOW_SPEED_DRIVE_BUTTON = 3;

    // Launcher Buttons on the mechanism joystick
    public static final int LAUNCHER_STICK_PORT = 1;
    public static final int LAUNCH_BALL_BUTTON_NUMBER = 1;
    public static final int INTAKE_BALL_BUTTON_NUMBER = 2;
    public static final int AUTO_AIM_BUTTON_NUMBER = 8;
    public static final int LAUNCHER_UP_BUTTON_NUMBER = 6;
    public static final int LAUNCHER_DOWN_BUTTON_NUMBER = 7;

    //Scaling button values on the mechanism joystick
    public static final int SCALER_REACH_UP_BUTTON_NUMBER = 11;
    public static final int SCALER_REACH_DOWN_BUTTON_NUMBER = 10;
    public static final int SCALER_LIFT_BUTTON_NUMBER = 9;

    public static final int UP_DIRECTION = 1;
    public static final int DOWN_DIRECTION = UP_DIRECTION * -1;

    // creates new buttons
    // launchBall triggers a command group with commands that ultimately will
    // shoot the ball
    // grabBall triggers a command group with commands that will get the ball
    // into the basket
    // launcherUp and launcherDown increment the launcher height by a small
    // amount
    public JoystickButton launchBallButton;
    public JoystickButton grabBallButton;
    public JoystickButton autoAimButton;
    public JoystickButton launcherUpButton;
    public JoystickButton launcherDownButton;
    public JoystickButton scalerReachUpButton;
    public JoystickButton scalerReachDownButton;
    public JoystickButton scalerLiftButton;
    

    public OI() {
        this.driveStick = new Joystick(DRIVE_STICK_PORT);
        this.aimStick = new Joystick(LAUNCHER_STICK_PORT);

        // Bind module commands to buttons
        if (ModuleManager.DRIVE_MODULE_ON) {
            this.speedUpButton = new JoystickButton(driveStick, HIGH_SPEED_DRIVE_BUTTON);
            this.slowDownButton = new JoystickButton(driveStick, LOW_SPEED_DRIVE_BUTTON);

            this.speedUpButton.whenPressed(new GearShiftCommand(true));
            this.slowDownButton.whenPressed(new GearShiftCommand(false));
           

            System.out.println("ModuleManager OI initialized: TODO DriveTrain"); // TODO:
                                                                                 // OI
                                                                                 // init
                                                                                 // DriveTrain
        }

        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            this.aimStick = new Joystick(LAUNCHER_STICK_PORT);
            this.grabBallButton = new JoystickButton(this.aimStick, INTAKE_BALL_BUTTON_NUMBER);
            this.launchBallButton = new JoystickButton(this.aimStick, LAUNCH_BALL_BUTTON_NUMBER);
            this.launcherUpButton = new JoystickButton(this.aimStick, LAUNCHER_UP_BUTTON_NUMBER);
            this.launcherDownButton = new JoystickButton(this.aimStick, LAUNCHER_DOWN_BUTTON_NUMBER);

            this.grabBallButton.whenPressed(new IntakeBallCommandGroup());
            this.launchBallButton.whenPressed(new LaunchBallCommandGroup());
            this.launcherUpButton.whenPressed(new IncrementLauncherHeightCommand(UP_DIRECTION));
            this.launcherDownButton.whenPressed(new IncrementLauncherHeightCommand(DOWN_DIRECTION));
            System.out.println("ModuleManager initialized: IntakeLauncher");
        }

        if (ModuleManager.GYRO_MODULE_ON) {
            System.out.println("ModuleManager OI TODO: Initialize Gyro!"); // TODO:
                                                                           // OI
                                                                           // init
                                                                           // Gyro
        }

        if (ModuleManager.VISION_MODULE_ON) {
            SmartDashboard.putData(VisionState.getInstance());
            this.autoAimButton = new JoystickButton(this.aimStick, AUTO_AIM_BUTTON_NUMBER);
            this.autoAimButton.whenPressed(new AutoAimControlCommand());
            System.out.println("ModuleManager OI: Initialize Vision!");
        }

        if (ModuleManager.SCALING_MODULE_ON) {
            SmartDashboard.putData("Scaler Winch", RobotMap.scalingWinch);
            SmartDashboard.putData("Scaler Tape Measure Motor", RobotMap.scalingMotor);
            this.scalerReachUpButton = new JoystickButton(this.aimStick, SCALER_REACH_UP_BUTTON_NUMBER);
            this.scalerLiftButton = new JoystickButton(this.aimStick, SCALER_LIFT_BUTTON_NUMBER);
            this.scalerReachUpButton.whileHeld(new ScalerCommand(State.REACHING_UP));
            this.scalerLiftButton.whileHeld(new ScalerCommand(State.LIFTING));
            this.scalerReachDownButton =new JoystickButton(this.aimStick, SCALER_REACH_DOWN_BUTTON_NUMBER);
            this.scalerReachDownButton.whileHeld(new ScalerCommand(State.REACHING_DOWN));
        }
        
        if (ModuleManager.IMU_MODULE_ON) {
            System.out.println("ModuleManager initialized: imu");
        }

        /*
         * VERSION STRING!!
         */
        try (InputStream manifest = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF")) {
            Attributes attributes = new Manifest(manifest).getMainAttributes();

            /* Print the attributes into form fields on the dashboard */
            SmartDashboard.putString("Code Version", attributes.getValue("Code-Version"));
            SmartDashboard.putString("Built At", attributes.getValue("Built-At"));
            SmartDashboard.putString("Built By", attributes.getValue("Built-By"));

            /* And print the attributes into the log. */
            System.out.println("Code Version: " + attributes.getValue("Code-Version"));
            System.out.println("Built At: " + attributes.getValue("Built-At"));
            System.out.println("Built By: " + attributes.getValue("Built-By"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Joystick getJoystickDrive() {
        return this.driveStick;
    }

    public Joystick getJoystickAimStick() {
        return this.aimStick;
    }
}
