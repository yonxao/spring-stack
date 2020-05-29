# spring-stack

本人学习 spring 技术栈的一些 demo。
- 源码中有很多详细的注释
- 点击二级标题可以进入详细的学习笔记中，笔记都在 docs 文件夹下，也可以直接从 docs 文件夹中查看详细笔记。



## [001-spring-hello](docs/001-spring-hello.md)

1. 从`start.spring.io`快速构建项目
2. 第一个 RESTful API
3. 启用应用自省和监控功能
4. Maven 整合 Spring Boot
5. 无法设置`<parent>`为`spring-boot-starter-parent`的处理方法



## [002-spring-jdbc-h2(DataSource)](docs/002-spring-jdbc-h2.md)

1. 只需引入数据库驱动和`spring-boot-starter-jdbc`即可完成配置
2. Spring Boot 默认配置的 DataSource
3. CommandLineRunner/ApplicationRunner 和 @Order 的作用和用法
4. @PostConstruct 和 @PreDestroy 的作用和用法
5. @Resource/@Autowired、@PostConstruct、CommandLineRunner/ApplicationRunner 的执行顺序
6. schema.sql 和 data.sql 的作用和用法
7. Maven 依赖的`<optional>`和`<scope>`说明



## [003-spring-datasource-without-spring-boot](docs/003-spring-datasource-without-spring-boot.md)
1. 使用注解配置 DataSource
2. 使用注解配置 PlatformTransactionManager
3. xml 配置和注解配置时要注意的点
4. 注解 @Bean、@Configuration、@Resource/@Autowired、@ComponentScan、 @EnableTransactionManagement 的作用和用法
5. 依赖注入的三种方式及比较（4.3.1）
6. 使用 xml 配置 DataSource
7. xml 文件 xmlns、xmlns:xsi、xsi:schemaLocation 的作用和用法
8. Maven 中 `<version>`的一些写法和推荐







