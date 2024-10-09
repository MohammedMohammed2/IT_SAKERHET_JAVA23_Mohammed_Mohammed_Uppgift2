package org.example;

import org.example.RequestCalls.CallServer;

import java.net.URISyntaxException;
import java.util.Scanner;

public class Menu {

    private static final Scanner sc = new Scanner(System.in);
    private static String email;

    private static boolean isLoggedIn = false;
    private static String password;
    private static String role;
    private static boolean changeMenu = true;

    public static void menu() {
        while (changeMenu) {
            if (!isLoggedIn) {
                loggedOutMenu();
            } else {
                loggedInMenu();
            }
        }
    }

    private static boolean login() {
        System.out.print("Enter your email: ");
        email = sc.next();
        System.out.print("Enter you password: ");
        password = sc.next();
        role= String.valueOf(Role.USER);


        return CallServer.loginUser(email,password,role);
    }



    private static void register() {
        System.out.print("Enter your email: ");
        String email = sc.next();
        System.out.print("Enter you password: ");
        String password = sc.next();
        String role= String.valueOf(Role.USER);

        try {
            CallServer.registerUser(email, password,role);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private static void loggedOutMenu() {
        System.out.println("--------------------");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                isLoggedIn = login();
                break;
            case 2:
                register();
                break;
            case 3:
                changeMenu = false;
        }
    }
    private static void loggedInMenu(){
        System.out.println("------------LoggedIN-----------");
        System.out.println("1.Send Message");
        System.out.println("2.See Message");
        System.out.println("Enter your choice:");

        int choice = sc.nextInt();

        switch (choice){
            case 1:
                sendMessage();
                break;
            case 2:
                seeMessage();
                break;
        }
    }
    private static void sendMessage(){
        System.out.println("------------SendMessage-----------");
        System.out.println("write any message:");
        String messageSend = sc.next();
        CallServer.sendMessageCall(messageSend);
    }
    private static void seeMessage(){

    }
}
