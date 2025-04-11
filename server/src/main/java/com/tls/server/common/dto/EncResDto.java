package com.tls.server.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ==========================================================
 * PackageName : com.tls.server.common.dto
 * FileName    : ResDto
 * Author      : 이진우
 * Date        : 2025-03-20
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-20    |    이진우    |    최초생성
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EncResDto implements Serializable {

    /** {@link EncResDto.HeaderData} */
    private HeaderData header;

    /** {@link EncResDto.HeaderData} */
    private BodyData body;

    /**
     * 암호화 처리 응답 헤더 클래스
     */
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HeaderData {
        /** 응답 코드 */
        @Builder.Default
        private String code = "00000";

        /** 응답 메세지 */
        @Builder.Default
        private String msg = "성공";
    }

    /**
     * 암호화 처리 응답 바디 클래스
     */
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BodyData {
        /** 데이터 */
        private String data;

        /** 키 */
        private String key;

        /** 토큰 */
        private String token;
    }
}