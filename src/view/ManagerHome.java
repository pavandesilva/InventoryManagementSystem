package view;

import common.DB;
import common.SystemComponents;
import common.UserDetails;
import common.StatLogging;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Pavan De Silva
 */
public class ManagerHome extends javax.swing.JFrame {

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        backgroundLabel = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        sidebarPanel = new javax.swing.JPanel();
        invoiceLabel = new javax.swing.JLabel();
        grnLabel = new javax.swing.JLabel();
        stockLabel = new javax.swing.JLabel();
        addItemLabel = new javax.swing.JLabel();
        employeeManageLabel = new javax.swing.JLabel();
        resetUserPassewordLabel = new javax.swing.JLabel();
        addSystemUser = new javax.swing.JLabel();
        suppliersManagement = new javax.swing.JLabel();
        PO = new javax.swing.JLabel();
        customerManagement = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        logoutButton = new javax.swing.JButton();
        datetimeLabel = new javax.swing.JLabel();
        tableTitleLabel = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        empPersDetailButton = new javax.swing.JButton();
        empDetButton = new javax.swing.JButton();
        supplierDetButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home page");
        setMinimumSize(new java.awt.Dimension(1600, 880));
        setResizable(false);

        titleLabel.setBackground(new java.awt.Color(0, 0, 0));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(153, 153, 153));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Homepage");
        titleLabel.setToolTipText("");
        titleLabel.setOpaque(true);

        backgroundLabel.setText("backgroundLabel");

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(1500, 740));

        sidebarPanel.setBackground(new java.awt.Color(54, 70, 78));

        invoiceLabel.setBackground(new java.awt.Color(54, 70, 78));
        invoiceLabel.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        invoiceLabel.setForeground(new java.awt.Color(204, 204, 204));
        invoiceLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        invoiceLabel.setText("Invoice");
        invoiceLabel.setOpaque(true);
        invoiceLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invoiceLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                invoiceLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                invoiceLabelMouseExited(evt);
            }
        });

        grnLabel.setBackground(new java.awt.Color(54, 70, 78));
        grnLabel.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        grnLabel.setForeground(new java.awt.Color(204, 204, 204));
        grnLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        grnLabel.setText("GRN");
        grnLabel.setMaximumSize(new java.awt.Dimension(68, 23));
        grnLabel.setMinimumSize(new java.awt.Dimension(68, 23));
        grnLabel.setOpaque(true);
        grnLabel.setPreferredSize(new java.awt.Dimension(68, 23));
        grnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grnLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                grnLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                grnLabelMouseExited(evt);
            }
        });

        stockLabel.setBackground(new java.awt.Color(54, 70, 78));
        stockLabel.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        stockLabel.setForeground(new java.awt.Color(204, 204, 204));
        stockLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stockLabel.setText("Stock");
        stockLabel.setMaximumSize(new java.awt.Dimension(68, 23));
        stockLabel.setMinimumSize(new java.awt.Dimension(68, 23));
        stockLabel.setOpaque(true);
        stockLabel.setPreferredSize(new java.awt.Dimension(68, 23));
        stockLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                stockLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                stockLabelMouseExited(evt);
            }
        });

        addItemLabel.setBackground(new java.awt.Color(54, 70, 78));
        addItemLabel.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        addItemLabel.setForeground(new java.awt.Color(204, 204, 204));
        addItemLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addItemLabel.setText("Items Management");
        addItemLabel.setMaximumSize(new java.awt.Dimension(68, 23));
        addItemLabel.setMinimumSize(new java.awt.Dimension(68, 23));
        addItemLabel.setOpaque(true);
        addItemLabel.setPreferredSize(new java.awt.Dimension(68, 23));
        addItemLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addItemLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addItemLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addItemLabelMouseExited(evt);
            }
        });

        employeeManageLabel.setBackground(new java.awt.Color(54, 70, 78));
        employeeManageLabel.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        employeeManageLabel.setForeground(new java.awt.Color(204, 204, 204));
        employeeManageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        employeeManageLabel.setText("Employee Management");
        employeeManageLabel.setMaximumSize(new java.awt.Dimension(68, 23));
        employeeManageLabel.setMinimumSize(new java.awt.Dimension(68, 23));
        employeeManageLabel.setOpaque(true);
        employeeManageLabel.setPreferredSize(new java.awt.Dimension(68, 23));
        employeeManageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeManageLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                employeeManageLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                employeeManageLabelMouseExited(evt);
            }
        });

        resetUserPassewordLabel.setBackground(new java.awt.Color(54, 70, 78));
        resetUserPassewordLabel.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        resetUserPassewordLabel.setForeground(new java.awt.Color(204, 204, 204));
        resetUserPassewordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resetUserPassewordLabel.setText("Reset User Password");
        resetUserPassewordLabel.setMaximumSize(new java.awt.Dimension(68, 23));
        resetUserPassewordLabel.setMinimumSize(new java.awt.Dimension(68, 23));
        resetUserPassewordLabel.setOpaque(true);
        resetUserPassewordLabel.setPreferredSize(new java.awt.Dimension(68, 23));
        resetUserPassewordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetUserPassewordLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                resetUserPassewordLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetUserPassewordLabelMouseExited(evt);
            }
        });

        addSystemUser.setBackground(new java.awt.Color(54, 70, 78));
        addSystemUser.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        addSystemUser.setForeground(new java.awt.Color(204, 204, 204));
        addSystemUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addSystemUser.setText("Add System User");
        addSystemUser.setMaximumSize(new java.awt.Dimension(68, 23));
        addSystemUser.setMinimumSize(new java.awt.Dimension(68, 23));
        addSystemUser.setOpaque(true);
        addSystemUser.setPreferredSize(new java.awt.Dimension(68, 23));
        addSystemUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSystemUserMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addSystemUserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addSystemUserMouseExited(evt);
            }
        });

        suppliersManagement.setBackground(new java.awt.Color(54, 70, 78));
        suppliersManagement.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        suppliersManagement.setForeground(new java.awt.Color(204, 204, 204));
        suppliersManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        suppliersManagement.setText("Suppliers Management");
        suppliersManagement.setMaximumSize(new java.awt.Dimension(68, 23));
        suppliersManagement.setMinimumSize(new java.awt.Dimension(68, 23));
        suppliersManagement.setOpaque(true);
        suppliersManagement.setPreferredSize(new java.awt.Dimension(68, 23));
        suppliersManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                suppliersManagementMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                suppliersManagementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                suppliersManagementMouseExited(evt);
            }
        });

        PO.setBackground(new java.awt.Color(54, 70, 78));
        PO.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        PO.setForeground(new java.awt.Color(204, 204, 204));
        PO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PO.setText("Purchase Orders");
        PO.setMaximumSize(new java.awt.Dimension(68, 23));
        PO.setMinimumSize(new java.awt.Dimension(68, 23));
        PO.setOpaque(true);
        PO.setPreferredSize(new java.awt.Dimension(68, 23));
        PO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                POMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                POMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                POMouseExited(evt);
            }
        });

        customerManagement.setBackground(new java.awt.Color(54, 70, 78));
        customerManagement.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        customerManagement.setForeground(new java.awt.Color(204, 204, 204));
        customerManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        customerManagement.setText("Customer Management");
        customerManagement.setMaximumSize(new java.awt.Dimension(68, 23));
        customerManagement.setMinimumSize(new java.awt.Dimension(68, 23));
        customerManagement.setOpaque(true);
        customerManagement.setPreferredSize(new java.awt.Dimension(68, 23));
        customerManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerManagementMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                customerManagementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                customerManagementMouseExited(evt);
            }
        });

        javax.swing.GroupLayout sidebarPanelLayout = new javax.swing.GroupLayout(sidebarPanel);
        sidebarPanel.setLayout(sidebarPanelLayout);
        sidebarPanelLayout.setHorizontalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(invoiceLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
            .addComponent(grnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(stockLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(addItemLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(addSystemUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(resetUserPassewordLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(suppliersManagement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(employeeManageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(customerManagement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sidebarPanelLayout.setVerticalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarPanelLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(invoiceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(grnLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(stockLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(suppliersManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(customerManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(employeeManageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(resetUserPassewordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PO, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addSystemUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        jTable1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 24)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Qty"
            }
        ));
        jTable1.setRowHeight(25);
        jScrollPane2.setViewportView(jTable1);

        logoutButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        logoutButton.setText("Logout");
        logoutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        datetimeLabel.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        datetimeLabel.setText("datetime");
        datetimeLabel.setMaximumSize(new java.awt.Dimension(20, 12));
        datetimeLabel.setMinimumSize(new java.awt.Dimension(20, 12));

        tableTitleLabel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 16)); // NOI18N
        tableTitleLabel.setText("LOW ON STOCK ITEM LIST");

        userLabel.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        userLabel.setText("User");
        userLabel.setMaximumSize(new java.awt.Dimension(20, 12));
        userLabel.setMinimumSize(new java.awt.Dimension(20, 12));

        empPersDetailButton.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        empPersDetailButton.setText("Employee Personal Details");
        empPersDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empPersDetailButtonActionPerformed(evt);
            }
        });

        empDetButton.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        empDetButton.setText("Employee Details Report");
        empDetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empDetButtonActionPerformed(evt);
            }
        });

        supplierDetButton.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        supplierDetButton.setText("Supplier Details Report");
        supplierDetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierDetButtonActionPerformed(evt);
            }
        });

        jLayeredPane2.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(logoutButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(datetimeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(tableTitleLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(userLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(empPersDetailButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(empDetButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(supplierDetButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tableTitleLabel)
                            .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 563, Short.MAX_VALUE)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                                .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logoutButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(supplierDetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empPersDetailButton)
                                    .addComponent(empDetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(173, 173, 173)))))
                .addContainerGap())
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datetimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoutButton)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addComponent(empDetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(empPersDetailButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(supplierDetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(tableTitleLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.setLayer(sidebarPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLayeredPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(sidebarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(sidebarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 1576, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(backgroundLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1576, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(backgroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void invoiceLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invoiceLabelMouseClicked
        new InvoiceView(details).setVisible(true);
    }//GEN-LAST:event_invoiceLabelMouseClicked

    private void invoiceLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invoiceLabelMouseEntered
        invoiceLabel.setBackground(Color.white);
        invoiceLabel.setForeground(Color.black);
    }//GEN-LAST:event_invoiceLabelMouseEntered

    private void invoiceLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invoiceLabelMouseExited
        invoiceLabel.setBackground(new java.awt.Color(54, 70, 78));
        invoiceLabel.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_invoiceLabelMouseExited

    private void grnLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grnLabelMouseClicked
        new grn(details).setVisible(true);
    }//GEN-LAST:event_grnLabelMouseClicked

    private void grnLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grnLabelMouseEntered
        grnLabel.setBackground(Color.white);
        grnLabel.setForeground(Color.black);
    }//GEN-LAST:event_grnLabelMouseEntered

    private void grnLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grnLabelMouseExited
        grnLabel.setBackground(new java.awt.Color(54, 70, 78));
        grnLabel.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_grnLabelMouseExited

    private void stockLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockLabelMouseClicked
        new stock(details).setVisible(true);
    }//GEN-LAST:event_stockLabelMouseClicked

    private void stockLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockLabelMouseEntered
        stockLabel.setBackground(Color.white);
        stockLabel.setForeground(Color.black);
    }//GEN-LAST:event_stockLabelMouseEntered

    private void stockLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockLabelMouseExited
        stockLabel.setBackground(new java.awt.Color(54, 70, 78));
        stockLabel.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_stockLabelMouseExited

    private void addItemLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addItemLabelMouseClicked
        new AddItem(details).setVisible(true);
    }//GEN-LAST:event_addItemLabelMouseClicked

    private void addItemLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addItemLabelMouseEntered
        addItemLabel.setBackground(Color.white);
        addItemLabel.setForeground(Color.black);
    }//GEN-LAST:event_addItemLabelMouseEntered

    private void addItemLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addItemLabelMouseExited
        addItemLabel.setBackground(new java.awt.Color(54, 70, 78));
        addItemLabel.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_addItemLabelMouseExited

    private void employeeManageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeManageLabelMouseClicked
        new EmployeeManagement(details).setVisible(true);
    }//GEN-LAST:event_employeeManageLabelMouseClicked

    private void employeeManageLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeManageLabelMouseEntered
        employeeManageLabel.setBackground(Color.white);
        employeeManageLabel.setForeground(Color.black);
    }//GEN-LAST:event_employeeManageLabelMouseEntered

    private void employeeManageLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeManageLabelMouseExited
        employeeManageLabel.setBackground(new java.awt.Color(54, 70, 78));
        employeeManageLabel.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_employeeManageLabelMouseExited

    private void addSystemUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSystemUserMouseClicked
        new SetupLogin(details).setVisible(true);
    }//GEN-LAST:event_addSystemUserMouseClicked

    private void addSystemUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSystemUserMouseEntered
        addSystemUser.setBackground(Color.white);
        addSystemUser.setForeground(Color.black);
    }//GEN-LAST:event_addSystemUserMouseEntered

    private void addSystemUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSystemUserMouseExited
        addSystemUser.setBackground(new java.awt.Color(54, 70, 78));
        addSystemUser.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_addSystemUserMouseExited

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        components.logout(this);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void resetUserPassewordLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetUserPassewordLabelMouseClicked
        new ResetPassword().setVisible(true);
    }//GEN-LAST:event_resetUserPassewordLabelMouseClicked

    private void resetUserPassewordLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetUserPassewordLabelMouseEntered
        resetUserPassewordLabel.setBackground(Color.white);
        resetUserPassewordLabel.setForeground(Color.black);
    }//GEN-LAST:event_resetUserPassewordLabelMouseEntered

    private void resetUserPassewordLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetUserPassewordLabelMouseExited
        resetUserPassewordLabel.setBackground(new java.awt.Color(54, 70, 78));
        resetUserPassewordLabel.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_resetUserPassewordLabelMouseExited

    private void suppliersManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suppliersManagementMouseClicked
        new SupplierManagement(details).setVisible(true);
    }//GEN-LAST:event_suppliersManagementMouseClicked

    private void suppliersManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suppliersManagementMouseEntered
        suppliersManagement.setBackground(Color.white);
        suppliersManagement.setForeground(Color.black);
    }//GEN-LAST:event_suppliersManagementMouseEntered

    private void suppliersManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suppliersManagementMouseExited
        suppliersManagement.setBackground(new java.awt.Color(54, 70, 78));
        suppliersManagement.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_suppliersManagementMouseExited

    private void empDetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empDetButtonActionPerformed
        try {
            String path = "D:\\projects\\Java\\1stYear\\FinalProjects\\1stYearProject\\src\\reports\\EmployeeDetails.jrxml";
            JasperReport compileReport = JasperCompileManager.compileReport(path);
            JasperPrint fillReport = JasperFillManager.fillReport(compileReport, null, DB.getNewConnection());
            JasperViewer.viewReport(fillReport, false);
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
    }//GEN-LAST:event_empDetButtonActionPerformed

    private void empPersDetailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empPersDetailButtonActionPerformed
        try {
            String path = "D:\\projects\\Java\\1stYear\\FinalProjects\\1stYearProject\\src\\reports\\EmployeePresonalDetails.jrxml";
            JasperReport compileReport = JasperCompileManager.compileReport(path);
            JasperPrint fillReport = JasperFillManager.fillReport(compileReport, null, DB.getNewConnection());
            JasperViewer.viewReport(fillReport, false);
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
    }//GEN-LAST:event_empPersDetailButtonActionPerformed

    private void supplierDetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierDetButtonActionPerformed
        try {
            String path = "D:\\projects\\Java\\1stYear\\FinalProjects\\1stYearProject\\src\\reports\\SupplierDetails.jrxml";
            JasperReport compileReport = JasperCompileManager.compileReport(path);
            JasperPrint fillReport = JasperFillManager.fillReport(compileReport, null, DB.getNewConnection());
            JasperViewer.viewReport(fillReport, false);
        } catch (Exception e) {
            log.errorLog(details, e.getMessage());
            components.error(this, e.getMessage());
        }
    }//GEN-LAST:event_supplierDetButtonActionPerformed

    private void POMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_POMouseClicked
        new PurchaseOrder(details).setVisible(true);
    }//GEN-LAST:event_POMouseClicked

    private void POMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_POMouseEntered
        PO.setBackground(Color.white);
        PO.setForeground(Color.black);
    }//GEN-LAST:event_POMouseEntered

    private void POMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_POMouseExited
        PO.setBackground(new java.awt.Color(54, 70, 78));
        PO.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_POMouseExited

    private void customerManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerManagementMouseClicked
        new CustomerManagement(details).setVisible(true);
    }//GEN-LAST:event_customerManagementMouseClicked

    private void customerManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerManagementMouseEntered
        customerManagement.setBackground(Color.white);
        customerManagement.setForeground(Color.black);
    }//GEN-LAST:event_customerManagementMouseEntered

    private void customerManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerManagementMouseExited
        customerManagement.setBackground(new java.awt.Color(54, 70, 78));
        customerManagement.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_customerManagementMouseExited

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
            java.util.logging.Logger.getLogger(ManagerHome.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerHome.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerHome.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerHome.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagerHome().setVisible(true);
            }
        });
    }

    String sql;
    ResultSet rs;
    SystemComponents components;
    UserDetails details;
    StatLogging log;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PO;
    private javax.swing.JLabel addItemLabel;
    private javax.swing.JLabel addSystemUser;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JLabel customerManagement;
    private javax.swing.JLabel datetimeLabel;
    private javax.swing.JButton empDetButton;
    private javax.swing.JButton empPersDetailButton;
    private javax.swing.JLabel employeeManageLabel;
    private javax.swing.JLabel grnLabel;
    private javax.swing.JLabel invoiceLabel;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JLabel resetUserPassewordLabel;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JLabel stockLabel;
    private javax.swing.JButton supplierDetButton;
    private javax.swing.JLabel suppliersManagement;
    private javax.swing.JLabel tableTitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
