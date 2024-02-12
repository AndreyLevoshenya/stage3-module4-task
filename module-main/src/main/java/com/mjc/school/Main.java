package com.mjc.school;

import com.mjc.school.helper.Command;
import com.mjc.school.helper.CommandSender;
import com.mjc.school.helper.MenuHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
//        Scanner scanner = new Scanner(System.in);
//
//        MenuHelper menuHelper = applicationContext.getBean(MenuHelper.class);
//        CommandSender commandSender = applicationContext.getBean(CommandSender.class);
//
//        while (true) {
//            try {
//                menuHelper.printMenu();
//                String key = scanner.nextLine();
//                if (key.equals("0")) {
//                    System.exit(0);
//                }
//
//                Command command = menuHelper.getCommand(key, scanner);
//                Object o = commandSender.send(command);
//                if (o instanceof Iterable iterable) {
//                    iterable.forEach(System.out::println);
//                } else {
//                    System.out.println(o);
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
    }
}
