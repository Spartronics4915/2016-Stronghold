
package org.usfirst.frc.team4915.stronghold.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4915.stronghold.RobotMap;
import org.usfirst.frc.team4915.stronghold.commands.ArcadeDrive;

public class DriveTrain extends Subsystem {

    RobotDrive robotDrive = new RobotDrive(RobotMap.leftFrontMotor, RobotMap.leftBackMotor, RobotMap.rightFrontMotor, RobotMap.rightBackMotor);

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ArcadeDrive());
    }
}
