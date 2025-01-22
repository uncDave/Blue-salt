package com.blusalt.blusalt.enums;

public enum DroneModel {
    LIGHTWEIGHT("lightweight"),
    MIDDLEWEIGHT("middleweight"),
    CRUISERWEIGHT("cruiserweight"),
    HEAVYWEIGHT("heavyweight");


    private String value;

    DroneModel(String value) {
        this.value = value;
    }
}
