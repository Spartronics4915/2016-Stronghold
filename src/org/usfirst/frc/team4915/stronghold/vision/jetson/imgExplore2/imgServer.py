#!/usr/bin/env python

import os
import shutil
import SimpleHTTPServer
import SocketServer
PORT = 5810 # R60, viii contrains port usage.
homedir = "/var/tmp/imgServer.home"

Handler = SimpleHTTPServer.SimpleHTTPRequestHandler
httpd = SocketServer.TCPServer(("", PORT), Handler)
print("imgServer is serving %s on port %d"%(homedir, PORT))

try:
    shutil.copytree("imgServer.home", homedir)

except:
    pass

os.chdir(homedir)

while True:
    try:
        httpd.serve_forever()
    except:
        print("problem starting server")
