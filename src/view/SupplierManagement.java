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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class SupplierManagement extends javax.swing.JFrame {

    /**
     * Creates new form AddSystemUser
     */
    
    public SupplierManagement() {
        initComponents();
        otherComponents();
        generateSupplierID();
        startTimer();
    }

    public SupplierManagement(UserDetails details) {
        initComponents();
        this.details = details;
        this.log = new StatLogging();
        userLabel.setText(details.getFname());
        otherComponents();
        generateSupplierID();
        startTimer();
        log.infoLog(this.details, "Logged into supplier management");
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
        if (supplierIdTxt.getText().equals("")) {
            components.error(this, "Employeeid cannot be empty");
        } else if (supplierNameTxt.getText().equals("")) {
            components.error(this, "Enter Supplier name(Company name)");
        } else if (addressTxt.getText().equals("")) {
            components.error(this, "Address cannot be empty");
        } else if (contactNoTxt.getText().equals("")) {
            components.error(this, "Contact no cannot be empty");
        } else if (itemTable.getRowCount() == 0) {
            components.error(this, "Selet item(s) this supplier will supply.");
        } else {
            addSypplier();
        }
    }

    private void setStatus(String s) {
        try {
            sql = "UPDATE supplier SET status= " + s + " WHERE supplierid='" + rs.getString("supplierid") + "'";
            DB.addData(sql);
            components.infoMessage(this, "Supplier's status successfully changed");
            newSupplierCycle();
        } catch (Exception e) {
            components.error(this, e.getMessage());

        }
    }

    private void addSypplier() {
//        int stat = 0;
        try {
//            if (activeRadioButton.isSelected()) {
//                stat = 1;
//            }

            sql = "INSERT INTO supplier (supplierid, companyname, address, contactno) VALUES ('" + supplierIdTxt.getText() + "','" + supplierNameTxt.getText() + "', '" + addressTxt.getText() + "','" + contactNoTxt.getText() + "')";
            DB.addData(sql);

            DefaultTableModel dtm = (DefaultTableModel) itemTable.getModel();

            for (int row = 0; row < dtm.getRowCount(); row++) {

                System.out.println(dtm.getValueAt(row, 0).toString());
                String id = dtm.getValueAt(row, 0).toString();

                sql = "INSERT INTO itemsupplier (supplierid, itemid) VALUES ('" + supplierIdTxt.getText() + "','" + id + "') ";
                DB.addData(sql);
            }
            log.infoLog(details, "Added new supplier");
            JOptionPane.showMessageDialog(this, "Supplier successfully added.", "", JOptionPane.INFORMATION_MESSAGE);
            newSupplierCycle();
        } catch (Exception e) {
            components.error(this, e.getMessage());
        }
    }

    private void updateData() {
        try {
            sql = "UPDATE supplier SET companyname='" + supplierNameTxt.getText() + "', address='" + addressTxt.getText() + "', contactno='" + contactNoTxt.getText() + "' WHERE supplierid= '" + supplierIdTxt.getText() + "'";
            DB.addData(sql);

            DefaultTableModel dtm = (DefaultTableModel) itemTable.getModel();

            sql = " DELETE FROM itemsupplier WHERE supplierid='" + supplierIdTxt.getText() + "'";
            DB.addData(sql);

            for (int row = 0; row < dtm.getRowCount(); row++) {

                System.out.println(dtm.getValueAt(row, 0).toString());
                String id = dtm.getValueAt(row, 0).toString();

                sql = "INSERT INTO itemsupplier (supplierid, itemid) VALUES ('" + supplierIdTxt.getText() + "','" + id + "') ";
                DB.addData(sql);
                log.infoLog(details, supplierIdTxt.getText() + "'s details updated");
            }
            JOptionPane.showMessageDialog(this, "Item data successfully Updated", "", JOptionPane.INFORMATION_MESSAGE);
            newSupplierCycle();
        } catch (Exception e) {
            e.printStackTrace();
            components.error(this, e.getMessage());
        }
    }

    private void newSupplierCycle() {
        generateSupplierID();
        DefaultTableModel dtm = (DefaultTableModel) itemTable.getModel();
        supplierNameTxt.setText(null);
        addressTxt.setText(null);
        activeRadioButton.setSelected(true);
        contactNoTxt.setText(null);
        itemIdTxt.setText(null);
        itemNameTxt.setText(null);
        dtm.setRowCount(0);
        addSupplierButton.setVisible(true);
    }

    private void setData() {
        try {
            sql = "SELECT * FROM supplier WHERE supplierid='" + supplierIdTxt.getText() + "'";

            rs = DB.search(sql);
            if (rs.next()) {
                supplierNameTxt.setText(rs.getString("companyname"));
                addressTxt.setText(rs.getString("address"));
                contactNoTxt.setText(rs.getString("contactno"));

//                if (rs.getBoolean("status")) {
//                    activeRadioButton.setSelected(true);
//                    statButton.setText("Deactivate");
//                    statButton.setBackground(Color.red);
//                }
//                if (!rs.getBoolean("status")) {
//                    inactiveRadioButton.setSelected(true);
//                    statButton.setText("Activate");
//                    statButton.setBackground(Color.green);
//                }
                addSupplierButton.setVisible(false);
            } else {
                components.error(this, "Invalid Supplier Id");
            }
            sql = "SELECT item.name, itemsupplier.itemid FROM itemsupplier INNER JOIN item ON itemsupplier.itemid=item.itemid WHERE supplierid='" + supplierIdTxt.getText() + "'";
            rs = DB.search(sql);

            DefaultTableModel dtm = (DefaultTableModel) itemTable.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("itemid"));
                v.add(rs.getString("name"));
                System.out.println(rs.getString("itemid"));
                System.out.println(rs.getString("name"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
    }
    
    
    private void resetSupplier() {
        generateSupplierID();
        supplierNameTxt.setText("");
        addressTxt.setText("");
        contactNoTxt.setText("");
    }

    private void resetItem() {
        itemIdTxt.setText("");
        itemNameTxt.setText("");
    }

    private void addItemToTable() {
        try {
            if (itemIdTxt.getText().equals("") || itemNameTxt.getText().equals("")) {
                components.error(this, "Enter Valid Itemid");
            } else {
                Vector v = new Vector();
                DefaultTableModel dtm = (DefaultTableModel) itemTable.getModel();
                v.add(itemIdTxt.getText());
                v.add(itemNameTxt.getText());
                dtm.addRow(v);
                itemIdTxt.setText("");
                itemNameTxt.setText("");
                itemIdTxt.grabFocus();
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
            Image img1 = ImageIO.read(getClass().getResource("data/reload.png"));
            img1 = img1.getScaledInstance(refreshItemIdButton.getWidth(), refreshItemIdButton.getHeight(), Image.SCALE_SMOOTH);
            refreshItemIdButton.setIcon(new ImageIcon(img1));

            Image img2 = ImageIO.read(getClass().getResource("data/reload.png"));
            img2 = img2.getScaledInstance(refreshSupplierIdButton.getWidth(), refreshSupplierIdButton.getHeight(), Image.SCALE_SMOOTH);
            refreshSupplierIdButton.setIcon(new ImageIcon(img2));

//            URL path = this.getClass().getResource("data/EMBackground.jpg");
//            File imageFile = new File(path.getFile());
//            Image img = ImageIO.read(imageFile);
//            img = img.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
//            backgroundLabel.setIcon(new ImageIcon(img));
//            backgroundLabel.setVisible(false);
//            backButton.setVisible(false);
            components = new SystemComponents();
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
    }

    private void generateSupplierID() {
        supplierIdTxt.setText("" + components.generateID("supplier"));
        supplierNameTxt.grabFocus();
    }

    private void searchItem() {
        try {
            sql = "SELECT name FROM item WHERE itemid='" + itemIdTxt.getText() + "'";
            rs = DB.search(sql);
            if (rs.next()) {
                itemNameTxt.setText(rs.getString("name"));
                itemNameTxt.grabFocus();
            } else {
                components.error(this, "Invalid item id");
                itemIdTxt.setText("");
                itemIdTxt.grabFocus();
            }

        } catch (Exception e) {
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
        supplierIDLabel = new javax.swing.JLabel();
        supplierIdTxt = new javax.swing.JTextField();
        activeStatusLabel = new javax.swing.JLabel();
        activeRadioButton = new javax.swing.JRadioButton();
        inactiveRadioButton = new javax.swing.JRadioButton();
        supplierNameLabel = new javax.swing.JLabel();
        supplierNameTxt = new javax.swing.JTextField();
        contactNoLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addressTxt = new javax.swing.JTextArea();
        addSupplierButton = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        datetimeLabel = new javax.swing.JLabel();
        reloadButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        contactNoTxt = new javax.swing.JTextField();
        itemIdLabel = new javax.swing.JLabel();
        itemIdTxt = new javax.swing.JTextField();
        itemNameTxt = new javax.swing.JTextField();
        itemsLabel = new javax.swing.JLabel();
        refreshItemIdButton = new javax.swing.JButton();
        refreshSupplierIdButton = new javax.swing.JButton();
        ItemNameLabel = new javax.swing.JLabel();
        addItemButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        removeButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("User management");
        setMinimumSize(new java.awt.Dimension(1137, 620));
        setResizable(false);

        titleLabel.setBackground(new java.awt.Color(0, 0, 0));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(153, 153, 153));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Supplier Management");
        titleLabel.setToolTipText("");
        titleLabel.setOpaque(true);

        supplierIDLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        supplierIDLabel.setText("Supplier ID");

        supplierIdTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        supplierIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierIdTxtActionPerformed(evt);
            }
        });
        supplierIdTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                supplierIdTxtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                supplierIdTxtKeyReleased(evt);
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

        supplierNameLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        supplierNameLabel.setText("Supplier Name");

        supplierNameTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        supplierNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierNameTxtActionPerformed(evt);
            }
        });
        supplierNameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                supplierNameTxtKeyPressed(evt);
            }
        });

        contactNoLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        contactNoLabel.setText("Contact No");

        addressLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        addressLabel.setText("Address");

        addressTxt.setColumns(20);
        addressTxt.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        addressTxt.setRows(5);
        jScrollPane1.setViewportView(addressTxt);

        addSupplierButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        addSupplierButton.setText("Add Supplier");
        addSupplierButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSupplierButtonActionPerformed(evt);
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

        reloadButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        reloadButton.setText("Reload");
        reloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadButtonActionPerformed(evt);
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

        itemIdLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        itemIdLabel.setText("Item ID");

        itemIdTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        itemIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemIdTxtActionPerformed(evt);
            }
        });
        itemIdTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                itemIdTxtKeyReleased(evt);
            }
        });

        itemNameTxt.setEditable(false);
        itemNameTxt.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        itemNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNameTxtActionPerformed(evt);
            }
        });

        itemsLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        itemsLabel.setText("Items");

        refreshItemIdButton.setMaximumSize(new java.awt.Dimension(28, 28));
        refreshItemIdButton.setMinimumSize(new java.awt.Dimension(28, 28));
        refreshItemIdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshItemIdButtonActionPerformed(evt);
            }
        });

        refreshSupplierIdButton.setMaximumSize(new java.awt.Dimension(28, 28));
        refreshSupplierIdButton.setMinimumSize(new java.awt.Dimension(28, 28));
        refreshSupplierIdButton.setPreferredSize(new java.awt.Dimension(28, 28));
        refreshSupplierIdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshSupplierIdButtonActionPerformed(evt);
            }
        });

        ItemNameLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        ItemNameLabel.setText("Item Name");

        addItemButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        addItemButton.setText("Add Item");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemButtonActionPerformed(evt);
            }
        });

        itemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item id", "Name"
            }
        ));
        jScrollPane3.setViewportView(itemTable);
        if (itemTable.getColumnModel().getColumnCount() > 0) {
            itemTable.getColumnModel().getColumn(1).setResizable(false);
        }

        removeButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        removeButton1.setText("Remove");
        removeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButton1ActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(supplierIDLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(supplierIdTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(activeStatusLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(activeRadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(inactiveRadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(supplierNameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(supplierNameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(contactNoLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(addressLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(addSupplierButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(userLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(datetimeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(reloadButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(updateButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(contactNoTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(itemIdLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(itemIdTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(itemNameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(itemsLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(refreshItemIdButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(refreshSupplierIdButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(ItemNameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(addItemButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(removeButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(supplierNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addressLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(activeStatusLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplierIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contactNoLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                    .addComponent(supplierIdTxt)
                                    .addGap(18, 18, 18)
                                    .addComponent(refreshSupplierIdButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(activeRadioButton)
                                .addComponent(inactiveRadioButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1)
                                .addComponent(supplierNameTxt))
                            .addComponent(contactNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(itemIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ItemNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(itemsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(itemNameTxt)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                    .addComponent(itemIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(refreshItemIdButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(removeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addSupplierButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshItemIdButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(refreshSupplierIdButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supplierIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supplierIDLabel)
                            .addComponent(itemIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(itemIdLabel))))
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(itemNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ItemNameLabel)
                            .addComponent(supplierNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supplierNameLabel))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addressLabel))
                                .addGap(33, 33, 33)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(activeStatusLabel)
                                    .addComponent(activeRadioButton)
                                    .addComponent(inactiveRadioButton)))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(addSupplierButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(contactNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(contactNoLabel))
                                .addGap(2, 2, 2))
                            .addComponent(reloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(itemsLabel)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(removeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1)
                .addContainerGap())
            .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void supplierIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierIdTxtActionPerformed
        setData();
    }//GEN-LAST:event_supplierIdTxtActionPerformed

    private void supplierNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierNameTxtActionPerformed
        addressTxt.grabFocus();
    }//GEN-LAST:event_supplierNameTxtActionPerformed

    private void reloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadButtonActionPerformed
        newSupplierCycle();
    }//GEN-LAST:event_reloadButtonActionPerformed

    private void addSupplierButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSupplierButtonActionPerformed
        checkData();
    }//GEN-LAST:event_addSupplierButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        updateData();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void supplierNameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_supplierNameTxtKeyPressed
        if (evt.getKeyChar() >= 'a' && evt.getKeyChar() <= 'z' || evt.getKeyChar() >= 'A' && evt.getKeyChar() <= 'Z' || evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_SPACE) {
            supplierNameTxt.setEditable(true);
        } else {
            supplierNameTxt.setEditable(false);
        }
    }//GEN-LAST:event_supplierNameTxtKeyPressed

    private void supplierIdTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_supplierIdTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            supplierIdTxt.setEditable(true);
        } else {
            supplierIdTxt.setEditable(false);
        }
    }//GEN-LAST:event_supplierIdTxtKeyPressed

    private void contactNoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactNoTxtActionPerformed
        itemIdTxt.grabFocus();
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

    private void itemIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemIdTxtActionPerformed
        searchItem();
    }//GEN-LAST:event_itemIdTxtActionPerformed

    private void itemNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNameTxtActionPerformed
        addItemToTable();
    }//GEN-LAST:event_itemNameTxtActionPerformed

    private void refreshSupplierIdButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshSupplierIdButtonActionPerformed
        resetSupplier();
    }//GEN-LAST:event_refreshSupplierIdButtonActionPerformed

    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemButtonActionPerformed

        addItemToTable();
    }//GEN-LAST:event_addItemButtonActionPerformed

    private void itemIdTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemIdTxtKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            DropDownItemSearch dropDownItemSearch = new DropDownItemSearch(this);
            dropDownItemSearch.setVisible(true);
        }
    }//GEN-LAST:event_itemIdTxtKeyReleased

    private void supplierIdTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_supplierIdTxtKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            DropDownSupplierSearch dropDownSupplierSearch = new DropDownSupplierSearch(this);
            dropDownSupplierSearch.setVisible(true);
        }
    }//GEN-LAST:event_supplierIdTxtKeyReleased

    private void refreshItemIdButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshItemIdButtonActionPerformed
        resetItem();
    }//GEN-LAST:event_refreshItemIdButtonActionPerformed

    private void removeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButton1ActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) itemTable.getModel();
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow != -1) {
            dtm.removeRow(selectedRow);
        }
    }//GEN-LAST:event_removeButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(SupplierManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SupplierManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SupplierManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SupplierManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new SupplierManagement().setVisible(true);
            }
        });
    }

    String sql;
    ResultSet rs;
    UserDetails details;
    SystemComponents components;
    StatLogging log;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ItemNameLabel;
    private javax.swing.JRadioButton activeRadioButton;
    private javax.swing.JLabel activeStatusLabel;
    private javax.swing.JButton addItemButton;
    private javax.swing.JButton addSupplierButton;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextArea addressTxt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel contactNoLabel;
    private javax.swing.JTextField contactNoTxt;
    private javax.swing.JLabel datetimeLabel;
    private javax.swing.JRadioButton inactiveRadioButton;
    private javax.swing.JLabel itemIdLabel;
    public static javax.swing.JTextField itemIdTxt;
    private javax.swing.JTextField itemNameTxt;
    private javax.swing.JTable itemTable;
    private javax.swing.JLabel itemsLabel;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton refreshItemIdButton;
    private javax.swing.JButton refreshSupplierIdButton;
    private javax.swing.JButton reloadButton;
    private javax.swing.JButton removeButton1;
    private javax.swing.JLabel supplierIDLabel;
    public static javax.swing.JTextField supplierIdTxt;
    private javax.swing.JLabel supplierNameLabel;
    private javax.swing.JTextField supplierNameTxt;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
