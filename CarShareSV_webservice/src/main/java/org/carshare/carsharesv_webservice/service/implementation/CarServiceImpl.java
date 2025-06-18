package org.carshare.carsharesv_webservice.service.implementation;


import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateCarDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.CarResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.carshare.carsharesv_webservice.exception.ExistingCarException;
import org.carshare.carsharesv_webservice.exception.ResourceNotFoundException;
import org.carshare.carsharesv_webservice.repository.iCarRepository;
import org.carshare.carsharesv_webservice.service.iCarService;
import org.carshare.carsharesv_webservice.util.CurrentUserInfo;
import org.carshare.carsharesv_webservice.util.UsefullMethods;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Validated
public class CarServiceImpl implements iCarService {
    private final iCarRepository carRepository;
    private final ModelMapper modelMapper;
    private final UsefullMethods usefullMethods;

    @Override
    public CarResponseDTO saveCar(CreateCarDTO carDTO) {
        Car existingCar = carRepository.findCarByPlateNumber(carDTO.getPlateNumber()).orElse(null);
        CurrentUserInfo currentUser = usefullMethods.getUserInfo(null);

        if(existingCar != null) throw new ExistingCarException("Car already exists");

        Car newCar = new Car();
        newCar.setPlateNumber(carDTO.getPlateNumber());
        newCar.setModel(carDTO.getModel());
        newCar.setYear(carDTO.getYear());
        newCar.setDoors(carDTO.getDoors());
        newCar.setYear(carDTO.getYear());
        newCar.setBrand(carDTO.getBrand());
        newCar.setUser(currentUser.currentUser());
        newCar.setDailyPrice(carDTO.getDailyPrice());

        carRepository.save(newCar);

        return modelMapper.map(newCar, CarResponseDTO.class);
    }

    @Override
    public List<CarResponseDTO> getAllCars() {
        List<CarResponseDTO> cars = carRepository.findAll().stream().map(car -> modelMapper.map(car, CarResponseDTO.class)).toList();

        if(cars.isEmpty()) throw new ResourceNotFoundException("No Cars found");

        return cars;
    }

    @Override
    public List<CarResponseDTO> getAllCarsByModel(Integer modelId) {
        List<CarResponseDTO> cars = carRepository.findCarsByModelModelId(modelId).stream().map(car -> modelMapper.map(car, CarResponseDTO.class)).toList();

        if(cars.isEmpty()) throw new ResourceNotFoundException("No Cars found");

        return cars;
    }

    @Override
    public List<CarResponseDTO> getAllCarsByBrand(Integer brand) {
        List<CarResponseDTO> cars = carRepository.findCarsByBrandBrandId(brand).stream().map(car -> modelMapper.map(car, CarResponseDTO.class)).toList();
        if(cars.isEmpty()) throw new ResourceNotFoundException("No Cars found");
        return cars;
    }

    @Override
    public List<CarResponseDTO> getAllCarsByYear(Integer yearId) {
        List<CarResponseDTO> cars = carRepository.findCarsByYearId(yearId).stream().map(car -> modelMapper.map(car, CarResponseDTO.class)).toList();

        if(cars.isEmpty()) throw new ResourceNotFoundException("No Cars found");
        return cars;
    }

    @Override
    public CarResponseDTO getCarById(UUID carId) {
        Car car = carRepository.findCarByCarId(carId).orElse(null);
        if (car == null) throw new ResourceNotFoundException("Car not found");
        return modelMapper.map(car, CarResponseDTO.class);
    }

    @Override
    public CarResponseDTO updateCarByDailyPrice(UUID carId, float price) {
        Car car = carRepository.findCarByCarId(carId).orElse(null);
        if (car == null) throw new ResourceNotFoundException("Car not found");
        car.setDailyPrice(price);

        return modelMapper.map(car, CarResponseDTO.class);
    }

    @Override
    public void deleteCarById(UUID carId) {
        Car car = carRepository.findCarByCarId(carId).orElse(null);
        if (car == null) throw new ResourceNotFoundException("Car not found");
        carRepository.delete(car);
    }

    @Override
    public List<CarResponseDTO> getAllCarsByUserId(UUID userId) {
        List<Car> cars = carRepository.findCarsByUserUserId(userId);
        if(cars.isEmpty()) throw new ResourceNotFoundException("No Cars found");
        return cars.stream().map(car -> modelMapper.map(car, CarResponseDTO.class)).toList();
    }
}
