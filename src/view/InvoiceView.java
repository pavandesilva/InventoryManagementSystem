/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author DELL
 */
public class InvoiceView extends javax.swing.JFrame {

    /**
     * Creates new form InvoiceView
     *
     * @param details
     */
    public InvoiceView(UserDetails details) {
        initComponents();
        this.details = details;
        this.log = new StatLogging();
        components = new SystemComponents();
        userLabel.setText(details.getFname());
        generateInvoiceID();
        setdefaultCustomer();
        othercomponents();
        startTimer();
        log.infoLog(details, "Logged into Invoice");
        itemIdTxt.grabFocus();
    }

    public InvoiceView() {
        initComponents();
        components = new SystemComponents();
        generateInvoiceID();
        setdefaultCustomer();
        othercomponents();
        startTimer();
    }

    private void othercomponents() {
        try {
            priceList.setVisible(false);
            itemIdTxt.grabFocus();
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
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

    private void generateInvoiceID() {
        try {
            sql = "SELECT invoiceid FROM invoice ORDER BY invoiceid DESC LIMIT 1;";
            rs = DB.search(sql);
            if (rs.next()) {
                invoiceIdTxt.setText(String.valueOf(Integer.valueOf(rs.getString("invoiceid")) + 1));
            }
            sql = "";
            rs = null;
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
    }

    private void setdefaultCustomer() {
        try {
            sql = "SELECT firstname, lastname FROM customer WHERE customerid = '1'";
            rs = DB.search(sql);

            if (rs.next()) {
                customerIdTxt.setText("1");
                customerNameTxt.setText(rs.getString("firstname") + " " + rs.getString("lastname"));
            }
            itemIdTxt.grabFocus();
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
    }

    private void rotationToItem() {
        itemIdTxt.setText(null);
        itemNameTxt.setText(null);
        unitPriceTxt.setText(null);
        stockQtyTxt.setText(null);
        buyingQtyTxt.setText(null);
        itemTotalTxt.setText(null);
        itemDiscountTxt.setText("0");
        subTotalQtyTxt.setText(null);
        priceList.clearSelection();
        priceList.setVisible(false);
        itemIdTxt.grabFocus();
    }

        private void rotationToNewInvoice() {
        DefaultTableModel dtm = (DefaultTableModel) table1.getModel();
        dtm.setRowCount(0);
        itemIdTxt.setText("");
        itemNameTxt.setText("");
        unitPriceTxt.setText("");
        stockQtyTxt.setText("");
        buyingQtyTxt.setText("");
        itemTotalTxt.setText("");
        itemDiscountTxt.setText("");
        subTotalQtyTxt.setText("");
        invoiceTotalTxt.setText("0");
        invoiceDiscountTxt.setText("0");
        netTotalTxt.setText("0");
        paymentTxt.setText("0");
        balanceTxt.setText("0");
        customerIdTxt.setText("");
        customerNameTxt.setText("");
        setdefaultCustomer();
        generateInvoiceID();
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        invoiceIdTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        customerIdTxt = new javax.swing.JTextField();
        customerNameTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        itemIdTxt = new javax.swing.JTextField();
        qtyLabel = new javax.swing.JLabel();
        buyingQtyTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        itemNameTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        itemTotalTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        unitPriceTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        itemDiscountTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        stockQtyTxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        subTotalQtyTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        invoiceTotalTxt = new javax.swing.JTextField();
        invoiceDiscountTxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        netTotalTxt = new javax.swing.JTextField();
        paymentTxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        balanceTxt = new javax.swing.JTextField();
        datetimeLabel = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        priceList = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        newCustomerButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Invoice");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel1.setText("Invoice ID");

        invoiceIdTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel4.setText("Customer");

        customerIdTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        customerIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerIdTxtActionPerformed(evt);
            }
        });
        customerIdTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                customerIdTxtKeyReleased(evt);
            }
        });

        customerNameTxt.setEditable(false);
        customerNameTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel2.setText("Item ID");

        itemIdTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
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

        qtyLabel.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        qtyLabel.setText("QTY");

        buyingQtyTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        buyingQtyTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyingQtyTxtActionPerformed(evt);
            }
        });
        buyingQtyTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buyingQtyTxtKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel5.setText("Item Name");

        itemNameTxt.setEditable(false);
        itemNameTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel6.setText("Total");

        itemTotalTxt.setEditable(false);
        itemTotalTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel7.setText("Unit Price");

        unitPriceTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel8.setText("Discount %");

        itemDiscountTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        itemDiscountTxt.setText("0");
        itemDiscountTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemDiscountTxtActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel9.setText("Stock Qty");

        stockQtyTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel10.setText("Sub Total");

        subTotalQtyTxt.setEditable(false);
        subTotalQtyTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        subTotalQtyTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subTotalQtyTxtActionPerformed(evt);
            }
        });

        table1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEM ID", "NAME", "QTY", "U PRICE", "TOTAL", "DISCOUNT %", "SUB TOTAL"
            }
        ));
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel11.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel11.setText("Total Value");

        invoiceTotalTxt.setEditable(false);
        invoiceTotalTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        invoiceTotalTxt.setForeground(new java.awt.Color(255, 51, 51));
        invoiceTotalTxt.setText("0.0");

        invoiceDiscountTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        invoiceDiscountTxt.setForeground(new java.awt.Color(0, 204, 0));
        invoiceDiscountTxt.setText("0");
        invoiceDiscountTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invoiceDiscountTxtActionPerformed(evt);
            }
        });
        invoiceDiscountTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                invoiceDiscountTxtKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel12.setText("DISCOUNT BY VALUE");

        jLabel13.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel13.setText("NET TOTAL");

        netTotalTxt.setEditable(false);
        netTotalTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        netTotalTxt.setForeground(new java.awt.Color(255, 0, 51));
        netTotalTxt.setText("0.0");

        paymentTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        paymentTxt.setText("0");
        paymentTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentTxtActionPerformed(evt);
            }
        });
        paymentTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                paymentTxtKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel14.setText("PAYMENT");

        jLabel15.setFont(new java.awt.Font("Segoe UI Semilight", 1, 36)); // NOI18N
        jLabel15.setText("BALANCE");

        balanceTxt.setFont(new java.awt.Font("Segoe UI Semilight", 1, 36)); // NOI18N
        balanceTxt.setForeground(new java.awt.Color(0, 0, 255));
        balanceTxt.setText("0.0");
        balanceTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceTxtActionPerformed(evt);
            }
        });
        balanceTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                balanceTxtKeyPressed(evt);
            }
        });

        datetimeLabel.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        datetimeLabel.setText("datetime");
        datetimeLabel.setMaximumSize(new java.awt.Dimension(20, 12));
        datetimeLabel.setMinimumSize(new java.awt.Dimension(20, 12));

        userLabel.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        userLabel.setText("User");
        userLabel.setMaximumSize(new java.awt.Dimension(20, 12));
        userLabel.setMinimumSize(new java.awt.Dimension(20, 12));

        logoutButton.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        priceList.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        priceList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                priceListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(priceList);

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        newCustomerButton.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
        newCustomerButton.setText("New Customer");
        newCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCustomerButtonActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(invoiceIdTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(customerIdTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(customerNameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(itemIdTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(qtyLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(buyingQtyTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(itemNameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(itemTotalTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(unitPriceTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(itemDiscountTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(stockQtyTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(subTotalQtyTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(invoiceTotalTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(invoiceDiscountTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(netTotalTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(paymentTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(balanceTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(datetimeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(userLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(logoutButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cancelButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(removeButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(newCustomerButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(invoiceTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(invoiceDiscountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(108, 108, 108)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(netTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(180, 180, 180)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paymentTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(36, 36, 36)))
                .addGap(111, 111, 111))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(qtyLabel))
                                .addGap(18, 18, 18)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(buyingQtyTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(invoiceIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(itemIdTxt))
                                .addGap(26, 26, 26)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(customerIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(96, 96, 96)
                                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jLabel7))
                                                .addGap(18, 18, 18)
                                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(unitPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(itemDiscountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                                .addComponent(customerNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(newCustomerButton)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel9)
                                            .addComponent(cancelButton))
                                        .addGap(65, 65, 65))
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(itemNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(itemTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(stockQtyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(subTotalQtyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(removeButton, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logoutButton)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(112, 112, 112)
                        .addComponent(balanceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoutButton)
                    .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cancelButton)
                                .addComponent(removeButton))
                            .addComponent(newCustomerButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(invoiceIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)
                                .addComponent(customerIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(customerNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(itemIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(itemNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(unitPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(stockQtyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(20, 20, 20)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(qtyLabel)
                                        .addComponent(buyingQtyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(itemTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(subTotalQtyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8)
                                        .addComponent(itemDiscountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10))))
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(invoiceTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(netTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(paymentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(invoiceDiscountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(balanceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(30, 30, 30))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1500, 730));

        titleLabel.setBackground(new java.awt.Color(0, 0, 0));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(153, 153, 153));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Invoice");
        titleLabel.setToolTipText("");
        titleLabel.setOpaque(true);
        getContentPane().add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 60));

        setSize(new java.awt.Dimension(1550, 840));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void customerIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerIdTxtActionPerformed
        searchCustomer();
    }//GEN-LAST:event_customerIdTxtActionPerformed

    private void itemIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemIdTxtActionPerformed
        searchItem();
    }//GEN-LAST:event_itemIdTxtActionPerformed

    private void buyingQtyTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyingQtyTxtActionPerformed
        calculateTotal();
    }//GEN-LAST:event_buyingQtyTxtActionPerformed

    private void itemDiscountTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemDiscountTxtActionPerformed
        setDataToTable();
    }//GEN-LAST:event_itemDiscountTxtActionPerformed

    private void itemIdTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemIdTxtKeyReleased

        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            buyingQtyTxt.grabFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            DropDownItemSearch dropDownItemSearch = new DropDownItemSearch(this);
            dropDownItemSearch.setVisible(true);
        }
    }//GEN-LAST:event_itemIdTxtKeyReleased

    private void invoiceDiscountTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invoiceDiscountTxtActionPerformed

        if (!invoiceDiscountTxt.getText().trim().equals("")) {
            netTotalTxt.setText("" + (calNettotal(Double.parseDouble(invoiceTotalTxt.getText().trim()), Double.parseDouble(invoiceDiscountTxt.getText().trim()))).toString());
            paymentTxt.grabFocus();
        } else {
            paymentTxt.setText(null);
            paymentTxt.grabFocus();
        }
    }//GEN-LAST:event_invoiceDiscountTxtActionPerformed

    private void paymentTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentTxtActionPerformed

        double netotal = Double.parseDouble(netTotalTxt.getText().trim());
        double payment = Double.parseDouble(paymentTxt.getText().trim());
        if (netotal <= payment) {
            balanceTxt.setText("" + (payment - netotal));
            balanceTxt.grabFocus();
        }
    }//GEN-LAST:event_paymentTxtActionPerformed

    private void balanceTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceTxtActionPerformed
        addInvoice();
    }//GEN-LAST:event_balanceTxtActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        components.logout(this);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void customerIdTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerIdTxtKeyReleased
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            customerIdTxt.setEditable(true);
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            new DropDownCustomerSearch(this).setVisible(true);
        } else {
            customerIdTxt.setEditable(false);
        }
    }//GEN-LAST:event_customerIdTxtKeyReleased

    private void priceListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_priceListMouseClicked
        setUnitPrice();
    }//GEN-LAST:event_priceListMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        generateInvoiceID();
        setdefaultCustomer();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void subTotalQtyTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subTotalQtyTxtActionPerformed
    }//GEN-LAST:event_subTotalQtyTxtActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        rotationToNewInvoice();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) table1.getModel();
        dtm.removeRow(table1.getSelectedRow());
        removeButton.setBackground(null);
    }//GEN-LAST:event_removeButtonActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        removeButton.setBackground(Color.red);
    }//GEN-LAST:event_table1MouseClicked

    private void buyingQtyTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buyingQtyTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buyingQtyTxt.setEditable(true);
        } else {
            buyingQtyTxt.setEditable(false);
        }
    }//GEN-LAST:event_buyingQtyTxtKeyPressed

    private void newCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCustomerButtonActionPerformed
        new CustomerManagement(details).setVisible(true);
    }//GEN-LAST:event_newCustomerButtonActionPerformed

    private void invoiceDiscountTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_invoiceDiscountTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            invoiceDiscountTxt.setEditable(true);
        } else {
            invoiceDiscountTxt.setEditable(false);
        }
    }//GEN-LAST:event_invoiceDiscountTxtKeyPressed

    private void paymentTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paymentTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            paymentTxt.setEditable(true);
        } else {
            paymentTxt.setEditable(false);
        }
    }//GEN-LAST:event_paymentTxtKeyPressed

    private void balanceTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_balanceTxtKeyPressed
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            balanceTxt.setEditable(true);
        } else {
            balanceTxt.setEditable(false);
        }
    }//GEN-LAST:event_balanceTxtKeyPressed

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
            java.util.logging.Logger.getLogger(InvoiceView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InvoiceView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InvoiceView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InvoiceView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new InvoiceView().setVisible(true);
            }
        });
    }

    UserDetails details;
    SystemComponents components;
    String sql, sellingType;
    ResultSet rs;
    StatLogging log;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField balanceTxt;
    private javax.swing.JTextField buyingQtyTxt;
    private javax.swing.JButton cancelButton;
    public static javax.swing.JTextField customerIdTxt;
    public static javax.swing.JTextField customerNameTxt;
    private javax.swing.JLabel datetimeLabel;
    private javax.swing.JTextField invoiceDiscountTxt;
    private javax.swing.JTextField invoiceIdTxt;
    private javax.swing.JTextField invoiceTotalTxt;
    private javax.swing.JTextField itemDiscountTxt;
    public static javax.swing.JTextField itemIdTxt;
    private javax.swing.JTextField itemNameTxt;
    private javax.swing.JTextField itemTotalTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTextField netTotalTxt;
    private javax.swing.JButton newCustomerButton;
    private javax.swing.JTextField paymentTxt;
    private javax.swing.JList<String> priceList;
    private javax.swing.JLabel qtyLabel;
    private javax.swing.JButton removeButton;
    private javax.swing.JTextField stockQtyTxt;
    private javax.swing.JTextField subTotalQtyTxt;
    private javax.swing.JTable table1;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField unitPriceTxt;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
