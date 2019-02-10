package sample.model.user;


public class UserModelTable {
    String userName;
    int userIdl;

    public UserModelTable(String userName, int userIdl)
    {
            this.userName=userName;
            this.userIdl=userIdl;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserIdl() {
        return userIdl;
    }

    public void setUserIdl(int userIdl) {
        this.userIdl = userIdl;
    }

}
