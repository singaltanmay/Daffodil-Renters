package com.daffodil.renters.controller;

import com.daffodil.renters.core.model.House;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("daffodil/api")
@RestController
public class Controller {

    @GetMapping(path = "/houses")
    public List<House> getAllHouses() {
        return new ArrayList<>();
    }

}
