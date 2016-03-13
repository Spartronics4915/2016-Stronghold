package org.usfirst.frc.team4915.stronghold.utils;

import org.usfirst.frc.team4915.stronghold.RobotMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

// IMUPIDSource adapts our pre-existing BMO055 implementation
// for use in conjunction with PIDController.
public class IMUPIDSource implements PIDSource {

    private BNO055 m_imu;

    public IMUPIDSource() {
        this.m_imu = RobotMap.imu;
    }

    public void	setPIDSourceType(PIDSourceType pidSource) {
        if (pidSource != PIDSourceType.kDisplacement) {
            System.out.println("IMUPIDSource only supports kDisplacement");
        }
    }

    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    public double pidGet() {
        return this.m_imu.getNormalizedHeading();
    }

    public double getHeading() {
        return this.m_imu.getNormalizedHeading();
    }
}
