### @Value

* 所在的类需要加入Bean容器中
* 读取的属性在配置中必须要有，否则报错
* 无法注入到 static 或 final 修饰的变量

> 缺点：只能单个属性赋值

```java
@SpringBootTest
class ValueTests {
    @Value("${xushu.name}")
    private String name;
}
```

```yaml
xushu:
  name: 元直
```

* 可以加默认值

```java
@SpringBootTest
class ValueTests {
    @Value("${xushu.name:默认值}")
    //@Value("${xushu.name:}")
    private String name;
}
```

### @ConfigurationProperties

* 可根据配置文件中属性的前缀批量赋值

```java
@ConfigurationProperties(prefix = "xushu")
@SpringBootTest
public class ConfigurationPropertiesTests {
    private String name;
    
}
```

```java
xushu:
  name: 元直
  id: 1
```

### Environment类

* 使用@Resource自动装配Environment
* 动态获取配置

```java
@SpringBootTest
@Slf4j
class EnvironmentTests{
   @Resource
   private Environment env;
    
   @Test
    public void varTest() {
        String var1 = env.getProperty("xushu.name");
        // 逻辑处理...
        log.info("Environment 配置获取 {}", var1)
    }
}
```

```yaml
xushu:
  name: 元直
  id: 1
  beanClass: bean1
  isEnabed
```

* 实现接口 EnvironmentAware 重写 setEnvironment
* 常用在基于SpringBoot的插件式的开发，自定义的 starter 或者 自定义的配置类（降低耦合性）

```java
@SpringBootTest
@Slf4j
class EnvironmentTests implements EnvironmentAware {
    //@Resource
    private Environment env;
    
    @Override
    public void setEnvironment(Environment environment){
        this.env = environment;
    }
   
}
```

### @PropertySources

* 加载自定义的外部配置文件（properties）属性

```java
@PropertySources({
    @PropertySource(value = "classpath:xushu.properties", encoding = "utf-8")
})
```

* 配置Bean（PropertySourcesPlaceholderConfigurer），加载自定义的yaml后缀配置文件

```java
@Configuration
public class MyYamlConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer yamlConfigurer(){
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertyFactoryBean yaml = new YamlPropertyFactoryBean();
        yaml.setResources(new ClassPathResource("xushu.yaml"))
        configurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return configurer;
    }
}
```

### Java原生态Properties

```java
@SpringBootTest
public class CustomTest {
    @Test
    public void cunstomTest() {
        Properties props = new Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
            	this.getClass().getClassLoader().getResourceAsStream("xushu.properties"),
                StandardCharsets.UTF_8);
            props.load(inputStreamReader);
        } catch (IOException e1) {
            System.out.println("Properties Name: " + props.getProperty("zhouyu.name"));
        }
    }
}
```

























