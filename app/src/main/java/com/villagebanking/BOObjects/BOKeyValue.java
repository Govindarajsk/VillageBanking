package com.villagebanking.BOObjects;

import java.security.PublicKey;

public class BOKeyValue<T> extends BOBase {
    private String displayValue;
    private T actualObject;

    public String getDisplayValue() {
        return displayValue;
    }

    public T getActualObject() {
        return actualObject;
    }

    public BOKeyValue(){
        displayValue="Empty";
        setPrimary_key(0);
    }

    public BOKeyValue(long key, String value, T... items) {
        setPrimary_key(key);
        displayValue = value;
        actualObject = items.length > 0 ? items[0] : null;
    }
}
