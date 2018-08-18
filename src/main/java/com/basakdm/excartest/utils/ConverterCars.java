package com.basakdm.excartest.utils;

import com.basakdm.excartest.dto.CarDTO;
import com.basakdm.excartest.entity.CarEntity;
import com.basakdm.excartest.enum_ent.car_enum.*;
import com.basakdm.excartest.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
public class ConverterCars {

    @Autowired
    private static CarService carService;

    public static CarDTO mapCar(CarEntity carEntity) {

        log.info("carEntity = " + carEntity + ", mapCar()");
        CarDTO carDto = new CarDTO();

        carDto.setId(carEntity.getId());
        carDto.setBrand(carEntity.getBrand());
        carDto.setModel(carEntity.getModel());
        carDto.setYear(carEntity.getYear());
        carDto.setStateNum(carEntity.getStateNum());
        carDto.setMileage(carEntity.getMileage());
        carDto.setSeats(carEntity.getSeats());
        carDto.setLocation(carEntity.getLocation());
        carDto.setPhoto(carEntity.getPhoto());
        carDto.setTransmissionType(carEntity.getTransmissionType());
        carDto.setCarBody(carEntity.getCarBody());
        carDto.setDriveGear(carEntity.getDriveGear());
        carDto.setEngineType(carEntity.getEngineType());
        carDto.setFuelType(carEntity.getFuelType());
        carDto.setFuelConsumption(carEntity.getFuelConsumption());
        carDto.setConditionInformation(carEntity.getConditionInformation());
        carDto.setInsurance(carEntity.getInsurance());
        carDto.setPriceLease(carEntity.getPriceLease());
        carDto.setAdText(carEntity.getAdText());
        carDto.setIsActivated(carEntity.getIsActivated());
        carDto.setPrice(carEntity.getPrice());
       // carDto.setTypeUser(carEntity.getTypeUser());
        carDto.setId_owner(carEntity.getId_owner());
        carDto.setOptionalAccessories(carEntity.getOptionalAccessories());

        log.info("carDto = " + carDto + ", mapCar()");
        return carDto;

    }

    public static CarEntity mapCar(CarDTO carDto) {
        CarEntity carEntity;
        Optional<CarEntity> carEntityOptional = carService.findById(carDto.getId());

        if (carEntityOptional.isPresent()) carEntity = carEntityOptional.get();
        else throw new RuntimeException("Incorrect ID of car");

        log.info("CarDTO = " + carDto + ", mapCar()");
        carEntity.setId(carDto.getId());
        carEntity.setBrand(carDto.getBrand());
        carEntity.setModel(carDto.getModel());
        carEntity.setYear(carDto.getYear());
        carEntity.setStateNum(carDto.getStateNum());
        carEntity.setMileage(carDto.getMileage());
        carEntity.setSeats(carDto.getSeats());
        carEntity.setLocation(carDto.getLocation());
        carEntity.setPhoto(carDto.getPhoto());
        carEntity.setTransmissionType(carDto.getTransmissionType());
        carEntity.setCarBody(carDto.getCarBody());
        carEntity.setDriveGear(carDto.getDriveGear());
        carEntity.setEngineType(carDto.getEngineType());
        carEntity.setFuelType(carDto.getFuelType());
        carEntity.setFuelConsumption(carDto.getFuelConsumption());
        carEntity.setConditionInformation(carDto.getConditionInformation());
        carEntity.setInsurance(carDto.getInsurance());
        carEntity.setPriceLease(carDto.getPriceLease());
        carEntity.setAdText(carDto.getAdText());
        carEntity.setIsActivated(carDto.getIsActivated());
        carEntity.setPrice(carDto.getPrice());
        //carEntity.setTypeUser(carDto.getTypeUser());
        carEntity.setId_owner(carDto.getId_owner());
        carEntity.setOptionalAccessories(carDto.getOptionalAccessories());
        log.info("carEntity = " + carEntity + ", mapCar()");

        return carEntity;
    }
}
