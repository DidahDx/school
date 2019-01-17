package sample.database;


import java.util.ResourceBundle;

/**
 * THIS CLASS IS USED TO GET DATABASE CONNECTION PROPERTIES
 * */
class connectionProperties {

    private String user;
    private String password;
    private String databaseUrl;

    connectionProperties(){

        ResourceBundle rb= ResourceBundle.getBundle("database/school");   //USED TO LOAD THE PROPERTIES FILE CONTAINING DATABASE PROPERTIES
        user= rb.getString("user");                                   //GETS USERNAME FROM THE FILE
        password=rb.getString("password");                             //GETS PASSWORD FROM THE FILE
        databaseUrl=rb.getString("databaseUrl");                       //GETS DATABASE URL FROM THE FILE
    }

    //these are getters to return the userName ,password and databaseUrl
    public String getUser(){
        return user;
    }
    public String getPassword(){
        return password;
    }
    public String getDatabaseUrl(){
        return databaseUrl;
     }

}