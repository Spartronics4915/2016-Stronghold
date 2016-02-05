#!/usr/bin/env python
#
# this is a fake robot, network table server..
#

import time, sys
from networktables import NetworkTable

# To see messages from networktables, you must setup logging
import logging
logging.basicConfig(level=logging.DEBUG)


visTable = {} 
dirty = False

def visValueChanged(table, key, value, isNew):
    global dirty
    visTable[key] = value
    dirty = True

sd = NetworkTable.getTable("SmartDashboard")
vis = sd.getSubTable("Vision")
vis.addTableListener(visValueChanged)

i = 0
while True:
    time.sleep(1)
    i += 1
    if dirty:
        print(visTable)
        dirty = False
