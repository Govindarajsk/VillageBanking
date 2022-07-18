package com.villagebanking.BOObjects;

public class BOKeyValue extends BOBase {
    private String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    public BOKeyValue(long key, String value) {
        setPrimary_key(key);
        displayValue=value;
    }
}
