package com.his.mybatis.config;

import com.github.pagehelper.PageHelper;
import com.his.mybatis.manager.DynamicDataSourceTransactionManager;
import com.his.mybatis.plugin.DynamicPlugin;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

/**
 * @author linsheng
 * @date 2018-01-11
 * @Description: 动态多数据源配置
 */
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfiguration extends MybatisAutoConfiguration {

    @Value("${datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Value("${mybatis.mapping-package}")
    private String mappingPackage;

    @Bean(name = "masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource masterDataSource() {
        System.out.println("-------------------- writeDataSource init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "slave.datasource")
    public DataSource slaveDataSource() {
        System.out.println("-------------------- readDataSourceOne init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "slaveDataSource2")
    @ConfigurationProperties(prefix = "slave2.datasource")
    public DataSource slaveDataSource2() {
        System.out.println("-------------------- readDataSourceSecond init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }
    public DruidDataSourceConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
    }

    @Bean
    public DynamicDataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setMasterDataSource(masterDataSource());
        dynamicDataSource.setReadDataSource(slaveDataSource());
        dynamicDataSource.setReadDataSource(slaveDataSource2());
        return dynamicDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dynamicDataSource());

            bean.setTypeAliasesPackage(typeAliasesPackage);

            //分页插件设置
            PageHelper pageHelper = new PageHelper();
            Properties properties = new Properties();
            properties.setProperty("reasonable", "true");
            properties.setProperty("supportMethodsArguments", "true");
            properties.setProperty("returnPageInfo", "check");
            properties.setProperty("params", "count=countSql");
            pageHelper.setProperties(properties);
            // 加载全局的配置文件
            //bean.setConfigLocation(
             //       new DefaultResourceLoader().getResource("classpath:mybatis/mybatis-config.xml"));//"classpath:mybatis/mybatis-config.xml"

            //System.out.println(
            //        new DefaultResourceLoader().getResource("classpath:mybatis/mybatis-config.xml")); //"classpath:mybatis/mybatis-config.xml"
            //添加分页插件

            //bean.setPlugins(new Interceptor[]{new DynamicPlugin(), new DataAccessInterceptor(),pageHelper});
            bean.setPlugins(new Interceptor[]{new DynamicPlugin(),pageHelper});
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            //基于注解扫描Mapper方法，通用接口不需配置xml路径
            //另外新加入接口的话必须加上
            bean.setMapperLocations(resolver.getResources(mapperLocations));
            System.out.println("mappingPackage:" + mappingPackage);
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * 配置事务管理器
     */
    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager() throws Exception{
        return new DynamicDataSourceTransactionManager(dynamicDataSource());
    }
}
