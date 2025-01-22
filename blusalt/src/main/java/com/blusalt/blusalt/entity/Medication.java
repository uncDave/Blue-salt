package com.blusalt.blusalt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "medication")
public class Medication extends BaseEntity{
    private String name;
    private double weight;
    private String code;
    private String logoUrl;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinColumn(name = "drone_id", nullable = false)
//    private Drone drone;
}
