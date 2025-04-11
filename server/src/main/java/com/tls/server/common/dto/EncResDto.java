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

    private HeaderData header;
    private BodyData body;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HeaderData {
        @Builder.Default
        private String code = "00000";
        @Builder.Default
        private String msg = "성공";
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BodyData {
        private String data;
        private String key;
        private String token;
    }
}