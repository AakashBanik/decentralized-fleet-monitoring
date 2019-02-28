from message import sendMail
from message import sendMessage
from getValues import get_data

def sendNotificationtoDevice():
    temp, hum = get_data()

    if temp >= 35 or hum >= 70:
        sendMail('aakashbanik510@gmail.com', 'Temp/Humidity Exceeded')
        sendMessage('Temp/Humidity Exceeded', '917624040671')
