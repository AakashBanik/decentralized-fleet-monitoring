var firebase = require("firebase");

//var serviceAccount = require("./serviceaccount.json");

var config = {
  apiKey: "AIzaSyDG-o-5zgeJJXw_5waQ9nF4caatbzHVZx0",
  authDomain: "my-pi-12.firebaseapp.com",
  databaseURL: "https://my-pi-12.firebaseio.com",
  projectId: "my-pi-12",
  storageBucket: "my-pi-12.appspot.com",
  messagingSenderId: "981634572804"
};
firebase.initializeApp(config);
var data
var db = firebase.database();
var ref = db.ref("sensor/dht");
ref.once("value", (snapshot) => {
  var data = []
  for (var key in snapshot.val()) {
    data.push(snapshot.val()[key])
  }
  getData(data)
});

function getData(data) {
  var datetime = new Date();
  todaysDate = datetime.toISOString().slice(0, 10).toString();
  
  data.forEach(element => {
    if (element['date'] === todaysDate) {
      console.log(`Humidity: ${element['humidity']}, Temperature: ${element['temp']}`)
    }
  });
}