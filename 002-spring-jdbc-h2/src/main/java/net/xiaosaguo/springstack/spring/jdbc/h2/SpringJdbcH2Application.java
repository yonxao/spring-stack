package net.xiaosaguo.springstack.spring.jdbc.h2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * description: 《学习 Spring 全家桶》之《DataSource 配置、使用 JDBC 连接 H2 数据库》
 * <p>
 * 启动应用时加上参数：aaa bbb,ccc --foo=bar --env.name=dev
 * 注：多个参数是以空格分隔的，不是英文逗号。
 * <p>
 * 用 warn 等级的 log 突出显示输出的信息
 * <p>
 * 关于 CommandLineRunner、ApplicationRunner、@PostConstruct 的功能、测试结论等在类名结尾为 1 的类的注释中。
 *
 * @author xiaosaguo
 * @date 2020/05/01
 */
@SpringBootApplication
@Slf4j
@Order(4)
public class SpringJdbcH2Application implements CommandLineRunner {

    /**
     * DataSource 有以下几个个关键成员变量及不配置时的默认值（默认 DataSource为 HikariDataSource）：
     * 1. driverClassName：org.h2.Driver(配置时可以忽略，spring boot 会根据 jdbcUrl 中的协议自动设置)
     * 2. jdbcUrl：jdbc:h2:mem:xxxxxx;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
     * 3. username：sa
     * 4. password：
     * 5. pool：HikariPool
     * 6. getConnection()
     * <p>
     * Spring Boot 做了了哪些配置：
     * 1. DataSourceAutoConfiguration - 配置 DataSource
     * 2. DataSourceTransactionManagerAutoConfiguration - 配置 DataSourceTransactionManager
     * 3. JdbcTemplateAutoConfiguration - 配置 JdbcTemplate
     * 这些配置都是符合条件时才进⾏行行配置，如果自定义配置了，就不会在自动配置了
     */
    @Resource
    private DataSource dataSource;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        /// SpringApplication.run(SpringJdbcH2Application.class, args);
        log.warn("--> Application::main1");
        ConfigurableApplicationContext context = SpringApplication.run(SpringJdbcH2Application.class, args);
        log.warn("--> Application::main2");
        ApplicationArguments applicationArguments = context.getBean(ApplicationArguments.class);
        log.warn("args.asList.toString = {}", Arrays.asList(args));
        log.warn("applicationArguments.getSourceArgs().asList.toString = {}", Arrays.asList(applicationArguments.getSourceArgs()));
        log.warn("applicationArguments.getOptionNames() = {}", applicationArguments.getOptionNames());
        log.warn("applicationArguments.getOptionValues(\"env.name\") = {}", applicationArguments.getOptionValues("env.name"));
    }

    @Override
    public void run(String... args) throws Exception {
        log.warn("--> @Order(4)");
        log.warn("--> Application::run");
        showConnection();
        showData();
    }

    private void showConnection() throws SQLException {
        log.warn("dataSource: {}", dataSource.toString());
        Connection connection = dataSource.getConnection();
        log.warn("connection: {}", connection.toString());
        connection.close();
    }

    private void showData() {
        jdbcTemplate.queryForList("SELECT * FROM foo").forEach(row -> log.info(row.toString()));
    }
}
