package com.tls.enc.data.collection;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * ==========================================================
 * PackageName : com.tls.enc.data.collection
 * FileName    : Authorize
 * Author      : 이진우
 * Date        : 2025-03-21
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-21    |    이진우    |    최초생성
 */
@Data
@Document(collection = "authorize")
public class Authorize {
    /** 요청 서버 키 */
    @MongoId
    private String authKey;

    /** 요청 서버 IP */
    private String serverIp;
}
