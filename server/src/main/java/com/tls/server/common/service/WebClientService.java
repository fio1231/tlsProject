package com.tls.server.common.service;

import com.tls.server.common.dto.EncReqDto;
import com.tls.server.common.dto.EncResDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * ==========================================================
 * PackageName : com.tls.server.common.service
 * FileName    : WebClientService
 * Author      : 이진우
 * Date        : 2025-03-27
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-27    |    이진우    |    최초생성
 */
@RequiredArgsConstructor
@Service
public class WebClientService {

    /** 암호화 서버 IP */
    @Value("${key-store.uri}")
    private String keyStoreBaseUri;

    /** Http 통신을 위한 WebClient */
    @NonNull
    private WebClient webClient;

    /**
     * 암호화 서버 통신 공통
     * @param reqDto
     * @param uri
     * @return
     */
    public EncResDto encServerPost(EncReqDto reqDto, String uri) {
        ResponseEntity<EncResDto> response = webClient.mutate()
                .baseUrl(keyStoreBaseUri)
                .build()
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(reqDto)
                .retrieve()
                .toEntity(EncResDto.class)
                .block();

        return Objects.requireNonNull(response).getBody();
    }
}
