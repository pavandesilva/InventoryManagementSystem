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
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author DELL
 */
public class EmployeeManagement extends javax.swing.JFrame {

    /**
     * Creates new form AddSystemUser
     */
    
    public EmployeeManagement() {
        initComponents();
        this.log = new StatLogging();
        otherComponents();
        generateSystemUserID();
        startTimer();
    }

    public EmployeeManagement(UserDetails details) {
        this.details = details;
        this.log = new StatLogging();
        initComponents();
        otherComponents();
        generateSystemUserID();
        startTimer();
        userLabel.setText(this.details.getFname());
        log.infoLog(this.details, "Logged into Employee managemnt");
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
        if (departmentCombobox.getSelectedIndex() == -1) {
            components.error(this, "Select the employee's department.");
        } else if (employeeIdTxt.getText().equals("")) {
            components.error(this, "Employeeid cannot be empty.");
        } else if (firstnameTxt.getText().equals("")) {
            components.error(this, "Firstname cannot be empty.");
        } else if (lastnameTxt.getText().equals("")) {
            components.error(this, "Lastname vannot be empty.");
        } else if (contactNoTxt.getText().equals("")) {
            components.error(this, "Contact number cannot be empty.");
        } else if (contactNoTxt.getText().length() != 11) {
            components.error(this, "Invalid contact number.");
        } else if (birthDateChooser.getDate().equals("")) {
            components.error(this, "Birthday cannot be empty.");
        } else if (addressTxt.getText().equals("")) {
            components.error(this, "Address cannot be empty.");
        } else if (nicTxt.getText().equals("")) {
            components.error(this, "NIC cannot be empty.");
        } else if (userTypeCombobox.getSelectedIndex() == -1) {
            components.error(this, "Usertype cannot be empty.");
        } else {
            addUser();
        }
    }
    
    private void setStatus(String s) {
        try {
            sql = "UPDATE employee SET status= " + s + " WHERE employeeid='" + rs.getString("employeeid") + "'";
            DB.addData(sql);

            log.infoLog(details, employeeIdTxt.getText() + "'Employee status updated");
            components.infoMessage(this, "User's access status successfully changed.");
            newUserCycle();
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
        }
    }

    private void addUser() {
        int stat = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date bday = birthDateChooser.getDate();
            String dob = sdf.format(bday);

            String depID = null;

            sql = "SELECT deptid FROM department WHERE name='" + departmentCombobox.getSelectedItem().toString() + "'";
            rs = DB.search(sql);
            if (rs.next()) {
                depID = rs.getString("deptid");
            }

            if (activeRadioButton.isSelected()) {
                stat = 1;
            }
            sql = "INSERT INTO employee (employeeid, deptid, firstname, lastname,address, dob, nic,contactno, type, imagepath, status) VALUES('" + employeeIdTxt.getText() + "' ,'" + depID + "' ,'" + firstnameTxt.getText() + "','" + lastnameTxt.getText() + "' ,'" + addressTxt.getText() + "' ,'" + dob + "' ,'" + nicTxt.getText() + "' ,'" + contactNoTxt.getText() + "' ,'" + userTypeCombobox.getSelectedItem().toString().trim() + "' ,'" + imagepath + "','" + stat + "')";
            DB.addData(sql);
            log.infoLog(details, "A new employee is added");

            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Setup system login for user?", "Employee added", JOptionPane.YES_NO_OPTION)) {
                new SetupLogin(details, employeeIdTxt.getText()).setVisible(true);
            }
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "View employee details report?", "Employee added", JOptionPane.YES_NO_OPTION)) {
                viewReport();
            }
            newUserCycle();

        } catch (Exception e) {
            components.error(this, e.getMessage());
            log.errorLog(details, e.getMessage());
        }
    }

    private void viewReport() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date bday = birthDateChooser.getDate();
            String dob = sdf.format(bday);

            String path = "D:\\projects\\Java\\1stYear\\FinalProjects\\1stYearProject\\src\\reports\\UserDetails.jrxml";
            JasperReport compileReport = JasperCompileManager.compileReport(path);
            JREmptyDataSource datasource = new JREmptyDataSource();

            Map<String, Object> map = new HashMap<>();
            map.put("id", employeeIdTxt.getText());
            System.out.println(employeeIdTxt.getText());
            map.put("fname", firstnameTxt.getText());
            System.out.println(firstnameTxt.getText());
            map.put("lname", lastnameTxt.getText());
            System.out.println(lastnameTxt.getText());
            map.put("nic", nicTxt.getText());
            System.out.println(nicTxt.getText());
            map.put("dob", dob);
            System.out.println(dob);
            map.put("contact", contactNoTxt.getText());
            System.out.println(contactNoTxt.getText());
            map.put("address", addressTxt.getText());
            System.out.println(addressTxt.getText());
            map.put("type", userTypeCombobox.getSelectedItem());
            System.out.println(userTypeCombobox.getSelectedItem());
            map.put("dep", departmentCombobox.getSelectedItem());
            System.out.println(departmentCombobox.getSelectedItem());

            JasperPrint fillReport = JasperFillManager.fillReport(compileReport, map, datasource);
