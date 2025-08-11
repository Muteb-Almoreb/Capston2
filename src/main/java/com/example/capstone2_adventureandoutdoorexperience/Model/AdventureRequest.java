package com.example.capstone2_adventureandoutdoorexperience.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdventureRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull(message = "User ID is required")
    @Column(columnDefinition = "int not null")
    private Integer userId;


    @NotNull(message = "Adventure ID is required")
    @Column(columnDefinition = "int not null")
    private Integer adventureId;


    @NotNull(message = "Gear ID is required")
    @Column(columnDefinition = "int not null")
    private Integer userGearId;


    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;


    @Pattern(
            regexp = "(?i)^(pending|approved|paid|released|rejected|attended)$",
            message = "Status must be one of: Pending, Approved, Paid, Released, Rejected, Attended"
    )
    @Column(columnDefinition = "varchar(20) not null")
    private String status;


}
