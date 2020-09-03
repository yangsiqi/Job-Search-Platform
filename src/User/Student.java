package User;

import JDBC.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends User {
    private int studentID;
    private int sin;
    private String name;
    private String school;
    private String workExperience;
    private String personalProject;
    private StudyProgress progress;
    private String birthday;
    private int phoneNumber;
    private String sex;

    public Student(String username, String password){

        this.username=username;
        this.password=password;
        this.userType="s";
    }
    public Student( int studentID, int sin, String name){
        this.studentID=studentID;
        this.name=name;
        this.sin=sin;
        this.userType="s";
    }
    public Student(String username, String password, int studentID, int sin, String name){
        this.username=username;
        this.password=password;
        this.studentID=studentID;
        this.name=name;
        this.sin=sin;
        this.school=null;
        this.workExperience=null;
        this.personalProject=null;
        this.userType="s";
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSin() {
        return sin;
    }

    public void setSin(int sin) {
        this.sin = sin;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getPersonalProject() {
        return personalProject;
    }

    public void setPersonalProject(String personalProject) {
        this.personalProject = personalProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void createProgress(String major, String graduate){
        progress=new StudyProgress(this,major,graduate);
    }
    //添加takencourse
    public void takenCourse(Course c){
        progress.addTakenCourse(c);
    }

    public StudyProgress getProgress() {
        return progress;
    }

    public void setProgress(StudyProgress progress) {
        this.progress = progress;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public void apply(String position, String companyName){
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
            //apply(companyName, position, postDate, sid)
            statement=connection.prepareStatement("insert into apply values(?,?,?)");
            statement.setString(1,companyName);
            statement.setString(2,position);
            statement.setInt(3,studentID);
            statement.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }

    //如果学生attend interview，对应公司会收到信息
    public void attendInterview(Interview i, Employer e){
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
            //conductInterview(student, interviewID, date,location)
            statement=connection.prepareStatement("insert into attendInterview(SID, interviewID, date,location) values(?,?,?,?)");
            statement.setInt(1,studentID);
            statement.setInt(2,i.getInterviewID());
            statement.setString(3,i.getDate());
            statement.setString(4,i.getLocation());
            statement.executeUpdate();
            i.sendNotToEmployer(this, e);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }

    //学生增加studyProgress
    public void addProgress(){
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
            //studyprogress(sid,major,takencourse, graduate year)
            statement=connection.prepareStatement("insert into studyProgress(SID, major, takencourse,graduateYear) values(?,?,?,?)");
            statement.setInt(1,studentID);
            statement.setString(2,progress.getMajor());
            statement.setString(3,progress.takenCourseToString());
            statement.setString(4,progress.getGraduateYear());
            statement.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }

    public ArrayList<String> getOffer(){
        Connection connection= JDBC.connect();
        PreparedStatement statement=null;
        ResultSet result=null;
        ArrayList<String> returnSet=new ArrayList<String>();
        try{
            //studyprogress(sid,major,takencourse, graduate year)
            statement=connection.prepareStatement("select * from conductInterview where sid=?");
            statement.setInt(1,studentID);
            result=statement.executeQuery();
            while (result.next()){
                returnSet.add(result.getString("CompanyName")+" invite you to attend the interview on "
                        +result.getString("date")+" "+result.getString("location"));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return returnSet;
    }
}
