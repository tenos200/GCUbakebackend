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
import javax.swing.table.DefaultTableModel;


public class staffPage extends javax.swing.JFrame {
    Menu run = new Menu();
    PreparedStatement pst = null;
    Connection conn = null;
    private String query;
    private String SelectedRow;
    private int parse;
    private String completed = "Star-Baker";
    private String ongoing = "on-going";
    private String cancelled = "cancelled";
    private String queryStarBaker;
    private String queryCancel;
    
    
    
    

    
    public staffPage() {
        initComponents();
        checkBookings();
       
        
    }
   
    
    private void checkBookings(){
            
        //refactoring of code used to check available lessons but used to fill combobox in staff edit page.
        run.getConnection();
        conn = run.con;
        
        try{

            pst = conn.prepareStatement("SELECT * FROM Lessons;");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel table = (DefaultTableModel)tableStaffbooking.getModel();
            table.setRowCount(0);
          
            while(rs.next()){
                
                //adds in the appopriate values into the right class
                Object bookings[]={rs.getInt("lessonID"),rs.getString("Customer"),rs.getString("lessonType"), rs.getString("lessonTime"),rs.getString("lessonDate"), rs.getString("chef"), rs.getString("sessionsRequired"), rs.getString("GCU_status")};
                table.addRow(bookings);
                
                }
            
            conn.close();
        }
        catch(Exception e){

            System.err.print(e);

        }   
    }
    
    
    private void createLesson(){
    run.getConnection();
        conn = run.con;

        try{

            query = "INSERT INTO Lessons(lessontype, chef, sessionsRequired)"
            + "VALUES(?, ?, ?);";
            pst = conn.prepareStatement(query);
            pst.setString(1, txtLessonType.getText());
            pst.setString(2, txtLessonChef.getText());
            pst.setString(3, cmbSessionsRequired.getSelectedItem().toString());
            pst.execute();
            JOptionPane.showMessageDialog(null, "LessonType: " + txtLessonType.getText()+ "\nChef:" + txtLessonChef.getText() 
                    + "\nSessions required:"+ cmbSessionsRequired.getSelectedItem().toString()+ "\nRegistered");
            txtLessonType.setText("");
            txtLessonChef.setText("");
            

        }

        catch(Exception e){
            System.err.print(e);

        }
    }
    
    private void viewCreatedlessons(){
        run.getConnection();
        conn = run.con;
        //method is used to show what entries are made in the Lesson table:
        try{

            pst = conn.prepareStatement("SELECT * FROM Lessons");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel table = (DefaultTableModel)tableLessons.getModel();
            table.setRowCount(0);

            while(rs.next()){

                Object viewTable[]={rs.getInt("LessonID"), rs.getString("lessonType"), rs.getString("chef"), rs.getString("sessionsRequired")};
                table.addRow(viewTable);

            }

        }

        catch(Exception e){

            System.err.print(e);

        }
        }
    
    private void setOngoingStatus(){
    //sets the status of the customers to on-going
        run.getConnection();
        conn = run.con;

        try{
            SelectedRow = tableStaffbooking.getValueAt(tableStaffbooking.getSelectedRow(), 0).toString();
            int i = Integer.parseInt(SelectedRow);
            
            query = "UPDATE Lessons set GCU_status = '"+ ongoing +"' where LessonID =" + i +";";

            pst = conn.prepareStatement(query);
            pst.execute();
            conn.close();

        }

        catch(Exception e){

            System.err.print(e);

        }
    }
    
    private void setCompleteStatus(){
        
        // start baker button, sets the customer status in a database to star-baker
        run.getConnection();
        conn = run.con;

        try{

            SelectedRow = tableStaffbooking.getValueAt(tableStaffbooking.getSelectedRow(), 0).toString();
            System.out.print(SelectedRow);
            int j = Integer.parseInt(SelectedRow);
            completed = "Star-Baker";
            queryStarBaker = "UPDATE Lessons set GCU_status ='"+ completed +"' where lessonID =" + j + ";";

            pst = conn.prepareStatement(queryStarBaker);
            pst.execute();
            conn.close();

        }

        catch(Exception e){
            System.err.print(e);

        }
    }
    
