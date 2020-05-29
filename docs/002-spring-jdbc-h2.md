## 002-spring-jdbc-h2(DataSource)

使用 Spring Boot 配置 DataSource。

要点：



### 1. 只需引入数据库驱动和`spring-boot-starter-jdbc`即可完成配置

什么都不需要配置，只需要引入依赖，即可完成 DataSource 配置。

Spring Boot 自动做了哪些配置？
1. DataSourceAutoConfiguration
    配置 DataSource

2. DataSourceTransactionManagerAutoConfiguration
    配置 DataSourceTransactionManager

3. JdbcTemplateAutoConfiguration
    配置 JdbcTemplate

这些配置都是符合条件时才进⾏行行配置，如果自定义配置了，就不会在自动配置了。



### 2. Spring Boot 默认配置的 DataSource

DataSource 有以下几个个关键成员变量及不配置时的默认值：
1.  默认 DataSource 为 HikariDataSource
2. driverClassName：org.h2.Driver
    配置时可以忽略，spring boot 会根据 jdbcUrl 中的协议自动设置
3. jdbcUrl：jdbc:h2:mem:xxxxxx;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
4. username：sa
5. password：
6. pool：HikariPool
7. getConnection()



### 3. CommandLineRunner 和 ApplicationRunner

#### 3.1. CommandLineRunner

作用：

如果想在 SpringBoot 项目启动完成之前，做一些初始化操作，可以实现以下任意一个接口完成
- `org.springframework.boot.CommandLineRunner`
- `org.springframework.boot.ApplicationRunner`

实现此接口，在将其加入 spring 容器中时会执行其 run 方法。

多个 CommandLineRunner/ApplicationRunner 可以被同时执行在同一个 spring 上下文中并且执行顺序可以用 @Order(1) 注解的参数设定。

如果需要访问转换后的 key-value 形式的参数（eg: --foo=bar）而不是原始 String 数组，请考虑使用 ApplicationRunner。

eg：
```java
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
```

#### 3.2. ApplicationRunner

作用：

同 CommandLineRunner，只不过可以直接使用处理后的参数。

eg:
```java
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
```

#### 3.3. @Order(int)

作用：

标识在实现了 CommandLineRunner/ApplicationRunner 接口的类上，指定其执行顺序。



### 4. @PostConstruct 和 @PreDestroy

[init method 和 destroy method的三种实现方式](https://blog.csdn.net/tuzongxun/article/details/53580695)

JSR-250: 在方法上加上注解 @PostConstruct，这个方法就会在 Bean 初始化之后被 Spring 容器执行。加上注解 @PreDestroy 会在 Bean 销毁前执行。

注：Bean 初始化 = 实例化 Bean + 装配 Bean的属性（依赖注入）



### 5. @Resource/@Autowired、@PostConstruct、CommandLineRunner/ApplicationRunner 的执行顺序

Constructor >> 
@Resource/@Autowired >> 
@PostConstruct >> 
CommandLineRunner/ApplicationRunner(order) >> 
Application::main->SpringApplication.run() 之后的代码，即 main->line2



### 6. schema.sql 和 data.sql

1. 将这两个文件放在 Resource 目录下，在应用启动时会执行此文件中的语句
2. 约定文件名默认为 schema.sql、data.sql
3. 约定 schema.sql 文件中存放 ddl 语句
4. 约定 data.sql 文件中存放 dml 语句
5. 如果要自定义文件名和位置
    1. spring.datasource.schema=schema.sql,schema2.sql
    2. spring.datasource.data=data.sql,data2.sql
6. Spring Boot2.x 必须添加 initialization-mode 配置才会执行，默认为 embedded 嵌入式数据库（H2这种），如在 mysql 下需设置为 always
    `spring.datasource.initialization-mode=always`
7. 如果sql脚本执行的数据库用户名和密码不相同，需要设置单独的属性



### 7. Maven 依赖的`<optional>`和`<scope>`说明

#### 7.1. `<optional>true</optional>`:

当其他模块依赖本模块时，如果不写 optional 会进行依赖传递，设置后可以节省开销、减少依赖冲突。
1. 当 optional 设置为 true 时，此依赖项不会传递到其他模块
2. 当 optional 设置为 false 时，此依赖项会传递到其他模块

#### 7.2. `<scope>runtime</scope>`

1. scope : compile
    默认值，表示在 build、test、runtime 阶段的 classpath 下都有依赖关系。
2. scope : test
    表示只在 test 阶段有依赖关系，例如 junit
3. scope : provided
    表示在 build、test 阶段都有依赖，在 runtime 时并不输出依赖关系而是由容器提供，例如 war 包不包括 servlet-api.jar，而是由tomcat等容器来提供。
    开发时，需要用到 servlet-api 这个依赖，但当将应用部署到 web 容器中时，此依赖容器也会提供，
    如果此时项目中再引用的话就会造成重复引用，会有版本不一致的风险。
4. scope : runtime
    表示在构建编译阶段不需要，只在 test、runtime 阶段需要。
    
    这种主要是指代码里并没有直接引用而是根据配置在运行时动态加载并实例化的情况。虽然用 runtime 的地方改成 compile 也不会出大问题，但是 runtime 的好处是可以避免在程序里意外地直接引用到原本应该动态加载的包。例如 JDBC 连接池。

总结 :
简单来说，compile、runtime 和 provided 的区别，需要在执行 mvn package 命令，且打包格式是 war之类（而不是默认的 jar）的时候才能看出来。通过 compile 和 runtime 引入的 jar 包，会出现在你的项目 war 包里，而 provided 引入的 jar 包则不会。

通过 compile 和 provided 引入的jar包，里面的类，你在项目中可以直接 import 进来用，编译没问题，但是 runtime 引入的 jar 包中的类，项目代码里不能直接用，用了无法通过编译，只能通过反射之类的方式来用。
