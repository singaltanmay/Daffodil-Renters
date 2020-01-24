//package com.daffodil.renters.core.service;
//
//import com.daffodil.renters.core.model.beans.postables.Occupant;
//import com.daffodil.renters.core.model.beans.postables.Room;
//import com.daffodil.renters.core.model.entities.EntityFactory;
//import com.daffodil.renters.core.model.entities.RoomEntity;
//import com.daffodil.renters.core.repo.HouseRepository;
//import com.daffodil.renters.core.repo.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Query;
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class RoomService {
//
//    HouseRepository houseRepository;
//    RoomRepository roomRepository;
//
//    OccupantService occupantService;
//
//    @Autowired
//    EntityManagerFactory factory;
//
//    @Autowired
//    public RoomService(HouseRepository houseRepository, RoomRepository roomRepository, OccupantService occupantService) {
//        this.houseRepository = houseRepository;
//        this.roomRepository = roomRepository;
//        this.occupantService = occupantService;
//    }
//
//    public List<Room> findRentBetween(long from, long to) {
//        List<RoomEntity> allByRentBetween = roomRepository.findAllByRentBetween(from, to);
//        return allByRentBetween.stream().map(entity -> new Room.Builder().build(entity)).collect(Collectors.toList());
//    }
//
//    public List<Room> getAllRooms(int page) {
//        return foreignRelationsInjector(roomRepository.findAll());
//    }
//
//    public List<Room> getAllRoomsByHouseId(long houseId) {
//        return foreignRelationsInjector(roomRepository.findRoomByHouseId(houseId));
//    }
//
//    public List<Room> getRoomsUsingFilteredQuery(Room.Filter filter) {
//        return foreignRelationsInjector(new QueryUtils(factory).executeFilteredQuery(filter));
//    }
//
//    public Optional<Room> getRoomById(long roomId) {
//        Optional<RoomEntity> room = roomRepository.findById(roomId);
//        if (room.isPresent()) {
//            List<Room> rooms = foreignRelationsInjector(List.of(room.get()));
//            if (rooms.size() > 0) {
//                return Optional.of(rooms.get(0));
//            }
//        }
//        return Optional.empty();
//    }
//
//    @Transactional
//    public void insertRoom(Room room, long houseId) {
//        RoomEntity build = new EntityFactory.RoomEntityBuilder().build(room);
//        build.setProperty(houseRepository.findHouseById(houseId));
//        roomRepository.save(build);
//    }
//
//    @Transactional
//    public void updateRoomById(long roomId, Room room) {
//        if (!roomRepository.existsById(roomId)) return;
//        occupantService.deleteAllOccupantsOfRoom(roomId);
//
//        List<Occupant> occupants = room.getOccupants();
//        occupants.forEach(occupant -> {
//            occupantService.insertOccupant(occupant, roomId);
//        });
//
//        roomRepository.updateRoomById(
//                roomId,
//                room.getCapacity(),
//                room.getRent()
//        );
//
//    }
//
//    @Transactional
//    public void deleteAllRooms() {
//        roomRepository.deleteAll();
//    }
//
//    @Transactional
//    public void deleteAllRoomsOfHouse(long id) {
//        roomRepository.deleteByHouseId(id);
//    }
//
//    @Transactional
//    public void deleteRoomById(long roomId) {
//        if (roomRepository.existsById(roomId)) roomRepository.deleteById(roomId);
//    }
//
//    private List<Room> foreignRelationsInjector(Iterable<RoomEntity> entities) {
//        List<Room> rooms = Room.listFrom(entities);
//        rooms.forEach(room -> room.setOccupants(occupantService.getAllOccupantsByRoomId(room.getId())));
//        return rooms;
//    }
//
//    // Debug method used to get query string
//    public String getFilteredQueryGeneratedString(Room.Filter filter) {
//        return new QueryUtils(factory).createQueryFromFilter(filter);
//    }
//
//    private class QueryUtils {
//
//        private EntityManagerFactory factory;
//        private EntityManager manager;
//
//        public QueryUtils(EntityManagerFactory factory) {
//            this.factory = factory;
//        }
//
//
//        //TODO Fix Query Syntax
//        public String createQueryFromFilter(Room.Filter filter) {
//            Optional<Long> id = filter.getId();
//            Optional<Long> beds = filter.getBeds();
//            Optional<Long> maxRent = filter.getMaxRent();
//            Optional<Boolean> roommates = filter.getRoommates();
//
//            boolean containsWhereables = filter.containsWhereables();
//            boolean containsJoinables = filter.containsJoinables();
//
//            boolean idPresent = id.isPresent();
//            boolean bedsPresent = beds.isPresent();
//            boolean maxRentPresent = maxRent.isPresent();
//            boolean roommatesPresent = roommates.isPresent();
//
//            String COLUMN_ID = "r.id";
//            String COLUMN_CAPACITY = "r.capacity";
//            String COLUMN_RENT = "r.rent";
//
//            StringBuilder builder = new StringBuilder("SELECT r FROM RoomEntity AS r");
//            if (containsJoinables) {
//                builder.append(" JOIN r.occupants AS o");
//            }
//
//            boolean not_first_entry = false;
//
//            // WHERE clause builder
//            if (containsWhereables) {
//                builder.append(" WHERE ");
//
//                if (idPresent) {
//                    builder.append(COLUMN_ID + " = " + id.get().toString());
//                    not_first_entry = true;
//                }
//
//                if (maxRentPresent) {
//                    if (not_first_entry) {
//                        builder.append(" AND ");
//                    } else not_first_entry = true;
//                    builder.append(COLUMN_RENT + " <= " + maxRent.get().toString());
//                }
//
//            }
//
//            // GROUP BY and HAVING clause builder
//            if (containsJoinables) {
//                // Rooms without roommates
//                if (roommatesPresent && !roommates.get()) {
//
//                    if (not_first_entry) {
//                        builder.append(" AND ");
//                    } else {
//                        builder.append(" WHERE ");
//                        not_first_entry = true;
//                    }
//
//                    if (bedsPresent) {
//                        builder.append(COLUMN_CAPACITY + " >= " + beds.get().toString());
//                    }
//                    builder.append(" GROUP BY r.id HAVING ");
//                    builder.append("count(o.id) = 0");
//                } else if (bedsPresent) {
//                    builder.append(" GROUP BY r.id HAVING ");
//                    builder.append("(" + COLUMN_CAPACITY + " - count(o.id) >= " + beds.get().toString() + ")");
//                }
//            }
//
//            return builder.toString();
//        }
//
//        public List<RoomEntity> executeFilteredQuery(Room.Filter filter) {
//
//            Query query = createFactoryAndBeginTransaction().createQuery(createQueryFromFilter(filter), RoomEntity.class);
//
//            List<RoomEntity> resultList = query.getResultList();
//            commitTransactionAndCloseManager();
//            return resultList;
//        }
//
//        private EntityManager createFactoryAndBeginTransaction() {
//            manager = factory.createEntityManager();
//            manager.getTransaction().begin();
//            return manager;
//        }
//
//        private void commitTransactionAndCloseManager() {
//            manager.getTransaction().commit();
//            manager.close();
//        }
//
//    }
//
//}
