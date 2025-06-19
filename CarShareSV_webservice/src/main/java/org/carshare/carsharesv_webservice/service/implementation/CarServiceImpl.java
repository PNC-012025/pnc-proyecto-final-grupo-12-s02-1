package org.carshare.carsharesv_webservice.service.implementation;


import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateCarDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.CarResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.Brand;
import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.carshare.carsharesv_webservice.domain.entity.Model;
import org.carshare.carsharesv_webservice.domain.entity.Year;
import org.carshare.carsharesv_webservice.exception.*;
import org.carshare.carsharesv_webservice.repository.iBrandRepository;
import org.carshare.carsharesv_webservice.repository.iCarRepository;
import org.carshare.carsharesv_webservice.repository.iModelRepository;
import org.carshare.carsharesv_webservice.repository.iYearRepository;
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
    private final iModelRepository modelRepository;
    private final iBrandRepository brandRepository;
    private final iYearRepository yearRepository;
    private final ModelMapper modelMapper;
    private final UsefullMethods usefullMethods;

    @Override
    public CarResponseDTO saveCar(CreateCarDTO carDTO) {
        Car existingCar = carRepository.findCarByPlateNumber(carDTO.getPlateNumber()).orElse(null);
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(null);

        if(existingCar != null) throw new ExistingCarException("Car already exists");

        Brand brand = brandRepository.findBrandByBrand(carDTO.getBrand())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));

        Model model = modelRepository.findModelByModel(carDTO.getModel())
                .orElseThrow(() -> new ModelNotFoundException("Model not found"));

        Year year = yearRepository.findYearByYear(carDTO.getYear())
                .orElseThrow(() -> new YearNotFoundException("Year not found"));

        Car newCar = new Car();

        if (!model.getBrand().getBrandId().equals(brand.getBrandId())) throw new NotValidModelForBrandException("The model does not belong to the selected brand");

        newCar.setPlateNumber(carDTO.getPlateNumber());
        newCar.setDescription(carDTO.getDescription());
        newCar.setModel(model);
        newCar.setYear(year);
        newCar.setDoors(carDTO.getDoors());
        newCar.setBrand(brand);
        newCar.setUser(userInfo.currentUser());
        newCar.setDailyPrice(carDTO.getDailyPrice());
        newCar.setDescription(carDTO.getDescription());
        newCar.setVisible(true);

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
