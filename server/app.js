var firebase = require("firebase");
var hbs = require('hbs')
var express = require('express')

var app = express()
var humidity = []
var Temperature = []
var time = []
var lat = -34.397
var long = 150.644
var mapsApiKey = 'AIzaSyBasFPXZ4mm6Wh_GJestTeZZF8ZMs6wxuc'

const port = process.env.PORT || 3000;

app.set('view engine', 'hbs')
hbs.registerPartials(__dirname + '/views/partials')
app.use(express.static(__dirname + '/server/public'))

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
var ref = db.ref("sensor/dht");

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
        console.log(`Humidity: ${element['humidity']}, Temperature: ${element['temp']}, Time: ${element['time'].toString()}`)
        humidity.push(element['humidity'])
        Temperature.push(element['temp'])
        time.push(element['time'])
      }
    });
  });

});


app.get('/temp', (req, res) => {
  res.render('temp.hbs', {
    date: new Date().toISOString().slice(0, 10).toString(),
    temp: Temperature[Temperature.length - 1]
  })
})

app.get('/hum', (req, res) => {
  res.render('hum.hbs', {
    date: new Date().toISOString().slice(0, 10).toString(),
    humidity: humidity[humidity.length - 1]
  })
})

app.get('/map', (req, res) => {
  res.render('maps.hbs', {
    lat: lat,
    long: long,
    apiKey: mapsApiKey
  })
})

app.get('/vib', (req, res) => {
  res.render('vibration.hbs', {
    date: new Date().toISOString().slice(0, 10).toString(),
  })
})

app.get('/gyp', (req, res) => {
  res.render('gyro.hbs', {
    date: new Date().toISOString().slice(0, 10).toString(),
  })
})

app.get('/acc', (req, res) => {
  res.render('acceleration.hbs', {
    date: new Date().toISOString().slice(0, 10).toString(),
  })
})

app.get('/mag', (req, res) => {
  res.render('magnet.hbs', {
    date: new Date().toISOString().slice(0, 10).toString(),
  })
})

app.get('/dash', (req, res) => {
  res.render('dash.hbs', {
    humidity: humidity, 
    date: new Date().toISOString().slice(0, 10).toString(),
    time: time,
     temp: Temperature
  })
})


app.listen(port)