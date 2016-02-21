package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4915.stronghold.commands.DriveTrain.GearShiftCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.ActivateLauncherServosCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.AimerGoToAngleCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IntakeBallCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LaunchBallCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.RetractLauncherServosCommand;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.StopWheelsCommand;
import org.usfirst.frc.team4915.stronghold.commands.Scaler.ScalerCommand;
import org.usfirst.frc.team4915.stronghold.commands.vision.AutoAimControlCommand;
import org.usfirst.frc.team4915.stronghold.subsystems.Autonomous;
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
    public static final int LAUNCHER_JUMP_TO_POSITION_BUTTON_NUMBER = 4; // Test
    public static final int LAUNCHER_FORCE_DOWN_BUTTON_NUMBER = 1;
    public static final int AUTO_AIM_BUTTON_NUMBER = 7;
    public static final int HIGH_LOW_BUTTON_NUMBER = 8;
    public static final int ACTIVATE_SERVOS_TEST_BUTTON_NUMBER = 6; // Test
    public static final int RETRACT_SERVOS_TEST_BUTTON_NUMBER = 7; // Test

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

    // Create buttons for the launcher on the mechanism stick
    public JoystickButton launchBallButton;
    public JoystickButton stopWheelsButton;
    public JoystickButton launcherZeroEncoderButton;
    public JoystickButton launcherSetSetpointForDashboardButton;
    public JoystickButton launcherJumpToPositionButton;
    public JoystickButton launcherForceDownButton;
    public JoystickButton activateServosTestButton;
    public JoystickButton retractServosTestButton;
    public JoystickButton autoAimButton;
    public JoystickButton highLowButton;

    // Create buttons for the scaler on the mechanism stick
    public JoystickButton scalerExtendButton;
    public JoystickButton scalerRetractButton;
    public JoystickButton scalerReachUpButton;
    public JoystickButton scalerReachDownButton;
    public JoystickButton scalerLiftButton;
    
    //variables for the sendable chooser
    public SendableChooser startingFieldPosition;
    public SendableChooser barrierType;
    public SendableChooser strategy;

    public OI() {

        // *****autonomous*****
        //***Three Sendable Choosers***
        //SendableChooser for the starting field position
        startingFieldPosition = new SendableChooser();
        SmartDashboard.putData("Starting Field Position for autonomous", startingFieldPosition);
        startingFieldPosition.addDefault("Field Position 1: Low Bar", Autonomous.Position.ONE);
        startingFieldPosition.addObject("Field Position 2", Autonomous.Position.TWO);
        startingFieldPosition.addObject("Field Position 3:", Autonomous.Position.THREE);
        startingFieldPosition.addObject("Field Position 4:", Autonomous.Position.FOUR);
        startingFieldPosition.addObject("Field Position 5", Autonomous.Position.FIVE);
        
        //SendableChooser for the barrier type
        //assigning each barrier to a number
        barrierType = new SendableChooser();
        SmartDashboard.putData("Barrier Type for autonomous", barrierType);
        barrierType.addDefault("Low Bar", Autonomous.Type.LOWBAR);
        barrierType.addObject("Cheval De Frise", Autonomous.Type.CHEVAL_DE_FRISE);
        barrierType.addObject("Moat", Autonomous.Type.MOAT);
        barrierType.addObject("Ramparts", Autonomous.Type.RAMPARTS);
        barrierType.addObject("Rough Terrain", Autonomous.Type.ROUGH_TERRAIN);
        barrierType.addObject("Rock Wall", Autonomous.Type.ROCK_WALL);
        
        //SendableChooser for the strategy
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
            initializeButton(this.launchBallButton, aimStick, LAUNCH_BALL_BUTTON_NUMBER, new LaunchBallCommand());
            initializeButton(this.stopWheelsButton, aimStick, STOP_WHEELS_BUTTON_NUMBER, new StopWheelsCommand());
            initializeButton(this.grabBallButton, aimStick, INTAKE_BALL_BUTTON_NUMBER, new IntakeBallCommand());
            initializeButton(this.launcherJumpToPositionButton, aimStick, LAUNCHER_JUMP_TO_POSITION_BUTTON_NUMBER, new AimerGoToAngleCommand(500));
            initializeButton(this.activateServosTestButton, aimStick, ACTIVATE_SERVOS_TEST_BUTTON_NUMBER, new ActivateLauncherServosCommand());
            initializeButton(this.retractServosTestButton, aimStick, RETRACT_SERVOS_TEST_BUTTON_NUMBER, new RetractLauncherServosCommand());

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
