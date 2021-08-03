package com.ridingmate.app.util.main;

public class MyBikeData {
    private String nickname;
    private String company;
    private String model;
    private String year;

    public MyBikeData(String nickname, String company, String model, String year) {
        this.nickname = nickname;
        this.company = company;
        this.model = model;
        this.year = year;
    }

    public MyBikeData() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
