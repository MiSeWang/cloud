package com.mrlv.common.i18n;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 区域化配置WebMvcConfigurer
 */
public class WebLocaleConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("*.js", "/**/*.js", "*.css", "/**/*.css", "*.html", "/**/*.html");
    }
}
