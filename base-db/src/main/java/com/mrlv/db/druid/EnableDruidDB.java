package com.mrlv.db.druid;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MybatisDataSource.class})
@Documented
public @interface EnableDruidDB {
}
