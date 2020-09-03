import JDBC.JDBC;
import User.Employer;
import User.*;
import User.Event;
import ui.User_ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.*;

public class employeeUI extends logingui{

    public static JPanel panel2;
    public static JTextField event_id;
    public static JTextField event_date;
    public static JTextField event_Location;
    public static Employer employer=new Employer();
    public static Connection connection = JDBC.connect();
    public static User_ui ui=new User_ui();
    public static PreparedStatement statement=null;
    public static ResultSet result=null;

    public static void setEmployeeManue(String username, String password){
        setEmployer(username, password);
        JPanel manue = new JPanel();
        manue.setLayout(null);
        frame.setSize(800,500);
        manue.setSize(800,100);
        manue.setLocation(0,0);
        manue.setBackground(Color.gray);

        panel2 = new JPanel();
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

        JButton application = new JButton("Application");
        application.setBounds(520,10,150,50);
        application.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                setApplication(panel2);
                panel2.updateUI();
            }
        });
//
        JButton event = new JButton("event");
        event.setBounds(350,10,150,50);
        event.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                setEvent(panel2);
                panel2.updateUI();

            }
        });

        JButton employeeinfo = new JButton("Me");
        employeeinfo.setBounds(690,10,70,50);
        employeeinfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                setPersonalInfo(panel2);
                panel2.updateUI();
            }
        });
