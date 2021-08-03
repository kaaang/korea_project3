package com.ridingmate.app.util.used;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URI;

public class UsedListData {
    private Uri used_thumb;
    private String used_thumbSrc;
    private String used_title;
    private String model_type;
    private String used_comment_cont;
    private String used_welth;
    private String used_id;

//    public UsedListData(int used_thumb, String used_thumbSrc, String used_title, String model_type, String used_comment_cont, String used_welth) {
//        this.used_thumb = used_thumb;
//        this.used_thumbSrc = used_thumbSrc;
//        this.used_title = used_title;
//        this.model_type = model_type;
//        this.used_comment_cont = used_comment_cont;
//        this.used_welth = used_welth;
//    }
    public UsedListData() {

    }

    public String getUsed_id() {
        return used_id;
    }

    public void setUsed_id(String used_id) {
        this.used_id = used_id;
    }

    public Uri getUsed_thumb() {
        return used_thumb;
    }

    public void setUsed_thumb(Uri used_thumb) {
        this.used_thumb = used_thumb;
    }

    public String getUsed_thumbSrc() {
        return used_thumbSrc;
    }

    public void setUsed_thumbSrc(String used_thumbSrc) {
        this.used_thumbSrc = used_thumbSrc;
    }

    public String getUsed_title() {
        return used_title;
    }

    public void setUsed_title(String used_title) {
        this.used_title = used_title;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    public String getUsed_comment_cont() {
        return used_comment_cont;
    }

    public void setUsed_comment_cont(String used_comment_cont) {
        this.used_comment_cont = used_comment_cont;
    }

    public String getUsed_welth() {
        return used_welth;
    }

    public void setUsed_welth(String used_welth) {
        this.used_welth = used_welth;
    }
}
