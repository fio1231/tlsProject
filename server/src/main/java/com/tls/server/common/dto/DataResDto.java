package com.tls.server.common.dto;

import lombok.*;

/**
 * ==========================================================
 * PackageName : com.tls.server.common.dto
 * FileName    : AbstractResDto
 * Author      : 이진우
 * Date        : 2025-03-27
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-27    |    이진우    |    최초생성
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataResDto {
    /** 응답 코드 */
    @Builder.Default
    String code = "00000";

    /** 응답 메세지 */
    String msg;

    /** 응답 데이터 */
    Object data;
}

