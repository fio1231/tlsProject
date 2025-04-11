package com.tls.server.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tls.server.annotation.EncRequestBody;
import com.tls.server.common.dto.EncReqDto;
import com.tls.server.common.dto.EncResDto;
import com.tls.server.common.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * ==========================================================
 * PackageName : com.tls.server.resolver
 * FileName    : ControllerArgumentResolver
 * Author      : 이진우
 * Date        : 25. 4. 7.
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 25. 4. 7.    |    이진우    |    최초생성
 */
@RequiredArgsConstructor
public class ControllerArgumentResolver implements HandlerMethodArgumentResolver {

    /** {@link CommonService} */
    @NonNull
    private CommonService commonService;

    /**
     * 처리할 파라미터 선정
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(EncRequestBody.class); // @EncRequestBody
    }

    /**
     * 파라미터 처리
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String paramKey = parameter.getParameterAnnotation(EncRequestBody.class).value();

        String strBody = request.getParameter(paramKey);
        String key = webRequest.getHeader("x-tlspj-key");
        String token = webRequest.getHeader("x-tlspj-token");

        EncReqDto reqDto = new EncReqDto();
        reqDto.setBody(EncReqDto.BodyData.builder().token(token).data(strBody).key(key).build());

        //암호화 서버를 통해 복호화 처리 한다.
        EncResDto resDto = commonService.dec(reqDto);
        String strDec = resDto.getBody().getData();

        ObjectMapper om = new ObjectMapper();

        return om.readValue(strDec, parameter.getParameterType());

    }
}
