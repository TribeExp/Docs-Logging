package com.basakdm.excartest.controller;

import com.basakdm.excartest.dao.CarRepositoryDAO;
import com.basakdm.excartest.dto.CarDTO;
import com.basakdm.excartest.entity.CarEntity;
import com.basakdm.excartest.entity.OrderEntity;
import com.basakdm.excartest.enum_ent.car_enum.*;
import com.basakdm.excartest.service.CarService;
import com.basakdm.excartest.utils.ConverterCars;
import com.sun.xml.internal.bind.v2.TODO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "Controller for interaction with the methods car", description = "The operations that can be performed with the car table are in this controller")
@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {

    @Autowired
    private CarService carServiceImpl;
    @Autowired
    private CarRepositoryDAO carRepositoryDAO;

    /**
     * Get all cars.
     * @return collection of <CarDTO>.
     */
    @ApiOperation(value = "Outputting the entire list of car in the car table.", notes = "")
    @GetMapping("/all")
    public Collection<CarDTO> findAll(){
        log.info("(/car/all), main page");
        return carServiceImpl.findAll().stream()
                .map(ConverterCars::mapCar)
                .collect(Collectors.toList());
    }

    /**
     * Find car by id
     * @param carId car unique identifier.
     * @return Optional with car, if car was founded. Empty optional in opposite case.
     */
    @ApiOperation(value = "Output of one machine from the table of the car, by id.", notes = "")
    @GetMapping(value = "/{carId}")
    public ResponseEntity<CarDTO> findCarById(@PathVariable @Positive @ApiParam("id car to find") Long carId){
        log.info("(/car/{carId}), findCarById()");
        return carServiceImpl.findById(carId)
                .map(ConverterCars::mapCar)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create car.
     * @param carEntity car params for create a new car.
     * @return Created car with id.
     */
    /////////// изменить адрес на create /////////////
    @ApiOperation(value = "The method creates a new row in the car table.", notes = "")
    @PostMapping("/createCar")
    public ResponseEntity<?> createCar(@RequestBody @ApiParam("Model for create car")CarEntity carEntity){
        carEntity.setIsActivated(false);
        log.info("(/car/createCar), setIsActivated(false)");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConverterCars.mapCar(carServiceImpl.createCar(carEntity)));
    }

    /**
     * Delete car by id.
     * @param carId car params for delete a car.
     * @return  Void.
     */
    @ApiOperation(value = "Removing a car from the database, by id.", notes = "")
    @DeleteMapping("/delete/{carId}")
    public void delete(@PathVariable @Positive @ApiParam("id car to delete") Long carId){
        log.info("(/car/delete/{carId}), delete()");
        carServiceImpl.delete(carId);
    }

    /**
     * Update car by id.
     * @param car car params for update a car.
     * @return  Void.
     */
    @ApiOperation(value = "Update a car from the database, by model.", notes = "")
    @PostMapping ("/update")
    public void update(@RequestBody @ApiParam("id car to delete") CarEntity car){
        log.info("(/car/update), updating()");
        carServiceImpl.update(car);
    }

    /**
     * Find cars by (isActivated = false).
     * @return  Collection<CarEntity>.
     */
    @ApiOperation(value = "Find cars by (isActivated = false).", notes = "")
    @GetMapping("/isActivated/False")
    public Collection<CarEntity> findAllByIsActivatedFalse(){
        log.info("(/car/isActivated/False), findAllByIsActivatedFalse()");
        return carServiceImpl.findAllByIsActivatedFalse();
    }

    /**
     * Find cars by (isActivated = true).
     * @return  Collection<CarEntity>.
     */
    @ApiOperation(value = "Find cars by (isActivated = true).", notes = "")
    @GetMapping("/isActivated/True")
    public Collection<CarEntity> findAllByIsActivatedTrue(){
        log.info("(/car/isActivated/True), findAllByIsActivatedTrue()");
        return carServiceImpl.findAllByIsActivatedTrue();
    }

    /**
     * Find photo reference by id.
     * @param carId car params to give reference to photo.
     * @return  String - reference to photo.
     */
    @ApiOperation(value = "Find photo reference by id.", notes = "")
    @GetMapping(value = "/getPhoto/{carId}")
    public String getPhotoById(@PathVariable @Positive @ApiParam("id car to find photo") Long carId){
        String photoReference = carServiceImpl.findById(carId).get().getPhoto();
        log.info("(/car/getPhoto/{carId}), photoReference = " + photoReference);
        return photoReference;
    }

    /**
     * Get Location car by id.
     * @param carId car params to give Location.
     * @return  String - Location(coordinates).
     */
    @ApiOperation(value = "Get Location car by id.", notes = "")
    @GetMapping(value = "/getLocation/{carId}")
    public String getLocationById(@PathVariable @Positive @ApiParam("id car to find location") Long carId){
        log.info("(/car/getLocation/{carId}), getLocationById()");
        return carServiceImpl.findById(carId).get().getLocation();
    }

    /**
     * Find cars by transmission type.
     * @param transmission car params to give out a list of cars with such a transmission.
     * @return  Collection<CarEntity>.
     */
    @ApiOperation(value = "Find cars by transmission type.", notes = "")
    @GetMapping(value = "/transmissionType/{transmission}")
    public Collection<CarEntity> getAllByTransmissionType(@PathVariable @Positive @ApiParam("transmission car params to give out a list of cars with such a transmission.") Transmission transmission){
        Collection<CarEntity> cars = carServiceImpl.findAllByTransmissionType(transmission);
        log.info("(/car/transmissionType/{transmission}), getAllByTransmissionType()");
        return cars;
    }

    /**
     * Find cars by car Body type.
     * @param carBody params to give out a list of cars with such a Body.
     * @return  Collection<CarEntity>.
     */
    @ApiOperation(value = "Find cars by car Body type.", notes = "")
    @GetMapping(value = "/carBody/{carBody}")
    public Collection<CarEntity> getAllByCarBody(@PathVariable @Positive @ApiParam("carBody params to give out a list of cars with such a Body.") CarBody carBody){
        log.info("(/car/carBody/{carBody}), getAllByCarBody()");
        return carServiceImpl.findAllByCarBody(carBody);
    }

    /**
     * Find cars by car Drive gear type.
     * @param driveGear params to give out a list of cars with such a drive gear.
     * @return  Collection<CarEntity>.
     */
    @ApiOperation(value = "Find cars by car Drive gear type.", notes = "")
    @GetMapping(value = "/driveGear/{driveGear}")
    public Collection<CarEntity> getAllByDriveGear(@PathVariable @Positive @ApiParam("driveGear params to give out a list of cars with such a drive gear.")DriveGear driveGear){
        log.info("(/car/driveGear/{driveGear}), getAllByDriveGear()");
        return carServiceImpl.findAllByDriveGear(driveGear);
    }

    /**
     * Find cars by car type Engine.
     * @param typeEngine params to give out a list of cars with such a type Engine.
     * @return  Collection<CarEntity>.
     */
    @ApiOperation(value = "Find cars by car type Engine.", notes = "")
    @GetMapping(value = "/typeEngine/{typeEngine}")
    public Collection<CarEntity> getAllByEngineType(@PathVariable @Positive @ApiParam("typeEngine params to give out a list of cars with such a type Engine.") TypeEngine typeEngine){
        log.info("(/car/typeEngine/{typeEngine}), getAllByEngineType()");
        return carServiceImpl.findAllByEngineType(typeEngine);
    }

    /**
     * Find cars by car type Fuel.
     * @param typeFuel params to give out a list of cars with such a type Fuel.
     * @return  Collection<CarEntity>.
     */
    @ApiOperation(value = "Find cars by car type Fuel.", notes = "")
    @GetMapping(value = "/typeFuel/{typeFuel}")
    public Collection<CarEntity> getAllByTypeFuel(@PathVariable @Positive @ApiParam("typeFuel params to give out a list of cars with such a type Fuel.") TypeFuel typeFuel){
        log.info("(/car/typeFuel/{typeFuel}), getAllByTypeFuel()");
        return carServiceImpl.findAllByFuelType(typeFuel);
    }

    /**
     * Get price car by idCar.
     * @param carId params to give price.
     * @return  Long - price.
     */
    @ApiOperation(value = "Get price car by idCar.", notes = "")
    @GetMapping(value = "/getPrice/{carId}")
    public Long getPriceById(@PathVariable @Positive @ApiParam("carId params to give price.")Long carId){
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
