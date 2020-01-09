package com.daffodil.renters.core.repo;

import com.daffodil.renters.core.model.beans.Filter;
import com.daffodil.renters.core.model.entities.HouseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceProvider;
import java.util.List;

public class FilteredQueries /*implements FilteredHouses*/ {

    //    private final EntityManagerFactory factory;
    private EntityManager manager;

    public FilteredQueries() {
//        this.factory = Persistence.createEntityManagerFactory("src/main/resources/application.properties");
    }

    //    @Override
    public List<HouseEntity> filteredHouses(Filter filter) {
//        trn().createQuery("SELECT * FROM house;");
//        cmt();
        return null;
    }

//    private EntityManager trn() {
//        manager = factory.createEntityManager();
//        manager.getTransaction().begin();
//        return manager;
//    }

    private void cmt() {
        manager.getTransaction().commit();
        manager.close();
    }

}
