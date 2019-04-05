import nacl.secret
from base64 import b64encode, b64decode
import nacl.utils

key = b'DUaZqu6/CQ82gzYFDtRQLNy8b1q3pVA6ZlwolbgznzI='
key = b64decode(key)

nonce = b'brDPxWDbO8yBSCg9uNWAN7AQlhZhhj3P'
nonce = b64decode(nonce)

def encrypt_data(data):
    
    message = bytes(str(data), 'utf-8')
    box = nacl.secret.SecretBox(key)
    encrypt = box.encrypt(message, nonce=nonce)

    value = b64encode(encrypt.ciphertext)

    return str(value.decode('utf-8'))
