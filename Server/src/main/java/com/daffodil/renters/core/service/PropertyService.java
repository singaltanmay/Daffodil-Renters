package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.Listing;
import com.daffodil.renters.core.model.beans.postables.PostableFactory;
import com.daffodil.renters.core.model.beans.postables.Property;
import com.daffodil.renters.core.model.entities.PropertyEntity;
import com.daffodil.renters.core.repo.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class PropertyService {

    PropertyRepository propertyRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Property getPropertyById(long property_id) {
        Optional<PropertyEntity> optional = propertyRepository.findById(property_id);
        return optional.map(propertyEntity -> new PostableFactory.PropertyBuilder().build(propertyEntity)).orElse(null);
    }

    public List<Property> getAllProperties() {
        return PostableFactory.PropertyBuilder.listFrom(propertyRepository.findAll());
    }

    private List<Property> foreignRelationsInjector(List<PropertyEntity> entities) {
        List<Property> properties = PostableFactory.PropertyBuilder.listFrom(entities);

        return properties;
    }

    public List<Property> runFilteredQuery(Listing.Filter filter) {
        List<PropertyEntity> propertyEntities = new QueryUtils(entityManagerFactory).runFilteredQuery(filter);

        propertyEntities.forEach(it -> {
            it.setRooms(null);
            it.setParkingSpots(null);
        });

        return foreignRelationsInjector(propertyEntities);
    }

    private static class QueryUtils {

        private EntityManagerFactory factory;
        private EntityManager manager;

        public QueryUtils(EntityManagerFactory factory) {
            this.factory = factory;
        }

        public static boolean isListingFilterable(Listing.Filter filter) {
            return filter.buildingId.isPresent() || filter.propertyId.isPresent();
        }

        public String createQueryString(Listing.Filter filter) {

            //Names of all the columns in the table
            String COLUMN_ID = "p.id";
            String COLUMN_BUILDING_ID = "p.building";

            boolean isFilterable = isListingFilterable(filter);
            AtomicBoolean notFirstParam = new AtomicBoolean(false);

            StringBuilder builder = new StringBuilder("SELECT p FROM PropertyEntity AS p");

            if (isFilterable) {
                builder.append(" WHERE ");

                filter.propertyId.ifPresent(it -> {
                    if (notFirstParam.get()) {
                        builder.append(" AND ");
                    } else notFirstParam.set(true);
                    builder.append(COLUMN_ID + " = " + it.toString());
                });

                filter.buildingId.ifPresent(it -> {
                    if (notFirstParam.get()) {
                        builder.append(" AND ");
                    } else notFirstParam.set(true);
                    builder.append(COLUMN_BUILDING_ID + " = " + it.toString());
                });

            }

            return builder.toString();
        }

        public List<PropertyEntity> runFilteredQuery(Listing.Filter filter) {

            String queryString = createQueryString(filter);
            System.out.println(this.getClass().getSimpleName() + " -> Query String : " + queryString);
            Query query = trn().createQuery(queryString, PropertyEntity.class);
            List<PropertyEntity> propertyEntities = query.getResultList();
            cmt();

            return propertyEntities;
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
