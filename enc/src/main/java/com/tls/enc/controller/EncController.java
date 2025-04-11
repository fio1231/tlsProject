package com.tls.enc.controller;

import com.tls.enc.data.dto.ReqDto;
import com.tls.enc.data.dto.ResDto;
import com.tls.enc.service.EncService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ==========================================================
 * PackageName : com.tls.enc.controller
 * FileName    : MainController
 * Author      : 이진우
 * Date        : 2025-03-20
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-20    |    이진우    |    최초생성
 */
@RestController
@RequiredArgsConstructor
public class EncController {

    /** {@link EncService} */
    @NonNull
    private EncService encService;

    /**
     * 키 교환
     * 
     * @param reqDto
     * @return
     * @throws Exception
     */
    @PostMapping("/exchange")
    public ResponseEntity<ResDto> exchange(@RequestBody ReqDto reqDto) throws Exception {
        return ResponseEntity.ok(encService.exchange(reqDto));
    }

    /**     * 암호화 처리
     * 
     * @param reqDto
     * @return
     * @throws Exception
     */
    @PostMapping("/enc")
    public ResponseEntity<ResDto> enc(@RequestBody ReqDto reqDto) throws Exception {
        return ResponseEntity.ok(encService.enc(reqDto));
    }

    /**
     * 복호화 처리
     * 
     * @param reqDto
     * @return
     * @throws Exception
     */
    @PostMapping("/dec")
    public ResponseEntity<ResDto> dec(@RequestBody ReqDto reqDto) throws Exception {
        return ResponseEntity.ok(encService.dec(reqDto));
    }

    /**
     * 키 정보 삭제
     * 
     * @param reqDto
     * @return
     * @throws Exception
     */
    @PostMapping("/clear")
    public ResponseEntity<?> clear(@RequestBody ReqDto reqDto) throws Exception {
        encService.clear(reqDto);
        return ResponseEntity.ok(ResDto.builder().build());
    }
}
