package com.medicalcenter.medicalcentersystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    // --- THIS IS THE FIX ---
    // Add length = 20 to make the database column larger.
    @Column(name = "role", length = 20)
    private Set<Role> roles;

    @OneToOne(mappedBy = "user")
    private Patient patient;
}