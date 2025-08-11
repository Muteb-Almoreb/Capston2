package com.example.capstone2_adventureandoutdoorexperience.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserGear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "User ID is required")
    @Column(columnDefinition = "int not null")
    private Integer userId;

    @NotEmpty(message = "Gear name must not be empty")
    @Column(columnDefinition = "varchar(150) not null")
    private String gearName;
}
