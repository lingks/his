package com.his.mybatis.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by linsheng on 2017/11/27.
 */
@Intercepts({
        @Signature(
                type= StatementHandler.class,
                method = "prepare",
                args = {Connection.class}
        )
})
public class DataAccessInterceptor implements Interceptor {
    /**
     * 拦截器要执行的方法
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String id = mappedStatement.getId();
        System.out.println("========================当前查询方法=========================");
        System.out.println(id);
        System.out.println("========================当前查询方法=========================");
        BoundSql boundSql = statementHandler.getBoundSql();

        return invocation.proceed();
    }

    /**
     * 拦截器需要拦截的对象
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 设置初始化的属性值
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
