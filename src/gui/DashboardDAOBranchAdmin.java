/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import model.MySQL2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAOBranchAdmin {
    
    
    public int getActiveCustomersCount() {
        int count = 0;
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT COUNT(*) FROM customers WHERE status = 'active'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error fetching active customers: " + e.getMessage());
        }
        return count;
    }
    
    
    public int getFailedOrdersCount() {
        int count = 0;
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT COUNT(*) FROM orders WHERE status = 'failed' OR status = 'canceled'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error fetching failed orders: " + e.getMessage());
        }
        return count;
    }
    
    // Get completed orders count
    public int getCompletedOrdersCount() {
        int count = 0;
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT COUNT(*) FROM orders WHERE status = 'completed'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error fetching completed orders: " + e.getMessage());
        }
        return count;
    }
    
    // Get today's revenue
    public double getTodaysRevenue() {
        double revenue = 0.0;
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT SUM(total_amount) FROM orders WHERE status = 'completed' AND DATE(order_date) = CURDATE()");
            if (rs.next()) {
                revenue = rs.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println("Error fetching today's revenue: " + e.getMessage());
        }
        return revenue;
    }
    
    // Get pending orders count
    public int getPendingOrdersCount() {
        int count = 0;
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT COUNT(*) FROM orders WHERE status = 'pending' OR status = 'in_progress'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error fetching pending orders: " + e.getMessage());
        }
        return count;
    }
    
    // Get active employees count
    public int getActiveEmployeesCount() {
        int count = 0;
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT COUNT(*) FROM employees WHERE status = 'active'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error fetching active employees: " + e.getMessage());
        }
        return count;
    }
    
    // Get progress data for table
    public List<ProgressDataBranchAdmin> getProgressData() {
        List<ProgressDataBranchAdmin> progressList = new ArrayList<>();
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT status, name, start_date, progress FROM projects");
            while (rs.next()) {
                ProgressDataBranchAdmin data = new ProgressDataBranchAdmin(
                    rs.getString("status"),
                    rs.getString("name"),
                    rs.getDate("start_date"),
                    rs.getInt("progress")
                );
                progressList.add(data);
            }
        } catch (Exception e) {
            System.out.println("Error fetching progress data: " + e.getMessage());
        }
        return progressList;
    }
}
