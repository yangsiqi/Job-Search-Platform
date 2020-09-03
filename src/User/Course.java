package User;

public class Course {
    private String department;
    private int courseNum;

    public Course(String department, int courseNum){
        this.department=department;
        this.courseNum=courseNum;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }




}
