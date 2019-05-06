package sample.dataAccessObject;


import java.util.ResourceBundle;

/**
 * THIS CLASS IS USED TO GET DATABASE CONNECTION PROPERTIES
 * */
class connectionProperties {

    private String user;
    private String password;
    private String databaseUrl;
    private String driverClassName;
    private String DataSource;


    connectionProperties(){

        ResourceBundle rb= ResourceBundle.getBundle("database/school");   //USED TO LOAD THE PROPERTIES FILE CONTAINING DATABASE PROPERTIES
        user= rb.getString("user");                                   //GETS USERNAME FROM THE FILE
        password=rb.getString("password");                             //GETS PASSWORD FROM THE FILE
        databaseUrl=rb.getString("databaseUrl");                       //GETS DATABASE URL FROM THE FILE
        driverClassName=rb.getString("driver.class.name");             //GETS DRIVER CLASS NAME FROM THE FILE
        DataSource=rb.getString("data.source.name");

    }


    //this method is used to return the UserName
    public String getUser(){ return user; }

    //this method is used to return the password
    String getPassword(){ return password; }

    //this method is used to return the databaseUrl
    String getDatabaseUrl(){ return databaseUrl; }

    //this method is used to return the driver class name
    public String getDriverClassName() {
        return driverClassName;
    }

    public String getDataSource() {
        return DataSource;
    }

}