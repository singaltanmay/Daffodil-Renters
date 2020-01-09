package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.Occupant;
import com.daffodil.renters.core.repo.OccupantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccupantService {

    OccupantRepository occupantRepository;

    @Autowired
    public OccupantService(OccupantRepository occupantRepository) {
        this.occupantRepository = occupantRepository;
    }

    public List<Occupant> getAllOccupants(long room_id) {
        return Occupant.listFrom(occupantRepository.findByRoomId(room_id));
    }

}
