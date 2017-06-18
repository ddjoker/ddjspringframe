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


