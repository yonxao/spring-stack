package net.xiaosaguo.springstack.spring.jdbc.h2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * description: 测试类
 *
 * @author xiaosaguo
 * @date 2020/05/28
 */
@Component
@Slf4j
public class PostConstructTest2 {

    public PostConstructTest2() {
        log.warn("PostConstructTest2::construct");
    }
}
