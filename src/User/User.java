package User;

import java.util.ArrayList;

public class User {
    protected String username;
    protected String password;
    protected String userType;
    protected ArrayList<Notification> notifications=new ArrayList<Notification>();

    public User(){

    }
    public User(String username, String password){
        this.username=username;
        this.password=password;
        this.userType=null;
        notifications=null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(Notification n){
        notifications.add(n);
    }
}
