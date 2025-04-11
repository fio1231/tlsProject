package com.tls.enc.service;

import com.tls.enc.data.collection.Keys;
import com.tls.enc.repository.KeysRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ==========================================================
 * PackageName : com.tls.server.data
 * FileName    : KeysService
 * Author      : 이진우
 * Date        : 2025-03-21
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-21    |    이진우    |    최초생성
 */
@Service
@RequiredArgsConstructor
public class KeysService {

    /** {@link KeysRepository} */
    @NonNull
    private KeysRepository keysRepository;

    /**
     * 키정보 데이터 삽입
     * 
     * @param keys
     */
    public void saveKeys(Keys keys) {
        keysRepository.save(keys);
    }

    /**
     * token으로 데이터 조회
     * 
     * @param token
     * @return
     */
    public Keys getKeysByToken(String token) {
        return keysRepository.findById(token).orElse(null);
    }

    /**
     * token으로 키정보 삭제
     * 
     * @param token
     */
    public void deleteByToken(String token) {
        keysRepository.deleteById(token);
    }


}
