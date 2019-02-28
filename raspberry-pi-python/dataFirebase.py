from firebase import firebase

import urllib2, urllib, httplib
import json
import os 
from functools import partial
from getValues import get_data
import datetime

firebase = firebase.FirebaseApplication('https://my-pi-12.firebaseio.com/', None)

def update_firebase():
	temp, hum = get_data()
	data = {"temp": temp, "humidity": hum, "date": datetime.date.today()}
	firebase.post('/sensor/dht', data)
	