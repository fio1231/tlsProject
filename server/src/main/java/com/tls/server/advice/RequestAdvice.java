package com.tls.server.advice;

import com.tls.server.common.dto.AbstractReqDto;
import com.tls.server.common.dto.EncReqDto;
import com.tls.server.common.dto.EncResDto;
import com.tls.server.common.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * ==========================================================
 * PackageName : com.tls.server.advice
 * FileName    : RequestAdvice
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
public class RequestAdvice implements RequestBodyAdvice {

    /** {@link CommonService} */
    @NonNull
    private CommonService commonService;

    /** 예외 처리 URI */
    private List<String> excludeUri = Arrays.asList("/exchange", "/actuator/health");

    /**
     * Body를 읽을 대상 선택
     * @param methodParameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        return !excludeUri.contains(uri)
                && !uri.contains("actuator")
                && methodParameter.getParameter().getType().getSuperclass().equals(AbstractReqDto.class);
    }

    /**
     * Request Body 읽기전 처리
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     * @throws IOException
     */
    @Override
    public @NonNull HttpInputMessage beforeBodyRead(@NonNull HttpInputMessage inputMessage
            , @NonNull MethodParameter parameter
            , @NonNull Type targetType
            , @NonNull Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        String strBody = IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8);
        String key = inputMessage.getHeaders().getFirst("x-tlspj-key");
        String token = inputMessage.getHeaders().getFirst("x-tlspj-token");

        EncReqDto reqDto = new EncReqDto();
        reqDto.setBody(EncReqDto.BodyData.builder().token(token).data(strBody).key(key).build());

        // 암호화 서버와 통신하여 복호화 처리
        EncResDto resDto = commonService.dec(reqDto);
        String strDec = resDto.getBody().getData();

        // Body 재가공
        return new MappingJacksonInputMessage(new ByteArrayInputStream(strDec.getBytes(StandardCharsets.UTF_8)), inputMessage.getHeaders());
    }

    @Override
    public @NonNull Object afterBodyRead(@NonNull Object body, @NonNull HttpInputMessage inputMessage
            , @NonNull MethodParameter parameter
            , @NonNull Type targetType
            , @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public @NonNull Object handleEmptyBody(@NonNull Object body, @NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
