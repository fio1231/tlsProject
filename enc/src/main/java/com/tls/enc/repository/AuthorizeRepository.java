package com.tls.enc.repository;

import com.tls.enc.data.collection.Authorize;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ==========================================================
 * PackageName : com.tls.enc.repository
 * FileName    : AuthorizeRepository
 * Author      : 이진우
 * Date        : 2025-03-24
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-24    |    이진우    |    최초생성
 */
public interface AuthorizeRepository extends MongoRepository<Authorize, String> {
}
