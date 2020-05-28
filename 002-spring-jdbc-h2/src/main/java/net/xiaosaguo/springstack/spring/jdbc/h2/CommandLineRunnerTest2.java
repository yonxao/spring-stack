package net.xiaosaguo.springstack.spring.jdbc.h2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * description: 测试 CommandLineRunner
 *
 * @author xiaosaguo
 * @date 2020/05/28
 */
@Slf4j
@Component
@Order(1)
public class CommandLineRunnerTest2 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.warn("--> @Order(1)");
        log.warn("--> CommandLineRunnerTest2::run");
    }
}
