package org.usfirst.frc.team4915.stronghold;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IntakeBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LaunchBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.vision.robot.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.commands.highSpeedMode;
import org.usfirst.frc.team4915.stronghold.commands.lowSpeedMode;

/**
 * This class handles the "operator interface", or the interactions between the
 * driver station and the robot code.
 */
public class OI {
    // create  joysticks for driving and aiming the launcher
    public Joystick driveStick;
    public Joystick aimStick;
    public static final int DRIVE_STICK_PORT = 0; 
    public static final int LAUNCHER_STICK_PORT = 1; 

    // Drive train two speed controls
    public JoystickButton speedUpButton;
    public JoystickButton slowDownButton;
    
    // FIXME: IntakeLauncher button values
    public static final int LAUNCH_BALL_BUTTON_NUMBER = 2; 
    public static final int INTAKE_BALL_BUTTON_NUMBER = 3; 
    public static final int AUTO_AIM_BUTTON_NUMBER = 4;

    public JoystickButton launchBallButton; // triggers a command group to shoot the ball
    public JoystickButton grabBallButton;   // triggers a command group to get the ball into the basket
    
    public JoystickButton autoAimButton;

    public OI() {
        this.driveStick = new Joystick(DRIVE_STICK_PORT);
        this.aimStick = new Joystick(LAUNCHER_STICK_PORT);
        
        // Bind module commands to buttons
        if (ModuleManager.DRIVE_MODULE_ON) {
            this.speedUpButton = new JoystickButton(driveStick, 4);
            this.slowDownButton = new JoystickButton(driveStick, 3);

            this.speedUpButton.whenPressed(new highSpeedMode());
            this.slowDownButton.whenPressed(new lowSpeedMode());
            System.out.println("ModuleManager OI initialized: TODO DriveTrain");    // TODO: OI init DriveTrain
        }
        
        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            this.grabBallButton = new JoystickButton(this.aimStick, INTAKE_BALL_BUTTON_NUMBER);
            this.launchBallButton = new JoystickButton(this.aimStick, LAUNCH_BALL_BUTTON_NUMBER);

            this.grabBallButton.whenPressed(new IntakeBallCommandGroup());
            this.launchBallButton.whenPressed(new LaunchBallCommandGroup());
            System.out.println("ModuleManager initialized: IntakeLauncher");
        }
        
        if (ModuleManager.GYRO_MODULE_ON) {
            System.out.println("ModuleManager OI TODO: Initialize Gyro!");          // TODO: OI init Gyro
        }

        if (ModuleManager.VISION_MODULE_ON) {
            SmartDashboard.putData(VisionState.getInstance());
            this.autoAimButton = new JoystickButton(this.aimStick, AUTO_AIM_BUTTON_NUMBER);
            this.autoAimButton.whenPressed(new AutoAimControlCommand());
            System.out.println("ModuleManager initialized: Vision");
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
