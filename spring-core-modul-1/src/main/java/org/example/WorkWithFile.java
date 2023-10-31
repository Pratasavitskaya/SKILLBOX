package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WorkWithFile {

    @Value("${app.fileToSave}")
    private  String pathFileToSave ;


    public  void writeToFile(Map<String,Contacts> mapContacts) throws IOException {

        try(FileWriter writer = new FileWriter(pathFileToSave, false))
        {
            for(Map.Entry<String, Contacts> entry : mapContacts.entrySet()){

                writer.write(entry.getValue().toString());
                writer.append('\n');

            }
            writer.flush();
            System.out.println("Contacts added to the file");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }
}
