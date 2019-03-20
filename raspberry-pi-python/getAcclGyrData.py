from mpu6050 import mpu6050
from time import sleep
from math import sqrt, pow

sensor = mpu6050(0x68)

def getAccelGryData():
    accel_data = sensor.get_accel_data()
    gyro_data = sensor.get_gyro_data()
    temp = sensor.get_temp()

    mag_accel = sqrt(pow(accel_data['x'], 2) + pow(accel_data['y'], 2) + pow(accel_data['z'], 2))
    mag_gyro = sqrt(pow(gyro_data['x'], 2) + pow(gyro_data['y'], 2) + pow(gyro_data['z'], 2))

    temp = int((temp * 100) + 0.5) / 100   #nearest floating point conv formula
    mag_accel = int((mag_accel * 100) + 0.5) / 100
    mag_gyro = int((mag_gyro * 100) + 0.5) / 100

    return mag_accel, mag_gyro, temp
