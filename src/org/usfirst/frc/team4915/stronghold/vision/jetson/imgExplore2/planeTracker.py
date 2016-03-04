'''

planeTracker.py: derived from opencv/samples/python2/plane_tracker.py

'''

import numpy as np
import cv2
import json
from collections import namedtuple
import sys
import common

if cv2.__version__[0] == '3':
    cv2.ORB = cv2.ORB_create


FLANN_INDEX_KDTREE = 1
FLANN_INDEX_LSH    = 6
flann_params = dict(algorithm = FLANN_INDEX_LSH,
                   table_number = 6, # 12
                   key_size = 12,     # 20
                   multi_probe_level = 1) #2

MIN_MATCH_COUNT = 10

'''
  image     - image to track
  rect      - tracked rectangle (x1, y1, x2, y2)
  keypoints - keypoints detected inside rect
  descrs    - their descriptors
  data      - some user-provided data
'''
PlanarTarget = namedtuple('PlanarTarget', 
                         'image, rect, keypoints, descrs, data')

'''
  target - reference to PlanarTarget
  p0     - matched points coords in target image
  p1     - matched points coords in input frame
  H      - homography matrix from p0 to p1
  quad   - target bounary quad in input frame
'''
TrackedTarget = namedtuple('TrackedTarget', 
                           'target, p0, p1, H, quad')

