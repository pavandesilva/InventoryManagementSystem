/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import common.DB;
import common.MD5;
import common.SystemComponents;
import common.UserDetails;
import common.StatLogging;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Pavan De Silva
 */
public class ResetPassword extends javax.swing.JFrame {

    /**
     * Creates new form ResetPassword
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        usernameLabel = new javax.swing.JLabel();
        usernameTxt = new javax.swing.JTextField();
        selectQuestionLabel = new javax.swing.JLabel();
        questionsCombobox = new javax.swing.JComboBox<>();
        answerLabel = new javax.swing.JLabel();
        newpasswordLabel1 = new javax.swing.JLabel();
        newpasswordPw = new javax.swing.JPasswordField();
        confirmNewPasswordLabel = new javax.swing.JLabel();
        confirmNewPasswordPW = new javax.swing.JPasswordField();
        cancelButton = new javax.swing.JButton();
        changePasswordButton = new javax.swing.JButton();
        answerTxt = new javax.swing.JPasswordField();
        backgroundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Password Reset");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleLabel.setBackground(new java.awt.Color(0, 0, 0));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(153, 153, 153));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Password Reset");
        titleLabel.setToolTipText("");
        titleLabel.setOpaque(true);
        getContentPane().add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 64));

        usernameLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        usernameLabel.setText("Username");

        usernameTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        usernameTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usernameTxtMouseClicked(evt);
            }
        });
        usernameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTxtActionPerformed(evt);
            }
        });

        selectQuestionLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        selectQuestionLabel.setText("Your Security Question");

        questionsCombobox.setEditable(true);
        questionsCombobox.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        questionsCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Your elder child's birth month", "Your pet's name", "Company name of your first job", "Your favourite subject in school", "Your first car ", "Your hobby", "Your age when you got married" }));
        questionsCombobox.setSelectedIndex(-1);

        answerLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        answerLabel.setText("Answer");

        newpasswordLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        newpasswordLabel1.setText("New Password");

        newpasswordPw.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        newpasswordPw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newpasswordPwMouseClicked(evt);
            }
        });
        newpasswordPw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newpasswordPwActionPerformed(evt);
            }
        });

        confirmNewPasswordLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        confirmNewPasswordLabel.setText("Confirm New Password");

        confirmNewPasswordPW.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        confirmNewPasswordPW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmNewPasswordPWActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        changePasswordButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        changePasswordButton.setText("Change Password");
        changePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasswordButtonActionPerformed(evt);
            }
        });

        answerTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        answerTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerTxtActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(usernameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(usernameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(selectQuestionLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(questionsCombobox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(answerLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(newpasswordLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(newpasswordPw, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(confirmNewPasswordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(confirmNewPasswordPW, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cancelButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(changePasswordButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(answerTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(selectQuestionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(58, 58, 58)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(questionsCombobox, 0, 432, Short.MAX_VALUE)
                            .addComponent(usernameTxt)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(confirmNewPasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(answerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(newpasswordLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(cancelButton)
                                .addGap(51, 51, 51)))
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(changePasswordButton)
                                .addGap(206, 206, 206))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(answerTxt)
                                    .addComponent(newpasswordPw, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(confirmNewPasswordPW, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addGap(43, 43, 43))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectQuestionLabel)
                    .addComponent(questionsCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answerLabel)
                    .addComponent(answerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newpasswordLabel1)
                    .addComponent(newpasswordPw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmNewPasswordLabel)
                    .addComponent(confirmNewPasswordPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changePasswordButton)
                    .addComponent(cancelButton))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 850, 550));

        backgroundLabel.setText("backgroundLabel");
        getContentPane().add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 630));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void usernameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTxtActionPerformed

        try {
            sql = "SELECT questionid, backupanswer FROM login WHERE username='" + usernameTxt.getText() + "'";
            rs = DB.search(sql);
            if (rs.next()) {
                questionsCombobox.setSelectedIndex(rs.getInt("questionid"));
                questionsCombobox.setEditable(false);
                JTextField txt = (JTextField) questionsCombobox.getEditor().getEditorComponent();
                txt.setEditable(false);
//                Component [] a = txt.getComponents();
//                for(Component c : a){
//                    c.setEnabled(false);
//                }
                answerTxt.grabFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username", "Error", JOptionPane.ERROR_MESSAGE);
                usernameTxt.setText(null);
                usernameTxt.grabFocus();
            }
        } catch (Exception e) {
            log.errorLog(user, e.getMessage());
        }
    }//GEN-LAST:event_usernameTxtActionPerformed

    private void changePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordButtonActionPerformed
        emptyCheck();
    }//GEN-LAST:event_changePasswordButtonActionPerformed

    private void newpasswordPwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newpasswordPwActionPerformed
        newpasswordPw.setEditable(false);
        confirmNewPasswordPW.grabFocus();
    }//GEN-LAST:event_newpasswordPwActionPerformed

    private void confirmNewPasswordPWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmNewPasswordPWActionPerformed
        emptyCheck();
    }//GEN-LAST:event_confirmNewPasswordPWActionPerformed

    private void newpasswordPwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newpasswordPwMouseClicked
        newpasswordPw.setEditable(true);
        newpasswordPw.setText(null);
        confirmNewPasswordPW.setText(null);
    }//GEN-LAST:event_newpasswordPwMouseClicked

    private void usernameTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usernameTxtMouseClicked
        clearFields();
    }//GEN-LAST:event_usernameTxtMouseClicked

    private void answerTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerTxtActionPerformed

        try {
            if (rs.getString("backupanswer").equals(answerTxt.getText())) {
                newpasswordPw.grabFocus();
            } else {
                components.error(this, "Wrong answer.");
            }
        } catch (Exception e) {
            components.error(this, e.getMessage());
        }
    }//GEN-LAST:event_answerTxtActionPerformed

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
            java.util.logging.Logger.getLogger(ResetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResetPassword().setVisible(true);
            }
        });
    }

    String sql;
    ResultSet rs;
    StatLogging log;
    SystemComponents components;
    UserDetails user;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel answerLabel;
    private javax.swing.JPasswordField answerTxt;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton changePasswordButton;
    private javax.swing.JLabel confirmNewPasswordLabel;
    private javax.swing.JPasswordField confirmNewPasswordPW;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel newpasswordLabel1;
    private javax.swing.JPasswordField newpasswordPw;
    private javax.swing.JComboBox<String> questionsCombobox;
    private javax.swing.JLabel selectQuestionLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTxt;
    // End of variables declaration//GEN-END:variables
}
