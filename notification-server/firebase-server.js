var admin = require("firebase-admin");

var serviceAccount = require("./serviceKey.json");
    admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://mobile-storage-e3c39.firebaseio.com"
});

var serverKey = "fTQiLcDqzLY:APA91bHNVY8ykX1v-d_q2aKZluEpsuZF1UpGJttGe93sCMgrJ7RFN8v8x9-SlRZsu4kfn6uH4klfZ3XB39rEaq3_lPTsHbY1oos4iquoSjNFCEouiEipypIySatrw0rXHudwWd4T4ZZc";


module.exports.firebaseMessage = (temp, hum) => {

    var message = {
        notification: {
            title: 'Critical',
            body: `Temperature: ${temp} and Humidity : ${hum}`,
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

