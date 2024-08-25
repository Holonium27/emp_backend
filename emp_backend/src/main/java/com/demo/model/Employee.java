package com.demo.model;

import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.docref.DocumentReferences;


public class Employee {
	
    private String id;
    private String employeeName;
    private String phoneNumber;
    private String email;
    private String reportsTo;
    private String profileImage;

    @JsonProperty("id")
    public String getId() {
        return id;
    }
    
    @JsonProperty("_id")
    private String id1;

    
    public String getId1() {
        return id1;
    }
    
    @JsonProperty("_rev")
    private String revision;
    
    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }
    
 
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }
    
    
    @JsonProperty("employeeName")
    public String getEmployeeName() {
        return employeeName;
    }
    
    @JsonProperty("employeeName")
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }
    
    @JsonProperty("reportsTo")
    public String getReportsTo() {
        return reportsTo;
    }

    @JsonProperty("reportsTo")
    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
    }

    @JsonProperty("profileImage")
    public String getProfileImage() {
        return profileImage;
    }

    @JsonProperty("profileImage")
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Employee(String employeeName, String phoneNumber, String email, String reportsTo, String profileImage) {
        this.id = UUID.randomUUID().toString();
        this.employeeName = employeeName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.reportsTo = reportsTo;
        this.profileImage = profileImage;
    }
    
    public Employee() {
    }
    
}
