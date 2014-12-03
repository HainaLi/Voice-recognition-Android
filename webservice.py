#!/usr/bin/python

from bootstrap import *

import web
from web import form
import urllib

import socket
import fcntl
import struct

import json
 

def get_ip_address(ifname):
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    return socket.inet_ntoa(fcntl.ioctl(
        s.fileno(),
        0x8915,  # SIOCGIFADDR
        struct.pack('256s', ifname[:15])
    )[20:24])


url = 'http://cs4720.cs.virginia.edu/ipregistration/?pokemon=Flaaffy&ip=' + get_ip_address('wlan0')
data = urllib.urlopen(url).read()

# Define the pages (index) for the site
urls = ('/rpi', 'index')
render = web.template.render('templates')




class index:
    def GET(self):
        return "Raspberry Pi Python Remote Control"
    def POST(self):

	json_data = (web.data()).replace("\r\n","").replace("\t","")
	jsonData = json.loads(json_data)
	
	

	for i in range( len(jsonData['lights'])):
		currentjson = jsonData['lights'][i]
		id = currentjson['lightId']
		red = currentjson['red']
		green = currentjson['green']
		blue = currentjson['blue']
		intensity = currentjson['intensity']
		if (jsonData['propagate'] == 1):
			print "entered true"
			if (i != len(jsonData['lights']) - 1 ):
				nextjsonid = jsonData['lights'][i+1]['lightId']
				print "nextjsonid",nextjsonid
			else:
				nextjsonid = 32
			led.fill(Color(red,green,blue,intensity),start=id,end=nextjsonid-1)

			
		else:
			led.fill(Color(red,green,blue,intensity),start=id,end=id)
			
	

	led.update()

		

	#led.all_off()


# run
if __name__ == '__main__':
    app = web.application(urls, globals())
    app.run()
