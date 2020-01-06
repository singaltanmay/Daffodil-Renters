package com.daffodil.renters.core.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String address;

    //Coordinates
    private double latitude;
    private double longitude;

    // Children
    @OneToMany(mappedBy = "house")
    List<Room> rooms;

}
