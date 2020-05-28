package net.xiaosaguo.springstack.spring.jdbc.h2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * description: 测试 CommandLineRunner
 * <p>
 * CommandLineRunner 的作用：
 * 如果想在 SpringBoot 项目启动完成之前，做一些初始化操作，可以实现以下任意一个接口完成
 * <ol><li> org.springframework.boot.CommandLineRunner </li>
 * <li> org.springframework.boot.ApplicationRunner </li></ol>
 * <p>
 * 实现该接口，在将其加入 spring 容器中时会执行其 run 方法。
 * 多个 CommandLineRunner/ApplicationRunner 可以被同时执行在同一个 spring 上下文中并且执行顺序可以用 @Order(1) 注解的参数设定。
 * <p>
 * 如果需要访问转换后的 key-value 形式的参数（eg: --foo=bar）而不是原始 String 数组，请考虑使用 ApplicationRunner。
 *
 * @author xiaosaguo
 * @date 2020/05/28
 */
@Slf4j
@Component
@Order(5)
public class CommandLineRunnerTest1 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.warn("--> @Order(5)");
        log.warn("--> CommandLineRunnerTest1::run");
        log.warn("args.asList.toString : {}", Arrays.asList(args));
        List<String> list = Arrays.asList(args);
        list.forEach(log::warn);
    }
}
