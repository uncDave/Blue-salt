package com.blusalt.blusalt.enums;


public enum UserType {
    ADMIN("admin"),
    USER("user");


    private String value;

    UserType(String value) {
        this.value = value;
    }
}
