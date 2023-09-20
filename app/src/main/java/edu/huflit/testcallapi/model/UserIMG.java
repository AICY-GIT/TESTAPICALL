package edu.huflit.testcallapi.model;

public class UserIMG {
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    private int accountId;
    private String username;
    private String password;
    private String avt;

    public UserIMG(int accountId, String username, String password, String avt) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.avt = avt;
    }
}
