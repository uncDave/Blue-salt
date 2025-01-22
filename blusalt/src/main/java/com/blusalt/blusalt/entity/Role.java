package com.blusalt.blusalt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "role")
public class Role extends BaseEntity {

    private String name;
}
