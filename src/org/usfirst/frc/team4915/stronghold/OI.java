package org.usfirst.frc.team4915.stronghold;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IntakeBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LaunchBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AutoAimCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IncrementLauncherHeightCommand;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.commands.HighSpeedModeCommand;
import org.usfirst.frc.team4915.stronghold.commands.LowSpeedModeCommand;

/**
 * This class handles the "operator interface", or the interactions between the
 * driver station and the robot code.
 */
public class OI {
    // create  joysticks for driving and aiming the launcher
    public Joystick driveStick;
    public Joystick aimStick;
    public static final int DRIVE_STICK_PORT = 0;

    // Drive train two speed controls
    public JoystickButton speedUpButton;
    public JoystickButton slowDownButton;
    
    public static final int HIGH_SPEED_DRIVE_BUTTON= 4;
    public static final int LOW_SPEED_DRIVE_BUTTON= 3;
    
    // FIXME: IntakeLauncher button values
    public static final int LAUNCH_BALL_BUTTON_NUMBER = 2; 
    public static final int INTAKE_BALL_BUTTON_NUMBER = 5; 
    public static final int LAUNCH_AUTOAIM_BUTTON_NUMBER = 6;
    

    // constants, need to talk to electrical to figure out correct port values
    public static final int PLACEHOLDER_NUMBER = 69;
    public static final int LAUNCHER_STICK_PORT = PLACEHOLDER_NUMBER; // TODO
    public static final int AUTO_AIM_BUTTON_NUMBER = 11; 
    public static final int LAUNCHER_UP_BUTTON_NUMBER = 7; 
    public static final int LAUNCHER_DOWN_BUTTON_NUMBER = 8; 

    public static final int UP_DIRECTION = 1;
    public static final int DOWN_DIRECTION = UP_DIRECTION * -1;

    // creates new buttons
    // launchBall triggers a command group with commands that ultimately will
    // shoot the ball
    // grabBall triggers a command group with commands that will get the ball
    // into the basket
    public JoystickButton launchBallButton;
    public JoystickButton grabBallButton;
    public JoystickButton autoAimButton;
    public JoystickButton launcherUpButton;
    public JoystickButton launcherDownButton;

    public OI() {
        this.driveStick = new Joystick(DRIVE_STICK_PORT);
        this.aimStick = new Joystick(LAUNCHER_STICK_PORT);
        
        // Bind module commands to buttons
        if (ModuleManager.DRIVE_MODULE_ON) {
            this.speedUpButton = new JoystickButton(driveStick, HIGH_SPEED_DRIVE_BUTTON);
            this.slowDownButton = new JoystickButton(driveStick, LOW_SPEED_DRIVE_BUTTON);

            this.speedUpButton.whenPressed(new HighSpeedModeCommand());
            this.slowDownButton.whenPressed(new LowSpeedModeCommand());
            
            SmartDashboard.putData("High speed mode- extending pneumatic", new HighSpeedModeCommand());
            SmartDashboard.putData("Low speed mode- detracting pneumatic", new LowSpeedModeCommand());
            
            System.out.println("ModuleManager OI initialized: TODO DriveTrain");    // TODO: OI init DriveTrain
        }
        
        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            this.grabBallButton = new JoystickButton(this.aimStick, INTAKE_BALL_BUTTON_NUMBER);
            this.launchBallButton = new JoystickButton(this.aimStick, LAUNCH_BALL_BUTTON_NUMBER);
            this.autoAimButton = new JoystickButton(this.aimStick, LAUNCH_AUTOAIM_BUTTON_NUMBER);

            this.grabBallButton.whenPressed(new IntakeBallCommandGroup());
            this.launchBallButton.whenPressed(new LaunchBallCommandGroup());
            this.autoAimButton.whenPressed(new AutoAimCommand());
            System.out.println("ModuleManager initialized: IntakeLauncher");
        }
        
        if (ModuleManager.GYRO_MODULE_ON) {
            System.out.println("ModuleManager OI TODO: Initialize Gyro!");          // TODO: OI init Gyro
        }

        if (ModuleManager.VISION_MODULE_ON) {
            SmartDashboard.putData(VisionState.getInstance());
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

        this.aimStick = new Joystick(LAUNCHER_STICK_PORT);
        this.grabBallButton = new JoystickButton(this.aimStick, INTAKE_BALL_BUTTON_NUMBER);
        this.launchBallButton = new JoystickButton(this.aimStick, LAUNCH_BALL_BUTTON_NUMBER);
        this.autoAimButton = new JoystickButton(this.aimStick, AUTO_AIM_BUTTON_NUMBER);
        this.launcherUpButton = new JoystickButton(this.aimStick, LAUNCHER_UP_BUTTON_NUMBER);
        this.launcherDownButton = new JoystickButton(this.aimStick, LAUNCHER_DOWN_BUTTON_NUMBER);

        // binds commands to buttons, autoAim is commented for now because we
        // don't know what the position will be
        this.grabBallButton.whenPressed(new IntakeBallCommandGroup());
        this.launchBallButton.whenPressed(new LaunchBallCommandGroup());
        this.autoAimButton.whenPressed(new AutoAimCommand());
        this.launcherUpButton.whenPressed(new IncrementLauncherHeightCommand(UP_DIRECTION));
        this.launcherDownButton.whenPressed(new IncrementLauncherHeightCommand(DOWN_DIRECTION));
    }

    public Joystick getJoystickDrive() {
        return this.driveStick;
    }
    
    public Joystick getJoystickAimStick() {
        return this.aimStick;
    }
}
