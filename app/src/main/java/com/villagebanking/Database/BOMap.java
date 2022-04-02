package com.villagebanking.Database;

import android.content.ContentValues;

public class BOMap {

    public BOMap(long primary_key, ContentValues contentValues) {
        this.primary_key = primary_key;
        this.contentValues = contentValues;
    }

    private long primary_key;

    public long getPrimary_key() {
        return primary_key;
    }

    private ContentValues contentValues;

    public ContentValues getContentValues() {
        return contentValues;
    }
}
