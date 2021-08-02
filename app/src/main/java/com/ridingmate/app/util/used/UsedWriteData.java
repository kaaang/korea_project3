package com.ridingmate.app.util.used;

import android.graphics.Bitmap;

public class UsedWriteData {
    private Bitmap bitmap;

    public UsedWriteData(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
