package net.xiaosaguo.springstack.spring.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 学习spring全家桶之从 start.spring.io 快速构建项目，在IDEA中集成了此功能，可以直接使用
 *
 * @author xiaosaguo
 * @date 2020/5/1
 */
@SpringBootApplication
@RestController
public class SpringHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringHelloApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring";
    }

    @GetMapping("/hi")
    public Object hi() {
        Map<String, String> map = new HashMap<>(1);
        map.put("hi", "spring");
        return map;
    }
}
