from neo6 import GpsNeo6


def getGPS():

    gps=GpsNeo6(port="/dev/ttyAMA0",debit=9600,diff=2) #diff is difference between utc time en local time    
    gps.traite()
    return gps.latitude, gps.longitude, gps.speed