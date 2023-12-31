package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EnvInit implements EnvPrinter{
    @Value("${app.env}")
    private String env;
    @Value("${app.file}")
    Resource resourceFile;

    @Override
    public  Map<String,Contacts> printEnv() {

        System.out.println("init work");
            Contacts contact;
            Map<String,Contacts> mapContacts = new HashMap<>();
            try {
               File file = resourceFile.getFile();

                FileReader fr = new FileReader(file);

                BufferedReader reader = new BufferedReader(fr);

                String line = reader.readLine();
                if (line!=null && !line.isEmpty()) {
                    String[] info = line.split(";");
                    contact = new Contacts(info[0], info[1], info[2]);
                    mapContacts.put(info[2], contact);
                    while (line != null) {
                        info = line.split(";");
                        contact = new Contacts(info[0], info[1], info[2]);
                        mapContacts.put(info[2], contact);
                        line = reader.readLine();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mapContacts;
        }


}
