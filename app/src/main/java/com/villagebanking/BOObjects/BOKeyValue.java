package com.villagebanking.BOObjects;

public class BOKeyValue<T> extends BOBase {
    private String displayValue;
    private T actualObject;

    public String getDisplayValue() {
        return displayValue;
    }

    public T getActualObject() {
        return actualObject;
    }

    public BOKeyValue(long key, String value, T... items) {
        setPrimary_key(key);
        displayValue = value;
        actualObject = items.length > 0 ? items[0] : null;
    }
}
