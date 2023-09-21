package edu.huflit.testcallapi.model;

public class MyResponse {
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "login='" + login + '\'' +
                '}';
    }

    private String login;

}
