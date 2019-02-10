package com.crawler.review.conf;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-22 11:49
 * @description: 主要数据源配置
 **/
@Configuration
@MapperScan(basePackages = CrawlerDataSourceConf.SCAN_PACKAGE, sqlSessionFactoryRef = "crawlerSqlSessionFactory")
public class CrawlerDataSourceConf {

    public static final String SCAN_PACKAGE = "com.aukey.crawler.review.mapper.crawler";
    public static final String MAPPER_LOCATION = "classpath*:mapper/crawler/*.xml";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.crawler")
    public DataSource crawlerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSourceTransactionManager crawlerTransactionManager(@Qualifier("crawlerDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory crawlerSqlSessionFactory(
            @Qualifier("crawlerDataSource") DataSource dataSource
    ) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        //分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();

        //更多详细配置见: https://pagehelper.github.io/docs/howtouse/
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");           //方言
        properties.setProperty("rowBoundsWithCount", "true");       //使用 RowBounds 分页会进行 count 查询
        properties.setProperty("reasonable", "true");               //pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页
        properties.setProperty("pageSizeZero", "true");             //如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果
        properties.setProperty("supportMethodsArguments", "true");  //支持通过 Mapper 接口参数来传递分页参数
        properties.setProperty("offsetAsPageNum", "true");          //将 RowBounds 中的 offset 参数当成 pageNum 使用

        pageInterceptor.setProperties(properties);

        //添加插件
        sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});

        //设置mapper location
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(CrawlerDataSourceConf.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate crawlerTestSqlSessionTemplate(@Qualifier("crawlerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * Mybatis 通用Mapper配置
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public MapperHelper crawlerMapperHelper(@Qualifier("crawlerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperHelper mapperHelper = new MapperHelper();
        //特殊配置
        Config config = new Config();
        config.setNotEmpty(false);
        config.setIDENTITY("MYSQL");

        //更多详细配置: http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/2.Integration.md
        mapperHelper.setConfig(config);
        mapperHelper.registerMapper(Mapper.class);
        mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());

        return mapperHelper;
    }
}
