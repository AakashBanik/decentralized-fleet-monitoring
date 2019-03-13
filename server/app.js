var firebase = require("firebase");
var hbs = require('hbs')
var express = require('express')

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
app.use('/public',express.static(__dirname + "/public"));
app.use('/scripts',express.static(__dirname + "/scripts"));

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
        console.log(`Acceleration: ${element['Acceleration']}, Temperature: ${element['temp']}, Angular Velocity: ${element['Gyroscope']} Time: ${element['time'].toString()}`)
        accl.push(element['Acceleration'])
        gyro.push(element['Gyroscope'])
        Temperature.push(element['temp'])
        time.push(element['time'])
        lat.push(element['latitude'])
        lng.push(element['longitude'])
        speed.push(element['speed']*1.852)
      }
    });
  });

});


app.get('/temp', (req, res) => {
  res.render('temp.hbs', {
    date: new Date().toISOString().slice(0, 10).toString(),
    temp: Temperature[Temperature.length - 1].toFixed(2),
    color: color = (Temperature[Temperature.length - 1] > 35) ? "red" : "orange"//#4CAF50"
  })
})

app.get('/map', (req, res) => {
  res.render('maps.hbs', {
    lat: lat[lat.length - 1],
    lng: lng[lng.length - 1],
    apiKey: mapsApiKey
  })
})

app.get('/vib', (req, res) => {
  res.render('vibration.hbs', {
  })
})

app.get('/gyro', (req, res) => {
  res.render('gyro.hbs', {
    gyro: gyro[gyro.length - 1].toFixed(2),
    color: color = (Temperature[Temperature.length - 1] > 35) ? "red" : "orange"
  })
})

app.get('/acc', (req, res) => {
  res.render('acceleration.hbs', {
    accl: accl[accl.length - 1].toFixed(2),
    color: color = (Temperature[Temperature.length - 1] > 35) ? "red" : "orange"
  })
})

app.get('/speed', (req, res) => {
  res.render('speed.hbs', {
    speed: speed[speed.length - 1].toFixed(2),
    color: color = (Temperature[Temperature.length - 1] > 35) ? "red" : "orange"
  })
})

app.get('/mag', (req, res) => {
  res.render('magnet.hbs', {
  })
})

app.get('/dash', (req, res) => {
  res.render('dash.hbs', {
    accl: accl,
    gyro: gyro,
    time: time,
    temp: Temperature,
    lat: lat,
    lng: lng
  })
})


app.listen(port)