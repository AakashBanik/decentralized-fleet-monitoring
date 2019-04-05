const functions = require('firebase-functions');
var firebase = require("firebase");
var hbs = require('hbs')
var express = require('express')
var decrypt = require('./decrypt_data')

var app = express()
var accl = []
var gyro = []
var Temperature = []
var time = []
var lat = []
var lng = []
var speed = []
var mapsApiKey = 'AIzaSyBasFPXZ4mm6Wh_GJestTeZZF8ZMs6wxuc'

const port = process.env.PORT || 3000;

app.set('view engine', 'hbs')
app.set('views', "./views");
// app.use('/public',express.static(__dirname + "/public"));
// app.use('/scripts',express.static(__dirname + "/scripts"));

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
        time.push(element['time'])
        lat.push(decrypt.decrypt_data(element['latitude'].toString()))
        lng.push(decrypt.decrypt_data(element['longitude'].toString()))
        speed.push(decrypt.decrypt_data(element['speed'].toString()))
      }
    });
  });

});

app.get('/', (req, res) => {
  res.render('index.hbs', {
    temp: Temperature[Temperature.length - 1],
    accl: accl[accl.length - 1],
    speed: speed[speed.length - 1],
    color1: color1 = (Temperature[Temperature.length - 1] > 35) ? "#DD3838" : "#365AF8",
    color2: color2 = (Temperature[Temperature.length - 1] > 35) ? "#FC9C9C" : "#A1A1F1"
  })
})

exports.app = functions.https.onRequest(app);