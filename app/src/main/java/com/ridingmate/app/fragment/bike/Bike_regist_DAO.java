package com.ridingmate.app.fragment.bike;

public class Bike_regist_DAO {
    private String company;
    private String model;
    private String year;
    private String driven;
    private String nickname;
    private String image;

    public Bike_regist_DAO() {
    }

    public Bike_regist_DAO(String company, String model, String year, String driven, String nickname, String image) {
        this.company = company;
        this.model = model;
        this.year = year;
        this.driven = driven;
        this.nickname = nickname;
        this.image = image;
    }

    public String getCompany() {
        return company;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getDriven() {
        return driven;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImage() {
        return image;
    }
}
