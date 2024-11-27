package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JTextField userText, passwordText;                  // these have to be declared globally because these have to be copied to the backend ass well
    JButton loginButton, cancelButton, signupButton;
    Choice loginChoice;
    Login(){

        super("Login");
        getContentPane().setBackground(Color.white);
        setSize(640,300);

        JLabel username = new JLabel("Username");               //adding username label
        username.setBounds(300, 60, 100, 20);
        add(username);

        userText = new JTextField();                     //adding username textfield
        userText.setBounds(400, 60, 150, 20);
        add(userText);

        JLabel password = new JLabel("Password");               //adding password label
        password.setBounds(300, 100, 100, 20);
        add(password);

        passwordText = new JTextField();                     //adding password textfield
        passwordText.setBounds(400, 100, 150, 20);
        add(passwordText);

        JLabel loggin = new JLabel("Loggin In As");     // adding loggin as label
        loggin.setBounds(300,140,100,20);
        add(loggin);

        loginChoice = new Choice();                         // adding the option to sign in as admin or customer
        loginChoice.add("Customer");
        loginChoice.add("Admin");
        loginChoice.setBounds(400,140,150,20);
        add(loginChoice);

        loginButton = new JButton("Login");             // adding the login button
        loginButton.setBounds(330,180, 100,20);
        loginButton.addActionListener(this);
        add(loginButton);

        cancelButton = new JButton("Cancel");            // adding the cancel button
        cancelButton.setBounds(460,180,100,20);
        cancelButton.addActionListener(this);
        add(cancelButton);

        signupButton = new JButton("Sign Up");          //adding the signup button
        signupButton.setBounds(390,210,100,20);
        signupButton.addActionListener(this);
        add(signupButton);

        ImageIcon profileOne = new ImageIcon(ClassLoader.getSystemResource("icon/profile.png"));
        Image profileTwo = profileOne.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT);
        ImageIcon fprofileOne = new ImageIcon(profileTwo);      //adding the image
        JLabel profileLabel = new JLabel(fprofileOne);
        profileLabel.setBounds(5,5,250,250);
        add(profileLabel);


        setLocation(400,200);           // specifications of the frame
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton){
            String susername = userText.getText();
            String spassword = passwordText.getText();
            String suser = loginChoice.getSelectedItem();

            try{
                database c = new database();
                String query = "select * from Signup where username = '"+susername+"' and password = '"+spassword+"' and usertype = '"+suser+"'";
                ResultSet resultset = c.statement.executeQuery(query);
                if(resultset.next()){
                    String meter = resultset.getString("meter_no");
                    setVisible(false);
                    new main_class(suser,meter);
                }else{
                    JOptionPane.showMessageDialog(null,"Invalid Login");
                }


            }catch(Exception E){
                E.printStackTrace();
            }

        }
        else if(e.getSource() == cancelButton){
            setVisible(false);
        }
        else if(e.getSource() == signupButton){
            setVisible(false);
            new Signup();
        }

    }

    public static void main(String[] args) {
        new Login();
    }
}
