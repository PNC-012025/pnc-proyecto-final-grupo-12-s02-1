package org.carshare.carsharesv_webservice.configuration;

import org.carshare.carsharesv_webservice.domain.dto.response.CarResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.ReservationResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.carshare.carsharesv_webservice.domain.entity.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Car.class, CarResponseDTO.class)
                .addMapping(src -> src.getModel().getModel(), CarResponseDTO::setModel)
                .addMapping(src -> src.getBrand().getBrand(), CarResponseDTO::setBrand)
                .addMapping(src -> src.getYear().getYear(), CarResponseDTO::setYear)
                .addMapping(src -> src.getUser().getUsername(), CarResponseDTO::setUsername);

        modelMapper.typeMap(Reservation.class, ReservationResponseDTO.class)
                .addMapping(src -> src.getUser().getUsername(), ReservationResponseDTO::setReservingUsername)
                .addMapping(src -> src.getCar().getPlateNumber(), ReservationResponseDTO::setCarPlateNumber);

        return modelMapper;
    }
}
