var admin = require("firebase-admin");

var serviceAccount = require("./keyfile.json");
    admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://my-pi-12.firebaseio.com"
});

var serverKey = "fWCFVeXABx8:APA91bF8EzYZ48y3QVOMZZGeDNW47HPHhPE2FVKPMacj_B5kRlk1ZpIRBC-KjnRXNLk6q51fPxG1uewZN9mCF21kUtuEGvtUs_J8ZTts0dNQBhOmAdXtJc8vqOT_zjqACJxvJtkDQF0-";


module.exports.firebaseMessage = (temp, speed, accl, lat, lng, msg) => {

    
    if (temp != "") {
        var message = `${msg}. Temperature: ${temp} . Latitude: ${lat} and Longitude: ${lng}`;
        sendMessage(message);
        if (speed != "") {
            var message = `${msg}. Speed: ${speed} . Latitude: ${lat} and Longitude: ${lng}`;
            sendMessage(message);
        }
        else if (accl != "") {
            var message = `${msg}. Acceleration: ${accl} . Latitude: ${lat} and Longitude: ${lng}`;
            sendMessage(message);
        }
    }
    else if (speed != "") {
        var message = `${msg}. Speed: ${speed} . Latitude: ${lat} and Longitude: ${lng}`;
        sendMessage(message);
        if (temp != "") {
            var message = `${msg}. Temperature: ${temp} . Latitude: ${lat} and Longitude: ${lng}`;
            sendMessage(message);
        } else if (accl != "") {
            var message = `${msg}. Acceleration: ${accl} . Latitude: ${lat} and Longitude: ${lng}`;
            sendMessage(message);
        }
    }
    else if (accl != "") {
        var message = `${msg}. Acceleration: ${accl} . Latitude: ${lat} and Longitude: ${lng}`;
        sendMessage(message);
        if (temp != "") {
            var message = `${msg}. Temperature: ${temp} . Latitude: ${lat} and Longitude: ${lng}`;
            sendMessage(message);
        } else if (speed != "") {
            var message = `${msg}. Speed: ${speed} . Latitude: ${lat} and Longitude: ${lng}`;
            sendMessage(message);
        }
    }
    
    
}

function sendMessage(message) {
    var message = {
        notification: {
            title: 'Critical',
            body: message,
        }
    };

    var options = {
        priority: "high",
        ttl: 60 * 60 * 24
    }

    // Send a message to devices subscribed to the provided topic.
    admin.messaging().sendToDevice(serverKey,message,options)
    .then((response) => {
        // Response is a message ID string.
        console.log('Successfully sent message:', response);
    })
    .catch((error) => {
        console.log('Error sending message:', error);
    });
}

