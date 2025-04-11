package com.tls.enc.repository;

import com.tls.enc.data.collection.Keys;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ==========================================================
 * packagename : com.tls.enc.repository
 * filename    : KeysRepository
 * author      : 이진우
 * date        : 2025-03-21
 * description :
 * ==========================================================
 * date          |    author    |    note
 * ----------------------------------------------------------
 * 2025-03-21    |    이진우    |    최초생성
 */
public interface KeysRepository extends MongoRepository<Keys, String> {
}

