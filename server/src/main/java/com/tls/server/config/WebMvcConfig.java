package com.tls.server.config;

import com.tls.server.common.service.CommonService;
import com.tls.server.resolver.ControllerArgumentResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * ==========================================================
 * PackageName : com.tls.server.config
 * FileName    : WebMvcConfig
 * Author      : 이진우
 * Date        : 25. 4. 7.
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 25. 4. 7.    |    이진우    |    최초생성
 */
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @NonNull
    private CommonService commonService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ControllerArgumentResolver(commonService));
    }

}