class PlaneTracker:
    def __init__(self, loadtargets=True):
        self.strategy = 'orb'
        if self.strategy == 'orb':
            self.detector = cv2.ORB(400)
        elif self.strategy == 'akaze': # AKAZE
            self.detector = cv2.AKAZE_create()
        elif self.strategy == 'brisk': # BRISK
            self.detector = cv2.BRISK_create()
        elif self.strategy == 'mser': # MSER
            # this doesn't currently work
            self.detector = cv2.MSER_create()

        self.matcher = cv2.FlannBasedMatcher(flann_params, {})
        #self.matcher = cv2.BFMatcher(cv2.NORM_HAMMING)

        self.targets = []
        self.featureFile = '.featureTracker_%s.json' % self.strategy
        if loadtargets:
            self.loadTargets()

    def GetTargetCount(self):
        return len(self.targets)

    def Clear(self):
        '''Remove all targets'''
        self.targets = []
        self.matcher.clear()

    def DetectTarget(self, image, rect, data=None):
        '''Add a new tracking target.'''
        x0, y0, x1, y1 = rect
        raw_points, raw_descrs = self.detectFeatures(image)
        points, descs = [], []

        # pick keypoints within the selected rectangle
        for kp, desc in zip(raw_points, raw_descrs):
            x, y = kp.pt
            if x0 <= x <= x1 and y0 <= y <= y1:
                points.append(kp)
                descs.append(desc)

        pt = PlanarTarget(image=image, 
                          rect=rect, 
                          keypoints=points, 
                          descrs=descs, 
                          data=data)
        self.addTarget(pt)
        self.stashTarget()

    def Track(self, frame):
        '''Returns a list of detected TrackedTarget objects'''
        fkplist, fdlist = self.detectFeatures(frame)
        if len(fkplist) < MIN_MATCH_COUNT:
            return []
        matches = self.matcher.knnMatch(fdlist, k = 2)
        matches = [m[0] for m in matches \
                    if len(m) == 2 and m[0].distance < m[1].distance * 0.75]
        if len(matches) < MIN_MATCH_COUNT:
            return []
        matches_by_id = [[] for _ in xrange(len(self.targets))]
        for m in matches:
            matches_by_id[m.imgIdx].append(m)
        tracked = []
        for imgIdx, matches in enumerate(matches_by_id):
            if len(matches) < MIN_MATCH_COUNT:
                continue
            target = self.targets[imgIdx]
            p0 = [target.keypoints[m.trainIdx].pt for m in matches]
            p1 = [fkplist[m.queryIdx].pt for m in matches]
            p0, p1 = np.float32((p0, p1))
            H, status = cv2.findHomography(p0, p1, cv2.RANSAC, 3.0)
            status = status.ravel() != 0
            if status.sum() < MIN_MATCH_COUNT:
                continue
            p0, p1 = p0[status], p1[status]

            x0, y0, x1, y1 = target.rect
            quad = np.float32([[x0, y0], [x1, y0], [x1, y1], [x0, y1]])
            qr = quad.reshape(1, -1, 2)
            quad = cv2.perspectiveTransform(qr, H).reshape(-1, 2)
            track = TrackedTarget(target=target, p0=p0, p1=p1, H=H, 
                                    quad=quad)
            tracked.append(track)
        tracked.sort(key = lambda t: len(t.p0), reverse=True)
        return tracked

    # --------------------------------------------------------------------
    def detectFeatures(self, frame):
        '''detect_features(self, frame) -> keypoints, descrs'''
        descrs = None
        if self.strategy == 'mser':
            keypoints = self.detector.detect(frame, None)
            descrs = []
        else:
            keypoints, descrs = self.detector.detectAndCompute(frame, None)
        if descrs is None:  
            # detectAndCompute returns descs=None if no keypoints found
            descrs = []
        return keypoints, descrs

    def filterMatches(self, kp1, kp2, matches, ratio=.75):
        mkp1, mkp2 = [], []
        for m in matches:
            if len(m) == 2 and m[0].distance < m[1].distance * ratio:
                m = m[0]
                mkp1.append( kp1[m.queryIdx] )
                mkp2.append( kp2[m.trainIdx] )
        p1 = np.float32([kp.pt for kp in mkp1])
        p2 = np.float32([kp.pt for kp in mkp2])
        kpPairs = zip(mkp1, mkp2)
        return p1, p2, kpPairs

    def addTarget(self, target):
        self.matcher.add([np.uint8(target.descrs)])
        self.targets.append(target)

    def stashTarget(self):
        if len(self.targets) > 0:
            jsonf = {
                'numtargets': len(self.targets)
            }
            
            i = 0
            for t in self.targets:
                # flatten KeyPoint into 7 numbers
                x0, y0, x1, y1 = t.rect           
                kpts = [[kp.pt[0], kp.pt[1], kp.size, kp.angle, kp.response, 
                   kp.octave, kp.class_id] for kp in t.keypoints]
                # target.descrs is list of np.array
                desc = [d.tolist() for d in t.descrs]
                jsonf['rect%d' % i] = [int(x0), int(y0), int(x1), int(y1)]
                jsonf['kpts%d' % i] = kpts
                jsonf['desc%d' % i] = desc
                i = i + 1

            f = open(self.featureFile, "w")
            f.write(json.dumps(jsonf))
            f.close()

    def loadTargets(self):
        if self.targets:
            img = self.targets[0].image
        else:
            img = None
        self.Clear()
        try: 
            f = open(self.featureFile, "r")
            jstr = f.read()
            f.close()
            d = json.loads(jstr)
            if 'numtargets' in d:
                for t in range(0, d['numtargets']):
                   self.addTarget(self.extractTarget(d, "%d"%t, img))
            else:
                self.addTarget(self.extractTarget(d, '', img))
            print("loaded: %d targets" % self.GetTargetCount())
        except:
            print("problem opening %s (%s)" % 
                    (self.featureFile, sys.exc_info()[1]))

    def extractTarget(self, d, id, img):
        r = d['rect'+id]
        kpts = [cv2.KeyPoint(x, y, _s, _a, _r, int(_o), int(_c))
                for x,y,_s,_a,_r,_o,_c in list(d['kpts'+id]) ]
        descs = [np.array(d, dtype=np.uint8) for d in d['desc'+id] ]
        pt = PlanarTarget(image=img,
                         rect=r,
                         keypoints=kpts, 
                         descrs=descs, 
                         data=None)
        return pt


