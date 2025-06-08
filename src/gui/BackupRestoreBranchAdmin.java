/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingUtilities;






public class BackupRestoreBranchAdmin extends javax.swing.JPanel {

    /**
     * Creates new form BackupRestoreBranchAdmin
     */
    public BackupRestoreBranchAdmin() {
        initComponents();
    }

private static final String DB_NAME = "123";
private static final String DB_USER = "root"; 
private static final String DB_PASSWORD = "Srj143@vimani"; 
private static final String DB_HOST = "localhost";
private static final String DB_PORT = "3306";
private static final String MYSQL_BIN_PATH = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\";

private String selectedBackupLocation = "";
private String selectedRestoreFile = "";
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        RestoreLocationTextField = new javax.swing.JLabel();
        BackupButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        BackupLocationTextField = new javax.swing.JLabel();
        RestoreBrowseLocationButton = new javax.swing.JButton();
        BackupBrowseLocationButton = new javax.swing.JButton();

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("BACKUP  RESTORE");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Backup");

        RestoreLocationTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        BackupButton.setText("Backup");
        BackupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackupButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Restore");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Restore");

        BackupLocationTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        RestoreBrowseLocationButton.setText("Browse Location");
        RestoreBrowseLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestoreBrowseLocationButtonActionPerformed(evt);
            }
        });

        BackupBrowseLocationButton.setText("Browse Location");
        BackupBrowseLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackupBrowseLocationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(200, 200, 200))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(BackupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RestoreLocationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 99, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BackupLocationTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(RestoreBrowseLocationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(BackupBrowseLocationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(RestoreLocationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackupButton)
                    .addComponent(jButton2))
                .addGap(13, 13, 13)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BackupLocationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RestoreBrowseLocationButton)
                    .addComponent(BackupBrowseLocationButton))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BackupBrowseLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackupBrowseLocationButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileChooser.setDialogTitle("Select Backup Location");
    
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "\\Documents"));
    
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        selectedBackupLocation = fileChooser.getSelectedFile().getAbsolutePath();
        BackupLocationTextField.setText("Location: " + selectedBackupLocation);
    }
    }//GEN-LAST:event_BackupBrowseLocationButtonActionPerformed

    private void BackupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackupButtonActionPerformed
       if (selectedBackupLocation.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Please select a backup location first!", 
            "No Location Selected", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to backup the database?\nLocation: " + selectedBackupLocation,
        "Confirm Backup",
        JOptionPane.YES_NO_OPTION);
        
    if (confirm == JOptionPane.YES_OPTION) {
        performBackup();
    }
    }//GEN-LAST:event_BackupButtonActionPerformed

    private void RestoreBrowseLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestoreBrowseLocationButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setDialogTitle("Select Backup File to Restore");
    
    fileChooser.setFileFilter(new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".sql");
        }
        
        @Override
        public String getDescription() {
            return "SQL Backup Files (*.sql)";
        }
    });
    
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        selectedRestoreFile = fileChooser.getSelectedFile().getAbsolutePath();
        RestoreLocationTextField.setText("File: " + fileChooser.getSelectedFile().getName());
    }

    }//GEN-LAST:event_RestoreBrowseLocationButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (selectedRestoreFile.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Please select a backup file to restore first!", 
            "No File Selected", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to restore the database?\n" +
        "This will OVERWRITE all current data!\n" +
        "File: " + new File(selectedRestoreFile).getName(),
        "Confirm Restore - WARNING",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
        
    if (confirm == JOptionPane.YES_OPTION) {
        performRestore();
        
    }
    }
    private void performBackup() {
    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                String timestamp = sdf.format(new Date());
                String backupFileName = "PlatePal_Backup_" + timestamp + ".sql";
                String fullPath = selectedBackupLocation + File.separator + backupFileName;
                
                String[] command = {
                    MYSQL_BIN_PATH + "mysqldump",
                    "-h", DB_HOST,
                    "-P", DB_PORT,
                    "-u", DB_USER,
                    DB_PASSWORD.isEmpty() ? "--skip-password" : "-p" + DB_PASSWORD,
                    "--routines",
                    "--triggers",
                    DB_NAME
                };
                
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.redirectOutput(new File(fullPath));
                pb.redirectErrorStream(true);
                
                Process process = pb.start();
                int exitCode = process.waitFor();
                
                if (exitCode == 0) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(BackupRestoreBranchAdmin.this,
                            "Backup completed successfully!\n" +
                            "File saved as: " + backupFileName,
                            "Backup Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    });
                } else {
                    throw new Exception("Backup failed with exit code: " + exitCode);
                }
                
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(BackupRestoreBranchAdmin.this,
                        "Backup failed: " + e.getMessage(),
                        "Backup Error",
                        JOptionPane.ERROR_MESSAGE);
                });
            }
            return null;
        }
    };
    
    worker.execute();
    JOptionPane.showMessageDialog(this, "Backup started... Please wait.", "Processing", JOptionPane.INFORMATION_MESSAGE);
}

private void performRestore() {
    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            try {
                // Build mysql command for restore
                String[] command = {
                    MYSQL_BIN_PATH + "mysql",
                    "-h", DB_HOST,
                    "-P", DB_PORT,
                    "-u", DB_USER,
                    DB_PASSWORD.isEmpty() ? "--skip-password" : "-p" + DB_PASSWORD,
                    DB_NAME
                };
                
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.redirectInput(new File(selectedRestoreFile));
                pb.redirectErrorStream(true);
                
                Process process = pb.start();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                
                int exitCode = process.waitFor();
                
                if (exitCode == 0) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(BackupRestoreBranchAdmin.this,
                            "Database restored successfully!",
                            "Restore Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    });
                } else {
                    throw new Exception("Restore failed with exit code: " + exitCode + "\n" + output.toString());
                }
                
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(BackupRestoreBranchAdmin.this,
                        "Restore failed: " + e.getMessage(),
                        "Restore Error",
                        JOptionPane.ERROR_MESSAGE);
                });
            }
            return null;
        }
    };
    
    worker.execute();
    JOptionPane.showMessageDialog(this, "Restore started... Please wait.", "Processing", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackupBrowseLocationButton;
    private javax.swing.JButton BackupButton;
    private javax.swing.JLabel BackupLocationTextField;
    private javax.swing.JButton RestoreBrowseLocationButton;
    private javax.swing.JLabel RestoreLocationTextField;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
