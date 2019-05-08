package sample.dataAccessObject.user;

import sample.dataAccessObject.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    //this method is used to login using the tUserName and Password
    public ResultSet login(String UserName,String Password ,Connection con) throws SQLException {
        String sql="select user_id,user_name,password,role from users_details where user_name=? and password=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,UserName);
        ps.setString(2,Password);
       return ps.executeQuery();
    }

    //this method is used to create a user in the system
    public void Create(String firstName,String SecondName,String Email,int Phone,String gender,String password,String  UserName,Connection con) throws SQLException
    {

        String sql="INSERT INTO users_details(first_name,second_name,email,phone_number,gender,user_name,password)" +
                " VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,firstName);
        ps.setString(2,SecondName);
        ps.setString(3,Email);
        ps.setInt(4,Phone);
        ps.setString(5,gender);
        ps.setString(6,UserName);
        ps.setString(7,password);

        ps.executeUpdate();

    }

    //this method is used to update user details in the system
    public void Update(String firstName,String SecondName,String Email,int Phone,String gender,String password,String  UserName,int userId,Connection con) throws SQLException
    {

        String sql="UPDATE users_details SET first_name=?,second_name=?,email=?,phone_number=?,gender=?,user_name=?,password=?" +
                " where user_id=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,firstName);
        ps.setString(3,Email);
        ps.setString(2,SecondName);
        ps.setInt(4,Phone);
        ps.setInt(8,userId);
        ps.setString(5,gender);
        ps.setString(6,UserName);
        ps.setString(7,password);

        ps.executeUpdate();
    }

    //this method is used to return all the detail of a certain user
    public ResultSet Read(int userId,Connection connection) throws SQLException{
        String sql="SELECT * FROM users_details WHERE user_id=? ";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,userId);
        return ps.executeQuery();
    }

    public ResultSet ReadAll(Connection con) throws SQLException{
        String sql="SELECT * FROM users_details";
        PreparedStatement ps=con.prepareStatement(sql);
        return ps.executeQuery();
    }


    //this method is used to return the users email Address
    public ResultSet ReadEmail(String userName,Connection con) throws SQLException{
        String sql="SELECT email FROM users_details WHERE user_name=? ";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,userName);
        return ps.executeQuery();
    }

    //this method is used update the password
    public void UpdatePassword(String password,String userName,Connection con) throws SQLException {
        String sql="UPDATE users_details SET password=?" +
                          " where user_name=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,password);
        ps.setString(2,userName);
        ps.executeUpdate();
    }

    //this method is used to set the users privilege
    public void UpdatePrivilege(String priv,int userId,Connection con) throws SQLException {
        String sql="UPDATE users_details SET role=? where user_id=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,priv);
        ps.setInt(2,userId);
        ps.executeUpdate();
    }

    //this method is used to delete a user from the database
    public void Delete(int userId,Connection con) throws SQLException {
        String sql="DELETE FROM users_details WHERE  user_id=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setInt(1,userId);
        ps.executeUpdate();
    }
}
