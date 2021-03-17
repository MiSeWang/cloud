package com.mrlv.common.i18n;

import com.mrlv.common.utils.ThreadLocalMap;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;


import java.util.Locale;

/**
 * 国际化转换工具
 */
public class LocaleUtils {

    private static MessageSource messageSource;

    public LocaleUtils(MessageSource messageSource) {
        LocaleUtils.messageSource = messageSource;
    }

    public static String getMessage(String msgKey) {
        return getMessage(msgKey, null);
    }

    public static String getMessage(String msgKey,Object[] args) {
        try {
            return messageSource.getMessage(msgKey, args, (Locale)ThreadLocalMap.get(I18nConstants.LOCAL_HEAD_NAME));
        } catch (Exception e) {
            return Strings.EMPTY;
        }
    }

}
