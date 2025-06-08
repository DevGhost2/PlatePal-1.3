/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.MySQL2;

/**
 *
 * @author hasit
 */
public class DashboardPanelBranchAdmin extends javax.swing.JPanel {

    private static DashboardPanelBranchAdmin dpba;
    public DashboardPanelBranchAdmin() {
        initComponents();
        loadDashboardData();
    }
    
    public static synchronized DashboardPanelBranchAdmin getInstance(){
        if(dpba==null){
            dpba=new DashboardPanelBranchAdmin();
        
        }
        return dpba;
    }

    private void loadDashboardData() {
    try {
        // Load Active Customers Count
        ResultSet activeCustomersRS = MySQL2.executeSearch("SELECT COUNT(*) as count FROM `customer`");
        if (activeCustomersRS.next()) {
            ActiveCustomersTotalNoLabel.setText(activeCustomersRS.getString("count"));
        } else {
            ActiveCustomersTotalNoLabel.setText("0");
        }
        
        // Load Complete Orders Count
        ResultSet completeOrdersRS = MySQL2.executeSearch(
            "SELECT COUNT(*) as count FROM `orders` " +
            "INNER JOIN `order_status` ON `orders`.`order_status_id` = `order_status`.`id` " +
            "WHERE `order_status`.`type` = 'Complete'");
        if (completeOrdersRS.next()) {
            TotalOrdersTotalNoLabel.setText(completeOrdersRS.getString("count"));
        } else {
            TotalOrdersTotalNoLabel.setText("0");
        }
        
        // Load Failed Orders Count
        ResultSet failedOrdersRS = MySQL2.executeSearch(
            "SELECT COUNT(*) as count FROM `orders` " +
            "INNER JOIN `order_status` ON `orders`.`order_status_id` = `order_status`.`id` " +
            "WHERE `order_status`.`type` = 'Failed'");
        if (failedOrdersRS.next()) {
            FailedOrdersTotalNoLabel.setText(failedOrdersRS.getString("count"));
        } else {
            FailedOrdersTotalNoLabel.setText("0");
        }
        
        // Load Today's Revenue
        LocalDate today = LocalDate.now();
        ResultSet todayRevenueRS = MySQL2.executeSearch(
            "SELECT SUM(payment) as total FROM `invoice` " +
            "WHERE DATE(date) = '" + today + "'");
        if (todayRevenueRS.next() && todayRevenueRS.getString("total") != null) {
            TodaysRevenueTotalNoLabel.setText(todayRevenueRS.getString("total"));
        } else {
            TodaysRevenueTotalNoLabel.setText("0");
        }
        
        // Load Pending Orders Count
        ResultSet pendingOrdersRS = MySQL2.executeSearch(
            "SELECT COUNT(*) as count FROM `orders` " +
            "INNER JOIN `order_status` ON `orders`.`order_status_id` = `order_status`.`id` " +
            "WHERE `order_status`.`type` = 'Pending'");
        if (pendingOrdersRS.next()) {
            PendingOrdersTotalNoLabel.setText(pendingOrdersRS.getString("count"));
        } else {
            PendingOrdersTotalNoLabel.setText("0");
        }
        
        // Load Active Employees Count
        ResultSet activeEmployeesRS = MySQL2.executeSearch("SELECT COUNT(*) as count FROM `employee`");
        if (activeEmployeesRS.next()) {
            ActiveEmployeesTotalNoLabel.setText(activeEmployeesRS.getString("count"));
        } else {
            ActiveEmployeesTotalNoLabel.setText("0");
        }
        
        // Load Progress Table
        loadProgressTable();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void loadProgressTable() {
    try {
        ResultSet progressRS = MySQL2.executeSearch(
            "SELECT o.order_id as OrderID, os.type as Status, " +
            "DATE_FORMAT(o.order_on, '%Y-%m-%d') as OrderDate, " +
            "CASE " +
            "  WHEN os.type = 'Pending' THEN 'In Progress' " +
            "  WHEN os.type = 'Processing' THEN '50%' " +
            "  WHEN os.type = 'Complete' THEN '100%' " +
            "  WHEN os.type = 'Failed' THEN 'Failed' " +
            "  ELSE 'Unknown' " +
            "END as Progress " +
            "FROM `orders` o " +
            "INNER JOIN `order_status` os ON o.order_status_id = os.id " +
            "ORDER BY o.order_on DESC LIMIT 10");
        
        DefaultTableModel model = (DefaultTableModel) progressTable.getModel();
        model.setRowCount(0);
        
        // Set column names if needed
        if (model.getColumnCount() == 0) {
            model.addColumn("Status");
            model.addColumn("Name");
            model.addColumn("Start Date");
            model.addColumn("Progress");
        }
        
        while (progressRS.next()) {
            Vector<String> row = new Vector<>();
            row.add(progressRS.getString("Status"));
            row.add(progressRS.getString("OrderID"));
            row.add(progressRS.getString("OrderDate"));
            row.add(progressRS.getString("Progress"));
            model.addRow(row);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    // This method should be called when you want to refresh the dashboard data
    public void refreshDashboard() {
        loadDashboardData();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // Your generated code here - DO NOT MODIFY
    // </editor-fold>
    

    // Variables declaration - do not modify                     
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        ActiveCustomersPanel = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        ActiveCustomersLabel = new javax.swing.JLabel();
        ActiveCustomersTotalNoLabel = new javax.swing.JLabel();
        jPanel4 = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        CompleteOrdersPanel = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        TotalOrdersTotalNoLabel = new javax.swing.JLabel();
        CompleteOrdersLabel = new javax.swing.JLabel();
        jPanel3 = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        FailedOrdersPanel = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        FailedOrdersTotalNoLabel = new javax.swing.JLabel();
        FailedOrdersLabel = new javax.swing.JLabel();
        jPanel2 = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        TodaysRevenuePanel = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        TodaysRevenueTotalNoLabel = new javax.swing.JLabel();
        TodaysRevenueLabel = new javax.swing.JLabel();
        jPanel5 = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        PendingOrdersPanel = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        PendingOrdersTotalNoLabel = new javax.swing.JLabel();
        PendingOrdersLabel = new javax.swing.JLabel();
        jPanel11 = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        ActiveeEmployeesPanel = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        ActiveEmployeesTotalNoLabel = new javax.swing.JLabel();
        ActiveEmployeesLabel = new javax.swing.JLabel();
        ProgressPanel = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        ProgressLabel = new javax.swing.JLabel();
        ProgressScrollPane = new javax.swing.JScrollPane();
        progressTable = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(500, 300));
        setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 200));
        jPanel1.setMinimumSize(new java.awt.Dimension(300, 200));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 200));
        jPanel1.setLayout(new java.awt.GridLayout(2, 3, 10, 10));

        jPanel6.setLayout(new java.awt.BorderLayout());

        ActiveCustomersPanel.setLayout(new java.awt.BorderLayout());

        ActiveCustomersLabel.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        ActiveCustomersLabel.setForeground(new java.awt.Color(153, 0, 0));
        ActiveCustomersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ActiveCustomersLabel.setText("Active Customers");
        ActiveCustomersPanel.add(ActiveCustomersLabel, java.awt.BorderLayout.PAGE_START);

        ActiveCustomersTotalNoLabel.setFont(new java.awt.Font("Poppins", 1, 55)); // NOI18N
        ActiveCustomersTotalNoLabel.setForeground(new java.awt.Color(0, 0, 1));
        ActiveCustomersTotalNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ActiveCustomersTotalNoLabel.setText("100");
        ActiveCustomersPanel.add(ActiveCustomersTotalNoLabel, java.awt.BorderLayout.CENTER);

        jPanel6.add(ActiveCustomersPanel, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel6);

        jPanel4.setLayout(new java.awt.BorderLayout());

        CompleteOrdersPanel.setLayout(new java.awt.BorderLayout());

        TotalOrdersTotalNoLabel.setFont(new java.awt.Font("Poppins", 1, 55)); // NOI18N
        TotalOrdersTotalNoLabel.setForeground(new java.awt.Color(0, 0, 1));
        TotalOrdersTotalNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TotalOrdersTotalNoLabel.setText("100");
        CompleteOrdersPanel.add(TotalOrdersTotalNoLabel, java.awt.BorderLayout.CENTER);

        CompleteOrdersLabel.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        CompleteOrdersLabel.setForeground(new java.awt.Color(153, 0, 0));
        CompleteOrdersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CompleteOrdersLabel.setText("Complete Orders");
        CompleteOrdersPanel.add(CompleteOrdersLabel, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(CompleteOrdersPanel, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4);

        jPanel3.setLayout(new java.awt.BorderLayout());

        FailedOrdersPanel.setLayout(new java.awt.BorderLayout());

        FailedOrdersTotalNoLabel.setFont(new java.awt.Font("Poppins", 1, 55)); // NOI18N
        FailedOrdersTotalNoLabel.setForeground(new java.awt.Color(0, 0, 1));
        FailedOrdersTotalNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FailedOrdersTotalNoLabel.setText("100");
        FailedOrdersPanel.add(FailedOrdersTotalNoLabel, java.awt.BorderLayout.CENTER);

        FailedOrdersLabel.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        FailedOrdersLabel.setForeground(new java.awt.Color(153, 0, 0));
        FailedOrdersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FailedOrdersLabel.setText("Failed Orders");
        FailedOrdersPanel.add(FailedOrdersLabel, java.awt.BorderLayout.PAGE_START);

        jPanel3.add(FailedOrdersPanel, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3);

        jPanel2.setLayout(new java.awt.BorderLayout());

        TodaysRevenuePanel.setLayout(new java.awt.BorderLayout());

        TodaysRevenueTotalNoLabel.setFont(new java.awt.Font("Poppins", 1, 55)); // NOI18N
        TodaysRevenueTotalNoLabel.setForeground(new java.awt.Color(0, 0, 1));
        TodaysRevenueTotalNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TodaysRevenueTotalNoLabel.setText("100");
        TodaysRevenuePanel.add(TodaysRevenueTotalNoLabel, java.awt.BorderLayout.CENTER);

        TodaysRevenueLabel.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        TodaysRevenueLabel.setForeground(new java.awt.Color(153, 0, 0));
        TodaysRevenueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TodaysRevenueLabel.setText("Today's Revenue");
        TodaysRevenuePanel.add(TodaysRevenueLabel, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(TodaysRevenuePanel, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        jPanel5.setLayout(new java.awt.BorderLayout());

        PendingOrdersPanel.setLayout(new java.awt.BorderLayout());

        PendingOrdersTotalNoLabel.setFont(new java.awt.Font("Poppins", 1, 55)); // NOI18N
        PendingOrdersTotalNoLabel.setForeground(new java.awt.Color(0, 0, 1));
        PendingOrdersTotalNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PendingOrdersTotalNoLabel.setText("100");
        PendingOrdersPanel.add(PendingOrdersTotalNoLabel, java.awt.BorderLayout.CENTER);

        PendingOrdersLabel.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        PendingOrdersLabel.setForeground(new java.awt.Color(153, 0, 0));
        PendingOrdersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PendingOrdersLabel.setText("Pending Orders");
        PendingOrdersPanel.add(PendingOrdersLabel, java.awt.BorderLayout.PAGE_START);

        jPanel5.add(PendingOrdersPanel, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5);

        jPanel11.setLayout(new java.awt.BorderLayout());

        ActiveeEmployeesPanel.setLayout(new java.awt.BorderLayout());

        ActiveEmployeesTotalNoLabel.setFont(new java.awt.Font("Poppins", 1, 55)); // NOI18N
        ActiveEmployeesTotalNoLabel.setForeground(new java.awt.Color(0, 0, 1));
        ActiveEmployeesTotalNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ActiveEmployeesTotalNoLabel.setText("100");
        ActiveeEmployeesPanel.add(ActiveEmployeesTotalNoLabel, java.awt.BorderLayout.CENTER);

        ActiveEmployeesLabel.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        ActiveEmployeesLabel.setForeground(new java.awt.Color(153, 0, 0));
        ActiveEmployeesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ActiveEmployeesLabel.setText("Active Employees");
        ActiveeEmployeesPanel.add(ActiveEmployeesLabel, java.awt.BorderLayout.PAGE_START);

        jPanel11.add(ActiveeEmployeesPanel, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel11);

        jPanel9.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        ProgressPanel.setPreferredSize(new java.awt.Dimension(250, 228));
        ProgressPanel.setLayout(new java.awt.BorderLayout());

        ProgressLabel.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        ProgressLabel.setForeground(new java.awt.Color(0, 0, 1));
        ProgressLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProgressLabel.setText("Progress");
        ProgressPanel.add(ProgressLabel, java.awt.BorderLayout.PAGE_START);

        ProgressScrollPane.setBorder(null);
        ProgressScrollPane.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

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
        progressTable.setShowGrid(false);
        progressTable.setIntercellSpacing(new Dimension(0, 0));

        progressTable.setBackground(new Color(230, 230, 230));
        progressTable.setForeground(Color.BLACK);
        progressTable.setFont(new Font("Poppins", Font.PLAIN, 14));
        progressTable.setRowHeight(25);

        JTableHeader header = progressTable.getTableHeader();
        header.setBackground(new Color(230, 230, 230));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Poppins", Font.PLAIN, 14));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setOpaque(true);

        ProgressPanel.add(ProgressScrollPane, java.awt.BorderLayout.CENTER);

        jPanel7.add(ProgressPanel, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel7, java.awt.BorderLayout.CENTER);

        add(jPanel8, java.awt.BorderLayout.CENTER);
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTable progressTable;
    // End of variables declaration//GEN-END:variables
}
