#!/usr/bin/env python

import os
import SimpleHTTPServer
import SocketServer
PORT = 8000

Handler = SimpleHTTPServer.SimpleHTTPRequestHandler
httpd = SocketServer.TCPServer(("", PORT), Handler)

os.chdir("imgServer.home")
httpd.serve_forever()
