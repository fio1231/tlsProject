package com.tls.enc.service;

import com.tls.enc.data.collection.Authorize;
import com.tls.enc.repository.AuthorizeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ==========================================================
 * PackageName : com.tls.enc.service
 * FileName    : AuthorizeService
 * Author      : 이진우
 * Date        : 2025-03-24
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-24    |    이진우    |    최초생성
 */
@Service
@RequiredArgsConstructor
public class AuthorizeService {

    /** {@link AuthorizeRepository} */
    @NonNull
    private AuthorizeRepository authorizeRepository;

    /**
     * authKey로 데이터 조회
     *
     * @param authKey
     * @return
     */
    public Authorize getAuthorizeByToken(String authKey) {
        return authorizeRepository.findById(authKey).orElse(null);
    }
}
