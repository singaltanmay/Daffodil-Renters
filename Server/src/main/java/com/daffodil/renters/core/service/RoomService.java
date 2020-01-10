package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.model.beans.Occupant;
import com.daffodil.renters.core.model.beans.Room;
import com.daffodil.renters.core.model.entities.RoomEntity;
import com.daffodil.renters.core.repo.HouseRepository;
import com.daffodil.renters.core.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    HouseRepository houseRepository;
    RoomRepository roomRepository;

    OccupantService occupantService;

    @Autowired
    EntityManagerFactory factory;

    @Autowired
    public RoomService(HouseRepository houseRepository, RoomRepository roomRepository, OccupantService occupantService) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.occupantService = occupantService;
    }

    public List<Room> findRentBetween(long from, long to) {
        List<RoomEntity> allByRentBetween = roomRepository.findAllByRentBetween(from, to);
        return allByRentBetween.stream().map(entity -> new Room.Builder().build(entity)).collect(Collectors.toList());
    }

    public List<Room> getAllRooms(int page) {
        return foreignRelationsInjector(roomRepository.findAll());
    }

    public List<Room> getAllRoomsByHouseId(long houseId) {
        return foreignRelationsInjector(roomRepository.findRoomByHouseId(houseId));
    }

    public List<Room> getRoomsUsingFilteredQuery(Room.Filter filter) {
        return foreignRelationsInjector(new QueryUtils(factory).executeFilteredQuery(filter));
    }

    public Optional<Room> getRoomById(long roomId) {
        Optional<RoomEntity> room = roomRepository.findById(roomId);
        if (room.isPresent()) {
            List<Room> rooms = foreignRelationsInjector(List.of(room.get()));
            if (rooms.size() > 0) {
                return Optional.of(rooms.get(0));
            }
        }
        return Optional.empty();
    }

    @Transactional
    public void insertRoom(Room room, long houseId) {
        RoomEntity build = new RoomEntity.Builder().build(room);
        build.setHouse(houseRepository.findHouseById(houseId));
        roomRepository.save(build);
    }

    @Transactional
    public void updateRoomById(long roomId, Room room) {
        if (!roomRepository.existsById(roomId)) return;
        occupantService.deleteAllOccupantsOfRoom(roomId);

        List<Occupant> occupants = room.getOccupants();
        occupants.forEach(occupant -> {
            occupantService.insertOccupant(occupant, roomId);
        });

        roomRepository.updateRoomById(
                roomId,
                room.getCapacity(),
                room.getRent()
        );

    }

    @Transactional
    public void deleteAllRooms() {
        roomRepository.deleteAll();
    }

    @Transactional
    public void deleteAllRoomsOfHouse(long id) {
        roomRepository.deleteByHouseId(id);
    }

    @Transactional
    public void deleteRoomById(long roomId) {
        if (roomRepository.existsById(roomId)) roomRepository.deleteById(roomId);
    }

    private List<Room> foreignRelationsInjector(Iterable<RoomEntity> entities) {
        List<Room> rooms = Room.listFrom(entities);
        Iterator<RoomEntity> iterator = entities.iterator();
        for (Room room : rooms) {
            room.setOccupants(occupantService.getAllOccupants(room.getId()));
            room.setHouse(new House.Builder().build(iterator.next().getHouse()));
        }
        return rooms;
    }

    // Debug method used to get query string
    public String getFilteredQueryGeneratedString(Room.Filter filter) {
        return new QueryUtils(factory).createQueryFromFilter(filter);
    }

    private class QueryUtils {

        private EntityManagerFactory factory;
        private EntityManager manager;

        public QueryUtils(EntityManagerFactory factory) {
            this.factory = factory;
        }

        public String createQueryFromFilter(Room.Filter filter) {
            String COLUMN_ID = "r.id";
            String COLUMN_CAPACITY = "r.capacity";
            String COLUMN_RENT = "r.rent";

            StringBuilder builder = new StringBuilder("SELECT r FROM RoomEntity AS r");

            Optional<Long> id = filter.getId();
            Optional<Long> beds = filter.getBeds();
            Optional<Long> maxRent = filter.getMaxRent();
            Optional<Boolean> roommates = filter.getRoommates();

            boolean unfiltered = filter.isUnfiltered();

            boolean idPresent = id.isPresent();
            boolean bedsPresent = beds.isPresent();
            boolean maxRentPresent = maxRent.isPresent();
            boolean roommatesPresent = roommates.isPresent();


            boolean not_first_entry = false;

            // TODO fix beds = capacity - occupants
            // TODO eliminate rooms based on roommatesPresent by intersecting with count occupants with room_id
            // WHERE clause builder
            if (!unfiltered) {
                builder.append(" WHERE ");
                if (idPresent) {
                    builder.append(COLUMN_ID + " = " + id.get().toString());
                    not_first_entry = true;
                }

                if (bedsPresent) {
                    if (not_first_entry) {
                        builder.append(" AND ");
                    } else not_first_entry = true;
                    builder.append(COLUMN_CAPACITY + " >= " + beds.get().toString());
                }

                if (maxRentPresent) {
                    if (not_first_entry) {
                        builder.append(" AND ");
                    } else not_first_entry = true;
                    builder.append(COLUMN_RENT + " <= " + maxRent.get().toString());
                }

            }

            return builder.toString();
        }

        public List<RoomEntity> executeFilteredQuery(Room.Filter filter) {

//            String s = "SELECT r, e.room, COUNT(e.room) FROM RoomEntity AS r LEFT OUTER JOIN OccupantEntity AS e ON r.id WHERE r.capacity >= 5 GROUP BY e.room";

            /*, count(o.id)*/

            String s = "SELECT r FROM RoomEntity AS r JOIN r.occupants AS o GROUP BY r.id HAVING count(o.id) > 2";

            Query query = trn().createQuery(/*createQueryFromFilter(filter)*/s, RoomEntity.class);

            List<Object[]> resultList = query.getResultList();
            cmt();
            return new LinkedList<>();
        }

        private EntityManager trn() {
            manager = factory.createEntityManager();
            manager.getTransaction().begin();
            return manager;
        }

        private void cmt() {
            manager.getTransaction().commit();
            manager.close();
        }

    }

}
