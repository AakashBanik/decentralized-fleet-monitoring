var express = require('express');
var bodyParser = require('body-parser');
var fcm = require('./firebase-server')

var app = express();

const port = process.env.PORT || 3000;

app.use(bodyParser.json());

app.post('/notify', (req, res) => {

    if (req.body.temp != "" && req.body.hum != "") {
        if (req.body.temp > 25) {
            console.log('Temp too high');
            fcm.firebaseMessage(req.body.temp, req.body.hum);
        } else {
            console.log('Temperatures are perfect');
    
        } 
        res.status(200).send({
            "response": "Perfect Request",
            "code" : 1
        });
    } else {
        res.status(400).send({
            "response": "Bad data sent to the server",
            "code" : 0
        })
    }
});

app.listen(port);