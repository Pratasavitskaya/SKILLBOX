package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class WorkWithFile {

    @Value("${app.file}")
    private  String pathFile ;


    public  void writeToFile(Map<String,Contacts> mapContacts) throws IOException {
        
        try(FileWriter writer = new FileWriter(pathFile, false))
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
//    public Map<String,Contacts> readFile(){
//
//        Contacts contact;
//        Map<String,Contacts> mapContacts = new HashMap<>();
//        try {
//
//            File file = new File(pathFile);
//
//            FileReader fr = new FileReader(file);
//
//            BufferedReader reader = new BufferedReader(fr);
//
//            String line = reader.readLine();
//            String[] info = line.split(";");
//            contact=new Contacts(info[0],info[1],info[2]);
//            mapContacts.put(info[2],contact);
//            while (line != null) {
//                info = line.split(";");
//                contact=new Contacts(info[0],info[1],info[2]);
//                mapContacts.put(info[2],contact);
//                line = reader.readLine();
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return mapContacts;
//    }
}
