package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="managers")
@Entity
@Data
@Builder
@NoArgsConstructor
public class ManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int managerId;


}
