package com.tls.enc.cmn;

import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * ==========================================================
 * PackageName : com.tls.enc.cmn
 * FileName    : Cipher
 * Author      : 이진우
 * Date        : 2025-03-21
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-21    |    이진우    |    최초생성
 */
@Getter
public class Crypto {

    private final String iv = "!<~tls~><~cuk~>!";
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String aesKey;

    /**
     * AES-256 key 생성
     */
    public void generateAesKey(){
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        this.aesKey = Base64.getEncoder().encodeToString(keyBytes);
    }

    /**
     * RSA Key 생성
     * @throws NoSuchAlgorithmException
     */
    public void generateRsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();

        this.publicKey = pair.getPublic();
        this.privateKey = pair.getPrivate();
    }

    /**
     * AES-256 암호화 처리
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String aesEnc(String data) throws Exception {
        this.generateAesKey();
        return aesEnc(data, this.aesKey);
    }

    /**
     * AES-256 암호화 처리
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public String aesEnc(String data, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(this.iv.getBytes());

        String encData = "";

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encDataBytes = cipher.doFinal(data.getBytes());

            encData = Base64.getEncoder().encodeToString(encDataBytes);
        } catch (Exception e) {
            throw new Exception("AES256 암호화 실패");
        }

        return encData;
    }

    /**
     * AES-256 복호화 처리
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public String aesDec(String data, String key) throws Exception {

        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(this.iv.getBytes());

        String decData = "";

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decDataBytes = cipher.doFinal(Base64.getDecoder().decode(data));

            decData = new String(decDataBytes);
        } catch (Exception e) {
            throw new Exception("AES256 복호화 실패");
        }
        return decData;
    }

    /**
     * RSA 암호화 처리
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String rsaEnc(String data) throws Exception {

        String encData = "";

        try {
            this.generateRsaKey();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] encDataBytes = cipher.doFinal(dataBytes);

            encData = Base64.getEncoder().encodeToString(encDataBytes);
        }catch(Exception e){
            throw new Exception("RSA 암호화 실패");
        }

        return encData;
    }

    /**
     * RSA 암호화 처리
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public String rsaEnc(String data, PublicKey publicKey) throws Exception {

        String decData = "";

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] encDataBytes = cipher.doFinal(dataBytes);

            decData = Base64.getEncoder().encodeToString(encDataBytes);
        }catch(Exception e){
            throw new Exception("RSA 암호화 실패");
        }

        return decData;
    }

    /**
     * RSA 암호화 처리
     * 
     * @param data
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public String rsaEnc(String data, String publicKeyStr) throws Exception {

        String encData = "";

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
            encData = this.rsaEnc(data, keyFactory.generatePublic(publicKeySpec));
        }catch(Exception e){
            throw new Exception("RSA 암호화 실패");
        }

        return encData;
    }

    /**
     * RSA 복호화 처리
     * 
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public String rsaDec(String data, PrivateKey privateKey) throws Exception {
        String decData = "";

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] dataBytes = Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
            byte[] decDataBytes = cipher.doFinal(dataBytes);

            decData = new String(decDataBytes, StandardCharsets.UTF_8);
        }catch(Exception e){
            throw new Exception("RSA 복호화 실패");
        }
        return decData;
    }

    /**
     * RSA 복호화 처리
     * 
     * @param data
     * @param strPrivateKey
     * @return
     * @throws Exception
     */
    public String rsaDec(String data, String strPrivateKey) throws Exception {
        String decData = "";

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] bytePrivateKey = Base64.getDecoder().decode(strPrivateKey);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);

            decData = this.rsaDec(data, keyFactory.generatePrivate(privateKeySpec));
        }catch(Exception e){
            throw new Exception("RSA 복호화 실패");
        }
        return decData;
    }

    /**
     * RSA Key String으로 반환
     * @param keyType
     * @return
     */
    public String getRsaStrKey(String keyType) {

        String strKey = "";

        if ("publicKey".equalsIgnoreCase(keyType)) {
            strKey = Base64.getEncoder().encodeToString(this.publicKey.getEncoded());
        }

        if ("privateKey".equalsIgnoreCase(keyType)) {
            strKey = Base64.getEncoder().encodeToString(this.privateKey.getEncoded());
        }

        return strKey;

    }

}
