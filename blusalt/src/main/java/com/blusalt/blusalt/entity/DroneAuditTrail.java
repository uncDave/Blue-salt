package com.blusalt.blusalt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "audit_event")
public class DroneAuditTrail extends BaseEntity{
    private String droneSerialNumber;
    private double batteryPercentage;
}
