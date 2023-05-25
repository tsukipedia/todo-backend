package com.todoapp.todolist.Model;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    public static Priority fromString(String value) {
        if(value != null) {
            for (Priority enumValue : Priority.values()) {
                if (enumValue.name().equalsIgnoreCase(value)) {
                    return enumValue;
                }
            }
            throw new IllegalArgumentException("Invalid enum value: " + value);
        }
        else return null;
    }
}