package org.usfirst.frc.team4915.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4915.stronghold.commands.IntakeLauncher.*;

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
    public static final int PLACEHOLDER_NUMBER = 69;
    public static final int LAUNCHER_STICK_PORT = PLACEHOLDER_NUMBER; // TODO
    public static final int LAUNCH_BALL_BUTTON_NUMBER = 1; 
    public static final int INTAKE_BALL_BUTTON_NUMBER = 2; 
    public static final int AUTO_AIM_BUTTON_NUMBER = 11; 
    public static final int LAUNCHER_UP_BUTTON_NUMBER = 7; 
    public static final int LAUNCHER_DOWN_BUTTON_NUMBER = 6; 

    public static final int UP_DIRECTION = 1;
    public static final int DOWN_DIRECTION = UP_DIRECTION * -1;

    // create new joysticks
    public Joystick driveStick;
    public Joystick aimStick;

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

    public OI(Joystick joystickDrive) {
        this.driveStick = new Joystick(0);
        joystickDrive = new Joystick(1);

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

}
