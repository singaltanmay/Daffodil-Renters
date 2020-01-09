package com.daffodil.renters.core.model.beans;

import java.util.Optional;

public class Filter {

    public class House {
        public Optional<Long> id;
        public Optional<String> address;
        public Optional<Double> latitude;
        public Optional<Double> longitude;
    }

}
