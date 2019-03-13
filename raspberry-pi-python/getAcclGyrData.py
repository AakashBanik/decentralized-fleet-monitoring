from mpu6050 import mpu6050
from time import sleep
from math import sqrt, pow

sensor = mpu6050(0x68)


accel_data = sensor.get_accel_data()
gyro_data = sensor.get_gyro_data()
temp = sensor.get_temp()

def getAccelGryData():
    mag_accel = sqrt(pow(accel_data['x'], 2) + pow(accel_data['y'], 2) + pow(accel_data['z'], 2))
    mag_gyro = sqrt(pow(gyro_data['x'], 2) + pow(gyro_data['y'], 2) + pow(gyro_data['z'], 2))

    return round(mag_accel, 2), round(mag_gyro, 2), round(temp, 2)
