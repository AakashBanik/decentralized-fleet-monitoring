from message import sendMail
from dataFirebase import update_firebase
from time import sleep
from sendNotification import sendNotificationtoDevice

while True:
	update_firebase()
	sendNotificationtoDevice()
	sleep(5)