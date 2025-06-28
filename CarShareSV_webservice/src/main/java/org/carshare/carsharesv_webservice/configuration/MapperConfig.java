package org.carshare.carsharesv_webservice.configuration;

import org.carshare.carsharesv_webservice.domain.dto.response.CarResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.ReservationResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.carshare.carsharesv_webservice.domain.entity.Image;
import org.carshare.carsharesv_webservice.domain.entity.Reservation;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Car -> CarResponseDTO
        modelMapper.typeMap(Car.class, CarResponseDTO.class)
                .addMapping(src -> src.getModel().getModel(), CarResponseDTO::setModel)
                .addMapping(src -> src.getBrand().getBrand(), CarResponseDTO::setBrand)
                .addMapping(src -> src.getYear().getYear(), CarResponseDTO::setYear)
                .addMapping(src -> src.getUser().getUsername(), CarResponseDTO::setUsername)
                .addMapping(src -> src.getUser().getPhoneNumber(), CarResponseDTO::setPhoneNumber)
                .addMappings(mapper -> mapper.using(carImagesToUrlListConverter())
                        .map(src -> src, CarResponseDTO::setImages));

        // Reservation -> ReservationResponseDTO
        modelMapper.typeMap(Reservation.class, ReservationResponseDTO.class)
                .addMapping(src -> src.getUser().getUsername(), ReservationResponseDTO::setReservingUsername)
                .addMapping(src -> src.getCar(), ReservationResponseDTO::setReservedCar);

        return modelMapper;
    }

    private Converter<Car, List<String>> carImagesToUrlListConverter() {
        return ctx -> {
            List<Image> images = ctx.getSource().getCarImages();
            if (images == null) return Collections.emptyList();
            return images.stream()
                    .map(Image::getUrl)
                    .collect(Collectors.toList());
        };
    }
}
