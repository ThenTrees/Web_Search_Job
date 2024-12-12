package com.thentrees.lab_week5_www.backend.enums;

import lombok.*;

public enum SkillLevel {
    MASTER(5),

    PROFESSIONAL(4),

    ADVANCED(3),

    IMTERMEDIATE(2),

    BEGINER(1);

    private final int value;

    SkillLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SkillLevel fromValue(int value) {
        for (SkillLevel level : SkillLevel.values()) {
            if (level.value == value) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid value for SkillLevel: " + value);
    }
}
