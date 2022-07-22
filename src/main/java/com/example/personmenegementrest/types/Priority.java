package com.example.personmenegementrest.types;

public enum Priority {
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий"),
    CRITICAL("Критичный"),
    UNDEFINED(null);

    private final String translation;

    Priority(String translation) {
        this.translation = translation;
    }

    public static Priority getDefine(String priority) {
        if (LOW.translation.equals(priority)) {
            return LOW;
        } else if (MEDIUM.translation.equals(priority)) {
            return MEDIUM;
        } else if (HIGH.translation.equals(priority)) {
            return HIGH;
        } else if (CRITICAL.translation.equals(priority)) {
            return CRITICAL;
        } else {
            return UNDEFINED;
        }
    }

    @Override
    public String toString() {
        return translation;
    }
}
