/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.MySQL2;
import java.sql.Time;
import model.Session;

/**
 *
 * @author hasit
 */
public class profile extends javax.swing.JPanel {

    /**
     * Creates new form dashbord
     */
    public profile() {
        initComponents();
        loadTable1();
        loadTable2();
        loadTable3();
        loadEmployeeData();
    }

    public static int employee_id = Session.employeeId;

    public void loadEmployeeData() {
        try {
            ResultSet resultSet = MySQL2.executeSearch(
                    "SELECT "
                    + "e.id AS employee_id, e.emp_id, e.first_name, e.last_name, e.mobile, e.email, e.selected_salary, "
                    + "g.type AS gender, "
                    + "er.type AS role, "
                    + "b.name AS branch_name, b.branch_id AS branch_code, "
                    + "ea.address AS full_address, d.name AS district_name, "
                    + "att.date AS attendance_date, att.checkin_time, att.checkout_time, "
                    + "at.name AS attendance_status "
                    + "FROM employee e "
                    + "LEFT JOIN gender g ON e.gender_id = g.id "
                    + "LEFT JOIN employee_role er ON e.employee_role_id = er.id "
                    + "LEFT JOIN branch b ON e.branch_id = b.id "
                    + "LEFT JOIN employee_address ea ON e.id = ea.employee_id "
                    + "LEFT JOIN district d ON ea.district_id = d.id "
                    + "LEFT JOIN employee_attendance att ON e.id = att.employee_id "
                    + "LEFT JOIN attendance_type at ON att.attendance_type_id = at.id "
                    + "WHERE e.id = " + employee_id + " "
                    + "ORDER BY att.date DESC "
                    + "LIMIT 1"
            );

            if (resultSet.next()) {
                uId.setText("ID: " + resultSet.getString("emp_id"));
                uName.setText("NAME: " + resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                uGender.setText("GENDER: " + resultSet.getString("gender"));
                uMobile.setText("MOBILE: " + resultSet.getString("mobile"));
                uEmail.setText("EMAIL: " + resultSet.getString("email"));
                uAddress.setText("ADDRESS: " + resultSet.getString("full_address") + ", " + resultSet.getString("district_name"));
                uBranch.setText("BRANCH: " + resultSet.getString("branch_name") + " (" + resultSet.getString("branch_code") + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTable1() {
        try {
            ResultSet resultset = MySQL2.executeSearch(
                    "SELECT "
                    + "CONCAT('INV', LPAD(CAST(i.id AS CHAR), 5, '0')) AS id, "
                    + "i.date AS date, "
                    + "i.payment AS value "
                    + "FROM invoice i "
                    + "INNER JOIN employee e ON e.id = i.employee_id "
                    + "WHERE e.id = " + employee_id + " "
                    + "ORDER BY date DESC"
            );

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("id"));
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
                    + "s.paid_date AS date, "
                    + "s.paid_amount AS value "
                    + "FROM salary_payments s "
                    + "INNER JOIN employee e ON e.id = s.employee_id "
                    + "WHERE e.id = " + employee_id + " "
                    + "UNION ALL "
                    + "SELECT "
                    + "CONCAT('PC', LPAD(CAST(p.id AS CHAR), 5, '0')) AS id, "
                    + "p.date, "
                    + "p.amount "
                    + "FROM petty_cash p "
                    + "INNER JOIN employee e ON e.id = p.employee_id "
                    + "WHERE e.id = " + employee_id + " "
                    + "ORDER BY date DESC"
            );

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("id"));
                vector.add(resultset.getString("date"));
                vector.add("Rs. " + resultset.getString("value"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTable3() {
        try {
            ResultSet resultset = MySQL2.executeSearch(
                    "SELECT * "
                    + "FROM employee_attendance a "
                    + "INNER JOIN employee e ON e.id = a.employee_id "
                    + "WHERE e.id = " + employee_id + " "
                    + "ORDER BY date DESC"
            );

            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("date"));
                vector.add(resultset.getString("checkin_time"));
                vector.add(resultset.getString("checkout_time"));

                Time checkin = resultset.getTime("checkin_time");
                Time checkout = resultset.getTime("checkout_time");

                if (checkin != null && checkout != null) {
                    long milliseconds = checkout.getTime() - checkin.getTime();
                    long hours = milliseconds / (1000 * 60 * 60);
                    long minutes = (milliseconds / (1000 * 60)) % 60;
                    vector.add(hours + "h " + minutes + "m");
                }

                if (checkin != null && checkout != null) {
                    long totalMilliseconds = checkout.getTime() - checkin.getTime();
                    long overtimeMilliseconds = Math.max(0, totalMilliseconds - (8 * 60 * 60 * 1000)); // subtract 8 hours in milliseconds

                    long overtimeHours = overtimeMilliseconds / (1000 * 60 * 60);
                    long overtimeMinutes = (overtimeMilliseconds / (1000 * 60)) % 60;

                    vector.add(overtimeHours + "h " + overtimeMinutes + "m");
                }

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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel13 = new RoundedPanel(15, new Color(230, 230, 230), new Color(0,0,0,0));
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        uId = new javax.swing.JLabel();
        uName = new javax.swing.JLabel();
        uGender = new javax.swing.JLabel();
        uMobile = new javax.swing.JLabel();
        uEmail = new javax.swing.JLabel();
        uAddress = new javax.swing.JLabel();
        uBranch = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(650, 400));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel6.setMaximumSize(new java.awt.Dimension(70000, 2147483647));
        jPanel6.setName(""); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(300, 650));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel4.setMaximumSize(new java.awt.Dimension(65534, 70));
        jPanel4.setPreferredSize(new java.awt.Dimension(179, 70));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel7.setText("User Profile");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 334, 50));

        jPanel1.add(jPanel4);

        jPanel6.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel13.setPreferredSize(new java.awt.Dimension(350, 848));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.Y_AXIS));

        jLabel5.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Your Sales");
        jLabel6.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel13.add(jLabel5);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Transction ID", "Date", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setPreferredSize(new java.awt.Dimension(120, 120));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(1);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(1);
        }
        jTable1.setShowGrid(false);
        //jScrollPane1.setViewportView(jTable1);
        //jScrollPane1.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));

