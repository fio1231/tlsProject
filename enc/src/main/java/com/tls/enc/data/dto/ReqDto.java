package com.tls.enc.data.dto;

import lombok.*;

import java.io.Serializable;

/**
 * ==========================================================
 * PackageName : com.tls.enc.data.dto
 * FileName    : ReqDto
 * Author      : 이진우
 * Date        : 2025-03-20
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-20    |    이진우    |    최초생성
 */
@Data
public class ReqDto implements Serializable {

    /** {@link ReqDto.HeaderData} */
    private HeaderData header;

    /** {@link ReqDto.BodyData} */
    private BodyData body;

    /**
     * 요청 해더 클래스
     */
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HeaderData {
        /** 인증 키 */
        private String authKey;
        
        /** 키 정보 삭제 여부 */
        @Builder.Default
        private boolean clear = false;
    }

    /**
     * 요청 바디 클래스
     */
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class BodyData {
        /** 데이터 */
        private String data;
        
        /** 키 */
        private String key;
        
        /** 토큰 */
        private String token;
    }
}
