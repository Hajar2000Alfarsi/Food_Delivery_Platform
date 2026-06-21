package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isActive;

    @PrePersist
    public void prePersist(){
        createdDate = LocalDateTime.now();
        isActive = true;
    }

    @PreUpdate
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}
