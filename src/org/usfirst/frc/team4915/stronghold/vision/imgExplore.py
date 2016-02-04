#!/usr/bin/python
#
#
# imgExplore.py
#   a python/opencv utility for perusing video or image streams.
#   Performance issues can be resolved by migrating algorithms to
#   gpu. (VisionWorks, Cuda, etc are available optimization avenues)
#
#   db: 1/28/16, for spartronics 4915
#
#

from __future__ import print_function
import cv2
import os.path
import sys, getopt
import common
import numpy as np
from os.pat import expanduser

from multiprocessing.pool import ThreadPool
from collections import deque

# fnpat = '/home/knx32542/Downloads/RealFullField/%d.jpg'
fnpat = expanduser("~/RealFullField/%d.jpg")

def main():
    opts, args = getopt.getopt(sys.argv[1:], '', ['video='])
    opts = dict(opts)
    video = int(opts.get('--video', "0"))
    vsrc = None

    index = 0
    lastFn = None
    cmode = 'rgb'
    mainImg = None
    value = 0
    algostate = {}

    print("keys:\n" \
          "  ESC: exit\n\n" \
          "  c: rgb\n" \
          "  r: red\n" \
          "  g: green\n" \
          "  b: blue,\n" \
          "  h: hue\n" \
          "  s: sat\n"\
          "  v: val\n" \
          "  0: adaptive threshold\n"\
          "  1: threshold\n"\
          "  2: huerange*valuerange\n"\
          "  3: canny edges\n"\
          "  4: simple blobs\n"\
          "\n"\
          "  <home>:           reset algorithm input\n"\
          "  <up arrow>:       increase algorithm input\n"\
          "  <down arrow>:     decrease algorithm input\n"\
          "\n"\
          "  <right arrow>:    increase img seq frame\n"\
          "  <left arrow>:     decrease img seq frame\n"\
          )

    if video:
        vsrc = cv2.VideoCapture(0)
        if not vsrc or not vsrc.isOpened():
            print("Problem opening video source")
            vsrc = None
            video = 0
        if 0:
            vsrc.set(cv2.cv.CV_CAP_PROP_FRAME_WIDTH, w)
            vsrc.set(cv2.cv.CV_CAP_PROP_FRAME_HEIGHT, h)
    else:
        vsrc = None

    def processFrame(frame, t0):
        keypoints = None
        if cmode != 'rgb':
            if cmode in ['h', 's', 'v']:
                #mode = (cv2.COLOR_BGR2HLS, cv2.COLOR_HLS2BGR)
                mode = (cv2.COLOR_BGR2HSV, cv2.COLOR_HSV2BGR)
                hsv = cv2.cvtColor(frame, mode[0])
                if cmode == 'h':
                    if mode[0] == cv2.COLOR_BGR2HSV: 
                        hsv[:,:,1] = 255 # s = 1
                        hsv[:,:,2] = 128 # v = .5
                    else:
                        hsv[:,:,1] = 128 # l = .5 
                        hsv[:,:,2] = 255 # s = 1
                    frame = cv2.cvtColor(hsv, mode[1])
                elif cmode == 's':
                    # extract the s as grayscale
                    if mode[0] == cv2.COLOR_BGR2HSV: 
                        h,frame,v = cv2.split(hsv)
                    else:
                        h,l,frame = cv2.split(hsv)
                elif cmode == 'v':
                    if mode[0] == cv2.COLOR_BGR2HSV: 
                        h,s,frame = cv2.split(hsv)
                    else:
                        h,frame,s = cv2.split(hsv)
            elif cmode in ['r', 'g', 'b']:
                if cmode == 'r':
                    b,g,frame = cv2.split(frame)
                elif cmode == 'g':
                    b,frame,r = cv2.split(frame)
                elif cmode == 'b':
                    frame,g,r = cv2.split(frame)
            elif cmode == '0':
                gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
                frame = cv2.adaptiveThreshold(gray, 
                                            200, # value to draw
                                            cv2.ADAPTIVE_THRESH_MEAN_C,
                                            cv2.THRESH_BINARY,
                                            5, 
                                            value)
            elif cmode == '1':
                # simple thresholding
                gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
                (ret,frame) = cv2.threshold(gray, 
                                      75+value,
                                      200, # value to draw
                                      cv2.THRESH_BINARY)
            elif cmode == '2':
                # threshold value (want bright areas)
                # multiply result by hue in range
                mode = cv2.COLOR_BGR2HSV # cv2.COLOR_HSV2BGR)
                hsv = cv2.cvtColor(frame, mode)
                h,s,v = cv2.split(hsv)
                vimg = cv2.inRange(v, 65, 255)
                himg = cv2.inRange(h, 50, 90)
                frame = cv2.multiply(vimg, himg, scale=1.0/255)
            elif cmode == '3':
                t1 = 2000+value*20
                t2 = t1+2000
                frame = cv2.Canny(frame, t1, t2, apertureSize=5)
            elif cmode == '4':
                if not "blobdetector" in algostate:
                    bp = cv2.SimpleBlobDetector_Params()
                    bp.filterByColor = True
                    bp.blobColor = 0

                    # Change thresholds
                    bp.minThreshold = 50
                    bp.maxThreshold = 150
                    bp.thresholdStep = 5

                    # Filter by Area.
                    bp.filterByArea = True
                    bp.minArea = 500
                    bp.maxArea = 10000

                    # Filter by Circularity
                    bp.filterByCircularity = False
                    bp.minCircularity = 0.1

                    # Filter by Convexity
                    bp.filterByConvexity = False
                    bp.minConvexity = 0.87

                    # Filter by Inertia
                    bp.filterByInertia = False
                    bp.minInertiaRatio = 0.01
                    
                    algostate["blobdetector"] = cv2.SimpleBlobDetector(bp)

                frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
                keypoints = algostate["blobdetector"].detect(frame)
                
                
            else:
                print("unknown cmode: " + cmode)
        return frame, t0, keypoints

    def showImg(frame, keypoints):
        if keypoints:
            frame = cv2.drawKeypoints(frame, keypoints,
                                   np.array([]),
                                   (0,0,255), 
                                   cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS) 
        cv2.imshow("img", frame)

    #cv2.namedWindow("img")
    #cv2.createTrackbar("value", "img", 0, 100, trackbarChange)
        
    threadn = cv2.getNumberOfCPUs()
    pool = ThreadPool(processes = threadn)
    pending = deque()
    threadedMode = True
    latency = common.StatValue()   
    frameT = common.StatValue() 
    lastFrameTime = common.clock()
    
    while True:
        while len(pending) > 0 and pending[0].ready():
            frame,t0,keypoints = pending.popleft().get()
            latency.update(common.clock() - t0)
            putStr(frame, (20, 20), 
                    "latency       : %.1f ms" % (latency.value*1000))
            putStr(frame, (20, 40), 
                    "frame interval: %.1f ms" % (frameT.value*1000))
            showImg(frame, keypoints)

        if video:
            if len(pending) < threadn:
                ret, mainImg = vsrc.read()
                t = common.clock()
                frameT.update(t - lastFrameTime)
                lastFrameTime = t
                task = pool.apply_async(processFrame, (mainImg.copy(), t))
                pending.append(task)

            done,update,cmode,index,value = checkKey(1, cmode, index, value)
            if done:
                break
        else:
            if index < 0:
                # index < 0 signals user's intent to go backward in framelist
                goback = True
                index = abs(index)
            else:
                goback = False
            fn = fnpat % index
            base = os.path.basename(fn)
            if not os.path.isfile(fn) and index >= 0:
                sys.stdout.write(base+" not found\r")
                sys.stdout.flush()
                if goback:
                    index = -(index-1)
                else:
                    index = index+1
                continue

            if fn != lastFn:
                mainImg = cv2.imread(fn, cv2.IMREAD_ANYCOLOR)
                (img, t0, keypts) = processFrame(mainImg.copy(), common.clock())
                frameT.update(common.clock() - t0)
                if cmode == 'rgb':
                    str = "%s     (%.2f ms)" % (base,frameT.value*1000)
                else:
                    str = "%s[%s] (%.2f ms)" % (base,cmode,frameT.value*1000)
                putStr(img, (20, 20), str)
                showImg(img, keypts)
                lastFn = fn

            done,update,cmode,index,value = checkKey(10, cmode, index, value)
            if done:
                break
            elif update:
                lastFn = None

