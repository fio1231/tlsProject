package com.tls.server.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ==========================================================
 * PackageName : com.tls.server.common.dto
 * FileName    : AbstractDto
 * Author      : 이진우
 * Date        : 2025-03-27
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-27    |    이진우    |    최초생성
 */
@Getter
@Setter
@ToString
public abstract class AbstractReqDto {
    /** 토큰 */
    String token;
}
