package com.ridingmate.app.util.main.maintenance;


public class Maintenance {
    public  String item_maintenance;
    public  String item_maintenance_date;
    public  String item_maintenance_location;
    public  String item_maintenance_detail;
    public  String document_ID;
    // Getter&Setter
    public Maintenance(String document_ID,String item_maintenance, String item_maintenance_date, String item_maintenance_location, String item_maintenance_detail){
        this.item_maintenance= item_maintenance;
        this.item_maintenance_date= item_maintenance_date;
        this.item_maintenance_location= item_maintenance_location;
        this.item_maintenance_detail= item_maintenance_detail;
        this.document_ID = document_ID;
    }

    public  String getItem_maintenance() {
        return item_maintenance;
    }

    public  void setItem_maintenance(String item_maintenance) {
       this.item_maintenance = item_maintenance;
    }

    public  String getItem_maintenance_date() {
        return item_maintenance_date;
    }

    public  void setItem_maintenance_date(String item_maintenance_date) {
        this.item_maintenance_date = item_maintenance_date;
    }

    public  String getItem_maintenance_location() {
        return item_maintenance_location;
    }

    public  void setItem_maintenance_location(String item_maintenance_location) {
        this.item_maintenance_location = item_maintenance_location;
    }

    public  String getItem_maintenance_detail() {
        return item_maintenance_detail;
    }

    public  void setItem_Maintenace_detail(String maintenace_detail) {
        this.item_maintenance_detail = maintenace_detail;
    }
    public  String getDocument_ID() {
        return document_ID;
    }

    public  void setDocument_ID(String Document_ID) {
        this.document_ID = Document_ID;
    }
}
