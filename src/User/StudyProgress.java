package User;

import JDBC.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudyProgress {
    private ArrayList<Course> takenCourse=new ArrayList<Course>();
    private String major;
    private String graduateYear;
    private Student user;

    public StudyProgress(Student s, String major, String graduateYear){

        this.user=s;
        this.major=major;
        this.graduateYear=graduateYear;
    }

    public ArrayList<Course> getTakenCourse() {
        return takenCourse;
    }

    public void setTakenCourse(ArrayList<Course> takenCourse) {
        this.takenCourse = takenCourse;
    }

    public Student getUser() {
        return user;
    }

    public void setUser(Student user) {
        this.user = user;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

    public void addTakenCourse(Course c){
        takenCourse.add(c);
    }

    //把takencourse转为一个string
    public String takenCourseToString(){
        String courses=null;
        for(Course c: takenCourse){
            courses+=c.getDepartment()+" "+c.getCourseNum()+" ";
        }
        return courses;
    }



}
