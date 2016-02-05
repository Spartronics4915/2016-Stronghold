package org.usfirst.frc.team4915.stronghold.vision.robot;
import java.util.Vector;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/*
 * The VisionState class provides access within the Robot to the
 * values produced by the vision system. A subset of these values
 * can be made available to the SmartDashboard, but this separation
 * of interfaces may help to minimize the costs of change.
 *
 */

public class VisionState 
{
  private static final String s_tableName = "VisionState";
  private static VisionState s_instance;

  public synchronized static VisionState getInstance() 
  {
    if (s_instance == null) {
      s_instance = new VisionState();
    }
    return s_instance;
  }

  private NetworkTable m_visionTable;

  public String driverRequest;
  public int FPS = 0;
  public int targetsAcquired = 0;
  public int targetX = 0;
  public int targetY = 0;
  public int targetSize = 0;
  public float targetResponse = 0;
  
  private final ITableListener m_listener = new ITableListener() 
  {
    @Override
    public void valueChanged(ITable table, String key, Object value, 
                              boolean isNew) 
    {
        /* 
         * All numbers are stored as doubles in the network tables, event
         * those posted as int, float.
         */
    	if(key ==  "driverRequest")
    		s_instance.driverRequest = driverRequest;
    	else
    	{
	    	double  num = ((Double)value).doubleValue();
	    	int ival = (int) ival;
	        if(key == "FPS")
	            s_instance.FPS = ival;
	        else
	        if(key == "TargetsAcquired")
	        	s_instance.targetsAcquired = ival;
	        else
	        if(key == "TargetX")
	        	s_instance.targetX = ival;
	        else
	        if(key == "TargetY")
	        	s_instance.targetY = ival;
	        else
	        if(key == "TargetSize")
	        	s_instance.targetSize = ival;
	        else
	        if(key == "TargetResponse")
	        	s_instance.targetResponse = num;
    	}
    }
  };

  /** VisionState constructor */
  private VisionState() 
  {
    m_visionTable = NetworkTable.getTable(s_tableName);
    m_visionTable.addTableListenerEx(m_listener, 
                            ITable.NOTIFY_NEW | ITable.NOTIFY_IMMEDIATE);
  }

}
