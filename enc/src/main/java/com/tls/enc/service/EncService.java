package com.tls.enc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tls.enc.cmn.Crypto;
import com.tls.enc.data.collection.Keys;
import com.tls.enc.data.dto.KeyInfo;
import com.tls.enc.data.dto.ReqDto;
import com.tls.enc.data.dto.ResDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * ==========================================================
 * PackageName : com.tls.enc.service
 * FileName    : EncService
 * Author      : 이진우
 * Date        : 2025-03-20
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-20    |    이진우    |    최초생성
 */
@Service
@RequiredArgsConstructor
public class EncService {

    /** {@link KeysService} */
    @NonNull
    private KeysService keysService;

    /** {@link ObjectMapper} */
    @NonNull
    private ObjectMapper objectMapper;

    /**
     * Public Key를 교환한다.
     *
     * @param reqDto
     * @return ResDto
     * @throws Exception
     */
    public ResDto exchange(ReqDto reqDto) throws Exception {

        //-- 1. 요청 데이터 가져옴
        ReqDto.BodyData body = reqDto.getBody();

        //-- 2. Back-end의 RSA 키쌍을 생성한다.
        Crypto ct = new Crypto();
        ct.generateRsaKey();

        //-- 3. Back-end의 RSA 키쌍과 Front-end의 public key를 몽고 db에 등록한다.
        KeyInfo keyInfo = KeyInfo.builder()
                .serverPrivateKey(ct.getRsaStrKey("privateKey"))
                .serverPublicKey(ct.getRsaStrKey("publicKey"))
                .clientPublicKey(body.getKey())
                .build();

        Keys keys = new Keys();
        keys.setToken(body.getToken());
        keys.setKeyInfo(keyInfo);
        keysService.saveKeys(keys);

        //-- 4. Back-end의 public key 반환
        return ResDto.builder()
                .header(ResDto.HeaderData.builder().build())
                .body(ResDto.BodyData.builder()
                        .key(ct.getRsaStrKey("publicKey"))
                        .token(body.getToken())
                        .build()
                ).build();
    }

    /**
     * 데이터 암호화 처리
     *
     * @param reqDto
     * @return
     * @throws Exception
     */
    public ResDto enc(ReqDto reqDto) throws Exception {

        //-- 1. 요청 데이터 가져옴
        ReqDto.HeaderData header = reqDto.getHeader();
        ReqDto.BodyData body = reqDto.getBody();

        //-- 2. 암호화 대상 데이터 가져옴
        String data = body.getData();

        //-- 3. 데이터를 암호화 처리
        Crypto ct = new Crypto();
        String encData = ct.aesEnc(data);

        //-- 4. Front-end의 public key 조회
        String token = body.getToken();
        Keys keys = keysService.getKeysByToken(token);

        KeyInfo keyInfo = keys.getKeyInfo();
        String strClientPublicKey = keyInfo.getClientPublicKey();

        //-- 5. Front-end의 public key로 AES-256 Key 암호화 처리
        String encAesKey = ct.rsaEnc(ct.getAesKey(), strClientPublicKey);

        //-- 6. 요청 정보에 키 정보 삭제 요청이 있으면 삭제 처리
        if(header.isClear()){
            keysService.deleteByToken(token);
        }

        //-- 7. 값 반환
        return ResDto.builder()
                .header(ResDto.HeaderData.builder().build())
                .body(ResDto.BodyData.builder()
                        .data(encData)
                        .key(encAesKey)
                        .build()
                ).build();
    }

    /**
     * 데이터 복호화 처리
     * 
     * @param reqDto
     * @return
     * @throws Exception
     */
    public ResDto dec(ReqDto reqDto) throws Exception {

        //-- 1. 요청 데이터 가져옴
        ReqDto.HeaderData header = reqDto.getHeader();
        ReqDto.BodyData body = reqDto.getBody();

        //-- 2. 복호화 대상, 암호화 된 AES-256 Key 데이터 가져옴
        String encData = body.getData().replaceAll("\"","");
        String encAesKey = body.getKey();

        //-- 3. Back-end의 private key 조회
        String token = body.getToken();
        Keys keys = keysService.getKeysByToken(token);

        //-- 4. AES-256 Key 복호화 처리
        KeyInfo keyInfo = keys.getKeyInfo();
        String strSeverPrivateKey = keyInfo.getServerPrivateKey();

        Crypto ct = new Crypto();
        String aesKey = ct.rsaDec(encAesKey, strSeverPrivateKey);

        //-- 5. 데이터 복호화 처리
        String data = ct.aesDec(encData, aesKey);

        //-- 6. 요청 정보에 키 정보 삭제 요청이 있으면 삭제 처리
        if(header.isClear()){
            keysService.deleteByToken(token);
        }

        //-- 7. 값 반환
        return ResDto.builder()
                .header(ResDto.HeaderData.builder().build())
                .body(ResDto.BodyData.builder()
                        .data(data)
                        .token(token)
                        .build()
                ).build();
    }

    /**
     * 키 정보 삭제
     * 
     * @param reqDto
     */
    public void clear(ReqDto reqDto) {

        //-- 1. 요청 데이터 가져옴
        ReqDto.BodyData body = reqDto.getBody();

        //-- 2. 토큰으로 키 정보 삭제
        String token = body.getToken();
        keysService.deleteByToken(token);
    }

}
