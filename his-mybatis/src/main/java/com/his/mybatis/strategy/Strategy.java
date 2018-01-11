package com.his.mybatis.strategy;
import javax.sql.DataSource;

/**
 * @Description: 负载均衡策略
 */
public interface Strategy {
    String select(java.util.List<DataSource> Slaves, DataSource master);
}

