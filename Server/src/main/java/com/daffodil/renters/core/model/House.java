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

    public House(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public House(long id, String address, double latitude, double longitude, List<Room> rooms) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rooms = rooms;
    }

    protected House() {
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
