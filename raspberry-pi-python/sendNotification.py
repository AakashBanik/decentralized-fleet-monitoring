import requests

def sendNotificationtoDevice(temp, accl, gyro):
        r = requests.post("https://ancient-refuge-36587.herokuapp.com/notify")