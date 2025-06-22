package org.carshare.carsharesv_webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarShareSvWebserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarShareSvWebserviceApplication.class, args);
    }

}
