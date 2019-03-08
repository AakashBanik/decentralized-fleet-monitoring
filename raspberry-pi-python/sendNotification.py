from message import sendMail
from message import sendMessage
from getValues import get_data
import requests

def sendNotificationtoDevice(temp, hum):

    if temp >= 38 or hum >= 80:
        r = requests.post("https://ancient-refuge-36587.herokuapp.com/notify", json={"temp": temp, "hum": hum})
        print(r)