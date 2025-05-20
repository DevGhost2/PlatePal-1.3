/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import model.MySQL2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class StaffDAOBranchAdmin {
    
    // Get all staff
    public List<StaffDataBranchAdmin> getAllStaff() {
        List<StaffDataBranchAdmin> staffList = new ArrayList<>();
        try {
            ResultSet rs = MySQL2.executeSearch(
                "SELECT e.EmployeeID, e.FirstName, e.LastName, e.Mobile, e.Email, " +
                "e.City, e.Address, e.AddressCategory, e.Branch, a.Date, a.Attendance " +
                "FROM staff_employees e LEFT JOIN staff_attendance a ON e.EmployeeID = a.EmployeeID " +
                "ORDER BY e.EmployeeID"
            );
            
            while (rs.next()) {
                StaffDataBranchAdmin staff = new StaffDataBranchAdmin(
                    rs.getInt("EmployeeID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Mobile"),
                    rs.getString("Email"),
                    rs.getString("City"),
                    rs.getString("Address"),
                    rs.getString("AddressCategory"),
                    rs.getString("Branch"),
                    rs.getDate("Date"),
                    rs.getString("Attendance")
                );
                staffList.add(staff);
            }
        } catch (Exception e) {
            System.out.println("Error fetching staff: " + e.getMessage());
        }
        return staffList;
    }
    
    // Search staff by ID or Branch
    public List<StaffDataBranchAdmin> searchStaff(String employeeID, String branch) {
        List<StaffDataBranchAdmin> staffList = new ArrayList<>();
        
        try {
            String query = "SELECT e.EmployeeID, e.FirstName, e.LastName, e.Mobile, e.Email, " +
                          "e.City, e.Address, e.AddressCategory, e.Branch, a.Date, a.Attendance " +
                          "FROM staff_employees e LEFT JOIN staff_attendance a ON e.EmployeeID = a.EmployeeID WHERE 1=1";
            
            if (employeeID != null && !employeeID.trim().isEmpty()) {
                query += " AND e.EmployeeID = " + employeeID;
            }
            
            if (branch != null && !branch.equals("Item 1")) {
                query += " AND e.Branch = '" + branch + "'";
            }
            
            query += " ORDER BY e.EmployeeID";
            
            ResultSet rs = MySQL2.executeSearch(query);
            
            while (rs.next()) {
                StaffDataBranchAdmin staff = new StaffDataBranchAdmin(
                    rs.getInt("EmployeeID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Mobile"),
                    rs.getString("Email"),
                    rs.getString("City"),
                    rs.getString("Address"),
                    rs.getString("AddressCategory"),
                    rs.getString("Branch"),
                    rs.getDate("Date"),
                    rs.getString("Attendance")
                );
                staffList.add(staff);
            }
        } catch (Exception e) {
            System.out.println("Error searching staff: " + e.getMessage());
        }
        
        return staffList;
    }
    
    // Create new staff account
    public boolean createStaff(String firstName, String lastName, String mobile, 
                              String email, String city, String address,
                              String addressCategory, String branch) {
        try {
            String query = "INSERT INTO staff_employees (FirstName, LastName, Mobile, Email, City, " +
                          "Address, AddressCategory, Branch) VALUES ('" +
                          firstName + "', '" + lastName + "', '" + mobile + "', '" +
                          email + "', '" + city + "', '" + address + "', '" +
                          addressCategory + "', '" + branch + "')";
                          
            int result = MySQL2.executeIUD(query);
            return result > 0;
        } catch (Exception e) {
            System.out.println("Error creating staff: " + e.getMessage());
            return false;
        }
    }
    
    // Update staff account
    public boolean updateStaff(int employeeID, String firstName, String lastName, String mobile, 
                              String email, String city, String address,
                              String addressCategory, String branch) {
        try {
            String query = "UPDATE staff_employees SET FirstName = '" + firstName +
                          "', LastName = '" + lastName +
                          "', Mobile = '" + mobile +
                          "', Email = '" + email +
                          "', City = '" + city +
                          "', Address = '" + address +
                          "', AddressCategory = '" + addressCategory +
                          "', Branch = '" + branch +
                          "' WHERE EmployeeID = " + employeeID;
                          
            int result = MySQL2.executeIUD(query);
            return result > 0;
        } catch (Exception e) {
            System.out.println("Error updating staff: " + e.getMessage());
            return false;
        }
    }
    
    // Get employee by ID
    public StaffDataBranchAdmin getEmployeeById(int employeeID) {
        StaffDataBranchAdmin employee = null;
        
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT * FROM staff_employees WHERE EmployeeID = " + employeeID);
            
            if (rs.next()) {
                employee = new StaffDataBranchAdmin(
                    rs.getInt("EmployeeID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Mobile"),
                    rs.getString("Email"),
                    rs.getString("City"),
                    rs.getString("Address"),
                    rs.getString("AddressCategory"),
                    rs.getString("Branch"),
                    null, // Date
                    null  // Attendance
                );
            }
        } catch (Exception e) {
            System.out.println("Error fetching employee: " + e.getMessage());
        }
        
        return employee;
    }
    
    // Get available branches for dropdown
    public List<String> getBranches() {
        List<String> branches = new ArrayList<>();
        
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT DISTINCT Branch FROM staff_branches ORDER BY Branch");
            
            while (rs.next()) {
                branches.add(rs.getString("Branch"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching branches: " + e.getMessage());
        }
        
        return branches;
    }
    
    // Get address categories for dropdown
    public List<String> getAddressCategories() {
        List<String> categories = new ArrayList<>();
        
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT DISTINCT Category FROM staff_address_categories ORDER BY Category");
            
            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching address categories: " + e.getMessage());
            // Add some default values if DB fetch fails
            categories.add("Home");
            categories.add("Work");
            categories.add("Other");
        }
        
        return categories;
    }
}