/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;



import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import gui.DashboardDAOBranchAdmin;
import gui.ProgressDataBranchAdmin;
import gui.ProgressRendererBranchAdmin;
import gui.StatusRendererBranchAdmin;

public class DashboardPanelBranchAdmin extends javax.swing.JPanel {
    
    private DashboardDAOBranchAdmin dataAccess;
    
    public DashboardPanelBranchAdmin() {
        initComponents();
        
        // Initialize data access
        dataAccess = new DashboardDAOBranchAdmin();
        
        // Apply custom renderers
        setupTableRenderers();
        
        // Load initial data
        loadData();
        
        // Set up automatic refresh (every 5 minutes)
        Timer timer = new Timer(300000, e -> loadData());
        timer.start();
    }
    
    private void setupTableRenderers() {
        // Get the JTable from the JScrollPane
        JTable progressTable = (JTable) ProgressScrollPane.getViewport().getView();
        
        if (progressTable != null && progressTable.getColumnModel().getColumnCount() > 0) {
            // Assuming your first column is Status
            progressTable.getColumnModel().getColumn(0).setCellRenderer(new StatusRendererBranchAdmin());
            
            // If you have a progress column (e.g., column 3), set its renderer
            if (progressTable.getColumnModel().getColumnCount() > 3) {
                progressTable.getColumnModel().getColumn(3).setCellRenderer(new ProgressRendererBranchAdmin());
            }
        }
    }
    
