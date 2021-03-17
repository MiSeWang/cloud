package com.mrlv.common.i18n;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

/**
 * @description
 */
public class I18nConfig {



    /**
     * 定义区域解析器， 可以从http头部信息中获取lang，格式为en_US America/Chicago或者zh_CN Asia/Shanghai
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        LocaleHeaderLocaleResolver localeResolver = new LocaleHeaderLocaleResolver();
        localeResolver.setLocaleHeadName(I18nConstants.LOCAL_HEAD_NAME);
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return localeResolver;
    }

    /**
     * 拦截请求，参数中是否含有lang，根据该参数可以修改语言
     * @return
     */
    @Bean
    public WebMvcConfigurer webLocaleInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
                localeInterceptor.setParamName(I18nConstants.LOCAL_HEAD_NAME);
                registry.addInterceptor(localeInterceptor);
            }
        };
    }

    /**
     * 拦截Feign请求，转发lang参数
     * @return
     */
    @Bean("i18nFeignRequestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String lang = "";
            if (Objects.nonNull(attributes)) {
                HttpServletRequest request = attributes.getRequest();
                lang = request.getHeader(I18nConstants.LOCAL_HEAD_NAME);
            }
                if (StrUtil.isNotBlank(lang)) {
                template.header(I18nConstants.LOCAL_HEAD_NAME, lang);
            }
        };
    }

    /**
     *处理feign熔断后，无法获取请求头信息问题
     * @return
     */
//    @Bean
//    public HystrixConcurrencyStrategy hystrixConcurrencyStrategy() {
//        return new RequestAttributeHystrixConcurrencyStrategy();
//    }

    /**
     * 拦截器
     * @return
     */
    @Bean
    public WebMvcConfigurer webLocaleConfig() {
        return new WebLocaleConfig();
    }

    @Bean
    public LocaleUtils localeUtils(MessageSource messageSource) {
        return new LocaleUtils(messageSource);
    }
}
