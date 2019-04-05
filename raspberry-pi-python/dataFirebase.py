from firebase import firebase

import json
import os
from getAcclGyrData import getAccelGryData 
from functools import partial
from datetime import datetime, date
from sendNotification import sendNotificationtoDevice
from gps import getGPS
from encryptData import encrypt_data

firebase = firebase.FirebaseApplication('https://my-pi-12.firebaseio.com/', None)

def update_firebase():
	accl, gyro, temp = getAccelGryData()
	lat, lng, speed = getGPS()
	
	data = {"temp": encrypt_data(temp), "Acceleration": encrypt_data(accl), "Gyroscope": encrypt_data(gyro), "date": date.today(),
			"latitude": encrypt_data(lat), "longitude": encrypt_data(lng), "speed": encrypt_data(speed), "time": datetime.now().strftime('%H:%M:%S')}

	print('Temp={0:0.1f}*C, Acceleration={1:0.1f}m/s2, Gyroscope={2:0.1f}rad/s, Speed: {3}, Latitude: {4}, Longitude: {5}'.format(temp, accl, gyro, speed, lat, lng))
	#sendNotificationtoDevice(speed, lat, lng)
	firebase.post('/sensor/raspberry', data)
	