package sample;

import com.jfoenix.controls.JFXDatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * THIS CLASS USED FOR VALIDATING USER INPUTS
 * AND IT RETURNS A TRUE OR FALSE AFTER VALIDATING
 *
 * */
public class Validation {
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

    //This method is used to change the date format
    public void changeDateFormat(JFXDatePicker jfxDatePicker)
    {
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

        jfxDatePicker.setConverter(new StringConverter<LocalDate>()
        {
            @Override
            public String toString(LocalDate object)
            {
                if(object!=null)
                {
                    return dateTimeFormatter.format(object);
                }
                return null;
            }

            @Override
            public LocalDate fromString(String string)
            {
              if(string !=null && !string.trim().isEmpty())
              {
                   return LocalDate.parse(string,dateTimeFormatter);
              }
              return null;
            }
        });
    }

    //This method prevents users from choosing future dates
    public boolean pastDates(JFXDatePicker jfxDatePicker){
      boolean check=false;
        LocalDate today=LocalDate.now();
        LocalDate date=jfxDatePicker.getValue();

        if(!(date==null || date.isAfter(today))){
           check=true;
       }
        return check;
    }

}