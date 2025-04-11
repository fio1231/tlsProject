package com.tls.enc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ==========================================================
 * PackageName : com.tls.enc.data.dto
 * FileName    : KeyInfo
 * Author      : 이진우
 * Date        : 2025-03-21
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-21    |    이진우    |    최초생성
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyInfo {
    /** 서버 개인 키 */
    private String serverPrivateKey;

    /** 서버 공개 키 */
    private String serverPublicKey;

    /** 프론트 공개 키 */
    private String clientPublicKey;
}
