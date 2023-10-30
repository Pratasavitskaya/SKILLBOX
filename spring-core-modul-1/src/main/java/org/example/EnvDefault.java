package org.example;

import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class EnvDefault implements EnvPrinter{
    @Value("${app.env}")
    private String env;
    @Override
    public Map<String,Contacts> printEnv() {
        System.out.println("default work");
        return new HashMap<>();
    }
}
