var express = require('express');
var bodyParser = require('body-parser');
var fcm = require('./firebase-server')
var firebase = require("firebase");
var decrypt = require('./decrypt_data')

var accl = []
var gyro = []
var Temperature = []
var lat = []
var lng = []
var speed = []

var config = {
  apiKey: "AIzaSyDG-o-5zgeJJXw_5waQ9nF4caatbzHVZx0",
  authDomain: "my-pi-12.firebaseapp.com",
  databaseURL: "https://my-pi-12.firebaseio.com",
  projectId: "my-pi-12",
  storageBucket: "my-pi-12.appspot.com",
  messagingSenderId: "981634572804"
};

firebase.initializeApp(config);
var db = firebase.database();
var ref = db.ref("sensor/raspberry");


var app = express();
const port = process.env.PORT || 3000;
app.use(bodyParser.json());

app.post('/notify', (req, res) => {
    ref.on("value", (snapshot) => {

        var promise = new Promise((resolve, reject) => {
            var data = []
            for (var key in snapshot.val()) {
                data.push(snapshot.val()[key])
            }
            resolve(data)
        })
        
        promise.then((data) => {
            var datetime = new Date();
            datetime.setDate(datetime.getDate());
            todaysDate = datetime.toISOString().slice(0, 10).toString();
            console.log(`Todays Date: ${todaysDate}\n`)
            data.forEach(element => {
                if (element['date'] === todaysDate) {
                    console.log(`Acceleration: ${decrypt.decrypt_data(element['Acceleration'].toString())}, Temperature: ${decrypt.decrypt_data(element['temp'].toString())}, 
                    Angular Velocity: ${decrypt.decrypt_data(element['Gyroscope'].toString())} Time: ${element['time'].toString()}`);
        
                    accl.push(decrypt.decrypt_data(element['Acceleration'].toString()))
                    gyro.push(decrypt.decrypt_data(element['Gyroscope'].toString()))
                    Temperature.push(decrypt.decrypt_data(element['temp'].toString()))
                    lat.push(decrypt.decrypt_data(element['latitude'].toString()))
                    lng.push(decrypt.decrypt_data(element['longitude'].toString()))
                    speed.push(decrypt.decrypt_data(element['speed'].toString()))
                }
            });
        });
    
        if (speed[speed.length - 1] >= 45) {
            fcm.firebaseMessage("", speed[speed.length - 1], "", lat[lat.length - 1], lng[lng.length - 1], "Increase in Speed")
        }
        if (accl[accl.length - 1] >= 25) {
            fcm.firebaseMessage("", "", accl[accl.length - 1], lat[lat.length - 1], lng[lng.length - 1], "Increased Acceleration")
        } 
        if (Temperature[Temperature.length - 1] >= 45 || Temperature[Temperature.length - 1] <= 5) {
            fcm.firebaseMessage(Temperature[Temperature.length - 1], "", "", lat[lat.length - 1], lng[lng.length - 1], "Temperature Increase")
        } 
        
    });
    // fcm.firebaseMessage(req.body.temp, req.body.speed, req.body.accl, req.body.lat, req.body.lng, req.body.msg);
});

app.listen(port);