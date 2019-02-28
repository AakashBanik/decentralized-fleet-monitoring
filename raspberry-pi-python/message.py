import smtplib, ssl
import requests

context = ssl.create_default_context()
API_KEY = 'aPC0UZj6Jpw-VXDs9GniIjZeLWdRJ6AsW15PXooHhI'

def sendMessage(body, toNumber):

    reqURL = 'https://api.textlocal.in/send/?apiKey={0}&sender=TXTLCL&numbers={1}&message={2}'.format(API_KEY, toNumber, body) 
    req = requests.post(reqURL)
    print(req)

def sendMail(toAddress, message):
    #commented lines are for python3, normal uncommented lines are for python2 for raspberry pi support
#    context = ssl.create_default_context()
#    port = 465  # For SSL
#    smtp_server = "smtp.gmail.com"
    sender_email = "aakashbaniktest@gmail.com"  # Enter your address
    password = 'Aakash@12'
#    with smtplib.SMTP_SSL(smtp_server, port) as server:
#        server.login(sender_email, password)
#        server.sendmail(sender_email, toAddress, message)

    print("Sending Email To Specified User")
    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.starttls()
    server.login(sender_email, password)
    server.sendmail(sender_email, toAddress, message)
    server.quit()

