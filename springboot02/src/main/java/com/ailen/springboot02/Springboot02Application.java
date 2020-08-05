package com.ailen.springboot02;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * 指定所扫的包，会自动扫描指定包下的全部标有@Component的类，并注册成bean，
 * 当然包括@Component下的子注解@Service,@Repository,@Controller。
 */
@SpringBootApplication(scanBasePackages = {"com.ailen.springboot02"})
@MapperScan("com.ailen.springboot02.mapper")
public class Springboot02Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot02Application.class, args);
    }

    /**
     * springboot 2.x 以上版本；特殊字符乱码问题解决
     * https://blog.csdn.net/qq_44593353/article/details/105875436
     * @return
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(( TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }


}