//
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

        manue.add(dashboard);
        manue.add(message);
        manue.add(employeeinfo);
        manue.add(application);
        manue.add(event);
        manue.setVisible(true);
        frame.add(manue);
    }

    public static void setEmployer(String username, String password){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employer WHERE Username =?");
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                employer=new Employer(result.getString("username"),password,result.getString("companyName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setEvent(JPanel event){

        event.setLayout(null);
        event.setSize(800,400);
        event.setLocation(0,100);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("id");
        tableModel.addColumn("Event Date");
        tableModel.addColumn("Location");

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM theEvent WHERE companyName =?");
            statement.setString(1,employer.getCompanyName());
            ResultSet result = statement.executeQuery();
            int i=0;
            while(result.next()){
                tableModel.addRow(new Object[]{result.getInt("eventid"), result.getString("eventDate"),result.getString("location")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(20,20,350,300);
        event.add(scrollpane);

        JLabel fastcreate = new JLabel("Quick Create");
        fastcreate.setBounds(500,20,200,20);
        fastcreate.setFont(new Font("Serif", Font.PLAIN, 20));
        event.add(fastcreate);

        event_id = new JTextField("Event ID");
        event_id.setBounds(420,100,100,30);

        event_date = new JTextField("Date:");
        event_date.setBounds(420,150,100,30);

        event_Location = new JTextField("Location:");
        event_Location.setBounds(420,200,100,30);

        addAction(event_date);
        addAction(event_id);
        addAction(event_Location);

        JButton create=new JButton("CREATE");
        create.setBounds(420,250,100,20);

        JTextField deleteID=new JTextField("Delete Event by EventID");
        JButton delete=new JButton("Delete");
        deleteID.setBounds(600,150,200,30);
        delete.setBounds(650,200,80,30);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=Integer.parseInt(event_id.getText());
                String loc=event_Location.getText();
                String date=event_date.getText();
                Event event1=new Event(i,loc,date);
                if(ui.checkExist("select * from theEvent where eventID="+Integer.parseInt(event_id.getText()))){
                    JLabel exist=new JLabel("Create Event Failed");
                    exist.setBounds(300,350,150,30);
                    event.add(exist);
                    exist.setForeground(Color.RED);
                    event.updateUI();
                }else{
                    event1.holdEvent(employer);
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employer.deleteEvent(Integer.parseInt(deleteID.getText()));
            }
        });

        event.add(delete);
        event.add(deleteID);
        event.add(create);
        event.add(event_date);
        event.add(event_id);
        event.add(event_Location);
        event.setVisible(true);
    }

    public static void addAction(JTextField text){

        text.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                text.selectAll();
            }
        });



        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    //add event and update event ui
                    String id = event_id.getText();
                    String date = event_date.getText();
                    String loc = event_Location.getText();
                    setEvent(panel2);
                }
            }});
    }

    //TODO
    public static void setApplication(JPanel application){
        application.setLayout(null);
        application.setSize(800,400);
        application.setLocation(0,100);
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollpane = new JScrollPane(table);
        tableModel.addColumn("Student who apply the posted job");
        scrollpane.setBounds(20,20,600,200);
        ArrayList<String> applications=employer.findApplication();
        for(String s: applications){
            tableModel.addRow(new Object[]{s});
        }

        application.add(scrollpane);

        JTextField interviewSID=new JTextField();
        JLabel sendInterview=new JLabel("Enter Student number to send interview invitation");
        sendInterview.setBounds(30,280,350,25);
        interviewSID.setBounds(100,300,100,25);
        JButton send=new JButton("Send");
        send.setBounds(550,300,100,25);
        JTextField interviewID=new JTextField("Interview ID");
        interviewID.setBounds(380,250,100,25);
        JTextField date=new JTextField("Date");
        date.setBounds(380,290,100,25);
        JTextField location=new JTextField("Location");
        location.setBounds(380,330,100,25);

        application.add(date);
        application.add(location);
        application.add(interviewID);
        application.add(interviewSID);
        application.add(sendInterview);
        application.add(send);

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Interview i=new Interview(date.getText(),location.getText(),Integer.parseInt(interviewID.getText()));
                employer.conductInterview(i,Integer.parseInt(interviewSID.getText()));
            }
        });

        application.setVisible(true);
    }

    public static void setdashboard(JPanel dashboard){
        dashboard.setLayout(null);
        dashboard.setSize(800,400);

        dashboard.setLocation(0,100);

        JLabel welcome = new JLabel("Welcome!");
        welcome.setBounds(10,10,150,50);
        dashboard.add(welcome);


        JTextField postDate=new JTextField("Post Date");
        JTextField position=new JTextField("Position");
        JTextField requirment=new JTextField("Requirement");

        postDate.setBounds(50,100,150,30);
        position.setBounds(50,180,150,30);
        requirment.setBounds(50,260,300,30);

        JButton post=new JButton("POST");
        post.setBounds(100,340,60,30);

        dashboard.add(position);
        dashboard.add(postDate);
        dashboard.add(requirment);
        dashboard.add(post);

        post.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JobPost job=new JobPost(postDate.getText(), position.getText(),employer.getCompanyName());
                job.setRequirement(requirment.getText());
                if(ui.checkExist("select * from JobPost where postDate="+"'"+postDate.getText()+"'"+"AND position="+
                        "'"+position.getText()+"'"+"AND companyName="+"'"+employer.getCompanyName()+"'")){
                    JLabel exist=new JLabel("Post Failed");
                    exist.setBounds(300,300,150,30);
                    dashboard.add(exist);
                    exist.setForeground(Color.RED);
                    dashboard.updateUI();
                }
                else{
                    employer.postJob(job);
                }
            }
        });
        dashboard.setVisible(true);
    }

    public static void setPersonalInfo(JPanel me){
        me.setLayout(null);
        me.setSize(800,400);
        me.setLocation(0,100);

        JLabel title = new JLabel("PERSONAL INFO");
        title.setBounds(0,0,800,55);
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setHorizontalAlignment(JLabel.CENTER);

        JLabel name = new JLabel("Company Name:"+employer.getCompanyName());
        name.setBounds(10,90,300,20);

        me.add(name);
        me.add(title);

        me.setVisible(true);
    }

    public static void setMessage(JPanel message){
        message.setLayout(null);
        message.setSize(800,400);
        message.setLocation(0,100);

        message.setVisible(true);
    }

}
