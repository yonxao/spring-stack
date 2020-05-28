package net.xiaosaguo.springstack.spring.jdbc.h2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * description: 测试 ApplicationRunner
 * <p>
 * 如果需要访问转换后的 key-value 形式的参数（eg: --foo=bar）而不是原始 String 数组，请考虑使用 ApplicationRunner。
 * <p>
 * 以下代码同样可以获取到
 * ConfigurableApplicationContext context = SpringApplication.run(SpringJdbcH2Application.class, args);
 * ApplicationArguments applicationArguments = context.getBean(ApplicationArguments.class);
 * applicationArguments.getOptionNames();
 *
 * @author xiaosaguo
 * @date 2020/05/28
 */
@Slf4j
@Component
@Order(2)
public class ApplicationRunnerTest1 implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.warn("--> @Order(2)");
        log.warn("--> ApplicationRunnerTest1::run");
        log.warn("ApplicationRunnerTest1: {}", Arrays.asList(args.getSourceArgs()));
        log.warn("========getOptionNames: {}", args.getOptionNames());
        log.warn("=======getOptionValues: {}", args.getOptionValues("foo"));
        log.warn("=======getOptionValues: {}", args.getOptionValues("env.name"));
    }
}