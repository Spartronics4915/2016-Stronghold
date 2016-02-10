#!/usr/bin/env python

import os
import shutil
import SimpleHTTPServer
import SocketServer
PORT = 5810 # R60, viii contrains port usage.

Handler = SimpleHTTPServer.SimpleHTTPRequestHandler
httpd = SocketServer.TCPServer(("", PORT), Handler)

homedir = "/var/tmp/imgServer.home"
shutil.copytree("imgServer.home", homedir)
os.chdir(homedir)
httpd.serve_forever()
