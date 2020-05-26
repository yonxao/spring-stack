package net.xiaosaguo.springstack.spring.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 《学习 Spring 全家桶》之《从 start.spring.io 快速构建项目》
 * <p>
 * IntelliJ IDEA 中集成了此功能，可以直接使用：File | New | Project/Model | Spring Initializr
 *
 * @author xiaosaguo
 * @date 2020/05/01
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
