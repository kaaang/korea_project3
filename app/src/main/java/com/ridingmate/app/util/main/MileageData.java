package com.ridingmate.app.util.main;

public class MileageData {
    private String main_mileage_oil;
    private String main_mileage_driven;
    private String main_mileage_date;

    public MileageData(String main_mileage_oil, String main_mileage_driven, String main_mileage_date) {
        this.main_mileage_oil = main_mileage_oil;
        this.main_mileage_driven = main_mileage_driven;
        this.main_mileage_date = main_mileage_date;
    }

    public String getMain_mileage_oil() {
        return main_mileage_oil;
    }

    public void setMain_mileage_oil(String main_mileage_oil) {
        this.main_mileage_oil = main_mileage_oil;
    }

    public String getMain_mileage_driven() {
        return main_mileage_driven;
    }

    public void setMain_mileage_driven(String main_mileage_driven) {
        this.main_mileage_driven = main_mileage_driven;
    }

    public String getMain_mileage_date() {
        return main_mileage_date;
    }

    public void setMain_mileage_date(String main_mileage_date) {
        this.main_mileage_date = main_mileage_date;
    }
}
