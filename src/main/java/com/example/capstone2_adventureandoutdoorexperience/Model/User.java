package com.example.capstone2_adventureandoutdoorexperience.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name must be not empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String name ;

    @Email
    @NotEmpty(message = "Email must be not empty")
    @Column(columnDefinition = "varchar(200) not null unique")
    private String email;

    @NotNull(message = "Date of birth must be not null")
    @Column(columnDefinition = "date not null")
    @Past(message = "The date of birth must be in the past")
    private LocalDate dateOfBirth;


    @Column(columnDefinition = "boolean not null default false")
    private Boolean hasOwnEquipment = false;


    @Column(columnDefinition = "varchar(10) not null")
    @NotEmpty(message = "Gender is required")
    @Pattern(regexp = "^(?i)(male|female)$" , message = "Gender must be either 'male' or 'female'")
    private String gender;

    @NotNull
    @Column(columnDefinition = "double not null default 0")
    private Double balance = 0.0;






}
