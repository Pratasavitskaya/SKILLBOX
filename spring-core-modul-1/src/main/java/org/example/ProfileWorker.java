package org.example;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProfileWorker {
    private final EnvPrinter envPrinter;

    public ProfileWorker(EnvPrinter envPrinter) {
        this.envPrinter = envPrinter;
    }
    public Map<String, Contacts> doWork(){
        return envPrinter.printEnv();
    }
}
