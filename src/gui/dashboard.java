/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import model.MySQL2;
import java.text.DecimalFormat;

/**
 *
 * @author hasit
 */
public class dashboard extends javax.swing.JPanel {

    /**
     * Creates new form dashbord
     */
    public dashboard() {
        outgoingValue();
        initComponents();
        loadTable1();
        loadTable2();
    }

    public static double income = 10000000;
    public static double outgoing = 0;

    private void outgoingValue() {
        try {
            BigDecimal total = BigDecimal.ZERO;

            ResultSet resultset = MySQL2.executeSearch(
                    "SELECT "
                    + "s.paid_amount AS value "
                    + "FROM salary_payments s "
                    + "UNION ALL "
                    + "SELECT "
                    + "p.amount AS value "
                    + "FROM petty_cash p "
                    + "INNER JOIN employee e ON e.id = p.employee_id "
            );

            while (resultset.next()) {
                String valStr = resultset.getString("value");
                BigDecimal val = new BigDecimal(valStr);
                total = total.add(val);
            }

            outgoing = total.doubleValue();
            
            BigDecimal total2 = BigDecimal.ZERO;

            ResultSet resultset2 = MySQL2.executeSearch(
                    "SELECT + i.payment AS value FROM invoice i"
            );

            while (resultset2.next()) {
                String valStr = resultset2.getString("value");
                BigDecimal val = new BigDecimal(valStr);
                total2 = total2.add(val);
            }

            income = total2.doubleValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTable1() {
        try {
            ResultSet resultset = MySQL2.executeSearch(
                    "SELECT "
                    + "CONCAT('INV', LPAD(CAST(i.id AS CHAR), 5, '0')) AS id, "
                    + "CONCAT('Counter - ', e.first_name) AS employee_name, "
                    + "i.date AS date, "
                    + "i.payment AS value "
                    + "FROM invoice i "
                    + "INNER JOIN employee e ON e.id = i.employee_id "
                    + "ORDER BY date DESC"
            );

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("id"));
                vector.add(resultset.getString("employee_name"));
                vector.add(resultset.getString("date"));
                vector.add("Rs. " + resultset.getString("value"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTable2() {
        try {
            ResultSet resultset = MySQL2.executeSearch(
                    "SELECT "
                    + "CONCAT('SV', LPAD(CAST(s.id AS CHAR), 5, '0')) AS id, "
                    + "CONCAT('Salary - ', e.first_name, ' ', e.last_name) AS employee_name, "
                    + "s.paid_date AS date, "
                    + "s.paid_amount AS value "
                    + "FROM salary_payments s "
                    + "INNER JOIN employee e ON e.id = s.employee_id "
                    + "UNION ALL "
                    + "SELECT "
                    + "CONCAT('PC', LPAD(CAST(p.id AS CHAR), 5, '0')) AS id, "
                    + "CONCAT('Petty Cash - ', e.first_name, ' ', e.last_name) AS employee_name, "
                    + "p.date, "
                    + "p.amount "
                    + "FROM petty_cash p "
                    + "INNER JOIN employee e ON e.id = p.employee_id "
                    + "ORDER BY date DESC"
            );

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("id"));
                vector.add(resultset.getString("employee_name"));
                vector.add(resultset.getString("date"));
                vector.add("Rs. " + resultset.getString("value"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new RoundedPanel(15, new Color(255, 255, 255), new Color(0,0,0,0));
        jPanel5 = new javax.swing.JPanel();
        IncomeIcon = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new RoundedPanel(15, new Color(255, 255, 255), new Color(0,0,0,0));
        jPanel8 = new javax.swing.JPanel();
        IncomeIcon1 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new RoundedPanel(15, new Color(255, 255, 255), new Color(0,0,0,0));
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel6.setMaximumSize(new java.awt.Dimension(70000, 2147483647));
        jPanel6.setName(""); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(300, 650));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel3.setPreferredSize(new java.awt.Dimension(179, 50));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jPanel5.setBackground(new Color(0, 0, 0, 0));

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resourcess/icons8-request-money-96.png"));
        Image resizedImage = originalIcon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH); // Change 48x48 to your desired size
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        IncomeIcon.setIcon(resizedIcon);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(IncomeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IncomeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel3.add(jPanel5);

        jPanel9.setBackground(new Color(0, 0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Total Income");

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Rs. "+ new java.text.DecimalFormat("#,###.00").format(income));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel9);

        jPanel1.add(jPanel3);

        jPanel4.setMaximumSize(new java.awt.Dimension(65534, 70));
        jPanel4.setPreferredSize(new java.awt.Dimension(179, 70));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel8.setBackground(new Color(0, 0, 0, 0));

        ImageIcon originalIcon1 = new ImageIcon(getClass().getResource("/resourcess/icons8-initiate-money-transfer-96.png"));
        Image resizedImage1 = originalIcon1.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH); // Change 48x48 to your desired size
        ImageIcon resizedIcon1 = new ImageIcon(resizedImage1);
        IncomeIcon1.setIcon(resizedIcon1);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(IncomeIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IncomeIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel4.add(jPanel8);

        jPanel10.setBackground(new Color(0, 0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Total Outgoing");

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Rs. "+ new java.text.DecimalFormat("#,###.00").format(outgoing));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel10);

        jPanel1.add(jPanel4);

        jPanel6.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel11.setPreferredSize(new java.awt.Dimension(473, 10));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.Y_AXIS));

        jLabel5.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Income");
        jLabel6.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel13.add(jLabel5);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Transction ID", "Transaction Detials", "Date", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(1);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(1);
        }
        jTable1.setShowGrid(false);
        jTable1.setIntercellSpacing(new Dimension(0, 0));

        jTable1.setBackground(Color.WHITE);
        jTable1.setForeground(Color.BLACK);
        jTable1.setFont(new Font("Poppins", Font.PLAIN, 14));
        jTable1.setRowHeight(25);

        JTableHeader header = jTable1.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Poppins", Font.PLAIN, 14));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setOpaque(true);

        jPanel13.add(jScrollPane1);

        jLabel6.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Outgoing");
        jLabel6.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel13.add(jLabel6);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Transction ID", "Transaction Detials", "Date", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(1);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(1);
        }
        jTable2.setShowGrid(false);
        jTable2.setIntercellSpacing(new Dimension(0, 0));

        jTable2.setBackground(Color.WHITE);
        jTable2.setForeground(Color.BLACK);
        jTable2.setFont(new Font("Poppins", Font.PLAIN, 14));
        jTable2.setRowHeight(25);

        JTableHeader header2 = jTable2.getTableHeader();
        header2.setBackground(Color.WHITE);
        header2.setForeground(Color.BLACK);
        header2.setFont(new Font("Poppins", Font.PLAIN, 14));
        header2.setBorder(BorderFactory.createEmptyBorder());
        header2.setOpaque(true);

        jPanel13.add(jScrollPane2);

        jPanel2.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(jPanel6);

        jPanel7.setPreferredSize(new java.awt.Dimension(300, 650));
        add(jPanel7);
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Income", income);
        pieDataset.setValue("Outgoing", outgoing);

        JFreeChart pieChart = ChartFactory.createPieChart(
            "Financial Overview",
            pieDataset,
            true,
            true,
            false
        );

        pieChart.setBackgroundPaint(new Color(0, 0, 0, 0));
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setBackgroundPaint(new Color(0, 0, 0, 0));
        plot.setOutlineVisible(false);
        plot.setLabelGenerator(null);
        pieChart.getTitle().setPaint(Color.WHITE);
        pieChart.getTitle().setBackgroundPaint(new Color(0,0,0,0));
        pieChart.getTitle().setFont(new Font("Poppins", Font.BOLD, 18));
        plot.setInteriorGap(0.02);
        plot.setShadowPaint(null);
        plot.setShadowXOffset(0);
        plot.setShadowYOffset(0);

        LegendTitle legend = pieChart.getLegend();
        legend.setBackgroundPaint(new Color(0, 0, 0, 0));
        legend.setItemPaint(Color.WHITE);
        legend.setFrame(new org.jfree.chart.block.BlockBorder(new Color(0,0,0,0)));
        legend.setItemFont(new Font("Poppins", Font.PLAIN, 11));

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setOpaque(false);
        chartPanel.setPreferredSize(new Dimension(400, 400));
        chartPanel.setMaximumDrawWidth(400);
        chartPanel.setMaximumDrawHeight(400);
        chartPanel.setMinimumDrawWidth(100);
        chartPanel.setMinimumDrawHeight(100);

        //jPanel7.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel7.add(chartPanel);
        jPanel7.revalidate();
        jPanel7.repaint();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IncomeIcon;
    private javax.swing.JLabel IncomeIcon1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
