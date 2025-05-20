/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import model.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class StatusRendererBranchAdmin extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        
        JLabel dot = new JLabel("●");
        // Set color based on status
        if (value != null && "Active".equals(value.toString())) {
            dot.setForeground(new Color(46, 204, 113)); // Green
        } else {
            dot.setForeground(new Color(231, 76, 60)); // Red
        }
        
        JLabel text = new JLabel(value != null ? value.toString() : "");
        
        panel.add(dot);
        panel.add(text);
        return panel;
    }
}
