package com.ridingmate.app.util.main;


public class MaintenanceDAO {
    public static String item_maintenance;
    public static String item_maintenance_date;
    public static String item_maintenance_location;
    public static  String maintenace_detail;

    public MaintenanceDAO(String item_maintenance, String item_maintenance_date, String item_maintenance_location){
        this.item_maintenance= item_maintenance;
        this.item_maintenance_date= item_maintenance_date;
        this.item_maintenance_location= item_maintenance_location;
        // 내역은 DB에서 끌어오기(사용자가 등록한 정비 내역)
    }

    public String getItem_maintenance() {
        return item_maintenance;
    }
    public void setItem_maintenance(String item_maintenance) {
        this.item_maintenance = item_maintenance;
    }

    public String getItem_maintenance_date() {
        return item_maintenance_date;
    }
    public void setItem_maintenance_date(String item_maintenance_date) {
        this.item_maintenance_date = item_maintenance_date;
    }

    public String getItem_maintenance_location() {
        return item_maintenance_location;
    }
    public void setItem_maintenance_location(String item_maintenance_location) {
        this.item_maintenance_location = item_maintenance_location;
    }

    public static String getMaintenace_detail() {
        return maintenace_detail;
    }

    public static void setMaintenace_detail(String maintenace_detail) {
        MaintenanceDAO.maintenace_detail = maintenace_detail;
    }


}
