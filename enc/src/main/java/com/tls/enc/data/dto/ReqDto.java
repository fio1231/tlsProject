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

    private HeaderData header;
    private BodyData body;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HeaderData {
        private String authKey;
        @Builder.Default
        private boolean clear = false;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class BodyData {
        private String data;
        private String key;
        private String token;
    }
}
