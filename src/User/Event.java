package User;

import JDBC.JDBC;
import User.Employer;
import User.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Event {
    private int eventID;
    private String location;
    private String date;
    private String employer;
    public Event(int eventID, String location, String date, String employer){
        this.eventID=eventID;
        this.location=location;
        this.date=date;
        this.employer=employer;
    }
    public Event(int eventID, String location, String date){
        this.eventID=eventID;
        this.location=location;
        this.date=date;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    //根据公司的输入信息创建一个event，添加到数据库
    public void holdEvent(Employer e){
        employer=e.getCompanyName();
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
            //Event(EventID, location,date,employer name)
            statement=connection.prepareStatement("insert into theEvent(eventid,location, eventDate,companyName) values(?,?,?,?)");
            statement.setInt(1,eventID);
            statement.setString(2,location);
            statement.setString(3,date);
            statement.setString(4,e.getCompanyName());
            statement.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            JDBC.close(connection,result,statement);
        }
    }
}
