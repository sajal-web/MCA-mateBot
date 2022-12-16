package com.tutorialsbysajal.mcametbot.modelClass;

public class User {
   String name,emailId,mobileNo,studentId,department,password;

    public User() {
    }

    public User(String name, String emailId, String mobileNo, String studentId, String department, String password) {
        this.name = name;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
        this.studentId = studentId;
        this.department = department;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
