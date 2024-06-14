package com.example.hotel_cms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String headline;
    private String city;
    private String address;
    private Double centerDistance;
    private Integer rating;
    private Integer reviewCount;

    @OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL)
    private List<Room> rooms;
}
