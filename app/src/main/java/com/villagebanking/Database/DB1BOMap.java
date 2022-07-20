package com.villagebanking.Database;

import android.content.ContentValues;

public class DB1BOMap {

    public DB1BOMap(long primary_key, ContentValues contentValues) {
        this.primary_key = primary_key;
        this.contentValues = contentValues;
    }

    private long primary_key;
    private ContentValues contentValues;

    public long getPrimary_key() {
        return primary_key;
    }

    public ContentValues getContentValues() {
        return contentValues;
    }
}
