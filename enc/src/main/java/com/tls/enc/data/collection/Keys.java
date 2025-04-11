package com.tls.enc.data.collection;

import com.tls.enc.data.dto.KeyInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * ==========================================================
 * PackageName : com.tls.enc.data.collection
 * FileName    : Keys
 * Author      : 이진우
 * Date        : 2025-03-21
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-21    |    이진우    |    최초생성
 */
@Data
@Document(collection = "keys")
public class Keys {
    /** 암호화 처리 키 값 */
    @MongoId
    private String token;

    /** {@link KeyInfo} */
    private KeyInfo keyInfo;
}
