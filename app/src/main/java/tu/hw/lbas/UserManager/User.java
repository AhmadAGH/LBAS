package tu.hw.lbas.UserManager;

import java.io.Serializable;

public class User implements Serializable
{
    String name,password,email,companyName;
    int userID;

    public User(String name, String password, String email, String companyName) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.companyName = companyName;
    }

    public User(String name, String password, String email, String companyName, int userID) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.companyName = companyName;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", userID=" + userID +
                '}';
    }
}
