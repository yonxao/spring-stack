## 003-spring-datasource-without-spring-boot

不使用 Spring Boot，只使用 Spring 配置 DataSource。

要点：



### 1. 配置数据源

```java
@Bean
public DataSource dataSource() throws Exception {
    Properties properties = new Properties();
    properties.setProperty("driverClassName", "org.h2.Driver");
    properties.setProperty("url", "jdbc:h2:mem:testdb");
    properties.setProperty("username", "sa");
    return BasicDataSourceFactory.createDataSource(properties);
}
```



### 2. 配置事物管理器

```java
@Bean
public PlatformTransactionManager transactionManager() throws Exception {
    return new DataSourceTransactionManager(dataSource());
}
```



### 3. xml 配置和注解配置时要注意的点

有些情况下可能会将 xml 配置和注解配置混着用，所以要注意 ApplicationContext 的类型。

```java
/*
 * 采用 xml 配置的时候，则 ApplicationContext.xml 的内容会生效。
 * 前提是要用 FileSystemXmlApplicationContext 或者 ClassPathXmlApplicationContext 去读取xml，配置才会生效！
 * 同时 @ComponentScan 会被忽略！
 */
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");

/*
 * 采用注解配置的时候，则应该使用 AnnotationConfigApplicationContext 来加载，这时配置类的中的 @ComponentScan 才会生效。
 */
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(App.class);
```



### 4. 注解学习

#### 4.1. @Bean

标记方法生成一个由 Spring 容器管理的 Bean，等价于 xml 配置中的 `<Bean></Bean>`。

#### 4.2. @Configuration

表示一个类声明一个或多个 @Bean 方法，并且可能被 Spring 容器在`运行时`为这些 Bean 生成 Bean 定义和服务请求处理，
等价于 xml 配置中的 `<Beans></Beans>`。

#### 4.3. @Resource/@Autowired

1. @Autowired 与 @Resource 都可以用来装配 bean。都可以写在字段上，或写在 setter 方法上。
2. 使用 @Autowired 与 @Resource 注入更优雅、易读，但是可能会出现注入失败的问题。
3. @Autowired 默认按类型装配，依赖对象必须存在，如果要允许 null 值，可以设置它的 required 属性为 false，
也可以使用名称装配，需要和 @Qualifier 注解配合使用：`@Autowired()@Qualifier("baseDao")privateBaseDao baseDao;`
4. @Resource 默认按名称进行装配，通过 name 属性进行指定，装配顺序：
    1. 如果同时指定了 name 和 type，则从 Spring 上下文中找到唯一匹配的 bean 进行装配，找不到则抛出异常
    2. 如果指定了 name，则从上下文中查找名称（id）匹配的 bean 进行装配，找不到则抛出异常
    3. 如果指定了 type，则从上下文中找到类型匹配的唯一 bean 进行装配，找不到或者找到多个，都会抛出异常
    4. 如果既没有指定 name，又没有指定 type，则自动按照 byName 方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配；
5. 推荐使用 @Resource ,这也是 IDEA 所推荐的。

##### 4.3.1. 依赖注入的三种方式

1. 变量（filed）注入
    ```java
    @Autowired
    private UserService userService;
    ```
   优点：变量方式注入非常简洁，没有任何多余代码，非常有效的提高了java的简洁性。即使再多几个依赖一样能解决掉这个问题。
   
   缺点：不能有效的指明依赖。当没有启动整个依赖容器时，依赖注入的对象为 null，这个类就不能运转，在反射时无法提供这个类需要的依赖。
2. 构造器注入
    ```java
    private final UserService userService;
    public XxxService(UserService userService) {
           this.userService = userService;
    }
    ```
   在使用 set 方式时，这是一种选择注入，可有可无，即使没有注入这个依赖，那么也不会影响整个类的运行。
