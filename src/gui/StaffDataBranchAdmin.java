/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.util.Date;

public class StaffDataBranchAdmin {
    private int employeeID;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String city;
    private String address;
    private String addressCategory;
    private String branch;
    private Date date;
    private String attendance;
    
    // Constructor
    public StaffDataBranchAdmin(int employeeID, String firstName, String lastName, 
                               String mobile, String email, String city, 
                               String address, String addressCategory, String branch,
                               Date date, String attendance) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.city = city;
        this.address = address;
        this.addressCategory = addressCategory;
        this.branch = branch;
        this.date = date;
        this.attendance = attendance;
    }
    
    // Getters
    public int getEmployeeID() { return employeeID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getMobile() { return mobile; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getAddressCategory() { return addressCategory; }
    public String getBranch() { return branch; }
    public Date getDate() { return date; }
    public String getAttendance() { return attendance; }
}