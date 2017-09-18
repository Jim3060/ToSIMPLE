import {JSEncrypt} from "./jsencrypt.js";

const getPublicKey = function() {
    return new Promise((resolve, reject) => {
        $.get("fetchRSA", data => {
            resolve(data.publicKey);
        }, "json").fail(() => {
            reject("error");
        });
    });
};

export default function(password) {
    return new Promise((resolve, reject) => {
        const encrypt = new JSEncrypt();
        getPublicKey().then(key => {
            encrypt.setPublicKey(key);
            if (typeof password == "string") {
                resolve(encrypt.encrypt(password));
            }
            else {
                let buffer = [];
                for (let p of password) {
                    buffer.push(encrypt.encrypt(p));
                }
                resolve(buffer);
            }
        }).catch(() => {
            reject("fail");
        });
    });
}