package sample.model.user;

import java.util.Random;

public class GenerateRandomPassword {

    public String RandomPassword(){
        String password ="";

        Random random=new Random();

        String[] letters={"a","b","w","x","y","z","g","h","c","d","e","f","m","n","o","p","q","i","j","k","v","r","s","t","u",};
        String [] special={"@","#","$","%"};

        for(int i=0;i<2;i++){
            password+=letters[random.nextInt(letters.length)];
            password+=random.nextInt(57);
            password+=(letters[random.nextInt(letters.length)]).toUpperCase();
            password+=special[random.nextInt(special.length)];
        }

        return password;
    }
}