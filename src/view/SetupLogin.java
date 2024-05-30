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

/**
 *
 * @author Pavan De Silva
 */
public class SetupLogin extends javax.swing.JFrame {

    /**
     * Creates new form ResetPassword
     */
    
     public SetupLogin() {
        initComponents();
        otherComponents();
        this.user = user;
        this.log = new StatLogging();
        log.infoLog(user, "Logged into Login setup");
    }
    public SetupLogin(UserDetails user) {
        initComponents();
        otherComponents();
        this.user = user;
        this.log = new StatLogging();
        log.infoLog(user, "Logged into Login setup");
    }

    public SetupLogin(UserDetails user, String id) {
        initComponents();
        otherComponents();
        employeeIDTxt.setText(id);
        employeeIDTxt.setEditable(false);
        this.user = user;
        this.log = new StatLogging();
        log.infoLog(this.user, "Logged into Login setup");
    }

    private void otherComponents() {
        try {
            URL path = this.getClass().getResource("data/EMBackground.jpg");
            File imageFile = new File(path.getFile());
            Image img = ImageIO.read(imageFile);
            img = img.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
            backgroundLabel.setIcon(new ImageIcon(img));
            components = new SystemComponents();
            usernameTxt.grabFocus();
        } catch (Exception e) {
            log.errorLog(user, e.getMessage());
        }
    }

    private void pwCheck() {
        if (!pwCheck(passwordPw.getText(), confirmPasswordPW.getText())) {
            JOptionPane.showMessageDialog(this, "Password and Confirm password does not match", "Error", JOptionPane.ERROR_MESSAGE);
            passwordPw.setEditable(true);
            passwordPw.setText(null);
            confirmPasswordPW.setText(null);
            passwordPw.grabFocus();
        } else {
            questionsCombobox.showPopup();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        EmployeeIDLabel = new javax.swing.JLabel();
        employeeIDTxt = new javax.swing.JTextField();
        usernameLabel = new javax.swing.JLabel();
        usernameTxt = new javax.swing.JTextField();
        passwordLabel1 = new javax.swing.JLabel();
        passwordPw = new javax.swing.JPasswordField();
        confirmPasswordLabel = new javax.swing.JLabel();
        confirmPasswordPW = new javax.swing.JPasswordField();
        selectQuestionLabel = new javax.swing.JLabel();
        questionsCombobox = new javax.swing.JComboBox<>();
        answerLabel = new javax.swing.JLabel();
        answerTxt = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        setupLoginButton = new javax.swing.JButton();
        backgroundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Setup Login");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleLabel.setBackground(new java.awt.Color(0, 0, 0));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(153, 153, 153));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("System User Login Creation");
        titleLabel.setToolTipText("");
        titleLabel.setOpaque(true);
        getContentPane().add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 64));

        EmployeeIDLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        EmployeeIDLabel.setText("Employee ID");

        employeeIDTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        employeeIDTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeIDTxtActionPerformed(evt);
            }
        });

        usernameLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        usernameLabel.setText("Username");

        usernameTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        usernameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTxtActionPerformed(evt);
            }
        });

        passwordLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        passwordLabel1.setText("Password");

        passwordPw.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        passwordPw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passwordPwMouseClicked(evt);
            }
        });
        passwordPw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordPwActionPerformed(evt);
            }
        });

        confirmPasswordLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        confirmPasswordLabel.setText("Confirm Your Password");

        confirmPasswordPW.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        confirmPasswordPW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmPasswordPWActionPerformed(evt);
            }
        });

        selectQuestionLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        selectQuestionLabel.setText("Select Your Security Question");

        questionsCombobox.setEditable(true);
        questionsCombobox.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        questionsCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Your elder child's birth month", "Your pet's name", "Company name of your first job", "Your favourite subject in school", "Your first car ", "Your hobby", "Your age when you got married" }));
        questionsCombobox.setSelectedIndex(-1);
        questionsCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                questionsComboboxActionPerformed(evt);
            }
        });

        answerLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        answerLabel.setText("Answer");

        answerTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        answerTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerTxtActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        setupLoginButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        setupLoginButton.setText("Setup Login");
        setupLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupLoginButtonActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(EmployeeIDLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(employeeIDTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(usernameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(usernameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(passwordLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(passwordPw, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(confirmPasswordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(confirmPasswordPW, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(selectQuestionLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(questionsCombobox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(answerLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(answerTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cancelButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(setupLoginButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(EmployeeIDLabel)
                        .addGap(18, 18, 18)
                        .addComponent(employeeIDTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(selectQuestionLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(confirmPasswordLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(passwordLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(93, 93, 93))
                            .addComponent(answerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(cancelButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(setupLoginButton)
                        .addGap(229, 229, 229))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(questionsCombobox, javax.swing.GroupLayout.Alignment.LEADING, 0, 397, Short.MAX_VALUE)
                            .addComponent(confirmPasswordPW, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(answerTxt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameTxt)
                            .addComponent(passwordPw))
                        .addGap(100, 100, 100))))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmployeeIDLabel)
                    .addComponent(employeeIDTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel1)
                    .addComponent(passwordPw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmPasswordLabel)
                    .addComponent(confirmPasswordPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectQuestionLabel)
                    .addComponent(questionsCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(answerLabel))
                .addGap(40, 40, 40)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setupLoginButton)
                    .addComponent(cancelButton))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 850, 580));

        backgroundLabel.setText("backgroundLabel");
        getContentPane().add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 670));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void setupLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupLoginButtonActionPerformed
        dataCheck();
    }//GEN-LAST:event_setupLoginButtonActionPerformed

    private void answerTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerTxtActionPerformed
        dataCheck();
    }//GEN-LAST:event_answerTxtActionPerformed

    private void passwordPwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordPwActionPerformed
        passwordPw.setEditable(false);
        confirmPasswordPW.grabFocus();
    }//GEN-LAST:event_passwordPwActionPerformed

    private void confirmPasswordPWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmPasswordPWActionPerformed
        pwCheck();

    }//GEN-LAST:event_confirmPasswordPWActionPerformed

    private void passwordPwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordPwMouseClicked
        passwordPw.setEditable(true);
        passwordPw.setText(null);
        confirmPasswordPW.setText(null);
    }//GEN-LAST:event_passwordPwMouseClicked

    private void usernameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTxtActionPerformed
        passwordPw.grabFocus();
    }//GEN-LAST:event_usernameTxtActionPerformed

    private void questionsComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_questionsComboboxActionPerformed
        answerTxt.grabFocus();
    }//GEN-LAST:event_questionsComboboxActionPerformed

    private void employeeIDTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeIDTxtActionPerformed
        checkLogin();
    }//GEN-LAST:event_employeeIDTxtActionPerformed

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
            java.util.logging.Logger.getLogger(SetupLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SetupLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SetupLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SetupLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new SetupLogin().setVisible(true);
            }
        });
    }

    String sql;
    ResultSet rs;
    UserDetails user;
    SystemComponents components;
    StatLogging log;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EmployeeIDLabel;
    private javax.swing.JLabel answerLabel;
    private javax.swing.JTextField answerTxt;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel confirmPasswordLabel;
    private javax.swing.JPasswordField confirmPasswordPW;
    private javax.swing.JTextField employeeIDTxt;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel passwordLabel1;
    private javax.swing.JPasswordField passwordPw;
    private javax.swing.JComboBox<String> questionsCombobox;
    private javax.swing.JLabel selectQuestionLabel;
    private javax.swing.JButton setupLoginButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTxt;
    // End of variables declaration//GEN-END:variables
}
