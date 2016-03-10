package org.usfirst.frc.team4915.stronghold.vision.robot;

import org.usfirst.frc.team4915.stronghold.ModuleManager;
import org.usfirst.frc.team4915.stronghold.RobotMap;

import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/*
 * The VisionState class provides access within the Robot and the
 * driver station to the values produced by the vision system.
 */

public class VisionState implements NamedSendable {

    private static VisionState s_instance;

    public synchronized static VisionState getInstance() {
        if (ModuleManager.VISION_MODULE_ON) {
            if (s_instance == null)
                s_instance = new VisionState();
        }
        return s_instance;
    }

    // these values originate from robot / driverstation
    public boolean AutoAimEnabled = false;
    public boolean TargetHigh = true;

    // these values are private to robot
    public boolean DriveLockedOnTarget = false;
    public boolean LauncherLockedOnTarget = false;

    // these values originate from jetson
    private int FPS = 0;
    private int TargetAcquired = 0;
    private double TargetX = 0;
    private double TargetY = 0;

    private ITable m_table = null;
    private final ITableListener m_listener = new ITableListener() {

        @Override
        public void valueChanged(ITable table, String key, Object value,
                boolean isNew) {
            /*
             * All numbers are stored as doubles in the network tables, event
             * those posted as int, float.
             */
        	/* debug:
        	System.out.println(key + " " + value + " " +
                    value.getClass().getName());
        	*/

        	if (key.equals("~TYPE~")) {
                return;
            }
        	else if (key.equals("AutoAimEnabled")) {
                s_instance.AutoAimEnabled = (boolean) value;
            }
            else {
                // System.out.println(key + " " + value);
                double num = (Double) value;
                int ival = (int) num;
                if (key.equals("FPS"))
                    s_instance.FPS = ival;
                else if (key.equals("TargetAcquired"))
                    s_instance.TargetAcquired = ival;
                else if (key.equals("TargetX")) {
                	s_instance.TargetX = num;
                }
                else if (key.equals("TargetY"))
                    s_instance.TargetY = num;
                else
                    System.out.println(key + " " + value + " " +
                            value.getClass().getName());
            }
        }
    };

    /** constructor is private, we're singleton for now */
    private VisionState() {
    }

    public String getName() {
        return "Vision";
    }

    public ITable getTable() {
        return m_table;
    }

    public String getSmartDashboardType() {
        return "Vision";
    }

    public void initTable(ITable subtable) {
        if (this.m_table != null)
            this.m_table.removeTableListener(m_listener);
        this.m_table = subtable;
        m_table.addTableListener(m_listener);

        // values expected to come from robot or dashboard
        this.AutoAimEnabled = m_table.getBoolean("AutoAimEnabled", false);
        this.TargetHigh = m_table.getBoolean("TargetHigh", true);

        // values expected to arrive from jetson
        this.FPS = (int) m_table.getNumber("FPS", 0.);
        this.TargetAcquired = (int) m_table.getNumber("TargetAcquired", 0);
        this.TargetX = m_table.getNumber("TargetX", 0);
        this.TargetY = m_table.getNumber("TargetY", 0);
    }

    public void toggleAimState(boolean toggleEnable, boolean toggleTarget) {
        if (toggleEnable) {
            this.AutoAimEnabled = !this.AutoAimEnabled;
            m_table.putBoolean("AutoAimEnabled", this.AutoAimEnabled);
            setLight(this.AutoAimEnabled);
        }
        if (toggleTarget) {
            this.TargetHigh = !this.TargetHigh;
            // m_table.putBoolean("TargetHigh", this.TargetHigh);
            // System.out.println("TargetHigh:" + this.TargetHigh);
        }
        DriveLockedOnTarget = false;
        LauncherLockedOnTarget = false;
    }

    public double getTargetHeading(double currentHeading) {
        // this.TargetX presumed current-heading-relative angle
        return this.TargetX + currentHeading;
    }

    public double getTargetElevation(double currentElevation) {
        return this.TargetY + currentElevation;
    }

    public boolean wantsControl() {
        return AutoAimEnabled;
    }

    /**
     * Enable or disable the "photonic cannon" on the robot.
     *
     * @param on True to turn on, false to turn off
     */
    public void setLight(boolean on) {
        RobotMap.PHOTONIC_CANNON.set(on);
    }
}
