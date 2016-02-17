package org.usfirst.frc.team4915.stronghold;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.AutoRotateDegrees;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.GearShiftCommand;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.MoveStraightPositionModeCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.ActivateLauncherPneumaticCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimerGoToAngleCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IntakeBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LaunchBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.RetractLauncherPneumaticCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.SetSetPointFromSmartDashboardCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.StopWheelsCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.ZeroAimerCommand;
import org.usfirst.frc.team4915.stronghold.commands.Scaler.ScalerCommand;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.subsystems.Scaler.State;
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

    // Ports for joysticks
    public static final int DRIVE_STICK_PORT = 0;
    public static final int LAUNCHER_STICK_PORT = 1;

    // Button numbers for driveStick buttons
    public static final int HIGH_SPEED_DRIVE_BUTTON = 6;
    public static final int LOW_SPEED_DRIVE_BUTTON = 4;
    public static final int INTAKE_BALL_BUTTON_NUMBER = 3;

    // Button numbers for launching related buttons on the mechanism stick
    public static final int LAUNCH_BALL_BUTTON_NUMBER = 2;
    public static final int STOP_WHEELS_BUTTON_NUMBER = 5;
    public static final int LAUNCHER_JUMP_TO_POSITION_BUTTON_NUMBER = 4; //Test
    public static final int AUTO_AIM_BUTTON_NUMBER = 7;
    public static final int HIGH_LOW_BUTTON_NUMBER = 8;
    public static final int ACTIVATE_PNEUMATIC_TEST_BUTTON_NUMBER = 6; //Test Button
    public static final int RETRACT_PNEUMATIC_TEST_BUTTON_NUMBER = 7; //Test Button

    // Button numbers for scaling related buttons on the mechanism joystick
    public static final int SCALER_REACH_UP_BUTTON_NUMBER = 2;
    public static final int SCALER_REACH_DOWN_BUTTON_NUMBER = 10;
    public static final int SCALER_LIFT_BUTTON_NUMBER = 9;

    // Create joysticks for driving and aiming the launcher
    public Joystick driveStick;
    public Joystick aimStick;

    // Create buttons for the driveStick
    public JoystickButton speedUpButton;
    public JoystickButton slowDownButton;
    public JoystickButton grabBallButton;

    // Create buttons for the launcher on the mechanism stick
    public JoystickButton launchBallButton;
    public JoystickButton stopWheelsButton;
    public JoystickButton launcherZeroEncoderButton;
    public JoystickButton launcherSetSetpointForDashboardButton;
    public JoystickButton launcherJumpToPositionButton;
    public JoystickButton autoAimButton;
    public JoystickButton highLowButton;
    public JoystickButton activatePneumaticTestButton;
    public JoystickButton retractPneumaticTestButton;

    // Create buttons for the scaler on the mechanism stick
    public JoystickButton scalerExtendButton;
    public JoystickButton scalerRetractButton;
    public JoystickButton scalerReachUpButton;
    public JoystickButton scalerReachDownButton;
    public JoystickButton scalerLiftButton;

    public SendableChooser autonomousProgramChooser;


    public OI() {
        autonomousProgramChooser = new SendableChooser();

        autonomousProgramChooser.addDefault("Autonomous Turn", new AutoRotateDegrees(false, 90));
        autonomousProgramChooser.addObject("Autonomous Just Drive", new MoveStraightPositionModeCommand(30));
        SmartDashboard.putString("Favorite Programmer", "Michaela");
        SmartDashboard.putData("Autonomous Program", autonomousProgramChooser);
        this.driveStick = new Joystick(DRIVE_STICK_PORT);
        this.aimStick = new Joystick(LAUNCHER_STICK_PORT);

        // Bind module commands to buttons
        if (ModuleManager.DRIVE_MODULE_ON) {
            System.out.println("ModuleManager OI initialized: TODO DriveTrain"); // TODO:
                                                                                 // OI
                                                                                 // init
                                                                                 // DriveTrain
        }

        if (ModuleManager.GEARSHIFT_MODULE_ON) {
            initializeButton(this.speedUpButton, driveStick, HIGH_SPEED_DRIVE_BUTTON, new GearShiftCommand(true));
            initializeButton(this.speedUpButton, driveStick, LOW_SPEED_DRIVE_BUTTON, new GearShiftCommand(false));

            System.out.println("ModuleManager OI initialized: TODO DriveTrain"); // TODO:
                                                                                 // OI
                                                                                 // init
                                                                                 // DriveTrain
        }

        if (ModuleManager.INTAKELAUNCHER_MODULE_ON) {
            initializeButton(this.launchBallButton, aimStick, LAUNCH_BALL_BUTTON_NUMBER, new LaunchBallCommandGroup());
            initializeButton(this.stopWheelsButton, aimStick, STOP_WHEELS_BUTTON_NUMBER, new StopWheelsCommand());
            initializeButton(this.grabBallButton, aimStick, INTAKE_BALL_BUTTON_NUMBER, new IntakeBallCommandGroup());
            initializeButton(this.launcherJumpToPositionButton, aimStick, LAUNCHER_JUMP_TO_POSITION_BUTTON_NUMBER, new AimerGoToAngleCommand(2000));
            initializeButton(this.activatePneumaticTestButton, aimStick, ACTIVATE_PNEUMATIC_TEST_BUTTON_NUMBER, new ActivateLauncherPneumaticCommand());
            initializeButton(this.retractPneumaticTestButton, aimStick, RETRACT_PNEUMATIC_TEST_BUTTON_NUMBER, new RetractLauncherPneumaticCommand());
            System.out.println("ModuleManager initialized: IntakeLauncher");
        }

        if (ModuleManager.GYRO_MODULE_ON) {
            System.out.println("ModuleManager OI TODO: Initialize Gyro!"); // TODO:
                                                                           // OI
                                                                           // init
                                                                           // Gyro
            SmartDashboard.putData("Gyro", RobotMap.gyro);
        }

        if (ModuleManager.VISION_MODULE_ON) {
            SmartDashboard.putData(VisionState.getInstance());
            initializeButton(this.autoAimButton, aimStick, AUTO_AIM_BUTTON_NUMBER, new AutoAimControlCommand(true, false));
            initializeButton(this.highLowButton, aimStick, HIGH_LOW_BUTTON_NUMBER, new AutoAimControlCommand(false, true));
            System.out.println("ModuleManager OI: Initialize Vision!");
        }

        if (ModuleManager.SCALING_MODULE_ON) {
            SmartDashboard.putData("Scaler Winch", RobotMap.scalingWinch);
            SmartDashboard.putData("Scaler Tape Measure Motor", RobotMap.scalingMotor);
            initializeButton(this.scalerReachUpButton, aimStick, SCALER_REACH_UP_BUTTON_NUMBER, new ScalerCommand(State.REACHING_UP));
            initializeButton(this.scalerLiftButton, aimStick, SCALER_LIFT_BUTTON_NUMBER, new ScalerCommand(State.LIFTING));
            initializeButton(this.scalerReachDownButton, aimStick, SCALER_REACH_DOWN_BUTTON_NUMBER, new ScalerCommand(State.REACHING_DOWN));
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

    public void initializeButton(JoystickButton Button, Joystick Joystick, int buttonNumber, Command Command) {
        Button = new JoystickButton(Joystick, buttonNumber);
        Button.whenPressed(Command);
    }

    public Joystick getJoystickDrive() {
        return this.driveStick;
    }

    public Joystick getJoystickAimStick() {
        return this.aimStick;
    }
}
