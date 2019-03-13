from message import sendMail
from message import sendMessage
import requests

def sendNotificationtoDevice(temp, accl, gyro):

    if temp >= 38:
        r = requests.post("https://ancient-refuge-36587.herokuapp.com/notify", json={"temp": temp, "accl": accl, "gyro": gyro})
        print(r)