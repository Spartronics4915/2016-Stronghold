#!/usr/bin/env python

import os, time, shutil, sys, datetime
import SimpleHTTPServer, SocketServer

PORT = 5810 # Stronghold rule: R60/viii contrains port usage.
homedir = "/var/tmp/imgServer.home"

sys.stdout.write("------------------------------------------------\n")
sys.stdout.write('{:%m-%d %H:%M:%S}'.format(datetime.datetime.now()))
sys.stdout.write(": imgServer is serving %s on port %d\n\n"%(homedir, PORT))
sys.stdout.flush()

Handler = SimpleHTTPServer.SimpleHTTPRequestHandler
while True:
    try:
        httpd = SocketServer.TCPServer(("", PORT), Handler)
        break
    except:
        print("problem starting server, retrying...")
        time.sleep(2)

try:
    shutil.copytree("imgServer.home", homedir)
except:
    pass

os.chdir(homedir)

while True:
    try:
        httpd.serve_forever()

    except:
        print("problem starting server, retrying...")
        time.sleep(2)

