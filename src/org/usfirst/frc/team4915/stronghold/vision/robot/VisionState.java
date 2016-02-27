package org.usfirst.frc.team4915.stronghold.vision.robot;

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
        if (s_instance == null)
            s_instance = new VisionState();
        return s_instance;
    }

    // these values originate from robot / driverstation
    public boolean AutoAimEnabled = false;
    public boolean TargetHigh = true;
    public int IMUHeading = 0;

    // these values are private to robot
    public boolean DriveLockedOnTarget = false;
    public boolean LauncherLockedOnTarget = false;

    // these values originate from jetson
    public int RelativeTargetingMode = 1;
    public int FPS = 0;
    public int TargetAcquired = 0;
    public int TargetX = 0;
    public int TargetY = 0;

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
            } else if (key.equals("AutoAimEnabled")) {
                s_instance.AutoAimEnabled = (boolean) value;
            }
            else if (key.equals("RelativeTargetingMode")) {
            	double num = (Double) value;
                int ival = (int) num;
                s_instance.RelativeTargetingMode = ival;
            }
            else {
                // System.out.println(key + " " + value);
                double num = (Double) value;
                int ival = (int) num;
                if (key.equals("FPS"))
                    s_instance.FPS = ival;
                else if (key.equals("IMUHeading"))
                    s_instance.IMUHeading = ival;
                else if (key.equals("TargetAcquired"))
                    s_instance.TargetAcquired = ival;
                else if (key.equals("TargetX")) {
                	System.out.println(s_instance.TargetX);
                	s_instance.TargetX = ival;
                }
                else if (key.equals("TargetY"))
                    s_instance.TargetY = ival;
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
        this.IMUHeading = (int) m_table.getNumber("IMUHeading", 0.);

        // values expected to arrive from jetson
        this.RelativeTargetingMode = (int)
                m_table.getNumber("RelativeTargetingMode", 1);
        this.FPS = (int) m_table.getNumber("FPS", 0.);

        this.IMUHeading = (int) m_table.getNumber("IMUHeading", 0.);
        this.TargetAcquired = (int) m_table.getNumber("TargetAcquired", 0);
        this.TargetX = (int) m_table.getNumber("TargetX", 0);
        this.TargetY = (int) m_table.getNumber("TargetY", 0);
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
            System.out.println("TargetHigh:" + this.TargetHigh);
        }
        DriveLockedOnTarget = false;
        LauncherLockedOnTarget = false;
    }

    public void updateIMUHeading(double heading) {
    	if(m_table != null) {
    		m_table.putNumber("IMUHeading", (int) (heading + .5));
    	}
    }

    /*
     * public boolean followTargetX(DriveTrain driveTrain) {
     * if(this.AutoAimEnabled && this.TargetAcquired > 0) { if (this.TargetX <=
     * -1){ driveTrain.turn(false); } else { driveTrain.turn(true); } return
     * true; } return false; } public boolean followTargetY(IntakeLauncher
     * intakeLauncher) { if(this.AutoAimEnabled && this.TargetAcquired > 0) {
     * intakeLauncher.setPointInDegrees(TargetY); return true; } else { return
     * false; } }
     */

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
