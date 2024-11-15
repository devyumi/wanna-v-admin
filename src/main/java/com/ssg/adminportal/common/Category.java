package com.ssg.adminportal.common;

public enum Category {
    MK("밀키트"), // Meal Kit
    C("화장품"), // Cosmetic
    S("간식"); // Snack

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
