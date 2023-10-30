package org.example;

import org.apache.commons.lang3.StringUtils;
import org.example.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static InputStream inputStream = System.in;
    private static Reader inputStreamReader = new InputStreamReader(inputStream);
    private static BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    private static Map<String, Contacts> mapContacts = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        mapContacts = context.getBean(ProfileWorker.class).doWork();

        showMenu();
    }

    public static void showMenu() throws IOException {
        String num = "";
        do {

            System.out.println("Enter number for work");
            System.out.println("Create contact: 1 ");
            System.out.println("view contacts: 2");
            System.out.println("remove contact: 3 ");
            System.out.println("save to file:4");
            System.out.println("exit: 5 ");
            num = bufferedReader.readLine();

            switch (num) {
                case "1":
                    createContact();
                    break;
                case "2":
                    viewContacts();
                    break;
                case "3":
                    removeContact();
                    break;
                case "4":
                    writeToFile();
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } while (!num.equals("5"));
        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();
    }

    private static void writeToFile() {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            context.getBean(WorkWithFile.class).writeToFile(mapContacts);

        } catch (IOException e) {
            System.out.println("error writing to file");

        }
    }

    private static void removeContact() throws IOException {
        System.out.println("enter email for remove:");
        String email = bufferedReader.readLine();
        if (mapContacts != null && !mapContacts.isEmpty()) {
            if (mapContacts.get(email) != null) {
                mapContacts.remove(email);
                System.out.println("Contact with email:" + email + " was deleted");
            } else System.out.println("error Email ");
        } else System.out.println("no saved contacts");

    }

    private static void viewContacts() throws IOException {
        if (mapContacts != null && !mapContacts.isEmpty()) {

            for (Map.Entry<String, Contacts> entry : mapContacts.entrySet()) {
                System.out.println(entry.getValue().toString());
            }
        } else System.out.println("no contacts");

    }

    public static void createContact() throws IOException {

        Contacts contact = new Contacts();
        System.out.println("Enter contact:<FIO>;<number phone>;<email>");
        String str = bufferedReader.readLine();
        String[] info = str.split(";");
        if (info.length == 3) {

            contact.setFullName(info[0]);

            if (StringUtils.isNumeric(info[1].substring(1)) && info[1].charAt(0) == '+')
                contact.setPhoneNumber(info[1]);
            else {
                System.out.println("error phone number");

            }

            if (emailValidator(info[2]) == true) contact.setEmail(info[2]);
            else {
                System.out.println("error email");

            }
        } else {
            System.out.println("error contact");

        }
        mapContacts.put(contact.getEmail(), contact);
        System.out.println("created contact:" + contact.toString());
        contact = new Contacts();


    }

    public static boolean emailValidator(String email) {
        String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        if (email == null) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

}