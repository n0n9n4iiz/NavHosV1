package com.example.navhosv1;

public class DataLogin {
    private String personid;
    private String lastname;
    private String firstname;
    private String address;
    private String birthdate;
    private String hn;
    private String phone;

    public DataLogin(String personid, String lastname, String firstname, String address, String birthdate, String hn, String phone) {
        this.personid = personid;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
        this.birthdate = birthdate;
        this.hn = hn;
        this.phone = phone;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getHn() {
        return hn;
    }

    public void setHn(String hn) {
        this.hn = hn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
