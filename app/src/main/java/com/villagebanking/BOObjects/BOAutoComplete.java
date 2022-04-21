package com.villagebanking.BOObjects;

public class BOAutoComplete extends BOBase{
    private String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    private void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
    public BOAutoComplete(long key,String value)
    {
        setPrimary_key(key);
        setDisplayValue(value);
    }
}
