package org.example.config;

import org.example.EnvDefault;
import org.example.EnvInit;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@PropertySource("classpath:application-default.properties")
@Profile("default")
public class DefaultAppConfig {
    @Bean
    public EnvDefault envPrinter(){
        return new EnvDefault();
    }
}
