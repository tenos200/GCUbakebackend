/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gcurefined;

/**
 *
 * @author timenos
 */
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Menu extends javax.swing.JFrame {
    Connection con = null;
    PreparedStatement pst = null;
    Statement stmt = null;
    ResultSet rs = null;
    private String logged_in_customer;
    private String createTable1;
    private String createTable2;
    private String createTable3; 
    private String createTable4;
    private String query;
    private String customerCreate;
    private String database;
    private String username;
    private String password;
    
    

    //Creates new form Menu
     
    public Menu() {
        initComponents();
        createDatabase();
        createTables();
        
    }
    
    
    protected void createDatabase(){
        //creates the database GCUBake
        
        try{
        
        database = "jdbc:mysql://127.0.0.1:3306/";
        username = "root";
        password = "Password";
        
        con = DriverManager.getConnection(database, username, password);
        
        query = "CREATE DATABASE IF NOT EXISTS GCUBake";
        
        pst = con.prepareStatement(query);
        pst.execute();
        con.close();
        }
        
        catch(Exception e){
        System.err.println(e.getMessage());
        
        }
    }
    
       
        
        
        
    
    protected Connection getConnection(){
    //creates the connection for the mysql server 
    
        try{
        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/GCUBake";
        String username = "root";
        String password = "Password";
        con = DriverManager.getConnection(mysqlUrl ,username,password);
        
        return con;
        }
        catch(Exception e){
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
        
        
        }return null;
    
    
    }
    protected void createTables(){
        try{
            getConnection();
            //A backend method which creates the tables on the specified unit if it does not exist
           createTable1 = "CREATE TABLE IF NOT EXISTS Customer("
                 + "customerID INT NOT NULL AUTO_INCREMENT, "
                 + "PRIMARY KEY(customerID),"
                 + "title TEXT,"
                 + "firstname TEXT,"
                 + "lastname TEXT,"
                 + "contactNo TEXT,"
                 + "email TEXT,"
                 + "username VARCHAR(250),"
                 + "password TEXT,"
                 + "UNIQUE KEY(username));";
            
           
            pst = con.prepareStatement(createTable1);
            pst.execute();
            
                createTable2 = "CREATE TABLE IF NOT EXISTS Staff("
                 + "staffID INT NOT NULL AUTO_INCREMENT, "
                 + "PRIMARY KEY(staffID),"
                 + "role TEXT,"
                 + "firstname TEXT,"
                 + "lastname TEXT,"
                 + "contactNo TEXT,"
                 + "email TEXT,"
                 + "username VARCHAR(250),"
                 + "password TEXT,"
                 + "UNIQUE(username)"
                 + ");";
            pst = con.prepareStatement(createTable2);
            pst.execute();
         
            
                createTable3 = "CREATE TABLE IF NOT EXISTS Login("
                 + "loginID INT NOT NULL AUTO_INCREMENT, "
                 + "PRIMARY KEY(loginID),"
                 + "username TEXT,"
                 + "password TEXT);";
            pst = con.prepareStatement(createTable3);
            pst.execute();
            
            
                createTable4 = "CREATE TABLE IF NOT EXISTS Lessons("
                 + "lessonID INT NOT NULL AUTO_INCREMENT, "
                 + "PRIMARY KEY(lessonID),"
                 + "Customer VARCHAR(250),"
                 + "lessonType VARCHAR(250),"
                 + "lessonTime VARCHAR(250),"
                 + "lessonDate VARCHAR(250),"
                 + "chef TEXT,"
                 + "sessionsRequired TEXT,"
                 + "GCU_status TEXT,"
                 + "FOREIGN KEY(Customer) REFERENCES Customer(username)"
                 + ");";
            
            pst = con.prepareStatement(createTable4);
            pst.execute();
            
            con.close();
            
            
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Date already set up");
            }
    
    }
    
    private void passUsername(){
        /*this method was built to pass the username which was eneterd as a string in order to 
        be able to identify which user was logged in.
        */
        
        logged_in_customer = txtLoginuser.getText();
        GCUbake test = new GCUbake(logged_in_customer);
        this.dispose();
        test.setVisible(true);
    }
    
    private void staffLoggin(){
        try{
            getConnection();
            
            
        query ="SELECT * FROM Staff Where username='"+txtStaffloginuser.getText()+ "' and password='" +txtStaffloginpass.getText() + "'";
        pst=con.prepareStatement(query);
        rs=pst.executeQuery();
        
        
        if(rs.next()){
            JOptionPane.showMessageDialog(null, "Logged in as staff");
            this.dispose();
            staffPage run = new staffPage();
            run.setVisible(true);
            
            
        }
        
        else{
            JOptionPane.showMessageDialog(null, "Invalid credentials");
            
            
            }                                        
        } 
        catch(Exception e){
        JOptionPane.showMessageDialog(null, e);        
        }
    }
    
    private void registerCustomer(){
    //create my mysql database connection
        getConnection();

        try{
            //if statement ensures that both passwords entered match

            if(regPassword1.getText().equals(regPassword2.getText())){
                customerCreate = "INSERT INTO Customer(title, firstname, lastname, contactNo, email, username, password)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                pst = con.prepareStatement(customerCreate);

                pst.setString(1,cmbRegtitle.getSelectedItem().toString());
                pst.setString(2,txtRegfirstname.getText());
                pst.setString(3,txtReglastname.getText());
                pst.setString(4,txtRegphone.getText());
                pst.setString(5,txtRegemail.getText());
                pst.setString(6,txtRegusername.getText());
                pst.setString(7,regPassword1.getText());
                pst.execute();

                String query2 = "INSERT INTO Login(username, password)" + "VALUES (?, ?)";
                pst = con.prepareStatement(query2);
                pst.setString(1,txtRegusername.getText());
                pst.setString(2,regPassword1.getText());
                pst.execute();
                con.close();
                JOptionPane.showMessageDialog(null, "Registration completed!");

                txtRegfirstname.setText("");
                txtReglastname.setText("");
                txtRegphone.setText("");
                txtRegemail.setText("");
                txtRegusername.setText("");
                regPassword1.setText("");
                regPassword2.setText("");
                this.dispose();
                logged_in_customer = txtRegusername.getText();
                passUsername();
                
            }

            else{

                JOptionPane.showMessageDialog(null, "Passwords do not match");

            }
        }

        catch (Exception e){

            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            /*added statement here since I could not figure out a way to print specific
            error when uniquness constraint was met on username column in customer table
            */
            JOptionPane.showMessageDialog(null, "Username already taken");
        }
    }
    
    private void customerLoggin(){
    
        try{
            getConnection();

            query ="SELECT * FROM Login Where username='"+txtLoginuser.getText()+ "' and password='" +txtLoginpass.getText() + "'";

            pst=con.prepareStatement(query);
            rs=pst.executeQuery();

            if(rs.next()){
                logged_in_customer = txtLoginuser.getText();
                JOptionPane.showMessageDialog(null, "Login Successful");
                System.out.println(logged_in_customer);
                passUsername();
            }

            else{
                JOptionPane.showMessageDialog(null, "Invalid credentials");

            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        Booking = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtLoginuser = new javax.swing.JTextField();
        btnLoginin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtLoginpass = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnRegister = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbRegtitle = new javax.swing.JComboBox<>();
        txtRegfirstname = new javax.swing.JTextField();
        txtReglastname = new javax.swing.JTextField();
        txtRegphone = new javax.swing.JTextField();
        txtRegemail = new javax.swing.JTextField();
        txtRegusername = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        regPassword1 = new javax.swing.JPasswordField();
        regPassword2 = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtStaffloginuser = new javax.swing.JTextField();
        btnStafflogin = new javax.swing.JButton();
        txtStaffloginpass = new javax.swing.JPasswordField();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 283, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        Booking.setBackground(new java.awt.Color(102, 102, 102));

        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        txtLoginuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginuserActionPerformed(evt);
            }
        });

        btnLoginin.setFont(new java.awt.Font("Monaco", 1, 16)); // NOI18N
        btnLoginin.setText("Login");
        btnLoginin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogininActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Monaco", 1, 16)); // NOI18N
        jLabel1.setText("Username:");

        jLabel2.setFont(new java.awt.Font("Monaco", 1, 16)); // NOI18N
        jLabel2.setText("Password:");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gcurefined/GCUBake_logo_resized.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Marker Felt", 3, 18)); // NOI18N
        jLabel15.setText("Keep a dough profile!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLoginpass, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLoginin, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLoginuser, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(38, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(130, 130, 130))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLoginuser, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(txtLoginpass, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnLoginin, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        Booking.addTab("Login", jPanel1);

        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel3.setText("Firstname");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel4.setText("Surname");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel5.setText("Phone");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel6.setText("Email");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel7.setText("Username");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel8.setText("Password");

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel10.setText("Title");

        cmbRegtitle.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        cmbRegtitle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mr", "Ms" }));

        txtRegfirstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRegfirstnameActionPerformed(evt);
            }
        });

        txtReglastname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReglastnameActionPerformed(evt);
            }
        });

        txtRegphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRegphoneActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel12.setText("Re-enter password");

        regPassword1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regPassword1ActionPerformed(evt);
            }
        });

        regPassword2.setMinimumSize(new java.awt.Dimension(12, 26));
        regPassword2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regPassword2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRegphone, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReglastname, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRegfirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRegemail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(106, 106, 106)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(regPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRegusername, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRegtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRegfirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(cmbRegtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtReglastname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(txtRegusername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtRegphone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtRegemail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Booking.addTab("Register", jPanel2);

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel9.setText("Username:");

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel11.setText("Password:");

        btnStafflogin.setText("Login");
        btnStafflogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStaffloginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(322, 322, 322)
                .addComponent(jLabel9)
                .addContainerGap(309, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnStafflogin, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addComponent(txtStaffloginpass, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addComponent(txtStaffloginuser, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                .addGap(198, 198, 198))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(315, 315, 315))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtStaffloginuser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(txtStaffloginpass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(btnStafflogin, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Booking.addTab("Staff", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Booking, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Booking, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

       
    
    private void btnStaffloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffloginActionPerformed
        staffLoggin();
        
    }//GEN-LAST:event_btnStaffloginActionPerformed

    private void regPassword1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regPassword1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regPassword1ActionPerformed

    private void txtRegphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRegphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegphoneActionPerformed

    private void txtReglastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReglastnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReglastnameActionPerformed

    private void txtRegfirstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRegfirstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegfirstnameActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        registerCustomer();

    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnLogininActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogininActionPerformed

        customerLoggin();
    }//GEN-LAST:event_btnLogininActionPerformed

    private void txtLoginuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoginuserActionPerformed

    private void regPassword2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regPassword2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regPassword2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Booking;
    private javax.swing.JButton btnLoginin;
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton btnStafflogin;
    private javax.swing.JComboBox<String> cmbRegtitle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPasswordField regPassword1;
    private javax.swing.JPasswordField regPassword2;
    private javax.swing.JPasswordField txtLoginpass;
    private javax.swing.JTextField txtLoginuser;
    private javax.swing.JTextField txtRegemail;
    private javax.swing.JTextField txtRegfirstname;
    private javax.swing.JTextField txtReglastname;
    private javax.swing.JTextField txtRegphone;
    private javax.swing.JTextField txtRegusername;
    private javax.swing.JPasswordField txtStaffloginpass;
    private javax.swing.JTextField txtStaffloginuser;
    // End of variables declaration//GEN-END:variables
}
