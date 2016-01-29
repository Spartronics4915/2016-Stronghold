package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4915.stronghold.commands.IntakeBallCommandGroup;
import org.usfirst.frc.team4915.stronghold.commands.LaunchBallCommandGroup;

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

    // create new joysticks
    public Joystick driveStick;
    public Joystick launcherStick;

    // creates new buttons
    public JoystickButton launchBallButton;
    public JoystickButton intakeBallButton;

    public OI(Joystick joystickDrive) {
        driveStick = new Joystick(0);
        joystickDrive = new Joystick(1);

        launcherStick = new Joystick(LAUNCHER_STICK_PORT);
        intakeBallButton = new JoystickButton(launcherStick, INTAKE_BALL_BUTTON_NUMBER);
        launchBallButton = new JoystickButton(launcherStick, LAUNCH_BALL_BUTTON_NUMBER);

        // binds commands to buttons
        intakeBallButton.whenPressed(new IntakeBallCommandGroup());
        launchBallButton.whenPressed(new LaunchBallCommandGroup());

    }

    public Joystick getJoystickDrive() {
        return driveStick;
    }

}
