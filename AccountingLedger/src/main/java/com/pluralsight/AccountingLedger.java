package com.pluralsight;
// always import what you need!!
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

public class AccountingLedger {
    //create objects, static so its global and can be used everywhere
    static Scanner scanner = new Scanner(System.in); // scanner object for the user's input
    static BufferedWriter bufferedWriter;
    static BufferedReader bufferedReader;
    // static variables!!!
    static LocalDate currentDate = LocalDate.now();
    static int currentMonth = currentDate.getMonthValue();
    static int currentYear = currentDate.getYear();
    static ArrayList<String> entries = new ArrayList<>();

    public static void main(String[] args) {
        homeScreen();
    }

    public static void homeScreen() {
        // display home screen
        System.out.println("D- Add Deposit");
        System.out.println("P- Make a Payment (Debit)");
        System.out.println("L- Ledger");
        System.out.println("X- Exit");

        String userChoice = scanner.nextLine(); // get the user's choice/input
        scanner.nextLine();

        if (userChoice.equalsIgnoreCase("D")) { // if user chooses "D"
            toCsv(addDeposit());
            homeScreen(); // after that send them back to home screen
        } else if (userChoice.equalsIgnoreCase("P")) { // if user chooses "P"
            toCsv(addPayment());
            homeScreen(); // back to home screen
        } else if (userChoice.equalsIgnoreCase("L")) { // if user chooses "L"
            ledgerMenu();  // sends user to the ledger menu
        } else if (userChoice.equalsIgnoreCase("X")) { // if user chooses "X"
            System.out.println("Have a good one, bye!!!"); // goodbye greeting!!
            scanner.close(); // consumes next line

            // what if the user puts something else?
        } else {
            System.out.println("Please try again, not a valid choice"); // prompts user to put in correct input
            homeScreen(); // sends user to home screen
        }
    }

    // create a method to write to the cvs file
    public static void toCsv(String action) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String input = (date + "|" + time.format(DateTimeFormatter.ofPattern("HH;mm;ss")) + "|" + action + "\n");

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
            bufferedWriter.write(input);
            // close the writer to release resources
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String addDeposit() {
        //prompt user for deposit info
        System.out.println("Please enter the deposit information ");
        System.out.println("Please describe the deposit ");
        String description = scanner.nextLine();
        System.out.println("Who is the vendor ");
        String vendor = scanner.nextLine();
        System.out.println("Deposit Amount:");
        float amount = scanner.nextFloat();
        scanner.nextLine();
        return (description + "|" + vendor + "|" + amount);
    }

    public static String addPayment() {
        System.out.println("Please enter the payment information");
        System.out.println("Please describe the purchased product");
        String product = scanner.nextLine();
        System.out.println("Who is the vendor");
        String vendor = scanner.nextLine();
        System.out.println("Please enter amount of the product as (- negative number)");
        float amount = scanner.nextFloat();
        if (amount > 0) {
            System.out.println("Oops, not a valid input try again");
            homeScreen();
        }
        scanner.nextLine();
        return (product + "|" + vendor + "|" + amount);

    }


    public static void ledgerMenu() {
        System.out.println("A- All");
        System.out.println("D- Deposits");
        System.out.println("P- Payments");
        System.out.println("R- Reports");
        System.out.println("H- Home");

        String userChoice = scanner.nextLine();
        scanner.nextLine();

        if (userChoice.equalsIgnoreCase("A")) {
            allEntries();
            ledgerMenu();
        } else if (userChoice.equalsIgnoreCase("D")) {
            viewDeposits();
            ledgerMenu();
        } else if (userChoice.equalsIgnoreCase("P")) {
            viewPayments();
            ledgerMenu();
        } else if (userChoice.equalsIgnoreCase("R")) {
            viewReports();
        } else if (userChoice.equalsIgnoreCase("H")) {
            homeScreen();
        } else {
            System.out.println("Oops not a valid input, try again!");
            ledgerMenu();
        }
    }

    public static void allEntries() {
        String input;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                System.out.println(input);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void viewDeposits() {
        String input;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                if (Float.parseFloat(tokens[4]) > 0) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void viewPayments() {
        String input;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                if (Float.parseFloat(tokens[4]) > 0) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sortArray(ArrayList<String> product) {
        Collections.reverse(product);
        for (String item : product) {
            System.out.println(item);
        }
        product.clear();
    }

    public static void viewReports() {
        System.out.println("1- Month To Date");
        System.out.println(" 2- Previous Month");
        System.out.println(" 3- Year To Date");
        System.out.println("4- Previous Year");
        System.out.println("5- Search by Vendor");
        System.out.println("6- Go Back");

        int userChoice = scanner.nextInt();
        scanner.nextLine();

        if (userChoice == 1) {
            monthToDate();
            viewReports();
        } else if (userChoice == 2) {
            previousMonth();
            viewReports();
        } else if (userChoice == 3) {
            yearToDate();
            viewReports();
        } else if (userChoice == 4) {
            previousYear();
            viewReports();
        } else if (userChoice == 5) {
            searchByVendor();
            viewReports();
        } else if (userChoice == 6) {
            ledgerMenu();
        } else {
            System.out.println("Oops! Not a valid input,try again");
            viewReports();
        }
    }
    public static void monthToDate() {
        String input;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                String[] date = tokens[0].split("-");
                if (Integer.parseInt(date[1]) == currentMonth && Integer.parseInt(date[0]) == currentYear) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void previousMonth() {
        String input;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                String[] date = tokens[0].split("-");
                if (Integer.parseInt(date[0]) == currentYear) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void yearToDate() {
        String input;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                String[] date = tokens[0].split("-");
                if (Integer.parseInt(date[1]) == currentMonth && Integer.parseInt(date[0]) == currentYear) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void previousYear() {
        String input;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                String[] date = tokens[0].split("-");
                if (Integer.parseInt(date[0]) == currentYear-1) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void searchByVendor() {
        // ask user for name the vendor
        System.out.println("Please enter name of the vendor: ");
        String vendor = scanner.nextLine();
        String input;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");
                if (tokens[3].equalsIgnoreCase(vendor)) {
                    entries.add(input);
                }
            }
            sortArray(entries);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

