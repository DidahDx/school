package sample;

/**
 * THIS CLASS USED FOR VALIDATING USER INPUTS
 * AND IT RETURNS A TRUE OR FALSE AFTER VALIDATING
 *
 * */
public class validation {
    private boolean check,checkPassword ,checkUserName,checkEmail,checkPhoneNumber;


    public boolean validate(String nValues){

        // checks if the textField is empty
        if(!(nValues.isEmpty()) && (nValues.matches("^[a-zA-Z0-9]{4,15}$")))
        {
            /* checks the textField has only letters and numbers and a minimum of 4 and maximum of 15 characters*/
          check=true;
        }
        else{
            check=false;
        }
        return check;
    }

    //This method is used to validates the Password
    public boolean validatePassword(String nValues){

        // checks if the passwordField is empty
        if(!(nValues.isEmpty()) && (nValues.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")))
        {
            //checks the passwordField has only letters, @#$% and numbers and a minimum of 6 and maximum of 20 characters
            checkPassword=true;
        }
        else{
            checkPassword=false;
        }
        return checkPassword;
    }

    //This method is used to validates the UserName
    public boolean validateUserName(String nValues){

        /* checks if the textField is empty*/
        if(!(nValues.isEmpty()) && (nValues.matches("^[a-zA-Z0-9]{4,15}$")))
        {
            // checks the textField has only letters and numbers and a minimum of 4 and maximum of 15 characters
            checkUserName=true;
        }
        else{
            checkUserName=false;
        }
        return checkUserName;
    }

    //This method is used to validates the Email
    public boolean validateEmail(String nValues){

        /* checks if the textField is empty*/
        if(!(nValues.isEmpty()) && (nValues.matches("^[a-zA-Z0-9]{15,35}$")))
        {
            /* checks the textField has only letters and numbers and a minimum of 4 and maximum of 15 characters*/
            checkEmail=true;
        }
        else{
            checkEmail=false;
        }
        return checkEmail;
    }

    //This method is used to validates the PhoneNumber
    public boolean validatePhoneNumber(String nValues){

        /* checks if the textField is empty*/
        if(!(nValues.isEmpty()) && (nValues.matches("^[0-9]{10}$")))
        {
            /* checks the textField has only letters and numbers and a minimum of 4 and maximum of 15 characters*/
            checkPhoneNumber=true;
        }
        else{
            checkPhoneNumber=false;
        }
        return checkPhoneNumber;
    }

}