package Test;
import JDBC.JDBC;
import User.*;
import org.junit.Test;
import ui.User_ui;
import User.Course;

import java.sql.*;
import java.util.ArrayList;

public class JDBCTest {
    User_ui ui=new User_ui();
    User u=new User("Student1","12345");
    Employer e=new Employer("Youtube123","1111","YouTube");
    Student s=new Student("Nancy123","9999",99992,10002,"Nancy");
    Interview interview=new Interview("2020-08-10", "MATH100", 8888);
    JobPost j=new JobPost("2020-02-21","Programmer","UBC");
    Connection connection=JDBC.connect();
    PreparedStatement statement=null;
    ResultSet result=null;
    @Test
    public void connectionTest(){
        Connection connection=JDBC.connect();
        try {
            Statement statement=connection.createStatement();
            ResultSet result=statement.executeQuery("select * from User");
            while(result.next()){
                System.out.println(result.getString("Username"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Test
    public void loginTest(){
        //System.out.println(ui.login(s));
        System.out.println(ui.login(e));

    }

    @Test
    public void testUpdate(){
        s.setSchool("U of S");
        ui.updateStudent(s);
    }
    @Test
    public void registerStudentTest(){
        ui.registerStudent(s);
    }
    @Test
    public void applyTest(){
    }

    @Test
    public void deleteUser(){
        ui.deleteUser(u);
    }

    @Test
    public void deleteStudent(){
        ui.deleteStudent(s);
    }

    @Test
    public void deleteEmployer(){
        ui.deleteEmployer(e);
    }

    @Test
    public void testPost(){
        JobPost j=new JobPost("20200611","manager","ubc");
        e.postJob(j);
    }
    @Test
    public void registerEmployerTest(){

        ui.registerEmployer(e);
    }
    @Test
    public void addEvent(){
        Event event=new Event(1234,"ICICS","2020-08-10");
        event.holdEvent(e);
    }

    @Test
    public void testSendEmployer(){
        //ui.registerStudent(s);
        s.attendInterview(interview,e);
        System.out.println(e.getNotifications().get(0).getContent());
    }

    @Test
    public void testSendStudent(){
        e.conductInterview(interview,s.getStudentID());
        System.out.println(s.getNotifications().get(0).getContent());

    }

    @Test
    public void testProgress(){
        Course c=new Course("MATH", 200);
        s.createProgress("MATH","2023");
        s.takenCourse(c);
        s.addProgress();
    }

    @Test
    public void testSearch(){
        for(String s:ui.searchEmployer("y")){
            System.out.println(s);
        }

    }
    @Test
    public  void testFindApp(){
        for(String s:e.findApplication()){
            System.out.println(s);
        }

    }
    @Test
    public void testoffer(){
        Student s2=new Student(22222,22222,null);
        for(String s:s2.getOffer()){
            System.out.println(s);
        }
    }
    @Test
    public void testExist(){
        String query="select * from JobPost where postDate=20200612 AND position='Programmer' AND companyName='e2'";
        System.out.println(ui.checkExist(query));
    }
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
}
