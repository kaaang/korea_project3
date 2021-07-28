package com.ridingmate.app.util.maintenance;


public class MaintenanceDAO {
    private String item_maintenance;
    private String item_maintenance_date;
    private String item_maintenance_location;

    public MaintenanceDAO(String item_maintenance, String item_maintenance_date, String item_maintenance_location){
        this.item_maintenance= item_maintenance;
        this.item_maintenance_date= item_maintenance_date;
        this.item_maintenance_location= item_maintenance_location;
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



}
