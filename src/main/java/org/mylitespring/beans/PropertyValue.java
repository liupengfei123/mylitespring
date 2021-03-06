package org.mylitespring.beans;

public class PropertyValue {

    private final String name;

    private final Object value;

    private boolean converted = false;

    private Object convertedValue;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public synchronized boolean isConverted() {
        return converted;
    }

    public synchronized void setConvertedValue(Object convertedValue) {
        this.converted = true;
        this.convertedValue = convertedValue;
    }

    public Object getConvertedValue() {
        return convertedValue;
    }
}
