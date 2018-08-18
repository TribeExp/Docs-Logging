package com.basakdm.excartest.controller;

import com.basakdm.excartest.dao.CarRepositoryDAO;
import com.basakdm.excartest.dto.CarDTO;
import com.basakdm.excartest.entity.CarEntity;
import com.basakdm.excartest.entity.OrderEntity;
import com.basakdm.excartest.enum_ent.car_enum.*;
import com.basakdm.excartest.service.CarService;
import com.basakdm.excartest.utils.ConverterCars;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {

    @Autowired
    private CarService carServiceImpl;
    @Autowired
    private CarRepositoryDAO carRepositoryDAO;

    @GetMapping("/all")
    public Collection<CarDTO> findAll(){
        log.info("(/car/all), main page");
        return carServiceImpl.findAll().stream()
                .map(ConverterCars::mapCar)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{carId}")
    public ResponseEntity<CarDTO> findCarById(@PathVariable @Positive Long carId){
        log.info("(/car/{carId}), findCarById()");
        return carServiceImpl.findById(carId)
                .map(ConverterCars::mapCar)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /////////// изменить адрес на create /////////////
    @PostMapping("/createCar")
    public ResponseEntity<?> createCar(@RequestBody CarEntity carEntity){
        carEntity.setIsActivated(false);
        log.info("(/car/createCar), setIsActivated(false)");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConverterCars.mapCar(carServiceImpl.createCar(carEntity)));
    }

    @DeleteMapping("/delete/{carId}")
    public void delete(@PathVariable @Positive Long carId){
        log.info("(/car/delete/{carId}), delete()");
        carServiceImpl.delete(carId);
    }

    @PostMapping ("/update")
    public void update(@RequestBody CarEntity car){
        log.info("(/car/update), updating()");
        carServiceImpl.update(car);
    }


    @GetMapping("/isActivated/False")
    public Collection<CarEntity> findAllByIsActivatedFalse(){
        log.info("(/car/isActivated/False), findAllByIsActivatedFalse()");
        return carServiceImpl.findAllByIsActivatedFalse();
    }
    @GetMapping("/isActivated/True")
    public Collection<CarEntity> findAllByIsActivatedTrue(){
        log.info("(/car/isActivated/True), findAllByIsActivatedTrue()");
        return carServiceImpl.findAllByIsActivatedTrue();
    }


    @GetMapping(value = "/getPhoto/{carId}")
    public String getPhotoById(@PathVariable @Positive Long carId){
        String photoReference = carServiceImpl.findById(carId).get().getPhoto();
        log.info("(/car/getPhoto/{carId}), photoReference = " + photoReference);
        return photoReference;
    }
    @GetMapping(value = "/getLocation/{carId}")
    public String getLocationById(@PathVariable @Positive Long carId){
        log.info("(/car/getLocation/{carId}), getLocationById()");
        return carServiceImpl.findById(carId).get().getLocation();
    }


    @GetMapping(value = "/transmissionType/{transmission}")
    public Collection<CarEntity> getAllByTransmissionType(@PathVariable @Positive Transmission transmission){
        Collection<CarEntity> cars = carServiceImpl.findAllByTransmissionType(transmission);
        log.info("(/car/transmissionType/{transmission}), getAllByTransmissionType()");
        return cars;
    }
    @GetMapping(value = "/carBody/{carBody}")
    public Collection<CarEntity> getAllByCarBody(@PathVariable @Positive CarBody carBody){
        log.info("(/car/carBody/{carBody}), getAllByCarBody()");
        return carServiceImpl.findAllByCarBody(carBody);
    }
    @GetMapping(value = "/driveGear/{driveGear}")
    public Collection<CarEntity> getAllByDriveGear(@PathVariable @Positive DriveGear driveGear){
        log.info("(/car/driveGear/{driveGear}), getAllByDriveGear()");
        return carServiceImpl.findAllByDriveGear(driveGear);
    }
    @GetMapping(value = "/typeEngine/{typeEngine}")
    public Collection<CarEntity> getAllByEngineType(@PathVariable @Positive TypeEngine typeEngine){
        log.info("(/car/typeEngine/{typeEngine}), getAllByEngineType()");
        return carServiceImpl.findAllByEngineType(typeEngine);
    }
    @GetMapping(value = "/typeFuel/{typeFuel}")
    public Collection<CarEntity> getAllByTypeFuel(@PathVariable @Positive TypeFuel typeFuel){
        log.info("(/car/typeFuel/{typeFuel}), getAllByTypeFuel()");
        return carServiceImpl.findAllByFuelType(typeFuel);
    }


    @GetMapping(value = "/getPrice/{carId}")
    public Long getPriceById(@PathVariable @Positive Long carId){
        log.info("(/car/getPrice/{carId}), getPriceById()");
        return carServiceImpl.findById(carId).get().getPrice();
    }
    /*@PostMapping ("/setPrice/{carId}/{price}")
    public void setPrice(@RequestBody @PathVariable @Positive Long carId, @PathVariable @Positive Long price){

        Optional<CarEntity> optionalCarEntity = carServiceImpl.findById(carId);
        CarEntity carEntity = optionalCarEntity.get();
        carEntity.setPrice(price);

        carRepositoryDAO.saveAndFlush(carEntity);
    }*/

}
