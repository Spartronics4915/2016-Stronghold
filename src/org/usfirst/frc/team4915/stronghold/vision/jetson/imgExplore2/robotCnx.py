#
# robotCnx:
#   manages our connection to the robot
#
# # SmartDashboard/vision:  the vision subtable for communications with 
#  Robot & Driver
#       FPS: 0 means inactive
#       DriverRequest: "reset"
#       TargetAcquired: 0 or 1
#       TargetLocationX:
#       TargetLocationY:
#       TargetDistance:


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

            self.smartDashboard = NetworkTable.getTable("SmartDashboard")
            self.connectionListener = ConnectionListener()
            self.smartDashboard.addConnectionListener(self.connectionListener)
            self.visTable = self.smartDashboard.getSubTable("vision")
            self.visTable.addTableListener(self.visValueChanged)
            self.targetState = targetState.TargetState(self.visTable)

        except:
            xcpt = sys.exc_info()
            print("ERROR initializing network tables", xcpt[0])
            traceback.print_tb(xcpt[2])

    def GetTargetState(self):
        return self.targetState

    def SetFPS(self, fps):
        self.visTable.putInt("FPS", fps)

    def Shutdown(self):
        self.SetFPS(0)
        self.visTable.removeTableListener(self.visValueChanged)
        self.smartDashboard.removeConnectionListener(self.connectionListener)

    def NewKeypoints(self, kplist):
        return self.targetState.NewKeypoints(kplist)

    def NewLines(self, llist):
        return self.targetState.NewLines(llist)

    @staticmethod
    def visValueChanged(table, key, value, isNew):
        if key == 'DriverRequest':
            print("visVal: '%s':%s new:%s" % (key, value, isNew))

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
