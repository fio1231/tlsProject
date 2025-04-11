package com.tls.server.common.service;

import com.tls.server.common.dto.EncReqDto;
import com.tls.server.common.dto.EncResDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * ==========================================================
 * PackageName : com.tls.server.common.service
 * FileName    : CommonService
 * Author      : 이진우
 * Date        : 2025-03-25
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-25    |    이진우    |    최초생성
 */
@RequiredArgsConstructor
@Service
public class CommonService {

    /** 서버 인증 키 */
    @Value("${key-store.key}")
    private String keyStoreKey;

    /** {@link WebClientService} */
    @NonNull
    private WebClientService webClientService;

    /**
     * 키교환 통신
     * @param key
     * @return
     */
    public EncResDto exchange(String key) {
        EncReqDto reqDto = new EncReqDto();
        reqDto.setHeader(EncReqDto.HeaderData.builder().authKey(keyStoreKey).build());
        reqDto.setBody(EncReqDto.BodyData.builder().key(key).token(UUID.randomUUID().toString()).build());
        return webClientService.encServerPost(reqDto, "exchange");
    }

    /**
     * 암호화 통신
     * @param reqDto
     * @return
     */
    public EncResDto enc(EncReqDto reqDto) {
        reqDto.setHeader(EncReqDto.HeaderData.builder().authKey(keyStoreKey).build());
        return webClientService.encServerPost(reqDto, "enc");
    }

    /**
     * 복호화 통신
     * @param reqDto
     * @return
     */
    public EncResDto dec(EncReqDto reqDto) {
        reqDto.setHeader(EncReqDto.HeaderData.builder().authKey(keyStoreKey).build());
        return webClientService.encServerPost(reqDto, "dec");
    }


}
