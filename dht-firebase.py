
import RPi.GPIO as GPIO
from time import sleep
import datetime
from firebase import firebase
import Adafruit_DHT

import urllib2, urllib, httplib
import json
import os 
from functools import partial
from message import sendMail

GPIO.setmode(GPIO.BCM)
GPIO.cleanup()
GPIO.setwarnings(False)

sensor = Adafruit_DHT.DHT11
pin = 4
humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
firebase = firebase.FirebaseApplication('https://my-pi-12.firebaseio.com/', None)

def sendNotification():
	temp, hum = get_temp()
	if temp > 27.0:
		sendMail('aakashbanik510@gmail.com', 'Temp/Humidity Exceeded')

def get_temp():
	humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
	if humidity is not None and temperature is not None:
		sleep(5)
		str_temp = ' {0:0.2f} *C '.format(temperature)	
		str_hum  = ' {0:0.2f} %'.format(humidity)
		print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temperature, humidity))	
	else:
		print('Failed to get reading. Try again!')	
		sleep(10)
	
	return temperature, humidity

def update_firebase():
	temp, hum = get_temp()
	data = {"temp": temp, "humidity": hum, "date": datetime.date.today()}
	firebase.post('/sensor/dht', data)
	
while True:
	update_firebase()
	sleep(5)
	








