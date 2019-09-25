import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import source.DatabaseSource;
import source.EmailSource;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String RED_COLOR = "\u001B[31m";
    private static final String GREEN_COLOR = "\u001B[35m";
    private static final String ANOTHER_COLOR = "\u001B[33m";
    private static final String YELLOW_COLOR = "\u001B[32m";
    private static Scanner scanner;

    public static int generaterandomNumber(int start, int end){
        Random random = new Random();
        int randomNumber = random.nextInt((end-start)+1)+start;
        return randomNumber;
    }

    public static void printMenu(){

        System.out.println(GREEN_COLOR+"List of available options:\n" +
                "press\n" +
                "1 - print Menu\n" +
                "2 - register new account\n" +
                "3 - get account number\n" +
                "4 - get last balance\n" +
                "5 - deposit money\n" +
                "6 - withdrawal money\n" +
                "7 - send any email\n" +
                "8 - send account data to my email\n" +
                "9 - exit");
    }


    public static void main(String[] args) {
        DatabaseSource databaseSource = new DatabaseSource();
        databaseSource.connectDB();
        if(!databaseSource.connectDB()){
            System.out.println("Something went wrong. Please try again.");
        }else{
            databaseSource.createTable();
            System.out.println(YELLOW_COLOR+"Welcome to the ATM of TUNDRA BANK!");
            printMenu();
            boolean quit = false;
            while (!quit) {

                scanner = new Scanner(System.in);
                int option = scanner.nextInt();
                if(option>=1&&option<=9){
                switch (option) {
                    case 1:
                        printMenu();
                        break;
                    case 2:
                        System.out.println(ANOTHER_COLOR+"Please enter bellow your FIRST NAME:");
                        Scanner scanner1 = new Scanner(System.in);
                        String userNname = scanner1.nextLine();

                        scanner.nextLine();
                        System.out.println(ANOTHER_COLOR+"Please create your PASSWORD and enter bellow:");
                        int password = scanner.nextInt();
                        int accountNumber = generaterandomNumber(23456,65432);

                        System.out.println(ANOTHER_COLOR+"Please enter bellow your EMAIL:");
                        String userEmail = scanner1.nextLine();
                        String subj = "Congrats! Your account was created successfully!";
                        String accountDetails = "'name': " + userNname + ",\n'account number': " + accountNumber + ",\n'password': " + password + ",\n'email': " + userEmail;

                        databaseSource.addNewUser(userNname, accountNumber, 0.00, password, userEmail);
                        EmailSource.sendEmail(userEmail,subj,accountDetails);

                        break;
                    case 3:
                        System.out.println(ANOTHER_COLOR+"Please enter bellow your PASSWORD:");
                        int number = scanner.nextInt();
                        scanner.nextLine();
                        databaseSource.getAccountNumber(number);
                        break;
                    case 4:
                        System.out.println(ANOTHER_COLOR+"Please enter bellow your PASSWORD:");
                        int passw5 = scanner.nextInt();
                        databaseSource.getLastBalance(passw5);
                        break;
                    case 5:
                        System.out.println(ANOTHER_COLOR+"Please enter bellow your PASSWORD:");
                        int passw1 = scanner.nextInt();
                        String email = databaseSource.getEmail(passw1);
                        if(!email.equalsIgnoreCase("not found")) {
                            scanner.nextLine();
                            System.out.println(ANOTHER_COLOR + "Please enter bellow AMOUNT of depositing:");
                            double amount = scanner.nextDouble();
                            databaseSource.depositMoney(passw1, amount);
                            System.out.println(GREEN_COLOR + "You have been successfully deposited $ " + amount);
                        }else{
                            System.out.println(RED_COLOR+"Not found data. Please check PASSWORD and TRY again...");
                        }
                        break;
                    case 6:
                        System.out.println(ANOTHER_COLOR+"Please enter bellow your PASSWORD:");
                        int passw2 = scanner.nextInt();
                        String email22 = databaseSource.getEmail(passw2);
                        if(!email22.equalsIgnoreCase("not found")) {
                            scanner.nextLine();
                            System.out.println(ANOTHER_COLOR + "Please enter bellow AMOUNT of withdrawing:");
                            double amount2 = scanner.nextDouble();
                            databaseSource.withdrawalMoney(passw2, amount2);
                        }else{
                            System.out.println(RED_COLOR+"Not found data. Please check PASSWORD and TRY again...");
                        }
                        break;
                    case 7:
                        Scanner scanner2 = new Scanner(System.in);
                        System.out.println("Please provide bellow recipient's EMAIL:");
                        String toAddr = scanner2.nextLine();
                      //  scanner2.nextLine();
                        System.out.println("Please provide header for your email:");

                        String subject = scanner2.nextLine();
                    //    scanner2.nextLine();
                        System.out.println("Please provide text for your email:");

                        String text = scanner2.nextLine();

                        EmailSource.sendEmail(toAddr, subject,text);
                    //    System.out.println("The email is sending... Please wait.");
                       // scanner2.close();
                        break;
                    case 8:
                        System.out.println(ANOTHER_COLOR+"Please enter bellow your PASSWORD:");
                        int passw9 = scanner.nextInt();
                        String email11 = databaseSource.getEmail(passw9);
                        if(!email11.equalsIgnoreCase("not found")) {
                            double balance2 = databaseSource.getDataBalance(passw9);
                            String subjectData = "Secure data";
                            String account_data = "Your account data:\n'email': " + email11 + ",\n'password': " + passw9 + ",\n'balance': $ " +
                                    "" + balance2;
                            EmailSource.sendEmail(email11, subjectData, account_data);
                        }else {
                            System.out.println(RED_COLOR+"Not found data. Please check PASSWORD and TRY again...");
                        }
                        break;
                    case 9:
                        quit=true;
                        break;
                    }
                }else{
                    System.out.println(RED_COLOR+"No such option is available...");
                }
            }
        }

        databaseSource.closeDB();
    }
}
