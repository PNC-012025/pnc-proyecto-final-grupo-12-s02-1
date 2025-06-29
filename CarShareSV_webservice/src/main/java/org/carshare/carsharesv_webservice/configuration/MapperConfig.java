package org.carshare.carsharesv_webservice.configuration;

import org.carshare.carsharesv_webservice.domain.dto.response.CarResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.ReservationResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
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

        modelMapper.typeMap(User.class, UserResponseDTO.class)
                .addMappings(mapper -> mapper.using(userRolesToListConverter()).map(src -> src, UserResponseDTO::setRoles));

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

    private Converter<User, List<String>> userRolesToListConverter() {
        return ctx -> {
            Set<Role> roles = ctx.getSource().getRoles();
            if (roles == null) return Collections.emptyList();
            return roles.stream()
                    .map(role -> role.getRoleName())
                    .collect(Collectors.toList());
        };
    }
}
