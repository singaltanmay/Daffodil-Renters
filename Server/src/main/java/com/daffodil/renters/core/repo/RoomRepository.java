package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Byte> {
}
