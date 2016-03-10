#
# TargetState:
#   encapsulates state associated with our analysis of the
#   state of the targets.
#
# microsoft lifecam hd-3000 specs:
#   720p: 1280/720
#   diagonal fov: 68.5 degress (diagonal)
#
# But with tegra's opencv+python, we can only capture at 640x480
# so we measured the hfov to be 54 degrees. Which would  give us
# a dfov of 67.5
#       800 = math.sqrt(w*w  + h*h)
#       1.25 =  800 / 640
#       67.5  = 1.25 * 54

import sys

class TargetState:
    def __init__(self, visTab):
        self.m_visTab = visTab
        self.m_kp = None
        self.m_lastKp = None
        self.m_res =  (640, 480)
        ar = self.m_res[0] / float(self.m_res[1])
        self.m_fov = (54., 54. / ar)  # full angles
        self.m_center = (self.m_res[0]/2, self.m_res[1]/2)
        self.m_visTab.putString("~TYPE~", "Vision")
        self.m_visTab.putInt("TargetAcquired", 0)
        self.m_kpHistory = []

    # a key-point sorter:
    #   We wish to find the most relevant keypoints.
    #   The approach is up for debate:
    #       - currently we'll look at maximum size
    #       - a distance from last good kp might be wise
    # keypoint fields:
    #   Point2f pt
    #   float   size  (diameter)
    #   float   angle (degrees) (-1 means NA)
    #   float   response
    #   int     octave  (pyramid layer)
    #   int     class_id  (as with a cluster_id)
    @staticmethod
    def kpcompare(kp1, kp2):
        if kp1.size > kp2.size:  # sort bigger to front of list
            return -1
        elif kp1.size == kp2.size:
            return 0
        else:
            return 1

    def nearbyPt(self, p0, p1):
        return (p0[0]-3 < p1[0] < p0[0]+3) and \
               (p0[1]-3 < p1[1] < p0[1]+3)

    def kpCompareByStability(self, kp1, kp2):
    	kp1Frequency = 0
    	kp2Frequency = 0
    	for kp in self.m_kpHistory:
    		if self.nearbyPt(kp.pt, kp1.pt):
    			kp1Frequency += 1
    		if self.nearbyPt(kp.pt, kp2.pt):
    			kp2Frequency += 1
        if kp1Frequency > kp2Frequency:  # sort most frequent to front of list
            return -1
        elif kp1.size == kp2.size:
            return 0
        else:
            return 1

    def SetFPS(self, fps):
        self.m_visTab.putInt("FPS", fps)

    def NewLines(self, lines):
        return lines

    def CompareKeypointToHistory(self):
    	self.m_kpHistory = self.m_kpHistory[-2:]
        #print("hello: len(%d) %s", (len(self.m_kpHistory),
        #                            repr(self.m_kpHistory[0])))
    	distBetweenKeypoints =(self.m_kpHistory[0].pt[0]-self.m_kp.pt[0])**2 + \
                              (self.m_kpHistory[0].pt[1] - self.m_kp.pt[1])**2
    	if distBetweenKeypoints > -1:
    		self.m_kp = self.m_kpHistory[0]

    def calcDist(self, kp1, kp2):
    	distBetweenKeypoints =(kp1.pt[0]-kp2.pt[0])**2 + \
                              (kp1.pt[1] - kp2.pt[1])**2
        return distBetweenKeypoints

    def AverageKeypoints(self):
     	avgKeypointX = 0
     	avgKeypointY = 0
    	for kp in self.m_kpHistory:
    		avgKeypointX += kp.pt[0]
    		avgKeypointY += kp.pt[1]
    	avgKeypointX += self.m_kpHistory[-1].pt[0]
    	avgKeypointY += self.m_kpHistory[-1].pt[1]
    	self.m_kp.pt = (avgKeypointX / 5, avgKeypointY / 5)
    	return self.m_kp

    # NewTarget: we assume that kp is in absolute (not screen-rel) coords
    def NewTarget(self, kp):
        self.updateVisionTable(kp)
        return [kp]

    def NewKeypoints(self, kplist):
        if len(kplist) > 0:
            if 0:
                # always sort biggest to front
                if len(kplist) > 0:
                    kplist.sort(self.kpcompare)
                    self.m_kp = kplist[0]
            elif 0:
                #average 5 most recent keypoints (not very useful because
                #creating a new keypoint is unsafe)
                kplist.sort(self.kpcompare)
                self.m_kp = kplist[0]
                self.m_kpHistory.append(self.m_kp)
                self.m_kpHistory = self.m_kpHistory[-4:]
                self.m_kp = self.AverageKeypoints()
            elif 0:
                #keep the 10 biggest keypoints ever found (not very good)
                kplist.extend(self.m_kpHistory)
                self.m_kpHistory = kplist
                kplist.sort(self.kpcompare)
                kplist = kplist[:10]
                self.m_kpHistory = kplist
            elif 0:
                nearest = None
                size = None
                nearestD = 10000
                nearestSize = 0
                if self.m_kp:
                    # old valid kp... just search near
                    # search near but also consider size
                    for kp in kplist:
                        dist = self.calcDist(self.m_kp, kp)
                        size = kp.size
                        if (1/dist)*size>(1/nearestD)*nearestSize and dist<125:
                            nearest = kp
                            nearestSize = size
                            nearestD = dist

                if not nearest:
                    kplist.sort(self.kpcompare)
                    self.m_kp = kplist[0]

                if nearest:
                    self.m_kp = nearest
            elif 1:
            	#go to most stable keypoint (has issues when moving)
            	self.m_kpHistory.extend(kplist)
            	self.m_kpHistory = self.m_kpHistory[:50]
            	kplist.sort(self.kpCompareByStability)
                self.m_kp = kplist[0]

            # append and trucate to fixed length:
            # self.m_kpHistory = self.m_kpHistory.append(self.m_kp)[-5:]

            # self.CompareKeypointToHistory()
            if 0:
                # print out our keypoints for debugging
                for kp in kplist:
                    sys.stdout.write("%d "%kp.size);
                sys.stdout.write("\n")
        else:
            self.m_kp = None
        self.updateVisionTable(self.m_kp)
        return kplist

    def pixelToAngle(self, pt):
        x = self.m_fov[0] * (pt[0] - self.m_center[0]) / self.m_res[0];
        y = self.m_fov[1] * (pt[1] - self.m_center[1]) / self.m_res[1];
        return (x,y)

    def updateVisionTable(self, kp):
        if not kp:
            self.m_visTab.putInt("TargetAcquired", 0)
        else:
            theta = self.pixelToAngle(kp.pt)
            self.m_visTab.putInt("TargetAcquired", 1)
            self.m_visTab.putNumber("TargetX", theta[0])
            self.m_visTab.putNumber("TargetY", theta[1])
