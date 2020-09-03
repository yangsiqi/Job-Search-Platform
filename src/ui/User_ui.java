package ui;

import JDBC.JDBC;
import User.User;
import User.*;

import java.sql.*;
import java.util.ArrayList;

public class User_ui {
    Connection connection=JDBC.connect();
    PreparedStatement statement=null;
    ResultSet result=null;

    //user输入用户名和密码，如果已经注册过的话login 成功
    public String login(User user){
        try {
            statement = connection.prepareStatement("select * from User where username=? and password=?");
            statement.setString(1,user.getUsername());
            statement.setString(2,user.getPassword());
            result=statement.executeQuery();
            if(result.next()){
                String type=result.getString("type");
                if(type.equals("s")) {

                    return "Student";
                }else if(type.equals("e")){
                    return "Employer";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "failed";
    }

    public String checkType(String username){
        try {
            statement = connection.prepareStatement("select * from User where username=?");
            statement.setString(1,username);
            result=statement.executeQuery();
            if(result.next()){
                String type=result.getString("type");
                if(type.equals("s")) {
                    return "s";
                }else if(type.equals("e")){
                    return "e";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "failed";
    }

    //用来输入sql query
    public boolean checkExist(String query){
        try{
            statement=connection.prepareStatement(query);
            result=statement.executeQuery();
            if(result.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //通过username和passwrod注册一个user
    public boolean registerUser(User user, String type){
        try {
            //User(username,password)
            statement = connection.prepareStatement("insert into User(username, password, type) values(?,?,?) ");
            statement.setString(1,user.getUsername());
            statement.setString(2,user.getPassword());
            statement.setString(3,type);
            statement.executeUpdate();
            return true;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //注册学生，如果学生没有注册user，还需要注册成user
    //根据user的输入信息创建一个student
    public boolean registerStudent(Student user){

        try {
            //Student(SID, SIN, name, school, WorkExperience, PersonalProject)
            statement = connection.prepareStatement("insert into Student(SID, SIN, name, school, WorkExperience, PersonalProject, birthday, phoneNumber, sex, username) values(?,?,?,?,?,?,?,?,?,?) ");
            statement.setInt(1,user.getStudentID());
            statement.setInt(2,user.getSin());
            statement.setString(3,user.getName());
            statement.setString(4,user.getSchool());
            statement.setString(5,user.getWorkExperience());
            statement.setString(6,user.getPersonalProject());
            statement.setString(7,user.getBirthday());
            statement.setInt(8,user.getPhoneNumber());
            statement.setString(9,user.getSex());
            statement.setString(10, user.getUsername());
            statement.executeUpdate();

            if(!checkExist("select * from User where username="+"'"+user.getUsername()+"'")){
                registerUser(user,"s");
            }
            return true;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //注册employer，如果没有注册成user还需要注册user
    //根据user的输入信息创建一个employer
    public boolean registerEmployer(Employer user){
        //Employer(Description, company name)
        try{
            statement = connection.prepareStatement("insert into Employer values(?,?,?) ");
            statement.setString(1,user.getCompanyName());
            statement.setString(2,user.getDescription());
            statement.setString(3,user.getUsername());
            statement.executeUpdate();

            if(!checkExist("select * from User where username="+"'"+user.getUsername()+"'")){
                registerUser(user,"e");
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public boolean updateStudent(Student user){
        try{
            statement = connection.prepareStatement("update Student set personalProject = ?, workExperience=?, birthday=?, phoneNumber=?, sex=?, school=? where SID=? ");
            statement.setString(1,user.getPersonalProject());
            statement.setString(2,user.getWorkExperience());
            statement.setString(3,user.getBirthday());
            statement.setInt(4,user.getPhoneNumber());
            statement.setString(5,user.getSex());
            statement.setString(6,user.getSchool());
            statement.setInt(7,user.getStudentID());

            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //更新employer信息
    public boolean updateEmployer(Employer user){
        try{
            statement = connection.prepareStatement("update Employer set description = ? where companyName=? ");
            statement.setString(1,user.getDescription());
            statement.setString(2,user.getCompanyName());
            result=statement.executeQuery();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //通过关键字查找公司
    public ArrayList<String> searchEmployer(String key){
        ArrayList<String> returnSet = new ArrayList<String>();
        try{
            statement = connection.prepareStatement("select * from JobPost where requirement LIKE ? or companyName LIKE ? or position LIKE ?");
            statement.setString(1,"%"+key+"%");
            statement.setString(2,"%"+key+"%");
            statement.setString(3,"%"+key+"%");
            result=statement.executeQuery();
            while(result.next()){
                returnSet.add(result.getString("companyName")+"     "+ result.getString("Position")+"    "+result.getString("postDate"));
            }
            return returnSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    //删除用户
    public void deleteUser(User user){
        if(!checkExist("select * from User where username="+"'"+user.getUsername()+"'")) return;
        try{
            statement = connection.prepareStatement("delete from User where username=? ");
            statement.setString(1,user.getUsername());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }  finally {
            JDBC.close(connection,result,statement);
        }
    }

    //删除student和对应的user
    public void deleteStudent(Student user){
        if(!checkExist("select * from Student where SID="+user.getStudentID())) return;
        try{
            if(checkExist("select * from User where username="+"'"+user.getUsername()+"'")){
                deleteUser(user);
            }
            statement = connection.prepareStatement("delete from Student where SID=? ");
            statement.setInt(1,user.getStudentID());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }  finally {
            JDBC.close(connection,result,statement);
        }
    }
    //删除对应的employer和user
    public void deleteEmployer(Employer user){
        if(!checkExist("select * from Employer where companyName="+"'"+user.getCompanyName()+"'")) return;
        try{
            if(checkExist("select * from User where username="+"'"+user.getUsername()+"'")){
                deleteUser(user);
            }
            statement = connection.prepareStatement("delete from Employer where companyName=? ");
            statement.setString(1,user.getCompanyName());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }  finally {
            JDBC.close(connection,result,statement);
        }
    }
}
