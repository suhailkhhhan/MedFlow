package com.medicalcenter.medicalcentersystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String manufacturer;
    private int quantity;
    private double price;
}
