import {JSEncrypt} from "jsencrypt";
import CryptoJS from 'crypto-js';

class Crypto {

    #aesKey;
    #iv;
    #privateKey;
    #publicKey;
    #serverPublicKey;
    #token;

    generateAesKey(){
        this.#aesKey = CryptoJS.lib.WordArray.random(32);
        this.#iv = '!<~tls~><~cuk~>!';
    }

    aesEnc(data) {
        const encData = CryptoJS.AES.encrypt(
            data
            ,this.#aesKey
            ,{
                iv: CryptoJS.enc.Utf8.parse(this.#iv)
                ,mode: CryptoJS.mode.CBC
                ,padding: CryptoJS.pad.Pkcs7
            }
        ).toString();

        return encData;
    }

    aesDec(data, key) {
        const decData = CryptoJS.AES.decrypt(
            data
            ,CryptoJS.enc.Base64.parse(key)
            ,{
                iv: CryptoJS.enc.Utf8.parse(this.#iv)
                ,mode: CryptoJS.mode.CBC
                ,padding: CryptoJS.pad.Pkcs7
            }
        ).toString(CryptoJS.enc.Utf8);

        return decData;
    }

    generateRsaKey(){
        const crypt = new JSEncrypt({default_key_size: 2048});
        this.#publicKey = crypt.getPublicKeyB64();
        this.#privateKey = crypt.getPrivateKeyB64();
    }

    rsaDec(data){
        const cipher = new JSEncrypt({ default_key_size: 2048 });
        cipher.setPrivateKey(this.#privateKey);
        return cipher.decrypt(data);
    }

    rsaEnc(data, publicKey){
        const cipher = new JSEncrypt({ default_key_size: 2048 });
        cipher.setPublicKey(publicKey);
        return cipher.encrypt(data);
    }

    getAesKey = () => {
        return this.#aesKey.toString(CryptoJS.enc.Base64);
    }

    getRsaPublicKey = () => {
        return this.#publicKey;
    }

    setRsaServerPublicKey = (serverPublicKey) => {
        this.#serverPublicKey = serverPublicKey;
    }

    getRsaServerPublicKey = () => {
        return this.#serverPublicKey;
    }

    setToken = (token) => {
        this.#token = token;
    }

    getToken = () => {
        return this.#token;
    }

}

export default Crypto;