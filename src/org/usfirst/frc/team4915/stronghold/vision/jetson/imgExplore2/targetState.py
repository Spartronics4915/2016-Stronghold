#
# TargetState:
#   encapsulates state associated with our analysis of the
#   state of the targets.
#

import sys

class TargetState:
    def __init__(self, visTab):
        self.m_visTab = visTab
        self.m_kp = None
        self.m_lastKp = None

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
    
    def NewLines(self, lines):
        return lines

    def NewKeypoints(self, kplist):
        if len(kplist) > 0:
            kplist.sort(self.kpcompare)
            self.m_kp = kplist[0]
            if 0:
                # print out our keypoints for debugging
                for kp in kplist:
                    sys.stdout.write("%d "%kp.size);
                sys.stdout.write("\n")
        else:
            self.m_kp = None
        self.updateVisionTable()
        return kplist

    def updateVisionTable(self):
        kp = self.m_kp
        if not kp:
            self.m_visTab.putBoolean("TargetAcquired", False)
        else:
            self.m_visTab.putBoolean("TargetAcquired", True)
            self.m_visTab.putInt("TargetX", int(.5+kp.pt[0]))
            self.m_visTab.putNumber("TargetY", int(.5+kp.pt[1]))
            self.m_visTab.putNumber("TargetSize", int(.5+kp.size))
            self.m_visTab.putNumber("TargetResponse", kp.response)
            self.m_visTab.putInt("TargetClass", kp.class_id)