3. set方法注入
    ```java
    private UserDao userDao;
    @Autowired
    public void setUserDao (UserDao userDao) {
        this.userDao = userDao;
    }
    ```
    在使用构造器方式时已经显式注明必须强制注入。通过强制指明依赖注入来保证这个类的运行。

总结：

依赖注入的核心思想之一就是被容器管理的类不应该依赖被容器管理的依赖，
换成白话来说就是如果这个类使用了依赖注入的类，那么这个类摆脱了这几个依赖必须也能正常运行。
然而使用变量注入的方式是不能保证这点的。

既然使用了依赖注入方式，那么就表明这个类不再对这些依赖负责，这些都由容器管理，那么如何清楚的知道这个类需要哪些依赖呢？
它就要使用set方法方式注入或者构造器注入。

变量方式注入应该尽量避免，使用set方式注入或者构造器注入，这两种方式的选择就要看这个类是强制依赖的话就用构造器方式，选择依赖的话就用set方法注入。

#### 4.4. @ComponentScan

@ComponentScan 主要就是定义**扫描的路径**，可以配置多个，从中找出标识了**需要装配**的类(标识了 @Controller、@Service、@Repository、@Component 的类)自动装配到 Spring的 bean 容器中。

eg：

```java
@ComponentScan("net.xiaosaguo.springstack.spring.datasource")
```


#### 4.5 @EnableTransactionManagement

Spring 事物管理很方便，首先使用注解 @EnableTransactionManagement 开启事务支持后，然后在访问数据库的 Service 方法上添加注解 @Transactional 便可。

关于事务管理器，不管是 JPA 还是 JDBC 等都实现自接口 PlatformTransactionManager。如果你添加的是 spring-boot-starter-jdbc 依赖，框架会默认注入 DataSourceTransactionManager 实例。如果你添加的是 spring-boot-starter-data-jpa 依赖，框架会默认注入 JpaTransactionManager 实例。



### 5. 使用 xml 配置 DataSource

查看 applicationContext.xml 文件。

1.  xmlns

    xmlns 的全称为 xml namespace，即 xml 命名空间，这个和 java 中 package 的概念基本一致，起的作用也基本一致：区分重复元素，解决了元素冲突的问题。

    xmlns 格式定义：`xmlns[:name] = "uri"`

    name 可以忽略不填，即为默认命名空间，表现效果则为命名空间中的元素可以不加前辍，在此 xml 中直接使用，类似于配置文件中的的 `<bean>`

    自定义命名空间，即 name 的值：表现效果为在 xml 文件中使用此命名空间下的元素时，必须加上前辍，类似配置文件中的 `<context:component-scan>`

2.  xmlns:xsi

    xsi : xml schema instance

    本质就是声明一个名为 xsi 的命名空间，其值为一个标准的命名空间。

    此命名空间还定义了 xsi:type, xsi:nil, xsi:schemaLocation 等属性。

    可以看成是`约定俗成的固定写法`。

3.  xsi:schemaLocation

    此为 xsi 命名空间中定义的一个属性，用于通知 xml 处理器 xml 文档与 xsd 文件的关联关系。

    命名空间和命名空间的 xsd (xml schema definition，定义了命名空间下元素的写法) 必须成对出现，中间用空格分隔，可以填写多对对应关系。

    可以看成是`约定俗成的固定写法`。



### 6. Maven 中 `<version>`的一些写法

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <spring.version>5.2.6.RELEASE</spring.version>
</properties>

<version>LATEST</version> : 使用最新的 jar 包
<version>RELEASE</version>: 使用最新且稳定的 jar 包
<version>1.3.5</version>  : 使用 1.3.5 版本
<version>${spring.version}</version>: 使用 properties 中定义的版本
```

推荐使用 `<version>${spring.version}</version>`这种写法。

-   可以统一管理依赖版本，尤其是在多模块项目中，可以避免同一个依赖引入多个版本
-   尽量使用固定版本号，避免某些依赖的不兼容升级带来的影响