var firebase = require("firebase");
var hbs = require('hbs')
var express = require('express')

var app = express()
var humidity = []
var Temperature = []

const port = process.env.PORT || 3000;

app.set('view engine', 'hbs')
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
    datetime.setDate(datetime.getDate())
    todaysDate = datetime.toISOString().slice(0, 10).toString();
    console.log(`Todays Date: ${todaysDate}\n`)
    data.forEach(element => {
      if (element['date'] === todaysDate) {
        console.log(`Humidity: ${element['humidity']}, Temperature: ${element['temp']}`)
        humidity.push(element['humidity'])
        Temperature.push(element['temp'])
      }
    });
  });

});

app.get('/', (req, res) => {
  res.render('main.hbs', {
    date: new Date().toISOString().slice(0, 10).toString(),
    humidity: humidity[humidity.length - 1],
    temp: Temperature[Temperature.length - 1]
  })
})

app.listen(port)