package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.Occupant;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface OccupantRepository extends CrudRepository<Occupant, Long> {

//    @Transient
//    private long rent = room.getRent() / room.getNumberOfOccupants();

//    @Transient
//    private LocalDate dateRentDue = LocalDateTime.from(timeLastRentPaid.toInstant()).plusDays(30).toLocalDate();

    long getRent();

    LocalDate getDateRentDue();

}
