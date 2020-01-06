package core.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "occupants")
public class Occupant {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "uuid", columnDefinition = "BINARY(16)")
    private UUID uuid;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    private LocalDate dateMovedIn = LocalDate.now();

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate timeLastRentPaid = dateMovedIn;

    // Parents
    private Room room;
    private House house;

    @Transient
    private long rent = room.getRent() / room.getNumberOfOccupants();

    @Transient
    private LocalDate dateRentDue = timeLastRentPaid.plusDays(30);

    public Occupant(Room room) {
        this.room = room;
        this.house = room.getHouse();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateMovedIn() {
        return dateMovedIn;
    }

    public LocalDate getTimeLastRentPaid() {
        return timeLastRentPaid;
    }

    public Room getRoom() {
        return room;
    }

    public House getHouse() {
        return house;
    }

    public long getRent() {
        return rent;
    }

    public LocalDate getDateRentDue() {
        return dateRentDue;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateMovedIn(LocalDate dateMovedIn) {
        this.dateMovedIn = dateMovedIn;
    }

    public void setTimeLastRentPaid(LocalDate timeLastRentPaid) {
        this.timeLastRentPaid = timeLastRentPaid;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
