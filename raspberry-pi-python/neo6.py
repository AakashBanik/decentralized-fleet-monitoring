#/usr/bin/python3
#-*- coding: utf-8 -*-

import serial
try:
    from geopy.geocoders import Nominatim
    geo=True
except:
    geo=False


class GpsNeo6():
    
    def __init__(self,port,debit=9600,diff=1):
        self.port=serial.Serial(port,debit)
        self.diff=diff
        self.tabCode=["GPVTG","GPGGA","GPGSA","GPGLL","GPRMC","GPGSV"]
        self.speed=""
        self.latitude=""
        self.longitude=""
        self.latitudeDeg=""
        self.longitudeDeg=""
        self.time=""
        self.altitude=""
        self.precision=""
        self.satellite=""
        self.geoloc=Nominatim()
        
    def __del__(self):
        self.port.close()
        
    def __repr__(self):
        """
            on affiche les info
            """
        rep="Hour: "+str(self.time)+"\rlatitude: "+str(self.latitude) \
            +"\rlongitude: "+str(self.longitude)+"\Speed: "+str(self.speed)+" km/h" \
            +"\raltitude: "+str(self.altitude)+" metre(s)"+"\rprecision: "+str(self.precision)+" metre(s)" \
            +"\rNombre de satelites vue: "+str(self.satellite)
        if geo:
            rep+="\rlieu : "+self.geolocation()
        return rep
    
    
    
    def recupData(self):
        l='->'
        line=""
        tab={}
        gp=[]
        while len(tab)<6:
            l=self.port.read(2)
            if b'\r' in l or b'\n' in l:
                l=''
                for i in self.tabCode:
                    if i in line:
                        if i=="GPGSV": 
                            gp.append(line)
                            tab["GPGSV"]=gp
                        else:                     
                            tab[i]=line
                            gp=[]
                line=""
            else: 
                try:
                    line+=str(l.decode().strip())
                except: pass
        return tab
    
    def degToDec(self,deg):
        dec=int(deg[0:deg.find(".")-2])
        min=int(deg[deg.find(".")-2:deg.find(".")])/60
        sec=float("0."+deg[deg.find(".")+1:])/36
        return round(dec+min+sec,10)
    
    
    def traite(self):
        donnees=self.recupData()
        data=donnees["GPGGA"]
        data=data.split(',')
        temps=str(int(data[1][0:2])+self.diff)+"h"+data[1][2:4]+"m"+data[1][4:6]+"s" #mets en forme la date avec le decalage de l'heure        
        self.time=temps
        self.latitude=self.degToDec(data[2]) #mets au format decimale xx.yyyyyy
        self.latitudeDeg=float(data[2])/100#+data[3]
        self.longitude=self.degToDec(data[4]) #mets au format decimale xx.yyyyyy
        self.longitudeDeg=float(data[4])/100#+data[5]
        self.altitude=data[9]
        self.precision=data[6]
        self.speed=self.traiteGPVTG(donnees["GPVTG"]) #recupere que la speed de deplacement
        self.satellite=int(donnees["GPGSV"][0].split(',')[3]) #recupere le nombre de satellite vue
        
        
        
    def traiteGPVTG(self,data):
        data=data.split(',')
        return data[7]
    
    def geolocation(self):
        if geo:
            try:
                
                location = self.geoloc.reverse(str(self.latitude)+", "+str(self.longitude))
                return str(location)
            except: return "Le Néant"
        else: return "le Néant"
        
    
    
if __name__=="__main__":
    #on definit le port la speed et la diferrence d'heure utc et locale
    gps=GpsNeo6(port="com5",debit=9600,diff=2)
    
    while True:
        #on appel un traitement gps
        gps.traite()
        #on affiche les infos
        print(gps)
        #print(gps.time)
        
