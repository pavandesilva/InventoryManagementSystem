package view;

import common.DB;
import common.SystemComponents;
import common.UserDetails;
import common.StatLogging;
import controller.ItemController;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pavan De Silva
 */
public class AddItem extends javax.swing.JFrame {

    public AddItem() {
        initComponents();
        loadItemID();
        otherComponents();
        startTimer();
    }

    public AddItem(UserDetails details) {
        this.details = details;
        this.log = new StatLogging();
        initComponents();
        loadItemID();
        userLabel.setText(details.getFname());
        otherComponents();
        startTimer();
        log.infoLog(details, "Logged into Item managemnet");
    }
    public AddItem(UserDetails details, String id) {
        this.details = details;
        this.log = new StatLogging();
        initComponents();
        loadItemID();
        userLabel.setText(details.getFname());
        otherComponents();
        startTimer();
        itemIDTxt.setText(id);
        itemIDTxt.setEditable(false);
        itemNameTxt.setEditable(false);
        categoryComboBox.setEnabled(false);
        sellingTypeComboBox.setEnabled(false);
        saveButton.setEnabled(false);
        addImageButton.setEnabled(false);
        addImageButton.setVisible(false);
        refreshjButton.setEnabled(false);
        refreshjButton.setVisible(false);
        searchItem(id);
        log.infoLog(details, "Logged into Item managemnet");
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

    private void loadItemID() {
        try {
            sql = "SELECT itemid FROM item ORDER BY itemid DESC LIMIT 1;";
            rs = DB.search(sql);
            if (rs.next()) {
                itemIDTxt.setText("" + String.valueOf(Integer.valueOf(rs.getString("itemid")) + 1));
            }
            sql = "";
            rs = null;
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void otherComponents() {
        try {
            components = new SystemComponents();
            itemNameTxt.grabFocus();
            addImageButton.setBackground(Color.green);
            Image img1 = ImageIO.read(getClass().getResource("data/reload.png"));
            img1 = img1.getScaledInstance(refreshjButton.getWidth(), refreshjButton.getHeight(), Image.SCALE_SMOOTH);
            refreshjButton.setIcon(new ImageIcon(img1));

            Image img2 = ImageIO.read(getClass().getResource("data/add.png"));
            Image img3 = img2.getScaledInstance(addSpButton.getWidth(), addSpButton.getHeight(), Image.SCALE_SMOOTH);
            addSpButton.setIcon(new ImageIcon(img3));

            img3 = img2.getScaledInstance(addBpButton.getWidth(), addBpButton.getHeight(), Image.SCALE_SMOOTH);
            addBpButton.setIcon(new ImageIcon(img3));
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void searchItem(String id) {
        imageLabel.setIcon(null);
        try {
            DefaultTableModel model = (DefaultTableModel) sellingPricesTable.getModel();
            model.setRowCount(0);
            model = (DefaultTableModel) buyingPricesTable.getModel();
            model.setRowCount(0);
            rs = ItemController.itemSearch(id);
            if (rs.next()) {
                itemNameTxt.setText(rs.getString("name"));
                categoryComboBox.setSelectedItem(rs.getString("category"));
                sellingTypeComboBox.setSelectedItem(rs.getString("sellingtype"));
//                sellingTypeComboBox.();
                if (rs.getBoolean("status")) {
                    activeRadioButton.setSelected(true);
                }
                if (!rs.getBoolean("status")) {
                    inactiveRadioButton.setSelected(true);
                }

                if (!rs.getString("imagepath").equals(" ")) {
                    imagepath = rs.getString("imagepath");
                    File file = new File(imagepath);
                    Image image = ImageIO.read(file);
                    image = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                }

                sql = "SELECT * FROM itemprice WHERE itemid ='" + itemIDTxt.getText() + "'";
                rs = DB.search(sql);
                DefaultTableModel dtm1 = (DefaultTableModel) sellingPricesTable.getModel();
                dtm1.setRowCount(0);

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("sellingprice"));
                    dtm1.addRow(v);
                }
                sql = "SELECT * FROM itembuyprice WHERE itemid ='" + itemIDTxt.getText() + "'";
                rs = DB.search(sql);

                DefaultTableModel dtm2 = (DefaultTableModel) buyingPricesTable.getModel();
                dtm2.setRowCount(0);

                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("price"));
                    dtm2.addRow(v);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No item data found", "404", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void checkData() {
        try {
            DefaultTableModel dtm = (DefaultTableModel) sellingPricesTable.getModel();
            sql = "SELECT * from item where itemid='" + itemIDTxt.getText() + "'";
            rs = DB.search(sql);
            if (rs.next()) {
                components.error(this, "Item ID cannot be duplicate");
            } else if (itemIDTxt.getText().equals("")) {
                components.error(this, "Item ID cannot be empty");
            } else if (itemNameTxt.getText().equals("")) {
                components.error(this, "Item name cannot be empty");
            } else if (categoryComboBox.getSelectedIndex() == -1) {
                components.error(this, "Select item category");
            } else if (sellingTypeComboBox.getSelectedIndex() == -1) {
                components.error(this, "Select item selling type");
            } else if (dtm.getRowCount() == 0) {
                components.error(this, "Add a Selling price for the item.");
            } else {
                saveData();
            }

        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveData() {
        try {
//            String newImageName = itemIDTxt.getText() + "-" + itemNameTxt.getText() + "." + extention;
//            Path source = Paths.get(imagepath);
//            Path newDir = Paths.get("picItems/");
//            Files.createDirectories(newDir);
//            String newImagePath = Files.copy(source, newDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING).toString();
//            Files.move(newDir.resolve(source.getFileName()), newDir.resolveSibling(newImageName));
//            System.out.println(imagepath);

            DefaultTableModel dtm = (DefaultTableModel) sellingPricesTable.getModel();
            int status = 0;
            if (activeRadioButton.isSelected()) {
                status = 1;
            }

            sql = "INSERT INTO item (itemid, name, category, qty, sellingtype, imagepath, status) VALUES ('" + itemIDTxt.getText() + "', '" + itemNameTxt.getText() + "','" + categoryComboBox.getSelectedItem() + "' ,'" + 0 + "' ,'" + sellingTypeComboBox.getSelectedItem() + "' ,'" + imagepath + "', '" + status + "')";
            DB.addData(sql);
            log.infoLog(details, "New item added");

            sql = " DELETE FROM itemprice WHERE itemid='" + itemIDTxt.getText() + "'";
            DB.addData(sql);

            for (int row = 0; row < dtm.getRowCount(); row++) {

                String price = dtm.getValueAt(row, 0).toString();

                sql = "INSERT INTO itemprice (itemid, sellingprice) VALUES ('" + itemIDTxt.getText() + "','" + price + "') ";
                DB.addData(sql);
            }
            sql = " DELETE FROM itembuyprice WHERE itemid='" + itemIDTxt.getText() + "'";
            DB.addData(sql);

            dtm = (DefaultTableModel) buyingPricesTable.getModel();

            for (int row = 0; row < dtm.getRowCount(); row++) {

                String price = dtm.getValueAt(row, 0).toString();

                sql = "INSERT INTO itembuyprice (itemid, price) VALUES ('" + itemIDTxt.getText() + "','" + price + "') ";
                DB.addData(sql);
            }

            JOptionPane.showMessageDialog(this, "New item successfully added", "", JOptionPane.INFORMATION_MESSAGE);
            newItemCycle();

        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void update() {
        try {
            DefaultTableModel dtm1 = (DefaultTableModel) sellingPricesTable.getModel();
            int status = 0;
            if (activeRadioButton.isSelected()) {
                status = 1;
            }
            sql = "UPDATE item SET name='" + itemNameTxt.getText() + "', category='" + categoryComboBox.getSelectedItem() + "', sellingtype='" + sellingTypeComboBox.getSelectedItem() + "', imagepath='" + imagepath + "', status='" + status + "' WHERE itemid='" + itemIDTxt.getText() + "'";
            DB.addData(sql);
            log.infoLog(details, "Item Details updated");

            sql = " DELETE FROM itemprice WHERE itemid='" + itemIDTxt.getText() + "'";
            DB.addData(sql);

            for (int row = 0; row < dtm1.getRowCount(); row++) {

                System.out.println(dtm1.getValueAt(row, 0).toString());
                String price = dtm1.getValueAt(row, 0).toString();

                sql = "INSERT INTO itemprice (itemid, sellingprice) VALUES ('" + itemIDTxt.getText() + "','" + price + "') ";
                DB.addData(sql);
            }

            dtm1 = (DefaultTableModel) buyingPricesTable.getModel();
            sql = " DELETE FROM itembuyprice WHERE itemid='" + itemIDTxt.getText() + "'";
            DB.addData(sql);

            for (int row = 0; row < dtm1.getRowCount(); row++) {

                System.out.println(dtm1.getValueAt(row, 0).toString());
                String price = dtm1.getValueAt(row, 0).toString();

                sql = "INSERT INTO itembuyprice (itemid, price) VALUES ('" + itemIDTxt.getText() + "','" + price + "') ";
                DB.addData(sql);
            }
            JOptionPane.showMessageDialog(this, "Item data successfully Updated", "", JOptionPane.INFORMATION_MESSAGE);
            newItemCycle();
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setSpToTable() {
        DefaultTableModel dtm = (DefaultTableModel) sellingPricesTable.getModel();
        Vector v = new Vector();
        v.add(sellingPriceTxt.getText());
        dtm.addRow(v);
        sellingPriceTxt.setText("");
        buyingPriceTxt.grabFocus();
    }

    private void setBpToTable() {
        DefaultTableModel dtm = (DefaultTableModel) buyingPricesTable.getModel();
        Vector v = new Vector();
        v.add(buyingPriceTxt.getText());
        dtm.addRow(v);
        buyingPriceTxt.setText("");
    }

    private void newItemCycle() {

        DefaultTableModel dtm = (DefaultTableModel) sellingPricesTable.getModel();
        dtm.setRowCount(0);
        dtm = (DefaultTableModel) buyingPricesTable.getModel();
        dtm.setRowCount(0);
        loadItemID();
        imagepath = " ";
        itemNameTxt.setText(null);
        activeRadioButton.setSelected(true);
        categoryComboBox.setSelectedIndex(-1);
        sellingTypeComboBox.setSelectedIndex(-1);
        sellingPricesTable.clearSelection();
        sellingPriceTxt.setText(null);
        imageLabel.setIcon(null);
        addImageButton.setText("Add Image");
        addImageButton.setBackground(Color.green);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusButtonGroup = new javax.swing.ButtonGroup();
        jLabel16 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        itemIDTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        itemNameTxt = new javax.swing.JTextField();
        logoutButton = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        categoryComboBox = new javax.swing.JComboBox<>();
        imageLabel = new javax.swing.JLabel();
        addImageButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        datetimeLabel = new javax.swing.JLabel();
        activeStatusLabel = new javax.swing.JLabel();
        activeRadioButton = new javax.swing.JRadioButton();
        inactiveRadioButton = new javax.swing.JRadioButton();
        sellingTypeComboBox = new javax.swing.JComboBox<>();
        sellingTypeLabel = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        refreshjButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        sellingPricesTable = new javax.swing.JTable();
        spRemoveButton = new javax.swing.JButton();
        sellingPricejLabel = new javax.swing.JLabel();
        sellingPriceTxt = new javax.swing.JTextField();
        addSpButton = new javax.swing.JButton();
        buyingPricejLabel = new javax.swing.JLabel();
        buyingPriceTxt = new javax.swing.JTextField();
        addBpButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        buyingPricesTable = new javax.swing.JTable();
        bpRemoveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Item Management");

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(153, 153, 153));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Items Management");
        jLabel16.setToolTipText("");
        jLabel16.setOpaque(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Item ID");

        itemIDTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        itemIDTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemIDTxtActionPerformed(evt);
            }
        });
        itemIDTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemIDTxtKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Item Name");

        itemNameTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        itemNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNameTxtActionPerformed(evt);
            }
        });
        itemNameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemNameTxtKeyPressed(evt);
            }
        });

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        userLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        userLabel.setText("User");

        categoryLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        categoryLabel.setText("Category");

        categoryComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bakery", "Beverage", "Bread & spreads", "Care Products", "Cosmetics", "Dairy", "Dried Goods", "Freezer", "Fruit", "Meat & Fish", "Pharmacy", "Produce & Floral", "Snacks", "Vegetables" }));
        categoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryComboBoxActionPerformed(evt);
            }
        });

        addImageButton.setText("Add Image");
        addImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addImageButtonActionPerformed(evt);
            }
        });

        saveButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        datetimeLabel.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        datetimeLabel.setText("datetime");
        datetimeLabel.setMaximumSize(new java.awt.Dimension(20, 12));
        datetimeLabel.setMinimumSize(new java.awt.Dimension(20, 12));

        activeStatusLabel.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        activeStatusLabel.setText("Active Status");

        statusButtonGroup.add(activeRadioButton);
        activeRadioButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        activeRadioButton.setSelected(true);
        activeRadioButton.setText("Active");

        statusButtonGroup.add(inactiveRadioButton);
        inactiveRadioButton.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        inactiveRadioButton.setText("Inactive");

        sellingTypeComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sellingTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pieces", "by Weight" }));
        sellingTypeComboBox.setSelectedIndex(-1);

        sellingTypeLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        sellingTypeLabel.setText("Selling Type");

        updateButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        refreshjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshjButtonActionPerformed(evt);
            }
        });

        sellingPricesTable.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        sellingPricesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Selling prices"
            }
        ));
        jScrollPane1.setViewportView(sellingPricesTable);

        spRemoveButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        spRemoveButton.setText("Remove");
        spRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spRemoveButtonActionPerformed(evt);
            }
        });

        sellingPricejLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        sellingPricejLabel.setText("Selling Price");

        sellingPriceTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sellingPriceTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellingPriceTxtActionPerformed(evt);
            }
        });
        sellingPriceTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sellingPriceTxtKeyPressed(evt);
            }
        });

        addSpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSpButtonActionPerformed(evt);
            }
        });

        buyingPricejLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buyingPricejLabel.setText("Buying Price");

        buyingPriceTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buyingPriceTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyingPriceTxtActionPerformed(evt);
            }
        });
        buyingPriceTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buyingPriceTxtKeyPressed(evt);
            }
        });

        addBpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBpButtonActionPerformed(evt);
            }
        });

        buyingPricesTable.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        buyingPricesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Buying prices"
            }
        ));
        jScrollPane2.setViewportView(buyingPricesTable);

        bpRemoveButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bpRemoveButton.setText("Remove");
        bpRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bpRemoveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(logoutButton)
                        .addGap(14, 14, 14)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(itemIDTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(refreshjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(imageLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(addImageButton)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spRemoveButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sellingPricejLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sellingPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addSpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(itemNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bpRemoveButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(categoryLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buyingPricejLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buyingPriceTxt)
                                .addGap(18, 18, 18)
                                .addComponent(addBpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(activeStatusLabel)
                                .addGap(18, 18, 18)
                                .addComponent(activeRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(inactiveRadioButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sellingTypeLabel)
                                .addGap(18, 18, 18)
                                .addComponent(sellingTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logoutButton)
                    .addComponent(userLabel)
                    .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(itemIDTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(itemNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(activeStatusLabel)
                        .addComponent(activeRadioButton)
                        .addComponent(inactiveRadioButton)
                        .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(categoryLabel)))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sellingTypeLabel)
                                .addComponent(sellingTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buyingPricejLabel)
                                        .addComponent(buyingPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sellingPricejLabel)
                                        .addComponent(sellingPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(addBpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addSpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(bpRemoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spRemoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addImageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(245, 245, 245))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addImageButtonActionPerformed

        try {
            if (addImageButton.getText().equals("Add Image")) {
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
                    File imageFile = new File(imagepath);
                    Image image = ImageIO.read(imageFile);
                    image = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                    addImageButton.setText("Remove Image");
                    addImageButton.setBackground(Color.red);
                }
            } else {
                imageLabel.setIcon(null);
                addImageButton.setText("Add Image");
                addImageButton.setBackground(Color.green);
            }
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addImageButtonActionPerformed

    private void itemIDTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemIDTxtActionPerformed
        searchItem(itemIDTxt.getText());
    }//GEN-LAST:event_itemIDTxtActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        checkData();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void itemNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNameTxtActionPerformed
        categoryComboBox.showPopup();
    }//GEN-LAST:event_itemNameTxtActionPerformed

    private void refreshjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshjButtonActionPerformed
        loadItemID();
    }//GEN-LAST:event_refreshjButtonActionPerformed

    private void sellingPriceTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellingPriceTxtActionPerformed
        if (!sellingPriceTxt.getText().equals("")) {
            setSpToTable();
        }
    }//GEN-LAST:event_sellingPriceTxtActionPerformed

    private void addSpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSpButtonActionPerformed
        if (!sellingPriceTxt.getText().equals("")) {
            setSpToTable();
        }
    }//GEN-LAST:event_addSpButtonActionPerformed

    private void sellingPriceTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sellingPriceTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sellingPriceTxt.setEditable(true);
        } else {
            sellingPriceTxt.setEditable(false);
        }

    }//GEN-LAST:event_sellingPriceTxtKeyPressed

    private void itemIDTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemIDTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            itemIDTxt.setEditable(true);
        } else {
            itemIDTxt.setEditable(false);
        }
    }//GEN-LAST:event_itemIDTxtKeyPressed

    private void itemNameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemNameTxtKeyPressed
        if (evt.getKeyChar() >= 'a' && evt.getKeyChar() <= 'z' || evt.getKeyChar() >= 'A' && evt.getKeyChar() <= 'Z' || evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER|| evt.getKeyCode() == KeyEvent.VK_SPACE) {
            itemNameTxt.setEditable(true);
        } else {
            itemNameTxt.setEditable(false);
        }
    }//GEN-LAST:event_itemNameTxtKeyPressed

    private void spRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spRemoveButtonActionPerformed

        DefaultTableModel dtm = (DefaultTableModel) sellingPricesTable.getModel();
        int selectedRow = sellingPricesTable.getSelectedRow();
        if (selectedRow != -1) {
            dtm.removeRow(selectedRow);
        }

    }//GEN-LAST:event_spRemoveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        newItemCycle();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        update();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        components.logout(this);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void buyingPriceTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyingPriceTxtActionPerformed
        if (!buyingPriceTxt.getText().equals("")) {
            setBpToTable();
        }
    }//GEN-LAST:event_buyingPriceTxtActionPerformed

    private void buyingPriceTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buyingPriceTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sellingPriceTxt.setEditable(true);
        } else {
            sellingPriceTxt.setEditable(false);
        }
    }//GEN-LAST:event_buyingPriceTxtKeyPressed

    private void addBpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBpButtonActionPerformed
        if (!buyingPriceTxt.getText().equals("")) {
            setBpToTable();
        }
    }//GEN-LAST:event_addBpButtonActionPerformed

    private void bpRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bpRemoveButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) buyingPricesTable.getModel();
        int selectedRow = buyingPricesTable.getSelectedRow();
        if (selectedRow != -1) {
            dtm.removeRow(selectedRow);
        }
    }//GEN-LAST:event_bpRemoveButtonActionPerformed

    private void categoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryComboBoxActionPerformed
        sellingPriceTxt.grabFocus();
    }//GEN-LAST:event_categoryComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(AddItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddItem().setVisible(true);
            }
        });
    }

    SystemComponents components;
    String sql, imagepath = " ", extention;
    ResultSet rs;
    UserDetails details;
    StatLogging log;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton activeRadioButton;
    private javax.swing.JLabel activeStatusLabel;
    private javax.swing.JButton addBpButton;
    private javax.swing.JButton addImageButton;
    private javax.swing.JButton addSpButton;
    private javax.swing.JButton bpRemoveButton;
    private javax.swing.JTextField buyingPriceTxt;
    private javax.swing.JLabel buyingPricejLabel;
    private javax.swing.JTable buyingPricesTable;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JLabel datetimeLabel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JRadioButton inactiveRadioButton;
    private javax.swing.JTextField itemIDTxt;
    private javax.swing.JTextField itemNameTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton refreshjButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField sellingPriceTxt;
    private javax.swing.JLabel sellingPricejLabel;
    private javax.swing.JTable sellingPricesTable;
    private javax.swing.JComboBox<String> sellingTypeComboBox;
    private javax.swing.JLabel sellingTypeLabel;
    private javax.swing.JButton spRemoveButton;
    private javax.swing.ButtonGroup statusButtonGroup;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
