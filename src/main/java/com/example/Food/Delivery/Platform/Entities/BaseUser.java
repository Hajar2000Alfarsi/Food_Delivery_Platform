package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUser extends Base {
    private String firstName;
    private String secondName;

    @Column(unique = true)
    private String email;

    private String phoneNumber;
    private String passwordHash;
}
