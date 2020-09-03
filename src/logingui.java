import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import JDBC.*;
import User.Employer;
import User.Student;
import User.User;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import ui.User_ui;


public class logingui {

    public static JFrame frame;
    public static int panelselect;
    public static String currUser;
    public static Connection connection = JDBC.connect();
    public static Student student=null;
    public Employer employer=null;
    public static User_ui ui=new User_ui();
    public static PreparedStatement statement=null;
    public static ResultSet result=null;
    public static employeeUI eui=new employeeUI();

    public static void main(String[] args){
        frame = new JFrame("Scope");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel loginPanel = new JPanel();
        loginPanel.setVisible(true);
        setLogin(loginPanel);
        frame.add(loginPanel);
        frame.setVisible(true);
    }


    public static void setLogin(final JPanel login){
        frame.setSize(400,300);
        login.setLayout(null);
        login.setFocusable(true);

        final JButton loginbut = new JButton("Login");
        loginbut.setBounds(50,200,100,25);


        final JButton studentRegister = new JButton("Student Register");
        studentRegister.setBounds(180,175,200,25);

        final JButton employerRegister = new JButton("Employer Register");
        employerRegister.setBounds(180,225,200,25);

        JLabel userl=new JLabel("Username");
        JLabel passl=new JLabel("Password");
        userl.setBounds(30,50,70,25);
        passl.setBounds(30,125,70,25);
        final JTextField username = new JTextField();
        username.setBounds(100,50,250,25);

        final JPasswordField password = new JPasswordField();
        password.setBounds(100,125,250,25);

        final JLabel notexist = new JLabel("The username/password you input isn't correct");
        notexist.setForeground(Color.RED);
        notexist.setBounds(50,25,300,25);

        username.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                username.selectAll();
            }
        });

        loginbut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String un = username.getText();
                String pw = password.getText();
                // need to check if user exist
                if(exist(un, pw)){
                    if(ui.checkType(un)=="s"){
                        login.setVisible(false);
                        setPersonalMenue();
                        currUser = username.getText();
                    }else if(ui.checkType(un)=="e"){
                        System.out.println("Employer login");
                        login.setVisible(false);
                        eui.setEmployeeManue(un,pw);
                    }
                } else{
                    login.add(notexist);
                    login.updateUI();
                }

            }
        });

        username.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String un = username.getText();
                    String pw = password.getText();
                    // need to check if user exist
                    if (exist(un, pw)) {
                        login.setVisible(false);
                        setPersonalMenue();
                    } else {
                        login.add(notexist);
                        login.updateUI();
                    }
                }
            }
        });

        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    String un = username.getText();
                    String pw = password.getText();
                    // need to check if user exist
                    if(exist(un, pw)){
                        login.setVisible(false);
                        setPersonalMenue();
                    } else{
                        login.add(notexist);
                        login.updateUI();
                    }
                }
            }});

        password.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                password.selectAll();
            }
        });

        studentRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentRpanel();
            }
        });

        employerRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employerRpanel();
            }
        });
        login.add(loginbut);
        login.add(studentRegister);
        login.add(employerRegister);
        login.add(username);
        login.add(userl);
        login.add(passl);
        login.add(password);
    }

    //done
    public static boolean exist(String username, String password){
        ArrayList<String> usernames = getUsernames();
        if (usernames.contains(username)){
            return checkPassword(username, password);
        } else {
            System.out.println("Username does not exist");
        }

        return false;
    }

    //done
    public static ArrayList<String> getUsernames(){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Username FROM user");
            ResultSet result = statement.executeQuery();

            ArrayList<String> usernames = new ArrayList<String>();
            while(result.next()) {
                usernames.add(result.getString("Username"));
            }

            return usernames;

        } catch(Exception e) {
            System.out.print("get username error");
            return null;
        }
    }

    //done
    public static boolean checkPassword(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Password FROM user WHERE Username = '" + username +"'");
            ResultSet result = statement.executeQuery();
            result.next();
            String truepassword = result.getString("Password");
            if(password.equals(truepassword)){
                student=new Student(username,password);
                setStudent(username);
            }
            return password.equals(truepassword);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //done
    public static void setPersonalMenue(){
        panelselect = 0;
        JPanel manue = new JPanel();
        manue.setLayout(null);
        frame.setSize(800,500);
        manue.setSize(800,100);
        manue.setLocation(0,0);
        manue.setBackground(Color.gray);

        final JPanel panel2 = new JPanel();
        setdashboard(panel2);
        frame.add(panel2);
        panel2.setBackground(Color.white);


        JButton dashboard = new JButton("DashBoard");
        dashboard.setBounds(10,10,150,50);
        dashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                setdashboard(panel2);
                panel2.updateUI();
            }
        });

        JButton message = new JButton("Message");
        message.setBounds(180,10,150,50);
        message.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                setMessage(panel2);
                panel2.updateUI();

            }
        });

        JButton jobsearch = new JButton("Job Searching");
        jobsearch.setBounds(350,10,150,50);
        jobsearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                setJobSearch(panel2);
                panel2.updateUI();

            }
        });

        JButton calendar = new JButton("Calendar");
        calendar.setBounds(520,10,150,50);
        calendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                setCalendar(panel2);
                panel2.updateUI();

            }
        });

        JButton personalinfo = new JButton("Me");
        personalinfo.setBounds(690,10,70,50);
        personalinfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                setPersonalInfo(panel2);
                panel2.updateUI();

            }
        });

        manue.add(message);
        manue.add(dashboard);
        manue.add(jobsearch);
        manue.add(calendar);
        manue.add(personalinfo);

        manue.setVisible(true);
        frame.add(manue);
    }

    public static void studentRpanel(){
        frame.setSize(400,350);

        JFrame studentR=new JFrame();
        studentR.setLocation(0,0);
        studentR.setSize(400,350);
        studentR.setLayout(null);
        studentR.setFocusable(true);

        JTextField username=new JTextField();
        JLabel usernamel=new JLabel("Username");
        usernamel.setBounds(30,30,80,25);
        username.setBounds(150,30,200,25);
        JTextField password=new JTextField();
        JLabel passwordl=new JLabel("Password");
        passwordl.setBounds(30,80,80,25);
        password.setBounds(150, 80, 200,25);
        JTextField name=new JTextField();
        JLabel namel=new JLabel("Name");
        namel.setBounds(30,130,80,25);
        name.setBounds(150,130,200,25);
        JTextField sid=new JTextField();
        JLabel sidl=new JLabel("StudentID");
        sidl.setBounds(30,180,80,25);
        sid.setBounds(150,180,200,25);
        JTextField sin=new JTextField();
        JLabel sinl=new JLabel("SIN Number");
        sinl.setBounds(30,230,80,25);
        sin.setBounds(150,230,200,25);
        JButton register = new JButton("Register");
        register.setBounds(180,280,100,25);

        studentR.add(usernamel);
        studentR.add(username);
        studentR.add(passwordl);
        studentR.add(password);
        studentR.add(name);
        studentR.add(namel);
        studentR.add(sid);
        studentR.add(sidl);
        studentR.add(sin);
        studentR.add(sinl);
        studentR.add(register);
        studentR.setVisible(true);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String un = username.getText();
                String pw = password.getText();
                String nm = name.getText();
                String sid2 = sid.getText();
                String sin2 = sin.getText();
                //todo:check to see if its int
                int sid3;
                int sin3;
                try{
                    sid3 = Integer.parseInt(sid2);
                    sin3 = Integer.parseInt(sin2);
                    if(un.isEmpty() || pw.isEmpty() || nm.isEmpty()) {
                        System.out.println("please fill out all required fields");
                    } else {
                        User user = new User(un, pw);
                        ui.registerUser(user, "s");
                        Student student = new Student(un, pw, sid3, sin3, nm);
                        ui.registerStudent(student);

                        JPanel loginPanel = new JPanel();
                        loginPanel.setVisible(true);
                        setLogin(loginPanel);
                        studentR.setVisible(false);
                    }
                } catch(Exception error) {
                    System.out.println("invalid input error");
                }
            }
        });

    }



    public static void employerRpanel(){
        frame.setSize(400,300);

        JFrame employerR=new JFrame();
        employerR.setLocation(0,0);
        employerR.setSize(400,300);
        employerR.setLayout(null);
        employerR.setFocusable(true);

        JTextField username=new JTextField();
        JLabel usernamel=new JLabel("Username");
        usernamel.setBounds(30,30,80,25);
        username.setBounds(150,30,200,25);
        JTextField password=new JTextField();
        JLabel passwordl=new JLabel("Password");
        passwordl.setBounds(30,80,80,25);
        password.setBounds(150, 80, 200,25);
        JTextField name=new JTextField();
        JLabel namel=new JLabel("Company Name");
        namel.setBounds(10,130,130,25);
        name.setBounds(150,130,200,25);
        JButton register = new JButton("Register");
        register.setBounds(180,230,100,25);

        employerR.add(usernamel);
        employerR.add(username);
        employerR.add(passwordl);
        employerR.add(password);
        employerR.add(name);
        employerR.add(namel);
        employerR.add(register);
        employerR.setVisible(true);

        //todo
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String un = username.getText();
                String pw = password.getText();
                String nm = name.getText();
                if (un.isEmpty() || pw.isEmpty() || nm.isEmpty()) {
                    System.out.println("please fill out all required fields");
                } else {
                    User user = new User(un, pw);
                    ui.registerUser(user, "e");
                    Employer employer = new Employer(un, pw, nm);
                    ui.registerEmployer(employer);
                    JPanel loginPanel = new JPanel();
                    loginPanel.setVisible(true);
                    setLogin(loginPanel);
                    employerR.setVisible(false);
                }
            }
        });


    }
    //todo
    public static void setdashboard(JPanel dashboard){
        dashboard.setLayout(null);
        dashboard.setSize(800,400);

        dashboard.setLocation(0,100);

        JLabel welcome = new JLabel("Welcome!");
        welcome.setBounds(10,10,150,50);
        dashboard.add(welcome);

        dashboard.setVisible(true);

    }

    //todo
    public static void setMessage(JPanel message){
        message.setLayout(null);
        message.setSize(800,400);
        message.setLocation(0,100);

        JTextField userinput = new JTextField();
        userinput.setBounds(200,280, 570,70);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(20,20,600,200);
        ArrayList<String> offers=student.getOffer();
        tableModel.addColumn("The offer you recevied");
        for(String s: offers){
            tableModel.addRow(new Object[]{s});
        }
        message.add(scrollpane);
        message.add(userinput);
        message.setVisible(true);
    }

    //todo
    public static void setJobSearch(final JPanel jobSearch){
        jobSearch.setLayout(null);
        jobSearch.setSize(800,400);
        jobSearch.setLocation(0,100);

        final JTextField userinput = new JTextField("Position; Company Name; Location (Partial Allowable)");
        userinput.setBounds(150,150,500,30);

        final JButton view = new JButton("View all postings");
        view.setBounds(50,350,300,25);
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = new DefaultTableModel();
                JTable table = new JTable(tableModel);
                tableModel.addColumn("Related Job Post");
                ArrayList<String> companies=ui.searchEmployer(userinput.getText());
                for(String s: companies){
                    tableModel.addRow(new Object[]{s});
                }
                JScrollPane scrollpane = new JScrollPane(table);
                scrollpane.setBounds(20,20,350,300);
                jobSearch.add(scrollpane);
            }
        });

        

        jobSearch.add(userinput);
        jobSearch.add(view);

        JTextField inputName=new JTextField();
        JTextField inputPos=new JTextField();

        JLabel pleaseInput=new JLabel("Please Enter Company Name and Position to Apply");
        pleaseInput.setBounds(450,200,350,30);
        JLabel position=new JLabel("Position");
        position.setBounds(600,230,80,30);
        inputPos.setBounds(600,255,100,30);
        JLabel name=new JLabel("Company Name");
        name.setBounds(600,285,100,30);
        inputName.setBounds(600,305,100,30);
        JButton apply=new JButton("Apply");
        apply.setBounds(600,350,80,20);

        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                student.apply(inputPos.getText(),inputName.getText());
            }
        });


        jobSearch.add(apply);
        jobSearch.add(pleaseInput);
        jobSearch.add(name);
        jobSearch.add(position);
        jobSearch.add(inputName);
        jobSearch.add(inputPos);
        jobSearch.setVisible(true);
    }

    public static void setView(JPanel view) {
        view.setLayout(null);
        view.setSize(800,400);
        view.setLocation(0,100);

        String[] columns = {"Company",
                            "Post Date",
                            "Position",
                            "Requirement"};
        String[][] rows = getJobPostings();
        final JTable postings = new JTable(rows, columns);
        postings.setBounds(50, 50, 1000, 1000);


        view.add(postings);
        view.setVisible(true);
    }

    public static String[][] getJobPostings() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT companyName, postDate, position, requirement FROM jobpost");
            ResultSet result = statement.executeQuery();

            PreparedStatement statement2 = connection.prepareStatement("SELECT COUNT(position) FROM jobpost");
            ResultSet result2 = statement2.executeQuery();
            result2.next();
            int count = result2.getInt(1);

            String[][] rows = new String[count][4];

            /*while(result.next()) {
                ArrayList<String> row = new ArrayList<String>();
                row.add(result.getString("companyName"));
                row.add(result.getString("postDate"));
                row.add(result.getString("position"));
                row.add(result.getString("requirement"));
                rows.add(row.toArray());
            }*/

            for (int i=0; i<count; i++) {
                result.next();
                rows[i][0] = result.getString("companyname");
                rows[i][1] = result.getString("postDate");
                rows[i][2] = result.getString("position");
                rows[i][3] = result.getString("requirement");
            }

            return rows;

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //todo
    public static void createTable(JPanel panel, String input){
    // Add information from database
        Object[][] jobpostinfo = {
                {"20200304", "Specialist","Apple"},{"20200614","Barista","Starbucks"},
        };
        String[] Names = {"Date", "Position", "Company"};

        JTable table = new JTable(jobpostinfo,Names);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(20,20,750,360);
        panel.add(scrollpane);
        panel.setVisible(true);
    }

    public static void setCalendar(JPanel calendar){
        calendar.setLayout(null);
        calendar.setPreferredSize(new Dimension(800,400));

        calendar.setLocation(0,100);

        String[] columns = {"Date", "Location", "Company"};
        String[][] rows = getEvents();

        JTable events = new JTable(rows, columns);
        JScrollPane scrollpane = new JScrollPane(events);
        scrollpane.setBounds(20,20,750,360);
        events.setBounds(50, 50, 800, 1000);
        calendar.add(scrollpane);
        calendar.setVisible(true);
    }
    public static String[][] getEvents() {
        try {
            statement = connection.prepareStatement("SELECT eventDate, location, companyName FROM theevent");
            result = statement.executeQuery();

            PreparedStatement statement2 = connection.prepareStatement("SELECT COUNT(eventid) FROM theevent");
            ResultSet result2 = statement2.executeQuery();
            result2.next();

            int count = result2.getInt(1);

            String[][] rows = new String[count][3];
            for (int i=0; i<count; i++) {
                result.next();
                rows[i][0] = result.getString("eventDate");
                rows[i][1] = result.getString("location");
                rows[i][2] = result.getString("companyName");
            }

            return rows;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //todo
    public static void setPersonalInfo(JPanel me){
        me.setLayout(null);
        me.setSize(800,400);
        me.setLocation(0,100);

        createworkexperience(me);
        createPersonalProject(me);


        JLabel title = new JLabel("PERSONAL INFO");
        title.setBounds(0,0,800,55);
//        title.setSize(200,55);
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setHorizontalAlignment(JLabel.CENTER);

        JLabel name = new JLabel("Name:" + student.getName());
        name.setBounds(10,90,100,10);

        JLabel sex = new JLabel("Sex:"+student.getSex());
        sex.setBounds(410,90,100,10);

        JLabel studentNum = new JLabel("StudentNumber:"+student.getStudentID());
        studentNum.setBounds(10,120,200,10);

        JLabel sin = new JLabel("SIN:"+student.getSin());
        sin.setBounds(10,150,100,10);

        JLabel workexperience = new JLabel("Work Experience");
        workexperience.setBounds(5,180,110,10);

        JLabel school = new JLabel("School:"+student.getSchool());
        school.setBounds(410,120,100,10);

        JLabel personalproject = new JLabel("Personal Project:");
        personalproject.setBounds(5,270,110,10);

        JButton addNewProject= new JButton("New Project");
        addNewProject.setBounds(630,280,100,25);

        JButton addNewWork= new JButton("New Work Experience");
        addNewWork.setBounds(600,200,180,25);

        me.add(name);
        me.add(studentNum);
        me.add(sin);
        me.add(title);
        me.add(school);
        me.add(workexperience);
        me.add(sex);
        me.add(personalproject);
        me.add(addNewProject);
        me.add(addNewWork);


        addNewProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newProject();
            }
        });
        addNewWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newWork();
            }
        });
        me.setVisible(true);
    }

    public static void newProject(){
        JFrame newProject=new JFrame();
        newProject.setLocation(100,100);
        newProject.setSize(300,100);
        newProject.setLayout(null);
        newProject.setFocusable(true);
        JTextField projectText=new JTextField("project");
        projectText.setBounds(0,30,250,25);

        JButton go=new JButton("GO");
        go.setBounds(270,30,30,25);
        newProject.add(go);
        newProject.add(projectText);
        newProject.setVisible(true);

        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectUpdate;
                PreparedStatement statement=null;
                ResultSet result=null;
                try{
                    statement = connection.prepareStatement("select * from Student where sid=?");
                    statement.setInt(1,student.getStudentID());
                    result=statement.executeQuery();
                    if(result.next()){
                        student.setPersonalProject(result.getString("PersonalProject"));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }  finally {
                    JDBC.close(connection,result,statement);
                }
                if(student.getPersonalProject()==null){
                    projectUpdate=projectText.getText();
                }else{
                    projectUpdate=student.getPersonalProject()+";"+projectText.getText();
                }
                student.setPersonalProject(projectUpdate);
                ui.updateStudent(student);
                
            }
        });
        projectText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                projectText.selectAll();
            }
        });
    }

    public static void newWork(){
        JFrame newWork=new JFrame();
        newWork.setLocation(100,100);
        newWork.setSize(300,100);
        newWork.setLayout(null);
        newWork.setFocusable(true);

        JTextField workText=new JTextField("WORK");
        workText.setBounds(0,30,250,25);

        JButton go=new JButton("GO");
        go.setBounds(270,30,30,25);
        newWork.add(go);
        newWork.add(workText);
        newWork.setVisible(true);

        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String workUpdate;
                PreparedStatement statement=null;
                ResultSet result=null;
                try{
                    statement = connection.prepareStatement("select * from Student where sid=?");
                    statement.setInt(1,student.getStudentID());
                    result=statement.executeQuery();
                    if(result.next()){
                        student.setWorkExperience(result.getString("workExperience"));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }  finally {
                    JDBC.close(connection,result,statement);
                }
                if(student.getWorkExperience()==null){
                    workUpdate=workText.getText();
                }else{
                    workUpdate=student.getWorkExperience()+"\r\n "+ workText.getText();
                }
                student.setWorkExperience(workUpdate);
                ui.updateStudent(student);
            }
        });
    }

    public static String getName() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Name FROM profile WHERE Username LIKE '" + currUser +"'");
            ResultSet result = statement.executeQuery();
            result.next();
            String name = result.getString("Name");
            return name;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //todo
    public static void createworkexperience(JPanel panel){
        Object[][] jobpostinfo = {
                {student.getWorkExperience()},
        };

        String[] Names = {"Description"};

        JTable table = new JTable(jobpostinfo,Names);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(120,180,500,70);
        panel.add(scrollpane);
        panel.setVisible(true);
    }

    //todo
    public static void createPersonalProject(JPanel panel){

        //setUpStudent();
        Object[][] projectinfo = {
                {student.getPersonalProject()},
        };
        String[] Names = {"Description"};

        JTable table = new JTable(projectinfo,Names);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(120,270,500,70);
        panel.add(scrollpane);
        panel.setVisible(true);
    }


    public static void setStudent(String username){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Student WHERE username=?");
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                student.setStudentID(result.getInt("SID"));
                student.setSin(result.getInt("sin"));
                student.setName(result.getString("name"));
                student.setWorkExperience(result.getString("workExperience"));
                student.setSchool(result.getString("school"));
                student.setPersonalProject(result.getString("personalProject"));
                student.setBirthday(result.getString("birthday"));
                student.setSex(result.getString("sex"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
