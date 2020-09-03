package User;

import JDBC.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Employer extends User {
    private String companyName;
    private String description;
    public Employer(String username, String password, String companyName){
        this.username=username;
        this.password=password;
        this.companyName=companyName;
        this.description=null;
        this.userType="e";
    }
    public Employer(String companyName){
        this.companyName=companyName;
        this.userType="e";
    }
    public Employer(){
        super();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //公司创建一个post，根据公司的输入信息添加jobpost到database
    public void postJob(JobPost job){
        //depend on the input of user to setup position, requirement.

        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
            //jobPosting(postid, position, description, companyName, requirement)
            statement=connection.prepareStatement("insert into JobPost(companyName, position, postDate, requirement) values(?,?,?,?)");
            statement.setString(3,job.getPostDate());
            statement.setString(2,job.getPosition());
            statement.setString(1,job.getCompanyName());
            statement.setString(4,job.getRequirement());
            statement.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }
    //公司创建一个interview的对象，以及确定邀请的学生
    //发出interview邀请给对应的学生，学生的inbox会收到notification
    public void conductInterview(Interview i, int sid){
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
            //conductInterview(comanyName, interviewID, date,location)
            statement=connection.prepareStatement("insert into conductInterview(companyName, interviewID, date,location) values(?,?,?,?)");
            statement.setString(1,companyName);
            statement.setInt(2,i.getInterviewID());
            statement.setString(3,i.getDate());
            statement.setString(4,i.getLocation());
            statement.executeUpdate();
            //i.sendNotToStudent(this,s);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    
    public ArrayList<String> findApplication(){
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        ArrayList<String> returnSet=new ArrayList<String>();
        try{
            statement=connection.prepareStatement("select * from apply a,Student s where companyName=? AND a.sid=s.sid");
            statement.setString(1,companyName);
            result=statement.executeQuery();
            while (result.next()){
                returnSet.add(result.getString("sid")+"    "+result.getString("name")+"    "+"Position: "+result.getString("position"));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return returnSet;
    }

    public void deleteEvent(int eventID){
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        ArrayList<String> returnSet=new ArrayList<String>();
        try{
            statement=connection.prepareStatement("delete from theEvent where eventid=?");
            statement.setInt(1,eventID);
            statement.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }



}
