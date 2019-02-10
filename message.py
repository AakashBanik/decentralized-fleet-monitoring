from twilio.rest import Client
import smtplib, ssl

account_sid = 'AC220dfb42781b7a1696980087b049c168'
auth_token = '12ed26fe4f23d36df63c064e8e79839d'
client = Client(account_sid, auth_token)

context = ssl.create_default_context()

def sendMessage(body, toNumber):

    message = client.messages.create(
    body= body,
    from_='+16784000661',
    to= toNumber
    )

    print(message.sid)

def sendMail(toAddress, message):

    port = 465  # For SSL
    smtp_server = "smtp.gmail.com"
    sender_email = "aakashbaniktest@gmail.com"  # Enter your address
    password = 'Aakash@12'
    with smtplib.SMTP_SSL(smtp_server, port, context=context) as server:
        server.login(sender_email, password)
        server.sendmail(sender_email, toAddress, message)

