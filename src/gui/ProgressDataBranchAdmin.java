/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import model.*;
import java.sql.Date;

public class ProgressDataBranchAdmin {
    private String status;
    private String name;
    private Date startDate;
    private int progress;
    
    public ProgressDataBranchAdmin(String status, String name, Date startDate, int progress) {
        this.status = status;
        this.name = name;
        this.startDate = startDate;
        this.progress = progress;
    }
    
    // Getters
    public String getStatus() { return status; }
    public String getName() { return name; }
    public Date getStartDate() { return startDate; }
    public int getProgress() { return progress; }
}