keyToCmode = {
    48: '0', 
    49: '1',
    50: '2',
    51: '3',
    52: '4',
    53: '5',
    54: '6',

    98: 'b',
    99: 'rgb',  # c key
    103: 'g',
    104: 'h',
    114: 'r',
    115: 's',
    118: 'v',
}

keyToFrameChange = {
    81: 'b',
    83: 'n',
}

algoChange = {
    80: 0, # home == zero
    82: 1,  # up arrow
    84: -1, # down arrow
}

def checkKey(waitMS, cmode, index, value):
    done=False
    forceUpdate=False
    key = 0xFF & cv2.waitKey(waitMS)
    if key == 27: # ESC
        done=True
    elif key == 255:
        pass
    elif key in keyToCmode:
        cmode = keyToCmode[key]
        forceUpdate = True
    elif key in keyToFrameChange:
        if keyToFrameChange[key] == 'n':
            index = (index + 1)
        else:
            index = -(index - 1)
    elif key in algoChange:
        dv = algoChange[key]
        if dv == 0:
            value = 0
        else:
            value = value + dv
        sys.stdout.write("value %d                  \r" % value)
        sys.stdout.flush()
        forceUpdate = True
    else:
        print("unbound key %d" % key)

    return done,forceUpdate,cmode,index,value,

def putStr(img, pos, str):
    cv2.putText(img, str, pos, cv2.FONT_HERSHEY_PLAIN, 1, 
                (0, 0, 0), thickness=2)  # colors arb bgr
    cv2.putText(img, str, pos, cv2.FONT_HERSHEY_PLAIN, 1, 
                (244, 100, 100))  # colors arb bgr


if __name__ == '__main__':
    main()

