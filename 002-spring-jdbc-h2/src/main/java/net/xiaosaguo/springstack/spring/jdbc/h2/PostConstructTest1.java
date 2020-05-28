package net.xiaosaguo.springstack.spring.jdbc.h2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * description: 测试 @PostConstruct、@PreDestroy
 * <p>
 * init method 和 destroy method的三种使用方式: https://blog.csdn.net/tuzongxun/article/details/53580695
 * <p>
 * JSR-250: 在方法上加上注解 @PostConstruct，这个方法就会在 Bean 初始化之后被 Spring 容器执行
 * 注：Bean 初始化 = 实例化 Bean + 装配 Bean的属性（依赖注入）
 * <p>
 * 结论：执行顺序
 * Constructor >> @Resource/@Autowired >> @PostConstruct >> CommandLineRunner/ApplicationRunner(order) >> Application::main->SpringApplication.run() 之后的代码，即 main->line2
 *
 * @author xiaosaguo
 * @date 2020/05/28
 */
@Component
@Slf4j
public class PostConstructTest1 {

    @Resource
    private PostConstructTest2 postConstructTest2;

    public PostConstructTest1() {
        log.warn("PostConstructTest1::construct");
        log.warn("此时 postConstructTest2 还未被注入，postConstructTest2 = {}", postConstructTest2);
    }

    @PostConstruct
    private void init() {
        log.warn("PostConstructTest1::init -> @PostConstruct");
        log.warn("@PostConstruct: 将在依赖注入完成后被自动调用，postConstructTest2 = " + postConstructTest2);
    }

    @PreDestroy
    private void destroy() {
        log.warn("经测试在 IDEA 中只有使用 debug 模式启动，在点击关闭应用时此日志才输出...");
        log.warn("PostConstructTest1::destroy -> @PreDestroy");
        log.warn("@PreDestroy: 将在销毁前被自动调用，postConstructTest2 = {}", postConstructTest2);
    }
}
