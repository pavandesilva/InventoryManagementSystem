package view;

import common.DB;
import common.SystemComponents;
import common.UserDetails;
import common.StatLogging;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author DELL
 */
public class CustomerManagement extends javax.swing.JFrame {

    /**
     * Creates new form AddSystemUser
     */
     public CustomerManagement() {
        initComponents();
        otherComponents();
        generateSystemUserID();
        startTimer();
    }

    public CustomerManagement(UserDetails details) {
        initComponents();
        this.details = details;
        this.log = new StatLogging();
        userLabel.setText(details.getFname());
        otherComponents();
        generateSystemUserID();
        startTimer();
        log.infoLog(details, "Logged into Customer management");
    }

    private void startTimer() {
        datetimeLabel.setText(DateFormat.getDateTimeInstance().format(new Date()));
        Timer t = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datetimeLabel.setText(DateFormat.getDateTimeInstance().format(new Date()));
            }
        });
        t.setRepeats(true);
        t.setCoalesce(true);
        t.setInitialDelay(0);
        t.start();
    }

    private void checkData() {
        if (customerIdTxt.getText().equals("")) {
            components.error(this, "Employeeid cannot be empty");
        } else if (firstnameTxt.getText().equals("")) {
            components.error(this, "Firstname cannot be empty");
        } else if (lastnameTxt.getText().equals("")) {
            components.error(this, "Lastname vannot be empty");
        } else if (nicTxt.getText().equals("")) {
            components.error(this, "NIC cannot be empty");
        } else if (birthDateChooser.getDate().equals("")) {
            components.error(this, "Birthday cannot be empty");
        } else if (addressTxt.getText().equals("")) {
            components.error(this, "Address cannot be empty");
        } else if (emailTxt.getText().equals("")) {
            components.error(this, "email cannot be empty");
        } else if (contactNoTxt.getText().equals("")) {
            components.error(this, "Contact number cannot be empty");
        } else if (contactNoTxt.getText().length() != 11) {
            components.error(this, "Invalid contact number");
        } else {
            addUser();
        }
    }

    private void setStatus(String s) {
        try {
            sql = "UPDATE customer SET status= " + s + " WHERE customerid='" + rs.getString("customreid") + "'";
            DB.addData(sql);
            log.infoLog(details, "Customer status changed");
            components.infoMessage(this, "User's access status successfully changed");
            newUserCycle();
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
        }
    }

    private void addUser() {
        int stat = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd / MM / yyyy");
            String dob = sdf.format(birthDateChooser.getDate());

            if (activeRadioButton.isSelected()) {
                stat = 1;
            }

            sql = "INSERT INTO customer (customerid, firstname, lastname, nic, birthday, address, email, mobile, status) VALUES ('" + customerIdTxt.getText() + "', '" + firstnameTxt.getText() + "','" + lastnameTxt.getText() + "' ,'" + nicTxt.getText() + "' ,'" + dob + "' ,'" + addressTxt.getText() + "' ,'" + emailTxt.getText() + "','" + contactNoTxt.getText() + "', '" + stat + "')";
            DB.addData(sql);
            log.infoLog(details, "New customer added");
            components.infoMessage(this, "New customer successfully added");
            newUserCycle();
        } catch (Exception e) {
            components.error(this, e.getMessage());
        }
    }

    private void updateData() {
        int stat = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd / MM / yyyy");
            String dob = sdf.format(birthDateChooser.getDate());

            if (activeRadioButton.isSelected()) {
                stat = 1;
            }

            sql = "UPDATE customer SET firstname='" + firstnameTxt.getText() + "', lastname='" + lastnameTxt.getText() + "', nic='" + nicTxt.getText() + "', birthday='" + dob + "', address='" + addressTxt.getText() + "', email='" + emailTxt.getText() + "', mobile='" + contactNoTxt.getText() + "', status='" + stat + "' WHERE customerid= '" + customerIdTxt.getText() + "'";
            DB.addData(sql);
            log.infoLog(details, "Customer details updated");                        
            components.infoMessage(this, "User's data succesfully updated");

            customerIdTxt.setEditable(true);
            newUserCycle();
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
    }

    private void newUserCycle() {
        generateSystemUserID();
        activeRadioButton.setSelected(true);
        firstnameTxt.setText(null);
        lastnameTxt.setText(null);
        contactNoTxt.setText(null);
        birthDateChooser.setDate(null);
        addressTxt.setText(null);
        nicTxt.setText(null);
        emailTxt.setText(null);
//        backButton.setVisible(false);
        addUserButton.setVisible(true);
        statButton.setVisible(false);
    }

    private void setData() {
        try {
            sql = "SELECT * FROM customer WHERE customerid='" + customerIdTxt.getText() + "'";

            rs = DB.search(sql);
            if (rs.next()) {
                firstnameTxt.setText(rs.getString("firstname"));
                lastnameTxt.setText(rs.getString("lastname"));
                contactNoTxt.setText(rs.getString("mobile"));
                addressTxt.setText(rs.getString("address"));
                String date = rs.getString("birthday");
                SimpleDateFormat sdf = new SimpleDateFormat("MM / dd / yyyy");
                Date bday = sdf.parse(date);
                birthDateChooser.setDate(bday);
                nicTxt.setText(rs.getString("nic"));
                emailTxt.setText(rs.getString("email"));

                if (rs.getBoolean("status")) {
                    activeRadioButton.setSelected(true);
                    statButton.setText("Deactivate");
                    statButton.setBackground(Color.red);
                }
                if (!rs.getBoolean("status")) {
                    inactiveRadioButton.setSelected(true);
                    statButton.setText("Activate");
                    statButton.setBackground(Color.green);
                }
//                backButton.setVisible(true);
                statButton.setVisible(true);
                addUserButton.setVisible(false);
            } else {
                components.error(this, "Invalid customer Id");
            }

        } catch (Exception e) {
           log.errorLog(details, e.getMessage());
        }
    }

