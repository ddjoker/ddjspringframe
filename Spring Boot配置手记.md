## spring boot依赖


### parent依赖模式
```xml
   <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.4.RELEASE</version>
   </parent>
```

1. 默认编译版本为java 1.6
2. 默认项目编码为UTF-8
3. 允许您省略常见依赖关系的`<version>`标记,此时默认使用POM中的版本.
4. 使用默认的 [资源管理配置](https://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html)
5. 使用默认的插件管理配置(exec plugin, surefire, Git commit ID, shade).
6. Sensible resource filtering for application.properties and application.yml including profile-specific files (e.g. application-foo.properties and application-foo.yml)
7. 由于默认的配置文件接受Spring样式占位符（`${...}`），Maven过滤更改为使用`@...@`占位符(你可以使用Maven的`resource.delimiter`进行更改)
8. 你可以使用maven中的`<properties>`来进行指定使用springboot中的特定包的版本(eg:`<spring-data-releasetrain.version>Fowler-SR2</spring-data-releasetrain.version>`)

### pom依赖模式
```xml
<dependencyManagement>
  <dependencies>
    <!-- pom start -->
    <dependency>
      <!-- Import dependency management from Spring Boot -->
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>1.5.4.RELEASE</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
    <!-- pom end -->
  </dependencies>
</dependencyManagement>
```
略

## spring boot MAVEN插件包
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

## spring boot 启动注解

```java

@SpringBootApplication
@ComponentScan(basePackages = "com.ddjoker.ddjframe")
public class SpringFrameRunner {

  public static void main(String[] args) {
    SpringApplication.run(SpringFrameRunner.class);
  }
}

```

1. 使用`@SpringBootApplication`已代表使用默认配置启用了`@Configuration`,`@EnableAutoConfiguration`和`@ComponentScan`
2. 如果需要使用自定义配置的`@EnableAutoConfiguration`和`@ComponentScan`可以额外进行配置.

## debug
```
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n \
       -jar target/myproject-0.0.1-SNAPSHOT.jar
```
## Developer tools for quick restart

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional> <!-- true 不会传递到引用此项目的子项目中 ,并且不会被打包-->
</dependency>
```

1. 使用spring-boot-devtools的应用程序将在类路径上的文件发生更改时自动重新启动
2. 某些资源（如静态资源和视图模板）不需要重新启动应用程序.(eg :
   `spring.devtools.restart.exclude=static/**,public/**`)
3. IDEA 的触发条件是编译程序(`Build -> build Project`)
4. DevTools relies on the application context’s shutdown hook to close it during a restart. It will not work correctly if you have disabled the shutdown hook ( SpringApplication.setRegisterShutdownHook(false)).
5. 关闭自动重启`spring.devtools.restart.enabled`/` System.setProperty("spring.devtools.restart.enabled", "false");`

[more detail](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools)

[Remote debug tunnel](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools-remote-debugtunnel)

## spring boot 配置说明
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config


##  Custom JSON Serializers and Deserializers
```java

import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.boot.jackson.*;

@JsonComponent
public class Example {

    public static class Serializer extends JsonSerializer<SomeObject> {
        // ...
    }

    public static class Deserializer extends JsonDeserializer<SomeObject> {
        // ...
    }

}
```

1. spring默认使用jackson 来进行JSON处理`@JsonComponent`的类.


## MessageCodesResolver

1. `spring.mvc.message-codes-resolver.format.PREFIX_ERROR_CODE`
2. `DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE`

##  Static Content

```properties
spring.mvc.static-path-pattern=/resources/**
```

To use cache busting, the following configuration will configure a cache
busting solution for all static resources, effectively adding a content
hash in URLs, such as `<link
href="/css/spring-2a2d595e6ed9a0b24f027f2b63b134d6.css"/>`:

```properties
    spring.resources.chain.strategy.content.enabled=true
    spring.resources.chain.strategy.content.paths=/**
```
When loading resources dynamically with, for example, a JavaScript module loader, renaming files is not an option. That’s why other strategies are also supported and can be combined. A "fixed" strategy will add a static version string in the URL, without changing the file name:

```properties
    spring.resources.chain.strategy.content.enabled=true
    spring.resources.chain.strategy.content.paths=/**
    spring.resources.chain.strategy.fixed.enabled=true
    spring.resources.chain.strategy.fixed.paths=/js/lib/
    spring.resources.chain.strategy.fixed.version=v12
```
With this configuration, JavaScript modules located under` "/js/lib/" `will use a fixed versioning strategy `"/v12/js/lib/mymodule.js"` while other resources will still use the content one `<link href="/css/spring-2a2d595e6ed9a0b24f027f2b63b134d6.css"/>`.

## ConfigurableWebBindingInitializer

Spring MVC uses a `WebBindingInitializer` to initialize a `WebDataBinder` for a particular request. If you create your own `ConfigurableWebBindingInitializer` `@Bean`, Spring Boot will automatically configure Spring MVC to use it.

##  Error Handling

1. 默认的 Spring boot 提供一个`/error`
   用于获取所有全局的错误信息.在客户端,会提供一个JSON数据用于显示所有error/HTTP
   status 详细信息.
   浏览器会加载一个空的视图来渲染这些错误信息(你可以添加一个`view`来处理错误信息界面).
2. 实现`ErrorController`来取代默认行为,或者简单地添加一个类型为`ErrorAttributes`的`bean`在沿用现有(错误提供)机制的基础上进行返回内容的更改。

        `BasicErrorController`作为`ErrorController`的基础实现.如果要添加新内容类型(eg:text/json)的处理程序（会默认将其他内容类型处理为text/html），
        只需要继承`BasicErrorController`并提供一个使用`@RequestMapping` that has a produces attribute


You can also define a @ControllerAdvice to customize the JSON document to return for a particular controller and/or exception type.

```java
@ControllerAdvice(basePackageClasses = FooController.class)
public class FooControllerAdvice extends ResponseEntityExceptionHandler {
``
    @ExceptionHandler(YourException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(new CustomErrorType(status.value(), ex.getMessage()), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
```
In the example above, if YourException is thrown by a controller defined in the same package as FooController, a json representation of the CustomerErrorType POJO will be used instead of the ErrorAttributes representation.

## custom error page

map 404 to a static HTML file, your folder structure would look like this:

```
src/
 +- main/
     +- java/
     |   + <source code>
     +- resources/
         +- public/
             +- error/
             |   +- 404.html
             +- <other public assets>
```
### without mvc error page
For applications that aren’t using Spring MVC, you can use the `ErrorPageRegistrar` interface to directly register ErrorPages.

##  Spring HATEOAS

If you’re developing a `RESTful` API that makes use of `hypermedia`,
Spring Boot provides auto-configuration for Spring HATEOAS that works
well with most applications. The auto-configuration replaces the need
to use `@EnableHypermediaSupport` and registers a number of beans to ease
building hypermedia-based applications including a `LinkDiscoverers`
(for client side support) and an ObjectMapper configured to correctly
marshal responses into the desired representation. The `ObjectMapper`
will be customized based on the` spring.jackson.* `properties or a
`Jackson2ObjectMapperBuilder` bean if one exists.

You can take control of Spring HATEOAS’s configuration by using` @EnableHypermediaSupport.`
Note that this will disable the `ObjectMapper` customization described above.


## CORS support

```java
@Configuration
public class MyConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**");
            }
        };
    }
}
```

## JAX-RS and Jersey
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-jersey

## OAuth2

If you have spring-security-oauth2 on your classpath you can take
advantage of some auto-configuration to make it easy to set up
Authorization or Resource Server. For full details, see the [Spring
Security OAuth 2 Developers Guide](http://projects.spring.io/spring-security-oauth/docs/oauth2.html) .

## Working with SQL databases
The Spring Framework provides extensive support for working with SQL
databases. From direct JDBC access using `JdbcTemplate` to complete
‘object relational mapping’ technologies such as Hibernate. Spring
Data provides an additional level of functionality, creating `Repository`
implementations directly from interfaces and using conventions to
generate queries from your method names.

If you use the `spring-boot-starter-jdbc` or
s`pring-boot-starter-data-jpa` 'starters' you will automatically get a
dependency to tomcat-jdbc.

1. DataSource configuration is controlled by external configuration
properties in `spring.datasource.*`. For example, you might declare the
following section in `application.properties`:

``` properties
spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=dbuser spring.datasource.password=dbpass
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```


2. The `spring.datasource.jndi-name` property can be used as an
alternative to the `spring.datasource.url`, `spring.datasource.username` and
`spring.datasource.password `properties to access the DataSource from a
specific JNDI location. For example, the following section in
`application.properties` shows how you can access a JBoss AS defined
DataSource:
``` properties
spring.datasource.jndi-name=java:jboss/datasources/customers
```

## Using JdbcTemplate
Spring’s `JdbcTemplate` and `NamedParameterJdbcTemplate` classes are
auto-configured and you can `@Autowire` them directly into your own
beans:
``` java
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.jdbc.core.JdbcTemplate;
   import org.springframework.stereotype.Component;
   
   @Component
   public class MyBean {
   
       private final JdbcTemplate jdbcTemplate;
   
       @Autowired
       public MyBean(JdbcTemplate jdbcTemplate) {
           this.jdbcTemplate = jdbcTemplate;
       }
   
       // ...
   
   }
```


##  Working with NoSQL technologies

Spring Data provides additional projects that help you access a variety
of NoSQL technologies including `MongoDB`, `Neo4J`, `Elasticsearch`, `Solr`,
`Redis`, `Gemfire`, `Cassandra`, `Couchbase` and `LDAP`. Spring Boot provides
auto-configuration for `Redis`, `MongoDB`, `Neo4j`, `Elasticsearch`, `Solr`
`Cassandra`, `Couchbase` and `LDAP`; you can make use of the other projects,
but you will need to configure them yourself. Refer to the appropriate
reference documentation at `projects.spring.io/spring-data.`

### Redis

Redis is a cache, message broker and richly-featured key-value store.
Spring Boot offers basic auto-configuration for the `Jedis` client library
and abstractions on top of it provided by `Spring Data Redis`. There is a
`spring-boot-starter-data-redis` ‘Starter’ for collecting the
dependencies in a convenient way.

#### Connecting to Redis

You can inject an auto-configured `RedisConnectionFactory`,
`StringRedisTemplate` or vanilla `RedisTemplate` instance as you would any
other Spring Bean. By default the instance will attempt to connect to a
Redis server using `localhost:6379`:
```   java
     @Component
     public class MyBean {
     
         private StringRedisTemplate template;
     
         @Autowired
         public MyBean(StringRedisTemplate template) {
             this.template = template;
         }
     
         // ...
     
     }
```
If you add a `@Bean` of your own of any of the auto-configured types it
will replace the default (except in the case of `RedisTemplate` the
exclusion is based on the bean name ‘redisTemplate’ not its type). If
`commons-pool2` is on the classpath you will get a pooled connection
factory by default.

### MONGODB
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-mongodb


## 30.5 Solr
Apache Solr is a search engine. Spring Boot offers basic
auto-configuration for the `Solr 5` client library and abstractions on top
of it provided by Spring Data Solr. There is a
`spring-boot-starter-data-solr` ‘Starter’ for collecting the
dependencies in a convenient way.

### 30.5.1 Connecting to Solr

You can inject an auto-configured `SolrClient` instance as you would any
other Spring bean. By default the instance will attempt to connect to a
server using `localhost:8983/solr`:
``` java
@Component
public class MyBean {

    private SolrClient solr;

    @Autowired
    public MyBean(SolrClient solr) {
        this.solr = solr;
    }

    // ...

}
```
If you add a `@Bean` of your own of type `SolrClient` it will replace the default.

### 30.5.2 Spring Data Solr repositories

Spring Data includes repository support for Apache Solr. As with the JPA
repositories discussed earlier, the basic principle is that queries are
constructed for you automatically based on method names.

In fact, both Spring Data  JPA and Spring Data Solr share the same common
infrastructure; so you could take the JPA example from earlier and,
assuming that City is now a `@SolrDocument` class rather than a JPA
`@Entity`, it will work in the same way.

## 30.6 Elasticsearch
Elasticsearch is an open source, distributed,
real-time search and analytics engine. Spring Boot offers basic
auto-configuration for the Elasticsearch and abstractions on top of it
provided by `Spring Data Elasticsearch`. There is a
`spring-boot-starter-data-elasticsearch `‘Starter’ for collecting the
dependencies in a convenient way. Spring Boot also supports Jest.

### 30.6.1 Connecting to Elasticsearch using Jest

If you have Jest on the classpath, you can inject an auto-configured
JestClient targeting `localhost:9200` by default. You can further tune how
the client is configured:
``` properties
spring.elasticsearch.jest.uris=http://search.example.com:9200
spring.elasticsearch.jest.read-timeout=10000
spring.elasticsearch.jest.username=user
spring.elasticsearch.jest.password=secret
```
You can also register an arbitrary number of beans implementing
`HttpClientConfigBuilderCustomizer` for more advanced customizations. The
example below tunes additional HTTP settings:
```java
static class HttpSettingsCustomizer implements HttpClientConfigBuilderCustomizer {

    @Override
    public void customize(HttpClientConfig.Builder builder) {
        builder.maxTotalConnection(100).defaultMaxTotalConnectionPerRoute(5);
    }

}
```
To take full control over the registration, define a `JestClient` bean.

### 30.6.2 Connecting to Elasticsearch using Spring Data

You can inject an auto-configured ElasticsearchTemplate or Elasticsearch
Client instance as you would any other Spring Bean. By default the
instance will embed a local in-memory server (a Node in Elasticsearch
terms) and use the current working directory as the home directory for
the server. In this setup, the first thing to do is to tell
Elasticsearch where to store its files:

spring.data.elasticsearch.properties.path.home=/foo/bar
Alternatively, you can switch to a remote server (i.e. a TransportClient) by setting spring.data.elasticsearch.cluster-nodes to a comma-separated ‘host:port’ list.

spring.data.elasticsearch.cluster-nodes=localhost:9300
@Component
public class MyBean {

    private ElasticsearchTemplate template;

    @Autowired
    public MyBean(ElasticsearchTemplate template) {
        this.template = template;
    }

    // ...

}
If you add a @Bean of your own of type ElasticsearchTemplate it will replace the default.

30.6.3 Spring Data Elasticsearch repositories

Spring Data includes repository support for Elasticsearch. As with the JPA repositories discussed earlier, the basic principle is that queries are constructed for you automatically based on method names.

In fact, both Spring Data JPA and Spring Data Elasticsearch share the same common infrastructure; so you could take the JPA example from earlier and, assuming that City is now an Elasticsearch @Document class rather than a JPA @Entity, it will work in the same way.

[Tip]
For complete details of Spring Data Elasticsearch, refer to their reference documentation.
