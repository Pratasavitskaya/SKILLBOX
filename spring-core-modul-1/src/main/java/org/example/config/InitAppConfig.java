package org.example.config;

import org.example.EnvInit;
import org.example.EnvPrinter;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application-init.properties")
@Profile("init")
public class InitAppConfig {
    @Bean
    public EnvInit envPrinter(){
        return new EnvInit();
    }
}
