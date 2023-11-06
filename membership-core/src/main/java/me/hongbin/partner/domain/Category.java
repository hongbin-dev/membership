package me.hongbin.partner.domain;

public enum Category {
    A("식품"),
    B("화장품"),
    C("식당");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
