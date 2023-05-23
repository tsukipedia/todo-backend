package com.todoapp.todolist.Model;

public enum Priority {
    HIGH,
    MEDIUM,
    LOW;

    public static Priority fromString(String value) {
        for (Priority enumValue : Priority.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Invalid enum value: " + value);
    }
}