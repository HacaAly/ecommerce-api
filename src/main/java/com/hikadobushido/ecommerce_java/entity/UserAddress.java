package com.hikadobushido.ecommerce_java.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_addresses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {

    @Id
    @Column(name = "user_address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAddressId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "address_name", nullable = false)
    private String addressName;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "created_at")
    private LocalDateTime updatedAt;
}
