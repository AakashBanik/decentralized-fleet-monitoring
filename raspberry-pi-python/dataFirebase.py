from firebase import firebase

import urllib2, urllib, httplib
import json
import os 
from functools import partial
from getValues import get_data
from datetime import datetime, date
from sendNotification import sendNotificationtoDevice

firebase = firebase.FirebaseApplication('https://my-pi-12.firebaseio.com/', None)

def update_firebase():
	temp, hum = get_data()
	data = {"temp": temp, "humidity": hum, "date": date.today(), "time": datetime.now().strftime('%H:%M:%S')}
	print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temp, hum))
	sendNotificationtoDevice(temp, hum)
	firebase.post('/sensor/dht', data)
	