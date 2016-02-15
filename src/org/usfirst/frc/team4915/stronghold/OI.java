package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.commands.GearShiftCommand;
import org.usfirst.frc.team4915.stronghold.commands.ScalerCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.ActivateLauncherServoCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimerGoToAngleCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IntakeBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LaunchBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.RetractLauncherServoCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.SetSetPointFromSmartDashboardCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.ZeroAimerCommand;
import org.usfirst.frc.team4915.stronghold.subsystems.Scaler.State;
import org.usfirst.frc.team4915.stronghold.vision.robot.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.vision.robot.VisionState;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

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

    public static final int HIGH_SPEED_DRIVE_BUTTON = 4;
    public static final int LOW_SPEED_DRIVE_BUTTON = 3;

    // buttons are now correct until we decide to change them
    public static final int LAUNCHER_STICK_PORT = 1;
    public static final int LAUNCH_BALL_BUTTON_NUMBER = 1;
    public static final int INTAKE_BALL_BUTTON_NUMBER = 11;
    public static final int AUTO_AIM_BUTTON_NUMBER = 11; //currently jumping to 2000
    public static final int LAUNCHER_SERVO_ACTIVATE_TEST_BUTTON_NUMBER = 4; //TODO
    public static final int LAUNCHER_SERVO_RETRACT_TEST_BUTTON_NUMBER = 5; //TODO
    public static final int LAUNCHER_ZERO_ENCODER_BUTTON_NUMBER = 3;
    public static final int SET_SETPOINT_FOR_DASHBOARD_BUTTON_NUMBER = 10;

    // FIXME: Scaling button values
    public static final int SCALER_EXTEND_BUTTON_NUMBER = 9;
    public static final int SCALER_RETRACT_BUTTON_NUMBER = 10;

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
    public JoystickButton launcher45DegreesButton;
    public JoystickButton launcherServoActivateTestButton;
    public JoystickButton launcherServoRetractTestButton;
    public JoystickButton launcherZeroEncoderButton;
    public JoystickButton launcherSetSetpointForDashboardButton;
    public JoystickButton scalerExtendButton;
    public JoystickButton scalerRetractButton;
   

    public OI() {
        this.driveStick = new Joystick(DRIVE_STICK_PORT);
        this.aimStick = new Joystick(LAUNCHER_STICK_PORT);

        // Bind module commands to buttons
        if (ModuleManager.DRIVE_MODULE_ON) {
            this.speedUpButton = new JoystickButton(driveStick, HIGH_SPEED_DRIVE_BUTTON);
            this.slowDownButton = new JoystickButton(driveStick, LOW_SPEED_DRIVE_BUTTON);
            this.grabBallButton = new JoystickButton(driveStick, INTAKE_BALL_BUTTON_NUMBER);

            this.speedUpButton.whenPressed(new GearShiftCommand(true));
            this.slowDownButton.whenPressed(new GearShiftCommand(false));
            this.grabBallButton.whenPressed(new IntakeBallCommandGroup());

            System.out.println("ModuleManager OI initialized: TODO DriveTrain"); // TODO:
                                                                                 // OI
                                                                                 // init
                                                                                 // DriveTrain
        }

        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            this.launchBallButton = new JoystickButton(this.aimStick, LAUNCH_BALL_BUTTON_NUMBER);
            this.launcherServoActivateTestButton = new JoystickButton(this.aimStick, LAUNCHER_SERVO_ACTIVATE_TEST_BUTTON_NUMBER);
            this.launcherServoRetractTestButton = new JoystickButton(this.aimStick, LAUNCHER_SERVO_RETRACT_TEST_BUTTON_NUMBER);
            this.launcherZeroEncoderButton = new JoystickButton(this.aimStick, LAUNCHER_ZERO_ENCODER_BUTTON_NUMBER);
            this.autoAimButton = new JoystickButton(this.aimStick, AUTO_AIM_BUTTON_NUMBER);
            this.launcherSetSetpointForDashboardButton = new JoystickButton(this.aimStick, SET_SETPOINT_FOR_DASHBOARD_BUTTON_NUMBER);

            //this.grabBallButton.whenPressed(new IncrementLauncherHeightCommand(0)); //Not right, good for testing
            this.launchBallButton.whenPressed(new LaunchBallCommandGroup()); //TODO uncomment
            this.launcherServoActivateTestButton.whenPressed(new ActivateLauncherServoCommand());
            this.launcherServoRetractTestButton.whenPressed(new RetractLauncherServoCommand());
            this.launcherZeroEncoderButton.whenPressed(new ZeroAimerCommand());
            this.autoAimButton.whenPressed(new AimerGoToAngleCommand(2000)); //testing
            this.launcherSetSetpointForDashboardButton.whenPressed(new SetSetPointFromSmartDashboardCommand());
            //SmartDashboard.putData("Setpoint = 2000", new AimerGoToAngleCommand(2000));
   
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
            SmartDashboard.putData("Scaler Top Switch", RobotMap.SCALING_TOP_SWITCH);
            SmartDashboard.putData("Scaler Bottom Switch", RobotMap.SCALING_BOTTOM_SWITCH);
            SmartDashboard.putData("Scaler Winch", RobotMap.SCALING_WINCH);
            SmartDashboard.putData("Scaler Tape Measure Motor", RobotMap.SCALING_MOTOR);
            this.scalerExtendButton = new JoystickButton(this.aimStick, SCALER_EXTEND_BUTTON_NUMBER);
            this.scalerRetractButton = new JoystickButton(this.aimStick, SCALER_RETRACT_BUTTON_NUMBER);
            this.scalerExtendButton.whenPressed(new ScalerCommand(State.EXTENDED));
            this.scalerRetractButton.whenPressed(new ScalerCommand(State.RETRACTED));
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
