package com.mrlv.common.i18n;


import com.mrlv.common.utils.ThreadLocalMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
/**
 * 定义一个拦截器
 */
public class LocaleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
        ThreadLocalMap.put(I18nConstants.LOCAL_HEAD_NAME, locale);
        return true;
    }
}
