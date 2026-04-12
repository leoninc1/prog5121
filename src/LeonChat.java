  // Part 1 complete
// Leon Ndou
// Student Number: st10510011
// PROG5121 Part 1
// This is the main class that runs LeonChat

import java.util.Scanner;

public class LeonChat {

    public static void main(String[] args) {
        
        // this is what we use to read what the user types
        Scanner input = new Scanner(System.in);
        
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("       Welcome to LeonChat!       ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("");
        
        // ---- REGISTRATION PART ----
        System.out.println("Please register to get started:");
        System.out.println("");
        
        System.out.println("Enter your first name: ");
        String fName = input.nextLine();
        
        System.out.println("Enter your last name: ");
        String lName = input.nextLine();
        
        System.out.println("Enter a username (must have _ and be max 5 characters): ");
        String uName = input.nextLine();
        
        System.out.println("Enter a password (must have capital, number and special character): ");
        String pWord = input.nextLine();
        
        System.out.println("Enter your cell number (must have international code e.g +27): ");
        String cell = input.nextLine();
        
        // create a new user with the details entered
        Login myUser = new Login(fName, lName, uName, pWord, cell);
        
        // check if registration worked
        String regResult = myUser.registerUser();
        System.out.println("");
        System.out.println(regResult);
        System.out.println("");
        
        // only go to login if registration was successful
        if (regResult.startsWith("Registration successful")) {
            
            // ---- LOGIN PART ----
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Now please log in:");
            System.out.println("");
            
            System.out.println("Enter your username: ");
            String loginName = input.nextLine();
            
            System.out.println("Enter your password: ");
            String loginPass = input.nextLine();
            
            // check login and show result
            String loginResult = myUser.returnLoginStatus(loginName, loginPass);
            System.out.println("");
            System.out.println(loginResult);
            System.out.println("");
            
        } else {
            System.out.println("Registration did not work. Please try again.");
        }
        
        input.close();
    }
}
