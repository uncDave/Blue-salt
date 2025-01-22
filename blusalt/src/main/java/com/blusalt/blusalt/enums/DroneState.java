package com.blusalt.blusalt.enums;

public enum DroneState {
    IDLE("idle"),
    LOADING("loading"),
    LOADED("loaded"),
    DELIVERING("delivering"),
    DELIVERED("delivered"),
    RETURNING("returning");


    private String value;

    DroneState(String value) {
        this.value = value;
    }
}
