package User;

import JDBC.JDBC;
import User.Employer;
import User.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Interview {
    private String date;
    private String location;
    private int interviewID;

    public Interview(String date, String location, int interviewID){
        this.date=date;
        this.location=location;
        this.interviewID=interviewID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getInterviewID() {
        return interviewID;
    }

    public void setInterviewID(int interviewID) {
        this.interviewID = interviewID;
    }

    public void sendNotToStudent(Employer e, Student s){
        //student depend on the choice
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
            statement=connection.prepareStatement("select * from Student where sid=?");
            statement.setInt(1,s.getStudentID());
            result=statement.executeQuery();

            while(result.next()){
                Notification n=new Notification(s,e);
                n.setContent("Congratulate! You are invited to attend the interview of company "+e.getCompanyName());
                s.addNotification(n);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBC.close(connection,null,statement);
        }
    }
    public void sendNotToEmployer(Student s, Employer e){
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
            statement=connection.prepareStatement("select * from Employer where companyName=?");
            statement.setString(1,e.getCompanyName());
            result=statement.executeQuery();

            while(result.next()){
                Notification n=new Notification(s,e);
                n.setContent("Student "+s.getStudentID()+" "+s.getName()+" apply the job position");
                e.addNotification(n);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBC.close(connection,null,statement);
        }
    }



}
