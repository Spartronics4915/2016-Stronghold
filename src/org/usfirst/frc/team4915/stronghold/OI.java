package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.IntakeBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.LaunchBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.ToggleLauncherClosedLoopControlCommand;

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

    // constants, need to talk to electrical to figure out correct port values
    public static final int LAUNCHER_STICK_PORT = -1;
    public static final int LAUNCH_BALL_BUTTON_NUMBER = -1;
    public static final int INTAKE_BALL_BUTTON_NUMBER = -1;
    public static final int TOGGLE_LAUNCHER_CLOSED_LOOP_CONTROL_BUTTON_NUMBER = -1;

    // create new joysticks
    public Joystick driveStick;
    public Joystick launcherStick;

    // creates new buttons
    // launchBall triggers a command group with commands that ultimately will
    // shoot the ball
    // grabBall triggers a command group with commands that will get the ball
    // into the basket
    // toggleLauncherClosedLoopControl toggles auto-refill for the launcher
    // compressor
    public JoystickButton launchBallButton;
    public JoystickButton grabBallButton;
    public JoystickButton toggleLauncherClosedLoopControlButton;

    public OI(Joystick joystickDrive) {
        this.driveStick = new Joystick(0);
        joystickDrive = new Joystick(1);

        this.launcherStick = new Joystick(LAUNCHER_STICK_PORT);
        this.grabBallButton = new JoystickButton(this.launcherStick, INTAKE_BALL_BUTTON_NUMBER);
        this.launchBallButton = new JoystickButton(this.launcherStick, LAUNCH_BALL_BUTTON_NUMBER);
        this.toggleLauncherClosedLoopControlButton = new JoystickButton(this.launcherStick, TOGGLE_LAUNCHER_CLOSED_LOOP_CONTROL_BUTTON_NUMBER);

        // binds commands to buttons
        this.grabBallButton.whenPressed(new IntakeBallCommandGroup());
        this.launchBallButton.whenPressed(new LaunchBallCommandGroup());
        this.toggleLauncherClosedLoopControlButton.whenPressed(new ToggleLauncherClosedLoopControlCommand());
    }

    public Joystick getJoystickDrive() {
        return this.driveStick;
    }

}
