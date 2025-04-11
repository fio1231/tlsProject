import {JSEncrypt} from "jsencrypt";
import CryptoJS from 'crypto-js';

/**
 * 암호화 클래스
 */
class Crypto {

    #aesKey;
    #iv;
    #privateKey;
    #publicKey;
    #serverPublicKey;
    #token;

    /**
     * AES 키 생성
     */
    generateAesKey(){
        this.#aesKey = CryptoJS.lib.WordArray.random(32);
        this.#iv = '!<~tls~><~cuk~>!';
    }

    /**
     * AES 알고리즘 암호화
     * @param {string} data
     * @returns {string}
     */
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

    /**
     * AES 알고리즘 복호화
     * @param {string} data
     * @param {string} key
     * @returns {string}
     */
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

    /**
     * RSA 키 생성
     */
    generateRsaKey(){
        const crypt = new JSEncrypt({default_key_size: 2048});
        this.#publicKey = crypt.getPublicKeyB64();
        this.#privateKey = crypt.getPrivateKeyB64();
    }

    /**
     * RSA 알고리즘 복호화
     * @param {string} data
     * @returns {string | false}
     */
    rsaDec(data){
        const cipher = new JSEncrypt({ default_key_size: 2048 });
        cipher.setPrivateKey(this.#privateKey);
        return cipher.decrypt(data);
    }

    /**
     * RSA 알고리즘 암호화
     * @param {string} data
     * @param {string} publicKey
     * @returns {string | false}
     */
    rsaEnc(data, publicKey){
        const cipher = new JSEncrypt({ default_key_size: 2048 });
        cipher.setPublicKey(publicKey);
        return cipher.encrypt(data);
    }

    /**
     * AES 키 반환
     * @returns {string}
     */
    getAesKey = () => {
        return this.#aesKey.toString(CryptoJS.enc.Base64);
    }

    /**
     * 자신의 공개 키 반환
     * @returns {string}
     */
    getRsaPublicKey = () => {
        return this.#publicKey;
    }

    /**
     * 서버 공개 키 셋팅
     * @param {string} serverPublicKey
     */
    setRsaServerPublicKey = (serverPublicKey) => {
        this.#serverPublicKey = serverPublicKey;
    }

    /**
     * 서버 공개 키 반환
     * @returns {string}
     */
    getRsaServerPublicKey = () => {
        return this.#serverPublicKey;
    }

    /**
     * 발급 된 토큰 저장
     * @param {string} token
     */
    setToken = (token) => {
        this.#token = token;
    }

    /**
     * 토큰 반환
     * @returns {string}
     */
    getToken = () => {
        return this.#token;
    }

}

export default Crypto;