        jTable1.setIntercellSpacing(new Dimension(0, 0));

        jTable1.setBackground(new Color(230, 230, 230));
        jTable1.setForeground(Color.BLACK);
        jTable1.setFont(new Font("Poppins", Font.PLAIN, 14));
        jTable1.setRowHeight(25);

        JTableHeader header = jTable1.getTableHeader();
        header.setBackground(new Color(230, 230, 230));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Poppins", Font.PLAIN, 14));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setOpaque(true);

        jPanel13.add(jScrollPane1);

        jLabel6.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Other Transactions");
        jLabel6.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel13.add(jLabel6);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Transction ID", "Date", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setPreferredSize(new java.awt.Dimension(120, 120));
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(1);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(1);
        }
        jTable2.setShowGrid(false);
        //jScrollPane2.setViewportView(jTable2);
        //jScrollPane2.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));

        jTable2.setIntercellSpacing(new Dimension(0, 0));

        jTable2.setBackground(new Color(230, 230, 230));
        jTable2.setForeground(Color.BLACK);
        jTable2.setFont(new Font("Poppins", Font.PLAIN, 14));
        jTable2.setRowHeight(25);

        JTableHeader header2 = jTable2.getTableHeader();
        header2.setBackground(new Color(230, 230, 230));
        header2.setForeground(Color.BLACK);
        header2.setFont(new Font("Poppins", Font.PLAIN, 14));
        header2.setBorder(BorderFactory.createEmptyBorder());
        header2.setOpaque(true);

        jPanel13.add(jScrollPane2);

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Attendance");
        jLabel6.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel13.add(jLabel8);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date", "Check In Time", "Check Out Time", "Worked Hours", "Overtime Hours"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setPreferredSize(new java.awt.Dimension(120, 120));
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(1);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(1);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(1);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(1);
        }
        jTable3.setShowGrid(false);
        //jScrollPane3.setViewportView(jTable3);
        //jScrollPane3.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

        jTable3.setIntercellSpacing(new Dimension(0, 0));

        jTable3.setBackground(new Color(230, 230, 230));
        jTable3.setForeground(Color.BLACK);
        jTable3.setFont(new Font("Poppins", Font.PLAIN, 14));
        jTable3.setRowHeight(25);

        JTableHeader header3 = jTable3.getTableHeader();
        header3.setBackground(new Color(230, 230, 230));
        header3.setForeground(Color.BLACK);
        header3.setFont(new Font("Poppins", Font.PLAIN, 14));
        header3.setBorder(BorderFactory.createEmptyBorder());
        header3.setOpaque(true);

        jPanel13.add(jScrollPane3);

        jPanel2.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(jPanel6);

        jPanel7.setPreferredSize(new java.awt.Dimension(300, 250));
        jPanel7.setRequestFocusEnabled(false);
        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel11.setPreferredSize(new java.awt.Dimension(409, 10));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        jPanel12.setPreferredSize(new java.awt.Dimension(315, 10));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel12, java.awt.BorderLayout.PAGE_END);

        jPanel14.setPreferredSize(new java.awt.Dimension(20, 469));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel14, java.awt.BorderLayout.LINE_START);

        jPanel15.setPreferredSize(new java.awt.Dimension(10, 489));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel15, java.awt.BorderLayout.LINE_END);

        jPanel9.setPreferredSize(new java.awt.Dimension(453, 500));
        jPanel9.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipady = 30;
        jPanel9.add(jLabel1, gridBagConstraints);
        ImageIcon icon = new ImageIcon(getClass().getResource("/resourcess/blue-circle-with-white-user.png"));
        Image image = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        jLabel1.setIcon(new ImageIcon(image));

        jPanel8.setLayout(new java.awt.GridLayout(0, 1, 10, 10));

        uId.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        uId.setText("ID");
        jPanel8.add(uId);

        uName.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        uName.setText("NAME");
        jPanel8.add(uName);

        uGender.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        uGender.setText("GENDER");
        jPanel8.add(uGender);

        uMobile.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        uMobile.setText("MOBILE");
        jPanel8.add(uMobile);

        uEmail.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        uEmail.setText("EMAIL");
        jPanel8.add(uEmail);

        uAddress.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        uAddress.setText("ADDRESS");
        jPanel8.add(uAddress);

        uBranch.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        uBranch.setText("BRANCH");
        jPanel8.add(uBranch);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel9.add(jPanel8, gridBagConstraints);

        jPanel5.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel5);

        add(jPanel7);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel uAddress;
    private javax.swing.JLabel uBranch;
    private javax.swing.JLabel uEmail;
    private javax.swing.JLabel uGender;
    private javax.swing.JLabel uId;
    private javax.swing.JLabel uMobile;
    private javax.swing.JLabel uName;
    // End of variables declaration//GEN-END:variables
}
