package com.tls.enc.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tls.enc.data.collection.Authorize;
import com.tls.enc.data.dto.ReqDto;
import com.tls.enc.service.AuthorizeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * ==========================================================
 * PackageName : com.tls.enc.advice
 * FileName    : AuthRequestBodyAdvice
 * Author      : 이진우
 * Date        : 2025-03-24
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-24    |    이진우    |    최초생성
 */
@RequiredArgsConstructor
@RestControllerAdvice
public class AuthRequestBodyAdvice implements RequestBodyAdvice {

    @NonNull
    private AuthorizeService authorizeService;

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        return !uri.contains("actuator");
    }

    @Override
    public @NonNull HttpInputMessage beforeBodyRead(@NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        //-- 1. body를 dto로 전환
        String strBody = IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        ReqDto reqDto = objectMapper.readValue(strBody, ReqDto.class);
        String authKey = reqDto.getHeader().getAuthKey();

        //-- 2. authKey로 인증 정보 조회
        Authorize authorize = authorizeService.getAuthorizeByToken(authKey);

        //-- 3. request 가져옴
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //-- 4. 인증 정보가 없거나 아이피 정보가 다르면 오류 발생
        if(null == authorize || !StringUtils.equalsIgnoreCase(authorize.getServerIp(), request.getRemoteAddr())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");
        }

        return new MappingJacksonInputMessage(new ByteArrayInputStream(strBody.getBytes()), inputMessage.getHeaders());
    }

    @Override
    public @NonNull Object afterBodyRead(@NonNull Object body, @NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public @NonNull Object handleEmptyBody(@NonNull Object body, @NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
