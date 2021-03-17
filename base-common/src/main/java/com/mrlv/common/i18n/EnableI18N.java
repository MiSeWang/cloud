package com.mrlv.common.i18n;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 *
 * @description 开启国际化，在服务中添加注解可以引入
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({I18nConfig.class})
@Documented
public @interface EnableI18N {
}
