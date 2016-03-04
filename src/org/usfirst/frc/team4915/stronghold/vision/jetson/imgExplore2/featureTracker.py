#!/usr/bin/python
#
# featureTracker.py
#   a python/opencv app to perform feature matching.
#   based on the opencv/python example: plane_tracker.py
#
#
from __future__ import print_function
import cv2
import os.path
import sys, traceback
import argparse
import numpy as np
import math
import daemon
from collections import deque
import robotCnx, planeTracker, common

class FSrc:
    def __init__(self, filepat):
        self.vsrc = None
        self.fnpat = filepat
        self.currentFn = None
        self.currentF = -1
        if filepat:
            self.Step(1)
        else:
            for i in range(0, 4):
                self.vsrc = cv2.VideoCapture(i)
                if not self.vsrc or not self.vsrc.isOpened():
                    print("Problem opening video source %d" % i)
                    self.vsrc = None
                else:
                    break
            if self.vsrc:
                ret1 = self.vsrc.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
                ret2 = self.vsrc.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)
                w = self.vsrc.get(cv2.CAP_PROP_FRAME_WIDTH)
                h = self.vsrc.get(cv2.CAP_PROP_FRAME_HEIGHT)
                print("video res: %d %d" % (w,h))

    def Step(self, incr):
        if self.fnpat:
            i = self.currentF + incr
            fn = None
            while fn == None:
                fn = self.fnpat % i
                if os.path.isfile(fn) and self.currentFn != fn:
                    self.currentF = i
                    break
                else:
                    i = i + incr
                    fn = None
                    if i <= 0 or i > 500:
                        break
            if fn:
                self.currentFn = fn
                self.currentFrame = cv2.imread(fn, cv2.IMREAD_ANYCOLOR)
            else:
                self.currentFn = None
                self.currentFrame = None

    # NextFrame:
    #   returns tuple (ret, image)
    def NextFrame(self):
        if self.vsrc:
            ret, self.currentFrame = self.vsrc.read() 
        else:
            if self.currentFrame != None:
                ret = 0
            else:
                ret = 1
        return (ret, self.currentFrame)

class App:
    def __init__(self):
        self.args = self.parseArgs()
        if self.args.cannedimages:
            self.fnpat = '../../pictures/RealFullField/%d.jpg'
            self.waitPeriod = 20
        else:
            self.fnpat = None
            self.waitPeriod = 1
            
        self.fsrc = FSrc(self.fnpat)
        if not self.fsrc:
            exit(1)
        self.frame = None
        self.frameBW = None
        self.paused = False
        load = not self.args.ignoretarget
        self.tracker = planeTracker.PlaneTracker(loadtargets=load)
        self.winNm = 'FeatureTracker'
        cv2.namedWindow(self.winNm)
        self.selRect = common.RectSelector(self.winNm, self.rectCB)

    def rectCB(self, rect):
        self.tracker.DetectTarget(self.frameBW, rect)
        print("target %d added" % self.tracker.GetTargetCount())

    def run(self):
        while True:
            playing = not self.paused and not self.selRect.dragging
            newFrame = False
            if playing or self.frame is None:
                ret, frame = self.fsrc.NextFrame()
                if frame != None:
                    self.frame = frame
                    # more general frame processing can happen here..
                    self.frameBW = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
                    #self.frameBW = self.applyGamma(self.frameBW, .3)
                    newFrame = True

            vis = self.frame.copy()
            if newFrame and playing:
                tracked = self.tracker.Track(self.frameBW)
                for tr in tracked:
                    cv2.polylines(vis, [np.int32(tr.quad)], True, 
                                        (255, 255, 255), 1)
                    for (x, y) in np.int32(tr.p1):
                            cv2.circle(vis, (x, y), 2, (255, 255, 255))
            self.selRect.draw(vis)
            cv2.imshow(self.winNm, vis)

            ch = cv2.waitKey(self.waitPeriod) & 0xFF
            if ch == ord(' '):
                self.paused = not self.paused
            elif ch == ord('c'):
                print("clearing...")
                self.tracker.Clear()
            elif ch == 27:
                break
            elif ch == 81: # left arrow
                self.fsrc.Step(-1)
            elif ch == 83: # right arrow
                self.fsrc.Step(1)
            elif ch == 255:
                pass
            else :
                print("unimplemented key: %d" % ch)

    # -------------------------------------------------------------------
    def applyGamma(self, image, gamma):
        # build a lookup table mapping the pixel values [0, 255] to
        # their adjusted gamma values
        invGamma = 1.0 / gamma
        table = np.array([((i / 255.0) ** invGamma) * 255
                          for i in np.arange(0, 256)]).astype("uint8")
        return cv2.LUT(image, table)

    def parseArgs(self):
        parser = argparse.ArgumentParser()
        parser.add_argument("-c", "--cannedimages",
                help="use canned images instead of video",
                action='store_true')
        parser.add_argument("-i", "--ignoretarget",
                help="ignore existing targets on startup",
                action='store_true')
        parser.add_argument("-n", "--nodisplay",
                help="disable display of images to screen",
                action='store_true')
        parser.add_argument("-s", "--stashinterval",
                default=0,
                type=float,
                help="number of seconds between image stashes (0 means off)")
        parser.add_argument("-d", "--daemonize",
                help="put imgExplore in background",
                action='store_true')
        return parser.parse_args()

if __name__ == '__main__':
    App().run()
