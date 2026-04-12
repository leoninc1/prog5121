// Leon Ndou
// Student Number: st10510011
// PROG5121 Part 1
// This class handles registration and login for LeonChat
public class Login {

    // these are the details the user will enter
    String firstName;
    String lastName;
    String userName;
    String passWord;
    String cellNum;

    // this is called when we create a new user
    public Login(String firstName, String lastName, String userName, String passWord, String cellNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passWord = passWord;
        this.cellNum = cellNum;
    }

    // this checks if the username is good
    // it must have an underscore and cant be longer than 5 letters
    public boolean checkUserName() {
        boolean hasUnderscore = userName.contains("_");
        boolean correctLength = userName.length() <= 5;
        
        if (hasUnderscore == true && correctLength == true) {
            return true;
        } else {
            return false;
        }
    }

    // this checks if the password is strong enough
    // must be 8 chars, have a capital, number and special char
    public boolean checkPasswordComplexity() {
        
        // first check the length
        if (passWord.length() < 8) {
            return false;
        }
        
        // now check each character one by one
        boolean gotCapital = false;
        boolean gotNumber = false;
        boolean gotSpecial = false;
        
        int i = 0;
        while (i < passWord.length()) {
            char letter = passWord.charAt(i);
            
            if (Character.isUpperCase(letter)) {
                gotCapital = true;
            }
            
            if (Character.isDigit(letter)) {
                gotNumber = true;
            }
            
            if (!Character.isLetterOrDigit(letter)) {
                gotSpecial = true;
            }
            
            i++;
        }
        
        // all three must be true
        if (gotCapital == true && gotNumber == true && gotSpecial == true) {
            return true;
        } else {
            return false;
        }
    }

    // this checks the cell number
    // it must start with + and have the country code
    public boolean checkCellPhoneNumber() {
        if (cellNum.startsWith("+") && cellNum.length() <= 13) {
            return true;
        } else {
            return false;
        }
    }

    // this puts everything together for registration
    // checks all three things and gives back a message
    public String registerUser() {
        
        if (checkUserName() == false) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        if (checkPasswordComplexity() == false) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        if (checkCellPhoneNumber() == false) {
            return "Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again.";
        }
        
        return "Registration successful! Welcome, " + firstName + " " + lastName + ".";
    }

    // this checks if the login details match what was saved
    public boolean loginUser(String enteredName, String enteredPass) {
        if (userName.equals(enteredName) && passWord.equals(enteredPass)) {
            return true;
        } else {
            return false;
        }
    }

    // this gives back the login message
    public String returnLoginStatus(String enteredName, String enteredPass) {
        if (loginUser(enteredName, enteredPass) == true) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
