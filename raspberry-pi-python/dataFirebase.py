from firebase import firebase

import json
import os
from getAcclGyrData import getAccelGryData 
from functools import partial
from datetime import datetime, date
from sendNotification import sendNotificationtoDevice
from gps import getGPS

firebase = firebase.FirebaseApplication('https://my-pi-12.firebaseio.com/', None)

def update_firebase():
	accl, gyro, temp = getAccelGryData()
	lat, lng, speed = getGPS()
	data = {"temp": temp, "Acceleration": accl, "Gyroscope": gyro,  "date": date.today(), "latitude": lat, "longitude": lng, "speed": speed, "time": datetime.now().strftime('%H:%M:%S')}
	print('Temp={0:0.1f}*C, Acceleration={1:0.1f}m/s2, Gyroscope={2:0.1f}rad/s, Speed: {3}, Latitude: {4}, Longitude: {5}'.format(temp, accl, gyro, speed, lat, lng))
	sendNotificationtoDevice(speed, lat, lng)
	firebase.post('/sensor/raspberry', data)
	