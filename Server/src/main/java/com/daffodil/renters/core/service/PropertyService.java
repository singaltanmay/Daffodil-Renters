package com.daffodil.renters.core.service;

import com.daffodil.renters.core.model.beans.postables.PostableFactory;
import com.daffodil.renters.core.model.beans.postables.Property;
import com.daffodil.renters.core.model.entities.PropertyEntity;
import com.daffodil.renters.core.repo.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    PropertyRepository propertyRepository;

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
}
