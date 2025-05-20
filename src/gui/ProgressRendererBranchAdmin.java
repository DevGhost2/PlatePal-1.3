/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import model.*;
import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ProgressRendererBranchAdmin extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        int progress = 0;
        if (value != null) {
            progress = Integer.parseInt(value.toString());
        }
        progressBar.setValue(progress);
        progressBar.setStringPainted(true);
        progressBar.setString(progress + "%");
        
        return progressBar;
    }
}
