package com.example.systemadminsite;

public class admin_user_data {
    private String userId;
    private String userName;
    private String email;
    private String phoneNo;
    private String status;
    private String userPassword;
    private String userRegistrationdate;
    public admin_user_data(String userId, String userName, String email, String phoneNo, String status, String userPassword, String userRegistrationDate) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.status = status;
        this.userPassword=userPassword;
        this.userRegistrationdate= userRegistrationDate;
        this.phoneNo=phoneNo;
    }
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getPhoneNo(){ return phoneNo;}

    public String getStatus(){return status;}

    public String getUserRegistrationdate(){return userRegistrationdate;}
}
