package com.tls.server.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tls.server.common.dto.DataResDto;
import com.tls.server.common.dto.EncReqDto;
import com.tls.server.common.dto.EncResDto;
import com.tls.server.common.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.List;

/**
 * ==========================================================
 * PackageName : com.tls.server.advice
 * FileName    : ResponseAdvice
 * Author      : 이진우
 * Date        : 2025-03-27
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-27    |    이진우    |    최초생성
 */
@RequiredArgsConstructor
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    /** {@link CommonService} */
    @NonNull
    private CommonService commonService;

    /** 예외 처리 URI */
    private List<String> excludeUri = Arrays.asList("/exchange");

    /**
     * Body를 읽을 대상 선택
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(@NonNull MethodParameter returnType
            , @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        return !excludeUri.contains(uri) && !uri.contains("actuator");
    }

    /**
     * Response Body 처리
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public @NonNull DataResDto beforeBodyWrite(@NonNull Object body
            , @NonNull MethodParameter returnType
            , @NonNull MediaType selectedContentType
            , @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType
            , @NonNull ServerHttpRequest request
            , @NonNull ServerHttpResponse response) {

        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = "";

        try {
            strBody = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            return DataResDto.builder().data(body).build();
        }

        String token = request.getHeaders().getFirst("x-tlspj-token");

        EncReqDto reqDto = new EncReqDto();
        reqDto.setBody(EncReqDto.BodyData.builder().token(token).data(strBody).build());

        // 암호화 서버와 통신하여 암호화 처리
        EncResDto resDto = commonService.enc(reqDto);

        // Body 전달
        return DataResDto.builder().data(resDto.getBody()).build();

    }
}