//            JasperPrint fillReport = JasperFillManager.fillReport(is, map, datasource);
            JasperViewer.viewReport(fillReport, false);
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
    }

    private void searchEmployee() {
        try {
//            sql = "SELECT department.name, * FROM employee INNER JOIN department ON deptid = employee.deptid WHERE employeeemployeeid='" + employeeIdTxt.getText() + "'";

            sql = "SELECT department.name, employee.employeeid, employee.deptid, employee.firstname, employee.lastname, employee.address, employee.dob, employee.nic, employee.contactno, employee.type,employee.imagepath, employee.status FROM employee INNER JOIN department ON employee.employeeid=department.deptid WHERE employee.employeeid='" + employeeIdTxt.getText() + "'";
            rs = DB.search(sql);

            if (rs.next()) {
                if (!rs.getString("imagepath").equals(" ")) {
                    String imagpath = rs.getString("imagepath");
                    File file = new File(imagpath);
                    Image image = ImageIO.read(file);
                    image = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                }

                firstnameTxt.setText(rs.getString("firstname"));
                lastnameTxt.setText(rs.getString("lastname"));
                contactNoTxt.setText(rs.getString("contactno"));
                addressTxt.setText(rs.getString("address"));
                String date = rs.getString("dob");
//                System.out.println(rs.getString("dob"));
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date bday = sdf.parse(date);
                birthDateChooser.setDate(bday);
                nicTxt.setText(rs.getString("nic"));
                userTypeCombobox.setSelectedItem(rs.getString("type"));
                departmentCombobox.setSelectedItem(rs.getString("name"));

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
                backButton.setVisible(true);
                statButton.setVisible(true);
                addUserButton.setVisible(false);
            } else {
                components.error(this, "Invalid employee Id.");
            }

        } catch (Exception e) {
//            e.printStackTrace();
            log.errorLog(details, e.getMessage());
        }
    }

    private void update() {
        try {
            sql = "SELECT deptid FROM department WHERE name='" + departmentCombobox.getSelectedItem() + "'";
            rs = DB.search(sql);
            String depid = "0";
            if (rs.next()) {
                depid = rs.getString("deptid").trim();
            }
            System.out.println(depid);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            System.out.println(birthDateChooser.getDate());
            Date bday = birthDateChooser.getDate();
            String dob = sdf.format(bday);

            int status = 0;
            if (activeRadioButton.isSelected()) {
                status = 1;
            }

            sql = "UPDATE employee SET employeeid='" + employeeIdTxt.getText() + "', deptid='" + depid + "', firstname='" + firstnameTxt.getText() + "', lastname='" + lastnameTxt.getText() + "', address='" + addressTxt.getText().trim() + "', dob='" + dob + "', nic='" + nicTxt.getText() + "', contactno='" + contactNoTxt.getText() + "', type='" + userTypeCombobox.getSelectedItem() + "', status='" + status + "' WHERE employeeid='" + employeeIdTxt.getText().trim() + "'";
            DB.addData(sql);

            log.infoLog(details, employeeIdTxt.getText() + "'s Employee details updated");
            JOptionPane.showMessageDialog(this, "Item data successfully Updated", "", JOptionPane.INFORMATION_MESSAGE);
            newUserCycle();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            log.errorLog(details, e.getMessage());
        }
    }

    private void newUserCycle() {
        try {
            generateSystemUserID();
            activeRadioButton.setSelected(true);
            firstnameTxt.setText(null);
            lastnameTxt.setText(null);
            contactNoTxt.setText(null);
            birthDateChooser.setDate(null);
            addressTxt.setText(null);
            nicTxt.setText(null);
            userTypeCombobox.setSelectedIndex(-1);
            departmentCombobox.setSelectedIndex(-1);
            statButton.setVisible(false);
            addUserButton.setVisible(true);
            firstnameTxt.setEditable(true);
            lastnameTxt.setEditable(true);
            Image img = ImageIO.read(getClass().getResource("data/add.png"));
            img = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
        }
    }

    private void otherComponents() {
        try {

            Image img1 = ImageIO.read(getClass().getResource("data/reload.png"));
            img1 = img1.getScaledInstance(refreshButton.getWidth(), refreshButton.getHeight(), Image.SCALE_SMOOTH);
            refreshButton.setIcon(new ImageIcon(img1));

            Image img2 = ImageIO.read(getClass().getResource("data/add.png"));
            img2 = img2.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img2));

