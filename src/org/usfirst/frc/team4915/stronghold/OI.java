package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.GearShiftCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AutoLaunchCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IntakeBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LaunchBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LauncherGoToAngleCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LauncherGoToIntakePositionCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LauncherGoToNeutralPositionCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LightSwitchCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.RetractLauncherServosCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.SpinIntakeWheelsOutCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.StopWheelsCommand;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.subsystems.Autonomous;
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
    public static final int HIGH_SPEED_DRIVE_BUTTON = 10;
    public static final int LOW_SPEED_DRIVE_BUTTON = 11;
    public static final int INTAKE_BALL_BUTTON_NUMBER = 3;
    public static final int DRIVE_STOP_INTAKE_WHEELS_BUTTON_NUMBER = 5;
    public static final int DRIVE_LAUNCHER_JUMP_TO_NEUTRAL_BUTTON_NUMBER = 6;
    public static final int DRIVE_LAUNCHER_JUMP_TO_INTAKE_BUTTON_NUMBER = 4;

    // Button numbers for launching related buttons on the mechanism stick
    public static final int KICK_BALL_BUTTON_NUMBER = 3;
    public static final int MECH_STOP_INTAKE_WHEELS_BUTTON_NUMBER = 5;
    public static final int MECH_LAUNCHER_JUMP_TO_NEUTRAL_BUTTON_NUMBER = 10;
    public static final int MECH_LAUNCHER_JUMP_TO_INTAKE_BUTTON_NUMBER = 6;
    public static final int SPIN_INTAKE_WHEELS_OUT_BUTTON_NUMBER = 4;
    public static final int LIGHT_SWITCH_BUTTON_NUMBER = 2;
    public static final int AUTO_AIM_BUTTON_NUMBER = 7;
    public static final int HIGH_LOW_BUTTON_NUMBER = 8;

    // Button numbers for scaling related buttons on the mechanism joystick
    public static final int SCALER_REACH_UP_BUTTON_NUMBER = 3;
    public static final int SCALER_REACH_DOWN_BUTTON_NUMBER = 10;
    public static final int SCALER_LIFT_BUTTON_NUMBER = 9;

    // Create joysticks for driving and aiming the launcher
    public Joystick driveStick;
    public Joystick aimStick;

    // Create buttons for the driveStick
    public JoystickButton speedUpButton;
    public JoystickButton slowDownButton;
    public JoystickButton grabBallButton;
    public JoystickButton driveLauncherJumpToNeutralButton;
    public JoystickButton driveLauncherJumpToIntakeButton;
    public JoystickButton driveStopIntakeWheelsButton;

    // Create buttons for the launcher on the mechanism stick
    public JoystickButton kickBallButton;
    public JoystickButton mechStopWheelsButton;
    public JoystickButton mechLauncherJumpToNeutralButton;
    public JoystickButton mechLauncherJumpToIntakeButton;
    public JoystickButton launcherJumpToAngleButton;
    public JoystickButton spinIntakeWheelsOutButton;
    public JoystickButton autoAimButton;
    public JoystickButton highLowButton;
    public JoystickButton lightSwitchButton;

    // Create buttons for the scaler on the mechanism stick
    public JoystickButton scalerExtendButton;
    public JoystickButton scalerRetractButton;
    public JoystickButton scalerReachUpButton;
    public JoystickButton scalerReachDownButton;
    public JoystickButton scalerLiftButton;

    // variables for the sendable chooser
    public SendableChooser startingFieldPosition;
    public SendableChooser barrierType;
    public SendableChooser strategy;

    public OI() {

        // *****autonomous*****
        // ***Three Sendable Choosers***
        // SendableChooser for the starting field position
        startingFieldPosition = new SendableChooser();
        SmartDashboard.putData("Starting Field Position for autonomous", startingFieldPosition);
        startingFieldPosition.addDefault("Field Position 1: Low Bar", Autonomous.Position.ONE);
        startingFieldPosition.addObject("Field Position 2", Autonomous.Position.TWO);
        startingFieldPosition.addObject("Field Position 3:", Autonomous.Position.THREE);
        startingFieldPosition.addObject("Field Position 4:", Autonomous.Position.FOUR);
        startingFieldPosition.addObject("Field Position 5", Autonomous.Position.FIVE);

        // SendableChooser for the barrier type
        // assigning each barrier to a number
        barrierType = new SendableChooser();
        SmartDashboard.putData("Barrier Type for autonomous", barrierType);
        barrierType.addDefault("Low Bar", Autonomous.Type.LOWBAR);
        barrierType.addObject("Moat", Autonomous.Type.MOAT);
        barrierType.addObject("Ramparts", Autonomous.Type.RAMPARTS);
        barrierType.addObject("Rough Terrain", Autonomous.Type.ROUGH_TERRAIN);
        barrierType.addObject("Rock Wall", Autonomous.Type.ROCK_WALL);

        // SendableChooser for the strategy
        strategy = new SendableChooser();
        SmartDashboard.putData("Strategy for autonomous", strategy);
        strategy.addDefault("None", Autonomous.Strat.NONE);
        strategy.addObject("Drive across barrier", Autonomous.Strat.DRIVE_ACROSS);
        strategy.addObject("Drive and shoot high goal with vision", Autonomous.Strat.DRIVE_SHOOT_VISION);
        strategy.addObject("Drive and shoot high goal without vision", Autonomous.Strat.DRIVE_SHOOT_NO_VISION);

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
            initializeButton(this.kickBallButton, aimStick, KICK_BALL_BUTTON_NUMBER, new LaunchBallCommandGroup());
            initializeButton(this.mechStopWheelsButton, aimStick, MECH_STOP_INTAKE_WHEELS_BUTTON_NUMBER, new StopWheelsCommand());
            initializeButton(this.mechLauncherJumpToNeutralButton, aimStick, MECH_LAUNCHER_JUMP_TO_NEUTRAL_BUTTON_NUMBER,
                    new LauncherGoToNeutralPositionCommand());
            initializeButton(this.mechLauncherJumpToIntakeButton, aimStick, MECH_LAUNCHER_JUMP_TO_INTAKE_BUTTON_NUMBER, new LauncherGoToIntakePositionCommand());
            initializeButton(this.grabBallButton, driveStick, INTAKE_BALL_BUTTON_NUMBER, new IntakeBallCommandGroup());
            initializeButton(this.launcherJumpToAngleButton, aimStick, 1, new LauncherGoToAngleCommand(10));
            initializeButton(this.spinIntakeWheelsOutButton, aimStick, SPIN_INTAKE_WHEELS_OUT_BUTTON_NUMBER, new SpinIntakeWheelsOutCommand());
            initializeButton(this.driveLauncherJumpToIntakeButton, driveStick, DRIVE_LAUNCHER_JUMP_TO_INTAKE_BUTTON_NUMBER, new LauncherGoToIntakePositionCommand());
            initializeButton(this.driveLauncherJumpToNeutralButton, driveStick, DRIVE_LAUNCHER_JUMP_TO_NEUTRAL_BUTTON_NUMBER, new LauncherGoToNeutralPositionCommand());
            initializeButton(this.driveStopIntakeWheelsButton, driveStick, DRIVE_STOP_INTAKE_WHEELS_BUTTON_NUMBER, new StopWheelsCommand());
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
            // initializeButton(this.scalerReachUpButton, aimStick,
            // SCALER_REACH_UP_BUTTON_NUMBER, new
            // ScalerCommand(State.REACHING_UP));
            // initializeButton(this.scalerLiftButton, aimStick,
            // SCALER_LIFT_BUTTON_NUMBER, new ScalerCommand(State.LIFTING));
            // initializeButton(this.scalerReachDownButton, aimStick,
            // SCALER_REACH_DOWN_BUTTON_NUMBER, new
            // ScalerCommand(State.REACHING_DOWN));
        }

        if (ModuleManager.IMU_MODULE_ON) {
            System.out.println("ModuleManager initialized: imu");
        }

        initializeButton(this.lightSwitchButton, aimStick, LIGHT_SWITCH_BUTTON_NUMBER, new LightSwitchCommand());

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