    private void loadData() {
        // Use SwingWorker to avoid freezing the UI
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private int activeCustomers;
            private int failedOrders;
            private int completedOrders;
            private double todaysRevenue;
            private int pendingOrders;
            private int activeEmployees;
            private List<ProgressDataBranchAdmin> progressData;
            
            @Override
            protected Void doInBackground() {
                try {
                    // Fetch data from database using DAO methods
                    // These methods now use MySQL2.executeSearch internally
                    activeCustomers = dataAccess.getActiveCustomersCount();
                    failedOrders = dataAccess.getFailedOrdersCount();
                    completedOrders = dataAccess.getCompletedOrdersCount();
                    todaysRevenue = dataAccess.getTodaysRevenue();
                    pendingOrders = dataAccess.getPendingOrdersCount();
                    activeEmployees = dataAccess.getActiveEmployeesCount();
                    
                    progressData = dataAccess.getProgressData();
                } catch (Exception e) {
                    System.out.println("Error loading dashboard data: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    // Update UI components with the fetched data
                    ActiveCustomersTotalNoLabel.setText(String.valueOf(activeCustomers));
                    FailedOrdersTotalNoLabel.setText(String.valueOf(failedOrders));
                    TotalOrdersTotalNoLabel.setText(String.valueOf(completedOrders));
                    TodaysRevenueTotalNoLabel.setText(String.format("$%.2f", todaysRevenue));
                    PendingOrdersTotalNoLabel.setText(String.valueOf(pendingOrders));
                    ActiveEmployeesTotalNoLabel.setText(String.valueOf(activeEmployees));
                    
                    updateProgressTable(progressData);
                } catch (Exception e) {
                    System.out.println("Error updating UI with dashboard data: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
    
    private void updateProgressTable(List<ProgressDataBranchAdmin> progressData) {
        // Get the JTable from the JScrollPane
        JTable progressTable = (JTable) ProgressScrollPane.getViewport().getView();
        
        if (progressTable != null) {
            DefaultTableModel model = (DefaultTableModel) progressTable.getModel();
            model.setRowCount(0); // Clear existing rows
            
            if (progressData != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                
                for (ProgressDataBranchAdmin data : progressData) {
                    model.addRow(new Object[]{
                        data.getStatus(),
                        data.getName(),
                        dateFormat.format(data.getStartDate()),
                        data.getProgress()
                    });
                }
            }
        }
    }
    
    
    // This method should be called if you want to manually refresh the data
    public void refreshData() {
        loadData();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        ActiveCustomersPanel = new javax.swing.JPanel();
        ActiveCustomersLabel = new javax.swing.JLabel();
        ActiveCustomersTotalNoLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        CompleteOrdersPanel = new javax.swing.JPanel();
        CompleteOrdersLabel = new javax.swing.JLabel();
        TotalOrdersTotalNoLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        FailedOrdersPanel = new javax.swing.JPanel();
        FailedOrdersLabel = new javax.swing.JLabel();
        FailedOrdersTotalNoLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        TodaysRevenuePanel = new javax.swing.JPanel();
        TodaysRevenueLabel = new javax.swing.JLabel();
        TodaysRevenueTotalNoLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        PendingOrdersPanel = new javax.swing.JPanel();
        PendingOrdersLabel = new javax.swing.JLabel();
        PendingOrdersTotalNoLabel = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        ActiveeEmployeesPanel = new javax.swing.JPanel();
        ActiveEmployeesLabel = new javax.swing.JLabel();
        ActiveEmployeesTotalNoLabel = new javax.swing.JLabel();
        ProgressPanel = new javax.swing.JPanel();
        ProgressLabel = new javax.swing.JLabel();
        ProgressScrollPane = new javax.swing.JScrollPane();
        progressTable = new javax.swing.JTable();

        setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(2, 3));

        ActiveCustomersPanel.setBackground(new java.awt.Color(255, 255, 255));
        ActiveCustomersPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ActiveCustomersLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ActiveCustomersLabel.setText("Active Customers");

        ActiveCustomersTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        ActiveCustomersTotalNoLabel.setText("100");

        javax.swing.GroupLayout ActiveCustomersPanelLayout = new javax.swing.GroupLayout(ActiveCustomersPanel);
        ActiveCustomersPanel.setLayout(ActiveCustomersPanelLayout);
        ActiveCustomersPanelLayout.setHorizontalGroup(
            ActiveCustomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActiveCustomersPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(ActiveCustomersLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ActiveCustomersPanelLayout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addComponent(ActiveCustomersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        ActiveCustomersPanelLayout.setVerticalGroup(
            ActiveCustomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActiveCustomersPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(ActiveCustomersLabel)
                .addGap(29, 29, 29)
                .addComponent(ActiveCustomersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ActiveCustomersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ActiveCustomersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel6);

        CompleteOrdersPanel.setBackground(new java.awt.Color(255, 255, 255));
        CompleteOrdersPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        CompleteOrdersLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CompleteOrdersLabel.setText("Complete Orders");

        TotalOrdersTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        TotalOrdersTotalNoLabel.setText("100");

        javax.swing.GroupLayout CompleteOrdersPanelLayout = new javax.swing.GroupLayout(CompleteOrdersPanel);
        CompleteOrdersPanel.setLayout(CompleteOrdersPanelLayout);
        CompleteOrdersPanelLayout.setHorizontalGroup(
            CompleteOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompleteOrdersPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(CompleteOrdersLabel)
                .addContainerGap(231, Short.MAX_VALUE))
            .addGroup(CompleteOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CompleteOrdersPanelLayout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addComponent(TotalOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(89, Short.MAX_VALUE)))
        );
        CompleteOrdersPanelLayout.setVerticalGroup(
            CompleteOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompleteOrdersPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(CompleteOrdersLabel)
                .addContainerGap(208, Short.MAX_VALUE))
            .addGroup(CompleteOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CompleteOrdersPanelLayout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(TotalOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CompleteOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CompleteOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel4);

        FailedOrdersPanel.setBackground(new java.awt.Color(255, 255, 255));
        FailedOrdersPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        FailedOrdersLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FailedOrdersLabel.setText("Failed Orders");

        FailedOrdersTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        FailedOrdersTotalNoLabel.setText("100");

        javax.swing.GroupLayout FailedOrdersPanelLayout = new javax.swing.GroupLayout(FailedOrdersPanel);
        FailedOrdersPanel.setLayout(FailedOrdersPanelLayout);
        FailedOrdersPanelLayout.setHorizontalGroup(
            FailedOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FailedOrdersPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(FailedOrdersLabel)
                .addContainerGap(255, Short.MAX_VALUE))
            .addGroup(FailedOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(FailedOrdersPanelLayout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addComponent(FailedOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(89, Short.MAX_VALUE)))
        );
        FailedOrdersPanelLayout.setVerticalGroup(
            FailedOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FailedOrdersPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(FailedOrdersLabel)
                .addContainerGap(208, Short.MAX_VALUE))
            .addGroup(FailedOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(FailedOrdersPanelLayout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(FailedOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FailedOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FailedOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3);

        TodaysRevenuePanel.setBackground(new java.awt.Color(255, 255, 255));
        TodaysRevenuePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TodaysRevenueLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TodaysRevenueLabel.setText("Today's Revenue");

        TodaysRevenueTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        TodaysRevenueTotalNoLabel.setText("100");

        javax.swing.GroupLayout TodaysRevenuePanelLayout = new javax.swing.GroupLayout(TodaysRevenuePanel);
        TodaysRevenuePanel.setLayout(TodaysRevenuePanelLayout);
        TodaysRevenuePanelLayout.setHorizontalGroup(
            TodaysRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TodaysRevenuePanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(TodaysRevenueLabel)
                .addContainerGap(229, Short.MAX_VALUE))
            .addGroup(TodaysRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TodaysRevenuePanelLayout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addComponent(TodaysRevenueTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(89, Short.MAX_VALUE)))
        );
        TodaysRevenuePanelLayout.setVerticalGroup(
            TodaysRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TodaysRevenuePanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(TodaysRevenueLabel)
                .addContainerGap(208, Short.MAX_VALUE))
            .addGroup(TodaysRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TodaysRevenuePanelLayout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(TodaysRevenueTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TodaysRevenuePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TodaysRevenuePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2);

        PendingOrdersPanel.setBackground(new java.awt.Color(255, 255, 255));
        PendingOrdersPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PendingOrdersLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PendingOrdersLabel.setText("Pending Orders");

        PendingOrdersTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        PendingOrdersTotalNoLabel.setText("100");

        javax.swing.GroupLayout PendingOrdersPanelLayout = new javax.swing.GroupLayout(PendingOrdersPanel);
        PendingOrdersPanel.setLayout(PendingOrdersPanelLayout);
        PendingOrdersPanelLayout.setHorizontalGroup(
            PendingOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PendingOrdersPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(PendingOrdersLabel)
                .addContainerGap(239, Short.MAX_VALUE))
            .addGroup(PendingOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PendingOrdersPanelLayout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addComponent(PendingOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(89, Short.MAX_VALUE)))
        );
        PendingOrdersPanelLayout.setVerticalGroup(
            PendingOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PendingOrdersPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(PendingOrdersLabel)
                .addContainerGap(208, Short.MAX_VALUE))
            .addGroup(PendingOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PendingOrdersPanelLayout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(PendingOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PendingOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PendingOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel5);

        ActiveeEmployeesPanel.setBackground(new java.awt.Color(255, 255, 255));
        ActiveeEmployeesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ActiveEmployeesLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ActiveEmployeesLabel.setText("Active Employees");

        ActiveEmployeesTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        ActiveEmployeesTotalNoLabel.setText("100");

        javax.swing.GroupLayout ActiveeEmployeesPanelLayout = new javax.swing.GroupLayout(ActiveeEmployeesPanel);
        ActiveeEmployeesPanel.setLayout(ActiveeEmployeesPanelLayout);
        ActiveeEmployeesPanelLayout.setHorizontalGroup(
            ActiveeEmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActiveeEmployeesPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(ActiveEmployeesLabel)
                .addContainerGap(225, Short.MAX_VALUE))
            .addGroup(ActiveeEmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ActiveeEmployeesPanelLayout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addComponent(ActiveEmployeesTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(89, Short.MAX_VALUE)))
        );
        ActiveeEmployeesPanelLayout.setVerticalGroup(
            ActiveeEmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActiveeEmployeesPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(ActiveEmployeesLabel)
                .addContainerGap(208, Short.MAX_VALUE))
            .addGroup(ActiveeEmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ActiveeEmployeesPanelLayout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(ActiveEmployeesTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ActiveeEmployeesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ActiveeEmployeesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel11);

        add(jPanel1);

        ProgressPanel.setBackground(new java.awt.Color(204, 204, 204));
        ProgressPanel.setLayout(new java.awt.BorderLayout());

        ProgressLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ProgressLabel.setText("Progress");
        ProgressPanel.add(ProgressLabel, java.awt.BorderLayout.PAGE_START);

        progressTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Status", "Name", "Start Date", "Progress"
            }
        ));
        ProgressScrollPane.setViewportView(progressTable);

        ProgressPanel.add(ProgressScrollPane, java.awt.BorderLayout.CENTER);

        add(ProgressPanel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ActiveCustomersLabel;
    private javax.swing.JPanel ActiveCustomersPanel;
    private javax.swing.JLabel ActiveCustomersTotalNoLabel;
    private javax.swing.JLabel ActiveEmployeesLabel;
    private javax.swing.JLabel ActiveEmployeesTotalNoLabel;
    private javax.swing.JPanel ActiveeEmployeesPanel;
    private javax.swing.JLabel CompleteOrdersLabel;
    private javax.swing.JPanel CompleteOrdersPanel;
    private javax.swing.JLabel FailedOrdersLabel;
    private javax.swing.JPanel FailedOrdersPanel;
    private javax.swing.JLabel FailedOrdersTotalNoLabel;
    private javax.swing.JLabel PendingOrdersLabel;
    private javax.swing.JPanel PendingOrdersPanel;
    private javax.swing.JLabel PendingOrdersTotalNoLabel;
    private javax.swing.JLabel ProgressLabel;
    private javax.swing.JPanel ProgressPanel;
    private javax.swing.JScrollPane ProgressScrollPane;
    private javax.swing.JLabel TodaysRevenueLabel;
    private javax.swing.JPanel TodaysRevenuePanel;
    private javax.swing.JLabel TodaysRevenueTotalNoLabel;
    private javax.swing.JLabel TotalOrdersTotalNoLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTable progressTable;
    // End of variables declaration//GEN-END:variables
}
