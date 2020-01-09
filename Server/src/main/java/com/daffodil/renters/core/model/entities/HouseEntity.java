package com.daffodil.renters.core.model.entities;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Room;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "house")
public class HouseEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String address;

    //Coordinates
    @Getter
    @Setter
    private double latitude;
    @Getter
    @Setter
    private double longitude;

    // Children
    @Getter
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    List<RoomEntity> rooms;

    // To be run whenever rooms are inserted
    private void mapAllRooms() {
        List<RoomEntity> rooms = this.rooms;
        if (rooms != null) {
            for (RoomEntity r : rooms) {
                mapRoom(r);
            }
        }
    }

    // To be run whenever a new room is added
    private void mapRoom(RoomEntity roomEntity) {
        if (roomEntity != null) roomEntity.setHouse(HouseEntity.this);
    }

    public HouseEntity(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public HouseEntity(long id, String address, double latitude, double longitude, List<RoomEntity> rooms) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rooms = rooms;
        mapAllRooms();
    }

    protected HouseEntity() {
    }

    private HouseEntity(Builder builder) {
        this.id = builder.id;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.rooms = builder.rooms;
        mapAllRooms();
    }

    public static class Builder {
        private long id;
        private String address;
        private double latitude;
        private double longitude;
        List<RoomEntity> rooms;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setRooms(List<RoomEntity> rooms) {
            this.rooms = rooms;
            return this;
        }

        public HouseEntity build() {
            return new HouseEntity(this);
        }

        public HouseEntity build(House house) {
            if (house == null) return null;
            this.id = house.getId();
            this.address = house.getAddress();
            this.latitude = house.getLatitude();
            this.longitude = house.getLongitude();
            List<Room> houseRooms = house.getRooms();
            List<Room> rooms = houseRooms != null ? houseRooms : new LinkedList<>();
            List<RoomEntity> roomEntities = new LinkedList<>();
            for (Room room : rooms) {
                RoomEntity build = new RoomEntity.Builder().build(room);
                if (build != null) {
                    roomEntities.add(build);
                }
            }
            this.rooms = roomEntities;
            return new HouseEntity(this);
        }

    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
        mapAllRooms();
    }
}
