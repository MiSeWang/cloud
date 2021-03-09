package com.mrlv.db.druid.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * Mybatis拦截器, 原作为多租户和myCat, 暂时不需要进行特殊处理
 **/
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisInterceptor implements Interceptor {

    private final Logger log = LoggerFactory.getLogger(MybatisInterceptor.class);

    @Value("${database.name}")
    private String databaseName;

//    @Value("${mycat.schema:}")
//    private String mycatSchema;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //String tenantId = (String) ThreadLocalMap.get(TENANT_KEY);
        //if (StringUtils.isBlank(tenantId) && StringUtils.isBlank(mycatSchema)) {
        //    return invocation.proceed();
        //}
        //tenantId = StringUtils.isNotBlank(mycatSchema) ? mycatSchema : tenantId;
//        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

//        BoundSql boundSql = statementHandler.getBoundSql();
        //获取到原始sql语句
//        String sql = boundSql.getSql();
//        StringBuilder sb = new StringBuilder();
//        if (!sql.startsWith(MYCAT_PREFIX)) {
//            sb.append(MYCAT_PREFIX)
//                    .append(tenantId)
//                    .append("_")
//                    .append(databaseName)
//                    .append(MYCAT_SUFFIX);
//        }
//        sb.append(sql);
//        //通过反射修改sql语句
//        Field field = boundSql.getClass().getDeclaredField("sql");
//        field.setAccessible(true);
//        field.set(boundSql, sb.toString());
//        if(log.isDebugEnabled()){
//            log.debug("修改sql，添加myCat注解：{}", sb.toString());
//        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
