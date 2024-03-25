package view;

import common.UserDetails;
import common.DB;
import common.MD5;
import common.StatLogging;
import java.awt.Image;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Pavan De Silva
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        otherComponents();
    }

    private void login() {
        try {
            sql = "SELECT login.employeeid, login.username, login.password, login.status, employee.firstname, employee.lastname, employee.type, employee.status FROM login INNER JOIN employee ON login.employeeid=employee.employeeid WHERE login.username='" + usernameTxt.getText() + "'";
            rs = DB.search(sql);
            if (rs.next()) {
                if (rs.getBoolean("employee.status")) {
                    if (rs.getString("username").equals(usernameTxt.getText())) {
                        String password = passwordTxt.getText();
                        password = MD5.getMd5(password);
                        if (rs.getString("password").equals(password)) {
                            String role = rs.getString("type");
                            UserDetails details = new UserDetails();
                            details.setId(rs.getString("employeeid"));
                            details.setFname(rs.getString("firstname"));
                            details.setLname(rs.getString("lastname"));
                            details.setRole(role);

                            if (rs.getBoolean("login.status")) {
                                switch (role) {
                                    case "Admin":
                                        new ManagerHome(details).setVisible(true);
                                        break;
                                    case "Cashier":
                                        new InvoiceView(details).setVisible(true);
                                        break;
                                    case "Manager":
                                        new ManagerHome(details).setVisible(true);
                                        break;
                                    case "Store-Keeper":
                                        new grn(details).setVisible(true);
                                        break;
                                    default:
                                        JOptionPane.showMessageDialog(this, "Couldn't login. Invalid user type", "Error", JOptionPane.ERROR_MESSAGE);
                                        break;
                                }
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "Your login access is BLOCKED. Contact Manager for further steps.", "Login Error", JOptionPane.WARNING_MESSAGE);
                                usernameTxt.setText(null);
                                passwordTxt.setText(null);
                                usernameTxt.grabFocus();
                            }
                        } else {
//                            System.out.println("1");
                            error();
                        }
                    } else {
//                        System.out.println("2");
                        error();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Employee is BLOCKED. Contact Manager for further steps.", "Login Error", JOptionPane.WARNING_MESSAGE);
                    usernameTxt.setText(null);
                    passwordTxt.setText(null);
                    usernameTxt.grabFocus();
                }
            } else {
                error();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Login error" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void error() {
        usernameTxt.setText(null);
        passwordTxt.setText(null);
        JOptionPane.showMessageDialog(this, "Invalid Login details.", "Error", JOptionPane.ERROR_MESSAGE);
        usernameTxt.grabFocus();
    }

    private void emptyCheck() {
        if (usernameTxt.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty");
            usernameTxt.grabFocus();
        } else if (passwordTxt.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty");
            passwordTxt.grabFocus();
        } else {
            login();
        }
    }

    private void otherComponents() {
        try {
            setIcon(usernameIconLabel, "data/username.png");
            setIcon(passwordIconLabel, "data/password.png");

//            Initial initial = new Initial();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void setIcon(javax.swing.JLabel label, String imgPath) {

        try {
            URL path = this.getClass().getResource(imgPath);
            FileInputStream imgStream = new FileInputStream(path.getPath());
            Image img = ImageIO.read(imgStream);
            img = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
  
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        usernameLabel = new javax.swing.JLabel();
        usernameTxt = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordTxt = new javax.swing.JPasswordField();
        closeButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();
        resetPasswordLabel = new javax.swing.JLabel();
        usernameIconLabel = new javax.swing.JLabel();
        passwordIconLabel = new javax.swing.JLabel();
        settingsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setMinimumSize(new java.awt.Dimension(400, 400));
        setUndecorated(true);
        setResizable(false);

        jLayeredPane1.setBackground(new java.awt.Color(51, 51, 255));

        usernameLabel.setFont(new java.awt.Font("Segoe UI Light", 1, 16)); // NOI18N
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel.setText("Enter Your Username");

        usernameTxt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        usernameTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTxtActionPerformed(evt);
            }
        });

        passwordLabel.setFont(new java.awt.Font("Segoe UI Light", 1, 16)); // NOI18N
        passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passwordLabel.setText("Enter Your Password");

        passwordTxt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        passwordTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTxtActionPerformed(evt);
            }
        });

        closeButton.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        loginButton.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        resetPasswordLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        resetPasswordLabel.setText("Forgot password?");
        resetPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetPasswordLabelMouseClicked(evt);
            }
        });

        usernameIconLabel.setMaximumSize(new java.awt.Dimension(52, 52));
        usernameIconLabel.setMinimumSize(new java.awt.Dimension(52, 52));

        passwordIconLabel.setMaximumSize(new java.awt.Dimension(52, 52));
        passwordIconLabel.setMinimumSize(new java.awt.Dimension(52, 52));
        passwordIconLabel.setName(""); // NOI18N

        settingsLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        settingsLabel.setText("Settings");
        settingsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsLabelMouseClicked(evt);
            }
        });

        jLayeredPane1.setLayer(usernameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(usernameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(passwordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(passwordTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(closeButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(loginButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(resetPasswordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(usernameIconLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(passwordIconLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(settingsLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(0, 62, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                            .addComponent(passwordIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(closeButton)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(usernameTxt)
                                .addComponent(passwordTxt)
                                .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(settingsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loginButton)
                            .addComponent(resetPasswordLabel))))
                .addGap(62, 62, 62))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(usernameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(usernameIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(passwordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(passwordIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(70, 70, 70)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetPasswordLabel)
                    .addComponent(settingsLabel))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void usernameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTxtActionPerformed
        passwordTxt.grabFocus();
    }//GEN-LAST:event_usernameTxtActionPerformed

    private void resetPasswordLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetPasswordLabelMouseClicked
        new ResetPassword().setVisible(true);
    }//GEN-LAST:event_resetPasswordLabelMouseClicked

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        emptyCheck();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void passwordTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTxtActionPerformed
        emptyCheck();
    }//GEN-LAST:event_passwordTxtActionPerformed

    private void settingsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsLabelMouseClicked
        new LoginSettings().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_settingsLabelMouseClicked

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    String sql;
    ResultSet rs;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel passwordIconLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTxt;
    private javax.swing.JLabel resetPasswordLabel;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JLabel usernameIconLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTxt;
    // End of variables declaration//GEN-END:variables
}
