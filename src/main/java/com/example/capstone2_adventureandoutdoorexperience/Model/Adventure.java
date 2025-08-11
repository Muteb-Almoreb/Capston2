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
public class Adventure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title must be not empty")
    @Size(min = 4 , max = 100 , message = "Title must be more than 3 character and less than 100 character")
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @NotEmpty(message = "Description must not be empty")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;

    @NotEmpty(message = "Location must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String location;

    @NotNull(message = "Total needed is required")
    @Column(columnDefinition = "int not null")
    private Integer totalNeeded;

    @NotNull(message = "Cost per person is required")
    @Column(columnDefinition = "double not null")
    private Double costPerPerson;


    @NotNull(message = "Date is required")
    @Column(columnDefinition = "date not null")
    @FutureOrPresent(message = "Date must be today or in the future")
    private LocalDate date;

    @NotNull(message = "Require approval flag is required")
    @Column(columnDefinition = "boolean not null")
    private Boolean requireApproval;


    @Column(columnDefinition = "varchar(500)")
    private String adventureLocationUrl;

    @Column(columnDefinition = "varchar(500)")
    private String meetingPointUrl;

    @NotNull(message = "Creator ID is required")
    @Column(columnDefinition = "int not null")
    private Integer creatorId;

    @Column(columnDefinition = "varchar(10)")
    @NotEmpty(message = "Gender restriction cannot be empty")
    @Pattern(regexp = "^(?i)(male|female|any)$", message = "Gender restriction must be 'male', 'female', or 'any'")
    private String genderRestriction;


    @Column(columnDefinition = "varchar(200)")
    private String requiredGear;



    @NotEmpty(message = "activityType is required")
    @Column(columnDefinition = "varchar(30) not null")
    @Pattern(regexp = "^(?i)(RACING|HUNTING|CAMPING|HIKING|DRIFTING|SPORT|OTHER)$",
            message = "activityType must be one of: RACING, HUNTING, CAMPING,DRIFTING ,SPORT, HIKING, OTHER")
    private String activityType;


    @Column(columnDefinition = "boolean not null default false")
    private Boolean isLocationApproved = false;

    @Column(columnDefinition = "boolean not null default false")
    private Boolean hasGovernmentPermit = false;


    @Column
    private Integer minAge;


    @NotNull( message = "Total Price cant be null")
    @Column(columnDefinition = "double not null default 0")
    private Double totalPrice = 0.0;


}
