package com.example.capstone2_adventureandoutdoorexperience.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdventureRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "adventureId cant be null")
    @Column(columnDefinition = "int not null")
    private Integer adventureId;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer userId;

    @NotNull(message ="score must be not null" )
    @Min(1)
    @Max(5)
    @Column(columnDefinition = "int not null")
    private Integer score;

    @NotEmpty(message = "review must be not empty")
    @Column(columnDefinition = "varchar(500)")
    private String review;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;
}
