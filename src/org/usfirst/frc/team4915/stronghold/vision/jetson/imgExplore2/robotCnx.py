#
# robotCnx:
#   manages our connection to the robot
#
# # SmartDashboard/vision:  the vision subtable for communications with 
#  Robot & Driver.  See targetState.py for details of the table contents.


from networktables import NetworkTable
import targetState
import sys, traceback

class RobotCnx:
    def __init__(self, fakerobot):
        try:
            if fakerobot:
                NetworkTable.setIPAddress("localhost")
            else:
                NetworkTable.setIPAddress("roboRIO-4915-FRC")
            NetworkTable.setClientMode()
            NetworkTable.initialize()

            self.sd = NetworkTable.getTable("SmartDashboard")
            self.visTable = self.sd.getSubTable("Vision")
            self.connectionListener = ConnectionListener()
            self.visTable.addConnectionListener(self.connectionListener)
            self.visTable.addTableListener(self.visValueChanged)
            self.targetState = targetState.TargetState(self.visTable)
            self.targetHigh = True
            self.autoAimEnabled = False
            self.imuHeading = 0

        except:
            xcpt = sys.exc_info()
            print("ERROR initializing network tables", xcpt[0])
            traceback.print_tb(xcpt[2])

    def GetTargetState(self):
        return self.targetState

    def GetIMUHeading(self):
        return self.imuHeading

    def SetFPS(self, fps):
        self.targetState.SetFPS(fps)

    def Shutdown(self):
        self.targetState.SetFPS(0)
        self.visTable.removeTableListener(self.visValueChanged)
        self.visTable.removeConnectionListener(self.connectionListener)

    def NewKeypoints(self, kplist):
        return self.targetState.NewKeypoints(kplist)
        
    def NewTarget(self, target):
        return self.targetState.NewTarget(target)

    def NewLines(self, llist):
        return self.targetState.NewLines(llist)

    @staticmethod
    def visValueChanged(table, key, value, isNew):
        # This is where we can be woken up if the driver station 
        # (or robot) wants to talk to us. This method fires only
        # on changes to /SmartDashboard/Vision/*
        if key == 'TargetHigh':
        	self.targetHigh = value
            #print(value)
        elif key == 'AutoAimEnabled':
        	self.autoAimEnabled = value
            #print(value)
        elif key == 'IMUHeading':
        	self.imuHeading = value
            #print(value)
        else:
            pass
        	# print("Unexpected key in visValueChanged")

class ConnectionListener:
    def __init__(self):
        self.table = None

    def connected(self, table):
        print("connected", table)
        self.table = table

    def disconnected(self, table):
        if self.table:
            print("disconnected", table)
        self.table = None

    def isConnected(self):
        return self.getTable() != None

    def getTable(self):
        return self.table
