var nacl = require('tweetnacl');
var utils = require('tweetnacl-util');
var toUint8Array = require('base64-to-uint8array')

function toArrayBuffer(buf) {
    var ab = new ArrayBuffer(buf.length);
    var view = new Uint8Array(ab);
    for (var i = 0; i < buf.length; ++i) {
        view[i] = buf[i];
    }
    return view;
}

module.exports.decrypt_data = (data) => {

    var arrNonce = toUint8Array('brDPxWDbO8yBSCg9uNWAN7AQlhZhhj3P=')
    let init = toArrayBuffer(arrNonce)
    let nonce = new Uint8Array(Array.from(init))

    message = utils.decodeBase64(data.toString());
    key = utils.decodeBase64('DUaZqu6/CQ82gzYFDtRQLNy8b1q3pVA6ZlwolbgznzI=')
    let decrypted = nacl.secretbox.open(message, nonce, key);
    return utils.encodeUTF8(decrypted);

}