    private void setCancelStatus(){
        
        //cancel button
        run.getConnection();
        conn = run.con;

        try{

            SelectedRow = tableStaffbooking.getValueAt(tableStaffbooking.getSelectedRow(), 0).toString();
            parse = Integer.parseInt(SelectedRow);
            System.out.print(SelectedRow);
            queryCancel = "UPDATE Lessons set GCU_status = '"+ cancelled +"' where lessonID = "+ parse + ";";

            pst = conn.prepareStatement(queryCancel);
            pst.execute();
            conn.close();

        }

        catch(Exception e){
            System.err.print(e);

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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableStaffbooking = new javax.swing.JTable();
        update_bookings_ = new javax.swing.JButton();
        btnOngoing = new javax.swing.JButton();
        btnCompleted = new javax.swing.JButton();
        cancel_booking_ = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableLessons = new javax.swing.JTable();
        create_lesson_ = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtLessonType = new javax.swing.JTextField();
        cmbSessionsRequired = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtLessonChef = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        loggout_staff_ = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableStaffbooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Customer", "Type", "Date", "Time", "Chef", "Sessions", "Status"
            }
        ));
        jScrollPane2.setViewportView(tableStaffbooking);

        update_bookings_.setText("Update");
        update_bookings_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_bookings_ActionPerformed(evt);
            }
        });

        btnOngoing.setBackground(new java.awt.Color(255, 255, 153));
        btnOngoing.setText("Set on-going");
        btnOngoing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOngoingActionPerformed(evt);
            }
        });

        btnCompleted.setBackground(new java.awt.Color(153, 255, 153));
        btnCompleted.setText("Set completed");
        btnCompleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompletedActionPerformed(evt);
            }
        });

        cancel_booking_.setBackground(new java.awt.Color(228, 56, 56));
        cancel_booking_.setText("Cancel");
        cancel_booking_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_booking_ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(update_bookings_, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnOngoing, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(cancel_booking_, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(update_bookings_, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addComponent(btnOngoing, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCompleted, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addComponent(cancel_booking_, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bookings", jPanel1);

        tableLessons.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "LessonID", "Type", "Chef", "Sessions"
            }
        ));
        jScrollPane1.setViewportView(tableLessons);

        create_lesson_.setText("Create Lesson");
        create_lesson_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_lesson_ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel5.setText("Lesson Type: ");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel6.setText("Sessions Required:");

        cmbSessionsRequired.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        cmbSessionsRequired.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel1.setText("Chef:");

        txtLessonChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLessonChefActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtLessonChef, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbSessionsRequired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtLessonType, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(create_lesson_, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLessonType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(cmbSessionsRequired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtLessonChef, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(141, 141, 141)))))
                .addGap(23, 23, 23)
                .addComponent(create_lesson_, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jTabbedPane1.addTab("Create Lessons", jPanel2);

        loggout_staff_.setText("Logout");
        loggout_staff_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loggout_staff_ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(280, Short.MAX_VALUE)
                .addComponent(loggout_staff_, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(265, 265, 265))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(375, Short.MAX_VALUE)
                .addComponent(loggout_staff_, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jTabbedPane1.addTab("Logout", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loggout_staff_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loggout_staff_ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        JOptionPane.showMessageDialog(null, "Logged out");
        Menu goback = new Menu();
        run.setVisible(true);
    }//GEN-LAST:event_loggout_staff_ActionPerformed

    private void create_lesson_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_lesson_ActionPerformed
        //creates the lesson and then updates the j table when the button create lesson is clicked
        createLesson();
        viewCreatedlessons();
    }//GEN-LAST:event_create_lesson_ActionPerformed

    private void txtLessonChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLessonChefActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLessonChefActionPerformed

    private void cancel_booking_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_booking_ActionPerformed

        //cancel button
        setCancelStatus();
        
    }//GEN-LAST:event_cancel_booking_ActionPerformed

    private void btnCompletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompletedActionPerformed

        // start baker button
        setCompleteStatus();
    }//GEN-LAST:event_btnCompletedActionPerformed

    private void btnOngoingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOngoingActionPerformed

        //sets the status to on-going
        setOngoingStatus();

    }//GEN-LAST:event_btnOngoingActionPerformed

    private void update_bookings_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_bookings_ActionPerformed
        //view all bookings done by customers
        checkBookings();
    }//GEN-LAST:event_update_bookings_ActionPerformed

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
            java.util.logging.Logger.getLogger(staffPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(staffPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(staffPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(staffPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new staffPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompleted;
    private javax.swing.JButton btnOngoing;
    private javax.swing.JButton cancel_booking_;
    private javax.swing.JComboBox<String> cmbSessionsRequired;
    private javax.swing.JButton create_lesson_;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton loggout_staff_;
    private javax.swing.JTable tableLessons;
    private javax.swing.JTable tableStaffbooking;
    private javax.swing.JTextField txtLessonChef;
    private javax.swing.JTextField txtLessonType;
    private javax.swing.JButton update_bookings_;
    // End of variables declaration//GEN-END:variables
}
