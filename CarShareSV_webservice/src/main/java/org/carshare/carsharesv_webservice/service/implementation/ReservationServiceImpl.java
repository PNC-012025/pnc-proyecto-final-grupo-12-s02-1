package org.carshare.carsharesv_webservice.service.implementation;


import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateReservationDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.ReservationResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.carshare.carsharesv_webservice.domain.entity.Reservation;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.exception.*;
import org.carshare.carsharesv_webservice.repository.iCarRepository;
import org.carshare.carsharesv_webservice.repository.iReservationRepository;
import org.carshare.carsharesv_webservice.repository.iUserRepository;
import org.carshare.carsharesv_webservice.service.iReservationService;
import org.carshare.carsharesv_webservice.util.Constants;
import org.carshare.carsharesv_webservice.util.CurrentUserInfo;
import org.carshare.carsharesv_webservice.util.UsefullMethods;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements iReservationService {
    private final iReservationRepository reservationRepository;
    private final iCarRepository carRepository;
    private final iUserRepository userRepository;
    private final UsefullMethods usefullMethods;
    private final ModelMapper modelMapper;


    @Override
    public ReservationResponseDTO createReservation(CreateReservationDTO reservationDTO) {
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(null);

        User currentUser = userRepository.findOneByUserId(userInfo.currentUser().getUserId());
        Car car = carRepository.findCarByPlateNumber(reservationDTO.getCarPlateNumber()).orElse(null);

        if(currentUser == null) throw new ResourceNotFoundException("User not found");
        if(car == null) throw new ResourceNotFoundException("Car not found");

        if (reservationDTO.getStartDate().isBefore(LocalDate.now()))
            throw new PastDateException("startDate cannot be before today's date");

        if (reservationDTO.getEndDate().isBefore(LocalDate.now()))
            throw new PastDateException("endDate cannot be before today's date");

        // Validate that endDate is not before startDate
        if (reservationDTO.getEndDate().isBefore(reservationDTO.getStartDate()))
            throw new InvalidDateRangeException("endDate cannot be before startDate");

        boolean hasDateOverlapping = reservationRepository.existsOverlappingReservation(car.getCarId(), reservationDTO.getStartDate(), reservationDTO.getEndDate());

        if(hasDateOverlapping)
            throw new NotAvailableDatesException("This car is already reserved for the given dates");

        long numberOfDays = ChronoUnit.DAYS.between(
                reservationDTO.getStartDate(),
                reservationDTO.getEndDate()
        ) + 1;

        float totalPrice = numberOfDays * car.getDailyPrice();

        Reservation newReservation = new Reservation();

        newReservation.setStartDate(reservationDTO.getStartDate());
        newReservation.setEndDate(reservationDTO.getEndDate());
        newReservation.setAddress(reservationDTO.getAddress());
        newReservation.setTotal(totalPrice);
        newReservation.setStatus(Constants.ACTIVE);
        newReservation.setUser(currentUser);
        newReservation.setCar(car);

        reservationRepository.save(newReservation);

        return modelMapper.map(newReservation, ReservationResponseDTO.class);
    }

    @Override
    public List<ReservationResponseDTO> getAllCarReservations(UUID carId) {
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(null);
        User currentUser = userRepository.findOneByUserId(userInfo.currentUser().getUserId());
        Car car = carRepository.findCarByCarId(carId).orElse(null);
        List<ReservationResponseDTO> carReservations;

        if(currentUser == null) throw new ResourceNotFoundException("User not found");
        if(car == null) throw new ResourceNotFoundException("Car not found");

        if(userInfo.roles().contains(Constants.SYSADMIN) || userInfo.roles().contains(Constants.ADMIN) || car.getUser().getUserId().equals(currentUser.getUserId())) {
            carReservations = reservationRepository.findAllByCarId(carId).stream().map(reservation -> modelMapper.map(reservation, ReservationResponseDTO.class)).toList();
        } else throw new NotAllowedOperationException("You don't have permissions to view this car's reservations. You can only view your own car's reservations");

        return carReservations;
    }


}
