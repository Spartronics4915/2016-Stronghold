#!/usr/bin/env python
#
# this is a fake robot, network table server..
#

import time
from networktables import NetworkTable

# To see messages from networktables, you must setup logging
import logging
logging.basicConfig(level=logging.DEBUG)


def visValueChanged(table, key, value, isNew):
    print("<robot>: %15s: %s" % (key, value))

sd = NetworkTable.getTable("SmartDashboard")
vis = sd.getSubTable("vision")
vis.addTableListener(visValueChanged)

i = 0
while True:
    time.sleep(1)
    i += 1
