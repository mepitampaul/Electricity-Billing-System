package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Signup extends JFrame implements ActionListener {
    Choice loginAsCho;                                              //declaring it globally
    TextField meterText, employerText, userNameText, nameText, passwordText;

    JButton create, back;
    Signup(){
        super("Signup Page");
        getContentPane().setBackground(new Color(168,203,255));

        JLabel createAs = new JLabel("Create Account As");      //adding create account as label
        createAs.setBounds(30,50,125,20);
        add(createAs);

        loginAsCho = new Choice();                                  // adding create account options
        loginAsCho.add("Customer");
        loginAsCho.add("Admin");
        loginAsCho.setBounds(170,50,120,20);
        add(loginAsCho);

        JLabel meterNo = new JLabel("Meter Number");            //adding meter number label
        meterNo.setBounds(30,100,125,20);
        meterNo.setVisible(true);
        add(meterNo);

        meterText = new TextField();                                 //adding meter number textfield
        meterText.setBounds(170,100,125,20);
        meterText.setVisible(true);
        add(meterText);

        JLabel employer = new JLabel("Employer ID");            //adding employer id label
        employer.setBounds(30,100,125,20);
        employer.setVisible(false);
        add(employer);

        employerText = new TextField();                               //adding employer id textfield
        employerText.setBounds(170,100,125,20);
        employerText.setVisible(false);
        add(employerText);

        JLabel userName = new JLabel("Username");
        userName.setBounds(30,140,125,20);
        add(userName);

        userNameText = new TextField();
        userNameText.setBounds(170,140,125,20);
        add(userNameText);

        JLabel name = new JLabel("Name");
        name.setBounds(30,180,125,20);
        add(name);

        nameText = new TextField("");
        nameText.setBounds(170,180,125,20);
        add(nameText);

        meterText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                    database c = new database();
                    ResultSet resultSet = c.statement.executeQuery("select * from Signup where meter_no = '"+meterText.getText()+"'");
                    if(resultSet.next()){
                        nameText.setText(resultSet.getString("name"));
                    }
                }catch(Exception E){
                    E.printStackTrace();
                }
            }
        });

        JLabel password = new JLabel("Password");
        password.setBounds(30,220,125,20);
        add(password);

        passwordText = new TextField();
        passwordText.setBounds(170,220,125,20);
        add(passwordText);

        loginAsCho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = loginAsCho.getSelectedItem();
                if(user.equals("Admin")){
                    meterNo.setVisible(false);
                    meterText.setVisible(false);
                    employer.setVisible(true);
                    employerText.setVisible(true);
                }
                else{
                    meterNo.setVisible(true);
                    meterText.setVisible(true);
                    nameText.setEditable(false);
                    employer.setVisible(false);
                    employerText.setVisible(false);
                }
            }
        });

        create = new JButton("Create");
        create.setBackground(new Color(8, 74, 189, 222));
        create.setForeground(Color.white);
        create.setBounds(50,285,100,25);
        create.addActionListener(this);
        add(create);

        back = new JButton("Back");
        back.setBackground(new Color(8, 74, 189, 222));
        back.setForeground(Color.white);
        back.setBounds(180,285,100,25);
        back.addActionListener(this);
        add(back);

        ImageIcon boyIcon = new ImageIcon(ClassLoader.getSystemResource("icon/boy.png"));
        Image boyImg = boyIcon.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT);
        ImageIcon boyIcon2 = new ImageIcon(boyImg);
        JLabel boyLabel = new JLabel(boyIcon2);
        boyLabel.setBounds(320,30,250,250);
        add(boyLabel);

        setLocation(500,200);
        setSize(600,380);
        setLayout(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == create){
            String sLoginAs = loginAsCho.getSelectedItem();
            String sUsername = userNameText.getText();
            String sName = nameText.getText();
            String spassword = passwordText.getText();
            String smeter = meterText.getText();
            try{
                database c = new database();
                String query = null;
                if(loginAsCho.equals("Admin")) {
                    query = "insert into Signup value ('" + smeter + "', '" + sUsername + "', '" + sName + "','" + spassword + "','" + sLoginAs + "' )";
                }else{
                    query = "update Signup set username = '"+sUsername+"', password = '"+spassword+"', usertype = '"+sLoginAs+"' where meter_no = '"+smeter+"'";
                }

                c.statement.executeUpdate(query);

                JOptionPane.showMessageDialog(null,"Account Created");
                setVisible(false);
                new Login();
            }catch(Exception E){
                E.printStackTrace();
            }
        }
        else if(e.getSource() == back){
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {

        new Signup();

    }
}