//    private void numberFilter(KeyEvent evt, JLabel label){
//        
//        if (evt.getKeyChar() >= 'a' && evt.getKeyChar() <= 'z' || evt.getKeyChar() >= 'A' && evt.getKeyChar() <= 'Z' || evt.getKeyCode()== KeyEvent.VK_BACK_SPACE){
//            label.setEditable(true);
//        } else {
//            label.setEditable(false);
//        }
//    }
    private void otherComponents() {
        try {
//            URL path = this.getClass().getResource("data/EMBackground.jpg");
//            File imageFile = new File(path.getFile());
//            Image img = ImageIO.read(imageFile);
//            img = img.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
//            backgroundLabel.setIcon(new ImageIcon(img));
//            backgroundLabel.setVisible(false);
//            backButton.setVisible(false);
            statButton.setVisible(false);

            components = new SystemComponents();
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
        }
    }

    private void generateSystemUserID() {
        customerIdTxt.setText("" + components.generateID("customer"));
        nicTxt.grabFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        titleLabel = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        customerIDLabel = new javax.swing.JLabel();
        customerIdTxt = new javax.swing.JTextField();
        activeStatusLabel = new javax.swing.JLabel();
        activeRadioButton = new javax.swing.JRadioButton();
        inactiveRadioButton = new javax.swing.JRadioButton();
        firstnameLabel = new javax.swing.JLabel();
        firstnameTxt = new javax.swing.JTextField();
        lastnameLabel = new javax.swing.JLabel();
        lastnameTxt = new javax.swing.JTextField();
        contactNoLabel = new javax.swing.JLabel();
        birthdayLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addressTxt = new javax.swing.JTextArea();
        nicLabel = new javax.swing.JLabel();
        nicTxt = new javax.swing.JTextField();
        addUserButton = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        datetimeLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        statButton = new javax.swing.JButton();
        emailLabel = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        updateButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        contactNoTxt = new javax.swing.JTextField();
        birthDateChooser = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("User management");
        setMinimumSize(new java.awt.Dimension(1378, 706));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleLabel.setBackground(new java.awt.Color(0, 0, 0));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(153, 153, 153));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Customer Management");
        titleLabel.setToolTipText("");
        titleLabel.setOpaque(true);
        getContentPane().add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1380, 64));

        customerIDLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        customerIDLabel.setText("Customer ID");

        customerIdTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        customerIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerIdTxtActionPerformed(evt);
            }
        });
        customerIdTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                customerIdTxtKeyPressed(evt);
            }
        });

        activeStatusLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        activeStatusLabel.setText("Active Status");

        buttonGroup1.add(activeRadioButton);
        activeRadioButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        activeRadioButton.setSelected(true);
        activeRadioButton.setText("Active");

        buttonGroup1.add(inactiveRadioButton);
        inactiveRadioButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        inactiveRadioButton.setText("Inactive");

        firstnameLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        firstnameLabel.setText("First Name");

        firstnameTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        firstnameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnameTxtActionPerformed(evt);
            }
        });
        firstnameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                firstnameTxtKeyPressed(evt);
            }
        });

        lastnameLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lastnameLabel.setText("Last Name");

        lastnameTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lastnameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastnameTxtActionPerformed(evt);
            }
        });
        lastnameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lastnameTxtKeyPressed(evt);
            }
        });

        contactNoLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        contactNoLabel.setText("Contact No");

        birthdayLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        birthdayLabel.setText("Birthday");

        addressLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        addressLabel.setText("Address");

        addressTxt.setColumns(20);
        addressTxt.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        addressTxt.setRows(5);
        addressTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addressTxtKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(addressTxt);

        nicLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        nicLabel.setText("NIC");

        nicTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        nicTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nicTxtActionPerformed(evt);
            }
        });
        nicTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nicTxtKeyPressed(evt);
            }
        });

        addUserButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        addUserButton.setText("Add new customer");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        userLabel.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        userLabel.setText("User");
        userLabel.setMaximumSize(new java.awt.Dimension(20, 12));
        userLabel.setMinimumSize(new java.awt.Dimension(20, 12));

        datetimeLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
        datetimeLabel.setText("datetime");
        datetimeLabel.setMaximumSize(new java.awt.Dimension(20, 12));
        datetimeLabel.setMinimumSize(new java.awt.Dimension(20, 12));

        backButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        statButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        statButton.setForeground(new java.awt.Color(255, 255, 255));
        statButton.setText("Button");
        statButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statButtonActionPerformed(evt);
            }
        });

        emailLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        emailLabel.setText("Email");

        emailTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        emailTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTxtActionPerformed(evt);
            }
        });
        emailTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emailTxtKeyPressed(evt);
            }
        });

        updateButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        contactNoTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        contactNoTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactNoTxtActionPerformed(evt);
            }
        });
        contactNoTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contactNoTxtKeyPressed(evt);
            }
        });

        jLayeredPane1.setLayer(customerIDLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(customerIdTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(activeStatusLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(activeRadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(inactiveRadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(firstnameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(firstnameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lastnameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lastnameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(contactNoLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(birthdayLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(addressLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(nicLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(nicTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(addUserButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(userLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(datetimeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(backButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(statButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(emailLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(emailTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(updateButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(contactNoTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(birthDateChooser, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(customerIDLabel)
                        .addGap(45, 45, 45)
                        .addComponent(customerIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstnameLabel)
                            .addComponent(birthdayLabel)
                            .addComponent(emailLabel)
                            .addComponent(contactNoLabel))
                        .addGap(53, 53, 53)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(emailTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                            .addComponent(contactNoTxt)
                            .addComponent(firstnameTxt)
                            .addComponent(birthDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(119, 119, 119)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastnameLabel)
                            .addComponent(addressLabel)
                            .addComponent(nicLabel))
                        .addGap(29, 29, 29)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(lastnameTxt)
                            .addComponent(nicTxt)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(activeStatusLabel)
                        .addGap(18, 18, 18)
                        .addComponent(activeRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(inactiveRadioButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(74, 74, 74))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nicLabel)
                    .addComponent(nicTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerIDLabel))
                .addGap(38, 38, 38)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstnameLabel)
                    .addComponent(firstnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastnameLabel)
                    .addComponent(lastnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addressLabel)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(birthdayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                                    .addComponent(birthDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(62, 62, 62)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(emailLabel)
                                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(63, 63, 63)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(activeStatusLabel)
                            .addComponent(activeRadioButton)
                            .addComponent(inactiveRadioButton)
                            .addComponent(contactNoLabel)
                            .addComponent(contactNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1350, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void customerIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerIdTxtActionPerformed
        customerIdTxt.setEditable(false);
        setData();
    }//GEN-LAST:event_customerIdTxtActionPerformed

    private void firstnameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnameTxtActionPerformed
        lastnameTxt.grabFocus();
    }//GEN-LAST:event_firstnameTxtActionPerformed

    private void lastnameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnameTxtActionPerformed
        birthDateChooser.grabFocus();
    }//GEN-LAST:event_lastnameTxtActionPerformed

    private void nicTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nicTxtActionPerformed
        firstnameTxt.grabFocus();
    }//GEN-LAST:event_nicTxtActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        newUserCycle();
    }//GEN-LAST:event_backButtonActionPerformed

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        checkData();
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void statButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statButtonActionPerformed
        switch (statButton.getText()) {
            case "Activate":
                setStatus("1");
                break;

            case "Deactivate":
                setStatus("0");
                break;
        }
    }//GEN-LAST:event_statButtonActionPerformed

    private void emailTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTxtActionPerformed
        contactNoTxt.grabFocus();
    }//GEN-LAST:event_emailTxtActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        updateData();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void addressTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressTxtKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {
            emailTxt.grabFocus();
        }

    }//GEN-LAST:event_addressTxtKeyPressed

    private void firstnameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstnameTxtKeyPressed
        if (evt.getKeyChar() >= 'a' && evt.getKeyChar() <= 'z' || evt.getKeyChar() >= 'A' && evt.getKeyChar() <= 'Z' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            firstnameTxt.setEditable(true);
        } else {
            firstnameTxt.setEditable(false);
        }
    }//GEN-LAST:event_firstnameTxtKeyPressed

    private void lastnameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastnameTxtKeyPressed
        if (evt.getKeyChar() >= 'a' && evt.getKeyChar() <= 'z' || evt.getKeyChar() >= 'A' && evt.getKeyChar() <= 'Z' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            lastnameTxt.setEditable(true);
        } else {
            lastnameTxt.setEditable(false);
        }
    }//GEN-LAST:event_lastnameTxtKeyPressed

    private void nicTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nicTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == KeyEvent.VK_V || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            nicTxt.setEditable(true);
        } else {
            nicTxt.setEditable(false);
        }
        if (nicTxt.getText().length() == 12) {
            nicTxt.setEditable(false);
        }
    }//GEN-LAST:event_nicTxtKeyPressed

    private void customerIdTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerIdTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            customerIdTxt.setEditable(true);
        } else {
            customerIdTxt.setEditable(false);
        }
    }//GEN-LAST:event_customerIdTxtKeyPressed

    private void contactNoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactNoTxtActionPerformed
        checkData();
    }//GEN-LAST:event_contactNoTxtActionPerformed

    private void contactNoTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactNoTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_DELETE) {
            contactNoTxt.setEditable(true);
        } else {
            contactNoTxt.setEditable(false);
        }
        if (contactNoTxt.getText().length() == 3 && evt.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
            contactNoTxt.setText(contactNoTxt.getText() + "-");
        }
        if (contactNoTxt.getText().length() >= 12) {
            components.error(this, "Maximum character count fot Contact Number is 11.");
            contactNoTxt.setText(null);
        }
    }//GEN-LAST:event_contactNoTxtKeyPressed

    private void emailTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailTxtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emailTxt.setEditable(false);
        }
    }//GEN-LAST:event_emailTxtKeyPressed

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
            java.util.logging.Logger.getLogger(CustomerManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new CustomerManagement().setVisible(true);
            }
        });
    }

    String sql;
    ResultSet rs;
    UserDetails details;
    SystemComponents components;
    StatLogging log;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton activeRadioButton;
    private javax.swing.JLabel activeStatusLabel;
    private javax.swing.JButton addUserButton;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextArea addressTxt;
    private javax.swing.JButton backButton;
    private com.toedter.calendar.JDateChooser birthDateChooser;
    private javax.swing.JLabel birthdayLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel contactNoLabel;
    public static javax.swing.JTextField contactNoTxt;
    private javax.swing.JLabel customerIDLabel;
    private javax.swing.JTextField customerIdTxt;
    private javax.swing.JLabel datetimeLabel;
    private javax.swing.JLabel emailLabel;
    public static javax.swing.JTextField emailTxt;
    private javax.swing.JLabel firstnameLabel;
    public static javax.swing.JTextField firstnameTxt;
    private javax.swing.JRadioButton inactiveRadioButton;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lastnameLabel;
    public static javax.swing.JTextField lastnameTxt;
    private javax.swing.JLabel nicLabel;
    private javax.swing.JTextField nicTxt;
    private javax.swing.JButton statButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
