/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author dhnrn
 */
public class AdminReports extends javax.swing.JPanel {
   
   public AdminReports() {
       initComponents();
       setupButtonLabels();
   }
   
   private void setupButtonLabels() {
       jButton1.setText("Employee Report");
       jButton2.setText("Product Report");
       jButton3.setText("Customer Report");
       jButton4.setText("Orders Report");
       jButton5.setText("Salary Report");
       jButton6.setText("Stock Report");
   }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridLayout(0, 3, 24, 24));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setText("jButton1");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        jButton3.setText("jButton1");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jButton4.setText("jButton1");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);

        jButton5.setText("jButton1");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);

        jButton6.setText("jButton1");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6);

        add(jPanel1, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       try {
           String dbUrl = "jdbc:mysql://localhost:3306/1234";
           String dbUser = "root";
           String dbPassword = "Srj143@vimani";
           
           Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
           
           String jrxmlPath = "src/reports/EmployeeReport.jrxml";
           JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
           
           HashMap<String, Object> parameters = new HashMap<>();
           JasperPrint jasperPrint = JasperFillManager.fillReport(
               jasperReport, 
               parameters, 
               connection
           );
           
          String pdfPath = "employee_report.pdf";
           net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(
               jasperPrint, 
               pdfPath
           );
           
           java.awt.Desktop.getDesktop().open(new java.io.File(pdfPath));
           
           connection.close();
           
           JOptionPane.showMessageDialog(
               this, 
               "Employee Report generated successfully at: " + pdfPath, 
               "Success", 
               JOptionPane.INFORMATION_MESSAGE
           );
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(
               this, 
               "Error generating Employee Report: " + e.getMessage(), 
               "Report Error", 
               JOptionPane.ERROR_MESSAGE
           );
           e.printStackTrace();
       }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
           String dbUrl = "jdbc:mysql://localhost:3306/1234";
           String dbUser = "root";
           String dbPassword = "Srj143@vimani";
           
           Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
           
           String jrxmlPath = "src/reports/ProductReport.jrxml";
           JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
           
           HashMap<String, Object> parameters = new HashMap<>();
           JasperPrint jasperPrint = JasperFillManager.fillReport(
               jasperReport, 
               parameters, 
               connection
           );
           
           String pdfPath = "product_report.pdf";
           net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(
               jasperPrint, 
               pdfPath
           );
           
           java.awt.Desktop.getDesktop().open(new java.io.File(pdfPath));
           
           connection.close();
           
           JOptionPane.showMessageDialog(
               this, 
               "Product Report generated successfully at: " + pdfPath, 
               "Success", 
               JOptionPane.INFORMATION_MESSAGE
           );
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(
               this, 
               "Error generating Product Report: " + e.getMessage(), 
               "Report Error", 
               JOptionPane.ERROR_MESSAGE
           );
           e.printStackTrace();
       }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {
           String dbUrl = "jdbc:mysql://localhost:3306/1234";
           String dbUser = "root";
           String dbPassword = "Srj143@vimani";
           
           Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
           
           String jrxmlPath = "src/reports/CustomerReport.jrxml";
           JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
           
           HashMap<String, Object> parameters = new HashMap<>();
           JasperPrint jasperPrint = JasperFillManager.fillReport(
               jasperReport, 
               parameters, 
               connection
           );
           
           String pdfPath = "customer_report.pdf";
           net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(
               jasperPrint, 
               pdfPath
           );
           
           java.awt.Desktop.getDesktop().open(new java.io.File(pdfPath));
           
           connection.close();
           
           JOptionPane.showMessageDialog(
               this, 
               "Customer Report generated successfully at: " + pdfPath, 
               "Success", 
               JOptionPane.INFORMATION_MESSAGE
           );
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(
               this, 
               "Error generating Customer Report: " + e.getMessage(), 
               "Report Error", 
               JOptionPane.ERROR_MESSAGE
           );
           e.printStackTrace();
       }       // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        try {
           String dbUrl = "jdbc:mysql://localhost:3306/1234";
           String dbUser = "root";
           String dbPassword = "Srj143@vimani";
           
           Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
           
           String jrxmlPath = "src/reports/OrdersReport.jrxml";
           JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
           
           HashMap<String, Object> parameters = new HashMap<>();
           JasperPrint jasperPrint = JasperFillManager.fillReport(
               jasperReport, 
               parameters, 
               connection
           );
           
           String pdfPath = "orders_report.pdf";
           net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(
               jasperPrint, 
               pdfPath
           );
           
           java.awt.Desktop.getDesktop().open(new java.io.File(pdfPath));
           
           connection.close();
           
           JOptionPane.showMessageDialog(
               this, 
               "Orders Report generated successfully at: " + pdfPath, 
               "Success", 
               JOptionPane.INFORMATION_MESSAGE
           );
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(
               this, 
               "Error generating Orders Report: " + e.getMessage(), 
               "Report Error", 
               JOptionPane.ERROR_MESSAGE
           );
           e.printStackTrace();
       }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        try {
           String dbUrl = "jdbc:mysql://localhost:3306/1234";
           String dbUser = "root";
           String dbPassword = "Srj143@vimani";
           
           Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
           
           String jrxmlPath = "src/reports/SalaryReport.jrxml";
           JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
           
           HashMap<String, Object> parameters = new HashMap<>();
           JasperPrint jasperPrint = JasperFillManager.fillReport(
               jasperReport, 
               parameters, 
               connection
           );
           
           String pdfPath = "salary_report.pdf";
           net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(
               jasperPrint, 
               pdfPath
           );
           
           java.awt.Desktop.getDesktop().open(new java.io.File(pdfPath));
           
           connection.close();
           
           JOptionPane.showMessageDialog(
               this, 
               "Salary Report generated successfully at: " + pdfPath, 
               "Success", 
               JOptionPane.INFORMATION_MESSAGE
           );
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(
               this, 
               "Error generating Salary Report: " + e.getMessage(), 
               "Report Error", 
               JOptionPane.ERROR_MESSAGE
           );
           e.printStackTrace();
       }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        try {
           String dbUrl = "jdbc:mysql://localhost:3306/1234";
           String dbUser = "root";
           String dbPassword = "Srj143@vimani";
           
           Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
           
           String jrxmlPath = "src/reports/StockReport.jrxml";
           JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
           
           HashMap<String, Object> parameters = new HashMap<>();
           JasperPrint jasperPrint = JasperFillManager.fillReport(
               jasperReport, 
               parameters, 
               connection
           );
           
           String pdfPath = "stock_report.pdf";
           net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(
               jasperPrint, 
               pdfPath
           );
           
           java.awt.Desktop.getDesktop().open(new java.io.File(pdfPath));
           
           connection.close();
           
           JOptionPane.showMessageDialog(
               this, 
               "Stock Report generated successfully at: " + pdfPath, 
               "Success", 
               JOptionPane.INFORMATION_MESSAGE
           );
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(
               this, 
               "Error generating Stock Report: " + e.getMessage(), 
               "Report Error", 
               JOptionPane.ERROR_MESSAGE
           );
           e.printStackTrace();
       }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
