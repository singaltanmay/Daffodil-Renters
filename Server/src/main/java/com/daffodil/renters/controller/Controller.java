package com.daffodil.renters.controller;

import com.daffodil.renters.core.model.beans.House;
import com.daffodil.renters.core.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("daffodil/api")
@RestController
public class Controller {

    private final HouseService houseService;

    @Autowired
    public Controller(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping(value = "house")
    public ResponseEntity<?> getHouseById(@RequestParam("id") Optional<Long> id) {
        if (id.isPresent()) {
            Optional<House> house = houseService.getHouseById(id.get());
            return new ResponseEntity<>(house.orElse(null), HttpStatus.OK);
        } else return new ResponseEntity<>(houseService.getAllHouses(), HttpStatus.OK);
    }

    @PostMapping(value = "house")
    public List<House> getHousesFiltered(@RequestBody Optional<House.Filter> filter) {
        if (filter.isEmpty()) return houseService.getAllHouses();
        else return houseService.getHousesUsingFilteredQuery(filter.get());
    }

//    @PostMapping(value = "test")
//    public String getFilteredQueryString(@RequestBody House.Filter filter) {
//        return houseService.test(filter);
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/custom")
//    public String controllerMethod(@RequestParam Map<String, String> customQuery) {
//
//        System.out.println("customQuery = brand " + customQuery.containsKey("brand"));
//        System.out.println("customQuery = limit " + customQuery.containsKey("limit"));
//        System.out.println("customQuery = price " + customQuery.containsKey("price"));
//        System.out.println("customQuery = other " + customQuery.containsKey("other"));
//        System.out.println("customQuery = sort " + customQuery.containsKey("sort"));
//
//        return customQuery.toString();
//    }

    @PostMapping(path = "/house")
    public void insertHouse(@RequestBody House insertable) {
        houseService.insertHouse(insertable);
    }

    @PutMapping(path = "/house")
    public void replaceOrInsertHouse(@RequestParam("id") long id, @RequestBody House insertable) {
        houseService.updateHouseById(id, insertable);
    }

    @DeleteMapping(value = "house")
    public void deleteHouseById(@RequestParam("id") Optional<Long> id) {
        if (id.isPresent()) {
            houseService.deleteHouseById(id.get());
        } else houseService.deleteAllHouses();
    }

}
