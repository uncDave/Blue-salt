package com.blusalt.blusalt.service;

public interface DroneAuditTrailService {

    void logBatteryLevel(String droneSerialNumber, double batteryPercentage);
}
