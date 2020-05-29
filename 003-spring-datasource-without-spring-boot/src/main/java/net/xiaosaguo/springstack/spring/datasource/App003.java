package net.xiaosaguo.springstack.spring.datasource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * description: 《学习 Spring 全家桶》之《使用 spring 配置 DataSource、DataSourceTransactionManager，使用 JDBC 连接 H2 数据库》
 * 没有使用 spring boot
 * <p>
 * 注意：有些情况下可能会将 xml 配置和注解配置混着用，所以要注意 ApplicationContext 的类型。
 * <p>
 * 1. 配置了 DataSource
 * <br>
 * 2. 配置了 PlatformTransactionManager
 * <p>
 * {@link @Configuration} 用法：
 * 表示一个类声明一个或多个 @Bean 方法，并且可能被 Spring 容器在运行时为这些 Bean 生成 Bean 定义和服务请求处理，等价于 {@code <Beans></Beans>}
 * <p>
 * {@link @Bean} 用法：
 * 标记方法生成一个由 Spring 容器管理的 Bean，等价于 {@code <Bean></Bean>}
 * <p>
 * 在 applicationContext.xml 中说明了 xmlns、xmlns:xsi、xsi:schemeLocation 的作用
 *
 * @author xiaosaguo
 * @date 2020/05/29
 */
@ComponentScan("net.xiaosaguo.springstack.spring.datasource")
@Configuration
@EnableTransactionManagement
public class App003 {

    /// @Autowired
    /**
     * 推荐使用 @Resource 注入，但是如果不是用 spring boot 的话需要额外引入依赖，这个依赖也更名几次了
     * jsr250-api -> javax.annotation-api -> jakarta.annotation-api
     */
    @Resource
    private DataSource dataSource;

    public static void main(String[] args) throws SQLException {
        System.out.println("Hello World!");
        /*
         * 采用 xml 配置的时候，则 ApplicationContext.xml 的内容会生效。
         * 前提是要用 FileSystemXmlApplicationContext 或者 ClassPathXmlApplicationContext 去读取xml，配置才会生效！
         * 同时 @ComponentScan 会被忽略！
         */
        /// ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");
        /*
         * 采用注解配置的时候，则应该使用 AnnotationConfigApplicationContext 来加载，这时配置类的中的 @ComponentScan 才会生效。
         */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(App003.class);
        showBeans(applicationContext);
        showDataSource(applicationContext);
    }

    @Bean
    public DataSource dataSource() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("driverClassName", "org.h2.Driver");
        properties.setProperty("url", "jdbc:h2:mem:testdb");
        properties.setProperty("username", "sa");
        return BasicDataSourceFactory.createDataSource(properties);
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource());
    }

    private static void showBeans(ApplicationContext applicationContext) {
        System.out.println("-->showBeans()...");
        Arrays.asList(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
    }

    private static void showDataSource(ApplicationContext applicationContext) throws SQLException {
        App003 appBean = applicationContext.getBean("app003", App003.class);
        appBean.showDataSource();
    }

    private void showDataSource() throws SQLException {
        System.out.println("-->showDataSource()...");
        System.out.println(dataSource.toString());
        System.out.println("-->showConnection...");
        Connection connection = dataSource.getConnection();
        System.out.println(connection.toString());
        connection.close();
    }
}
