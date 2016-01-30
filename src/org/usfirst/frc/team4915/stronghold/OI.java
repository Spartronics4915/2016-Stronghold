package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4915.stronghold.commands.IntakeBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.LaunchBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.SetElevatorHeightCommand;
import org.usfirst.frc.team4915.stronghold.commands.ToggleLauncherClosedLoopControlCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    // joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    // constants
    public static final int LAUNCHER_STICK_PORT = 0;
    public static final int LAUNCH_BALL_BUTTON_NUMBER = 0;
    public static final int INTAKE_BALL_BUTTON_NUMBER = 0;
    public static final int TOGGLE_LAUNCHER_CLOSED_LOOP_CONTROL_BUTTON_NUMBER = 0;
    public static final int MOVE_ELEVATOR_BUTTON_NUMBER = 0;

    // create new joysticks
    public Joystick driveStick;
    public Joystick launcherStick;

    // creates new buttons
    public JoystickButton launchBallButton;
    public JoystickButton intakeBallButton;
    public JoystickButton toggleLauncherClosedLoopControlButton;
    public JoystickButton moveElevatorButton;

    public OI(Joystick joystickDrive) {
        this.driveStick = new Joystick(0);
        joystickDrive = new Joystick(1);

        this.launcherStick = new Joystick(LAUNCHER_STICK_PORT);
        this.intakeBallButton = new JoystickButton(this.launcherStick, INTAKE_BALL_BUTTON_NUMBER);
        this.launchBallButton = new JoystickButton(this.launcherStick, LAUNCH_BALL_BUTTON_NUMBER);
        this.toggleLauncherClosedLoopControlButton = new JoystickButton(this.launcherStick, TOGGLE_LAUNCHER_CLOSED_LOOP_CONTROL_BUTTON_NUMBER);
        this.moveElevatorButton = new JoystickButton(this.launcherStick, MOVE_ELEVATOR_BUTTON_NUMBER);

        // binds commands to buttons
        this.intakeBallButton.whenPressed(new IntakeBallCommandGroup());
        this.launchBallButton.whenPressed(new LaunchBallCommandGroup());
        this.toggleLauncherClosedLoopControlButton.whenPressed(new ToggleLauncherClosedLoopControlCommand());
        this.moveElevatorButton.whileHeld(new SetElevatorHeightCommand(this.launcherStick));
    }

    public Joystick getJoystickDrive() {
        return this.driveStick;
    }

}
