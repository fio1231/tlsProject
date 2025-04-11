package com.tls.server.common.controller;

import com.tls.server.common.dto.EncResDto;
import com.tls.server.common.service.CommonService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * ==========================================================
 * PackageName : com.tls.server.common.controller
 * FileName    : CommonController
 * Author      : 이진우
 * Date        : 2025-03-25
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-25    |    이진우    |    최초생성
 */
@RequiredArgsConstructor
@RestController
public class CommonController {

    @NonNull
    private CommonService commonService;

    @PostMapping("/exchange")
    public ResponseEntity<EncResDto> exchange(@RequestHeader("x-tlspj-key") String key) {
        return ResponseEntity.ok(commonService.exchange(key));
    }

}
