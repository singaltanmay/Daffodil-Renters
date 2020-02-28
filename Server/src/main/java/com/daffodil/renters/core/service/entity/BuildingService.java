package com.daffodil.renters.core.service.entity;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.postables.Building;
import com.daffodil.renters.core.model.beans.postables.PostableFactory;
import com.daffodil.renters.core.model.entities.BuildingEntity;
import com.daffodil.renters.core.model.entities.EntityFactory;
import com.daffodil.renters.core.model.entities.PropertyEntity;
import com.daffodil.renters.core.repo.BuildingRepository;
import com.daffodil.renters.core.repo.PropertyRepository;
import com.daffodil.renters.core.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class BuildingService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private BuildingRepository buildingRepository;
    private PropertyRepository propertyRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository, PropertyRepository propertyRepository) {
        this.buildingRepository = buildingRepository;
        this.propertyRepository = propertyRepository;
    }

    public void insertBuilding(Building building) {
        BuildingEntity build = new EntityFactory.BuildingEntityBuilder().build(building);
        buildingRepository.save(build);
    }

    @Transactional
    public Building getBuildingByPropertyId(long property_id) {
        Optional<PropertyEntity> propertyEntity = propertyRepository.findById(property_id);
        if (propertyEntity.isPresent()) {
            PropertyEntity entity = propertyEntity.get();
            Building build = new PostableFactory.BuildingBuilder().build(entity.getBuilding());
            List<PropertyEntity> properties = new LinkedList<>();
            properties.add(entity);
            build.setProperties(Optional.of(PostableFactory.PropertyBuilder.listFrom(properties)));
            return build;
        }
        return null;
    }

    private List<Building> foreignRelationsInjector(List<BuildingEntity> entities) {

        List<Building> buildings = PostableFactory.BuildingBuilder.listFrom(entities);
//        if (buildings != null) {
//            buildings.forEach(building -> {
//                List<PropertyEntity> propertyEntities = propertyRepository.findPropertyByBuildingId(building.getId().get());
//                building.setProperties(Optional.of(PostableFactory.PropertyBuilder.listFrom(propertyEntities)));
//            });
//        }
        return buildings;
    }

    public boolean isListingFilterable(Listing.Filter filter) {
        return QueryUtils.isListingFilterable(filter);
    }

    public List<Building> runFilteredQuery(Listing.Filter filter) {

        List<BuildingEntity> buildingEntities = new QueryUtils(entityManagerFactory).runFilteredQuery(filter);

        /**
         * They have no information about their children anyway.
         * Accessing entity.child results in exception thus nullifying them
         */
        buildingEntities.forEach(it -> {
            it.setProperties(null);
            it.setParkingSpots(null);
        });

        return foreignRelationsInjector(buildingEntities);
    }

    private static class QueryUtils {

        private EntityManagerFactory factory;
        private EntityManager manager;

        public QueryUtils(EntityManagerFactory factory) {
            this.factory = factory;
        }

        public static boolean isListingFilterable(Listing.Filter filter) {
            return filter.latitude.isPresent() || filter.longitude.isPresent();
        }

        public String createQueryString(Listing.Filter filter) {

            //Names of all the columns in the table
            String COLUMN_LATITUDE = "b.latitude";
            String COLUMN_LONGITUDE = "b.longitude";

            boolean isFilterable = isListingFilterable(filter);
            AtomicBoolean notFirstParam = new AtomicBoolean(false);

            StringBuilder builder = new StringBuilder("SELECT b FROM BuildingEntity AS b");

            if (isFilterable) {
                builder.append(" WHERE ");

                if (filter.latitude.isPresent() && filter.longitude.isPresent()) {

                    Double lat = filter.latitude.get();
                    Double lon = filter.longitude.get();

                    filter.rangeKm.ifPresentOrElse(rng -> {

                                GeoLocationService location = GeoLocationService.fromDegrees(lat, lon);
                                GeoLocationService[] boundingCoordinates = location.boundingCoordinates(rng, null);
                                Double minLat = boundingCoordinates[0].getLatitudeInDegrees();
                                Double minLon = boundingCoordinates[0].getLongitudeInDegrees();
                                Double maxLat = boundingCoordinates[1].getLatitudeInDegrees();
                                Double maxLon = boundingCoordinates[1].getLongitudeInDegrees();

                                if (notFirstParam.get()) {
                                    builder.append(" AND ");
                                } else notFirstParam.set(true);

                                builder.append(COLUMN_LATITUDE + " >= " + minLat.toString());
                                builder.append(" AND ");
                                builder.append(COLUMN_LATITUDE + " <= " + maxLat.toString());
                                builder.append(" AND ");
                                builder.append(COLUMN_LONGITUDE + " >= " + minLon.toString());
                                builder.append(" AND ");
                                builder.append(COLUMN_LONGITUDE + " <= " + maxLon.toString());

                            },
                            /* If rangeKm is not present */
                            () -> {
                                if (notFirstParam.get()) {
                                    builder.append(" AND ");
                                } else notFirstParam.set(true);

                                builder.append(COLUMN_LATITUDE + " = " + lat.toString());
                                builder.append(" AND ");
                                builder.append(COLUMN_LONGITUDE + " = " + lon.toString());
                            });


                }
            }

            return builder.toString();
        }

        public List<BuildingEntity> runFilteredQuery(Listing.Filter filter) {

            String queryString = createQueryString(filter);
            System.out.println(this.getClass().getSimpleName() + " -> Query String : " + queryString);
            Query query = trn().createQuery(queryString, BuildingEntity.class);
            List<BuildingEntity> buildings = query.getResultList();
            cmt();

            return buildings;
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