//            URL path = this.getClass().getResource("data/EMBackground.jpg");
//            File imageFile = new File(path.getFile());
//            Image img = ImageIO.read(imageFile);
//            img = img.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
//            backgroundLabel.setIcon(new ImageIcon(img));
//            backButton.setVisible(false);
            backgroundLabel.setVisible(false);
            statButton.setVisible(false);

            components = new SystemComponents();
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
        }
    }

    private void generateSystemUserID() {
        employeeIdTxt.setText("" + components.generateID("employee"));
        firstnameTxt.grabFocus();
    }

    private void loadEmployeeId() {
        try {
            sql = "SELECT employeeid FROM employee ORDER BY employeeid DESC LIMIT 1;";
            rs = DB.search(sql);
            if (rs.next()) {
                employeeIdTxt.setText("00" + String.valueOf(Integer.valueOf(rs.getString("employeeid")) + 1));
            }
            sql = "";
            rs = null;
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupLogin() {
        try {
            sql = "SELECT username FROM login WHERE employeeid='" + employeeIdTxt.getText() + "'";
            rs = DB.search(sql);
            if (rs.next()) {
                components.error(this, "User already has a login.");
            } else {
                new SetupLogin(details, employeeIdTxt.getText()).setVisible(true);
            }
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        titleLabel = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        userIDLabel = new javax.swing.JLabel();
        employeeIdTxt = new javax.swing.JTextField();
        activeStatusLabel = new javax.swing.JLabel();
        activeRadioButton = new javax.swing.JRadioButton();
        inactiveRadioButton = new javax.swing.JRadioButton();
        firstnameLabel = new javax.swing.JLabel();
        firstnameTxt = new javax.swing.JTextField();
        lastnameLabel = new javax.swing.JLabel();
        lastnameTxt = new javax.swing.JTextField();
        contactNoLabel = new javax.swing.JLabel();
        contactNoTxt = new javax.swing.JTextField();
        birthdayLabel = new javax.swing.JLabel();
        birthDateChooser = new com.toedter.calendar.JDateChooser();
        addressLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addressTxt = new javax.swing.JTextArea();
        nicLabel = new javax.swing.JLabel();
        nicTxt = new javax.swing.JTextField();
        userTypeLabel = new javax.swing.JLabel();
        userTypeCombobox = new javax.swing.JComboBox<>();
        addUserButton = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        datetimeLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        statButton = new javax.swing.JButton();
        departmentCombobox = new javax.swing.JComboBox<>();
        departmentLabel = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        setupButton = new javax.swing.JButton();
        backgroundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee Management");
        setAutoRequestFocus(false);
        setMinimumSize(new java.awt.Dimension(1400, 650));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleLabel.setBackground(new java.awt.Color(0, 0, 0));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(153, 153, 153));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Employee Management");
        titleLabel.setToolTipText("");
        titleLabel.setOpaque(true);
        getContentPane().add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1590, 64));

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(1320, 520));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(1320, 520));

        userIDLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        userIDLabel.setText("Employee ID");

        employeeIdTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        employeeIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeIdTxtActionPerformed(evt);
            }
        });
        employeeIdTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                employeeIdTxtKeyPressed(evt);
            }
        });

        activeStatusLabel.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
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

        birthdayLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        birthdayLabel.setText("Birthday");

        birthDateChooser.setDateFormatString("MMM dd / yyyy");
        birthDateChooser.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N

        addressLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        addressLabel.setText("Address");

        addressTxt.setColumns(20);
        addressTxt.setRows(5);
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

        userTypeLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        userTypeLabel.setText("Type");

        userTypeCombobox.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        userTypeCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Accountant", "Cashier", "Cleaner", "Sequrity", "Store-Keeper", "Manager", "Admin" }));
        userTypeCombobox.setSelectedIndex(-1);
        userTypeCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userTypeComboboxActionPerformed(evt);
            }
        });

        addUserButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        addUserButton.setText("Add User");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        userLabel.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        userLabel.setText("User");
        userLabel.setMaximumSize(new java.awt.Dimension(20, 12));
        userLabel.setMinimumSize(new java.awt.Dimension(20, 12));

        datetimeLabel.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
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

        departmentCombobox.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        departmentCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administration", "Human-Resource", "Management", "Sales" }));
        departmentCombobox.setSelectedIndex(-1);
        departmentCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                departmentComboboxActionPerformed(evt);
            }
        });

        departmentLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        departmentLabel.setText("Department");

        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageLabelMouseClicked(evt);
            }
        });

        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        updateButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        setupButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        setupButton.setText("Setup");
        setupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupButtonActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(userIDLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(employeeIdTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(activeStatusLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(activeRadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(inactiveRadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(firstnameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(firstnameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lastnameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lastnameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(contactNoLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(contactNoTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(birthdayLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(birthDateChooser, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(addressLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(nicLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(nicTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(userTypeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(userTypeCombobox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(addUserButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(userLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(datetimeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(backButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(statButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(departmentCombobox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(departmentLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(imageLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(refreshButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(updateButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(setupButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(firstnameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(userIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(birthdayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(contactNoLabel)
                        .addComponent(userTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(userTypeCombobox, 0, 202, Short.MAX_VALUE)
                            .addComponent(birthDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(employeeIdTxt)
                                .addGap(18, 18, 18)
                                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(firstnameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(contactNoTxt))
                        .addGap(32, 32, 32)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lastnameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(departmentLabel))
                                .addGap(18, 18, 18))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nicLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                                .addGap(32, 32, 32)))
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lastnameTxt)
                            .addComponent(departmentCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(nicTxt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(activeStatusLabel)
                                .addGap(18, 18, 18)
                                .addComponent(activeRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(inactiveRadioButton))
                            .addComponent(statButton, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createSequentialGroup()
                                    .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(setupButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createSequentialGroup()
                                    .addComponent(backButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(addUserButton))))
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(userIDLabel)
                            .addComponent(departmentLabel)
                            .addComponent(departmentCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeeIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(firstnameLabel)
                            .addComponent(firstnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastnameLabel)
                            .addComponent(lastnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(birthdayLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthDateChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nicLabel)
                        .addComponent(nicTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(activeStatusLabel)
                        .addComponent(activeRadioButton)
                        .addComponent(inactiveRadioButton)))
                .addGap(39, 39, 39)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(addUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(statButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addressLabel)
                            .addComponent(contactNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactNoLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(userTypeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(userTypeLabel)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1380, 520));

        backgroundLabel.setText("jLabel1");
        getContentPane().add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1390, 530));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void employeeIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeIdTxtActionPerformed
        searchEmployee();
    }//GEN-LAST:event_employeeIdTxtActionPerformed

    private void firstnameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnameTxtActionPerformed
        lastnameTxt.grabFocus();
    }//GEN-LAST:event_firstnameTxtActionPerformed

    private void contactNoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactNoTxtActionPerformed
        userTypeCombobox.showPopup();
    }//GEN-LAST:event_contactNoTxtActionPerformed

    private void lastnameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnameTxtActionPerformed
        contactNoTxt.grabFocus();
    }//GEN-LAST:event_lastnameTxtActionPerformed

    private void nicTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nicTxtActionPerformed
        addressTxt.grabFocus();
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

    private void employeeIdTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeeIdTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            employeeIdTxt.setEditable(true);
        } else {
            employeeIdTxt.setEditable(false);
        }
    }//GEN-LAST:event_employeeIdTxtKeyPressed

    private void firstnameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstnameTxtKeyPressed
        if (evt.getKeyChar() >= 'a' && evt.getKeyChar() <= 'z' || evt.getKeyChar() >= 'A' && evt.getKeyChar() <= 'Z' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            firstnameTxt.setEditable(true);
        } else {
            firstnameTxt.setEditable(false);
        }
    }//GEN-LAST:event_firstnameTxtKeyPressed

    private void lastnameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastnameTxtKeyPressed
        if (evt.getKeyChar() >= 'a' && evt.getKeyChar() <= 'z' || evt.getKeyChar() >= 'A' && evt.getKeyChar() <= 'Z' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_DELETE) {
            lastnameTxt.setEditable(true);
        } else {
            lastnameTxt.setEditable(false);
        }
    }//GEN-LAST:event_lastnameTxtKeyPressed

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

    private void nicTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nicTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyChar() == KeyEvent.VK_V || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            nicTxt.setEditable(true);
        } else {
            nicTxt.setEditable(false);
        }
        if (nicTxt.getText().length() > 12) {
            components.error(this, "Maximum character count fot NIC Number is 12.");
            nicTxt.setText(null);
        }
    }//GEN-LAST:event_nicTxtKeyPressed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadEmployeeId();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        update();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void imageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageLabelMouseClicked
        try {
            JFileChooser jfc = new JFileChooser("D:\\");
            FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
            jfc.addChoosableFileFilter(imageFilter);
            jfc.setAcceptAllFileFilterUsed(false);
            int option = jfc.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                String selectedFileName = selectedFile.getName();

                imagepath = selectedFile.getAbsolutePath();

//                    extention = selectedFileName.substring(selectedFileName.lastIndexOf("."), selectedFileName.length());
                imagepath = selectedFile.getAbsolutePath().replace("\\", "/");
                System.out.println(imagepath);
                File imageFile = new File(imagepath);
                Image image = ImageIO.read(imageFile);
                image = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
            }
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_imageLabelMouseClicked

    private void userTypeComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userTypeComboboxActionPerformed
//        departmentCombobox.showPopup();
    }//GEN-LAST:event_userTypeComboboxActionPerformed

    private void departmentComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_departmentComboboxActionPerformed
        nicTxt.grabFocus();
    }//GEN-LAST:event_departmentComboboxActionPerformed

    private void setupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupButtonActionPerformed
        setupLogin();
    }//GEN-LAST:event_setupButtonActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeManagement().setVisible(true);
            }
        });
    }

    String sql, imagepath = " ";
    ResultSet rs;
    SystemComponents components;
    UserDetails details;
    StatLogging log;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton activeRadioButton;
    private javax.swing.JLabel activeStatusLabel;
    private javax.swing.JButton addUserButton;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextArea addressTxt;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel backgroundLabel;
    private com.toedter.calendar.JDateChooser birthDateChooser;
    private javax.swing.JLabel birthdayLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel contactNoLabel;
    public static javax.swing.JTextField contactNoTxt;
    private javax.swing.JLabel datetimeLabel;
    private javax.swing.JComboBox<String> departmentCombobox;
    private javax.swing.JLabel departmentLabel;
    private javax.swing.JTextField employeeIdTxt;
    private javax.swing.JLabel firstnameLabel;
    public static javax.swing.JTextField firstnameTxt;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JRadioButton inactiveRadioButton;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lastnameLabel;
    public static javax.swing.JTextField lastnameTxt;
    private javax.swing.JLabel nicLabel;
    private javax.swing.JTextField nicTxt;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton setupButton;
    private javax.swing.JButton statButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel userIDLabel;
    private javax.swing.JLabel userLabel;
    private javax.swing.JComboBox<String> userTypeCombobox;
    private javax.swing.JLabel userTypeLabel;
    // End of variables declaration//GEN-END:variables
}
