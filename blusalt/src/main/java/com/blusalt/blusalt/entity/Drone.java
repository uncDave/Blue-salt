package com.blusalt.blusalt.entity;

import com.blusalt.blusalt.enums.DroneModel;
import com.blusalt.blusalt.enums.DroneState;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "drone")
public class Drone extends BaseEntity{
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private DroneModel droneModel;
    @Enumerated(EnumType.STRING)
    private DroneState droneState;
    private double batteryPercentage;
    private double weight;
    private double totalWeightState = 0;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medication> medications;


}


