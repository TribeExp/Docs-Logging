package com.basakdm.excartest.service;


import com.basakdm.excartest.entity.CarEntity;
import com.basakdm.excartest.enum_ent.car_enum.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface CarService  {

    /**
     * Get all cars.
     * @return collection of cars.
     */
    Collection<CarEntity> findAll();

    /**
     * Find car by id
     * @param id car unique identifier.
     * @return Optional with car, if car was founded. Empty optional in opposite case.
     */
    Optional<CarEntity> findById(Long id);

    /**
     * Create car.
     * @param carEntity car params for create a new car.
     * @return Created car with id.
     */
    CarEntity createCar(CarEntity carEntity);

    /**
     * Delete car by id.
     * @param id car params for delete a car.
     * @return  Void.
     */
    void delete(long id);

    /**
     * Update car by id.
     * @param carEntity car params for update a car.
     * @return  Void.
     */
    void update(CarEntity carEntity);

    /**
     * Find cars by (isActivated = false).
     * @return  Collection<CarEntity>.
     */
    Collection<CarEntity> findAllByIsActivatedFalse();

    /**
     * Find cars by (isActivated = true).
     * @return  Collection<CarEntity>.
     */
    Collection<CarEntity> findAllByIsActivatedTrue();

    /**
     * Find cars by transmission type.
     * @param transmission car params to give out a list of cars with such a transmission.
     * @return  Collection<CarEntity>.
     */
    Collection<CarEntity> findAllByTransmissionType(Transmission transmission);

    /**
     * Find cars by car body.
     * @param carBody car params to give out a list of cars with such a car body.
     * @return  Collection<CarEntity>.
     */
    Collection<CarEntity> findAllByCarBody(CarBody carBody);

    /**
     * Find cars by drive gear.
     * @param driveGear car params to give out a list of cars with such a drive gear.
     * @return  Collection<CarEntity>.
     */
    Collection<CarEntity> findAllByDriveGear(DriveGear driveGear);

    /**
     * Find cars by type engine.
     * @param typeEngine car params to give out a list of cars with such a type engine.
     * @return  Collection<CarEntity>.
     */
    Collection<CarEntity> findAllByEngineType(TypeEngine typeEngine);

    /**
     * Find cars by type fuel.
     * @param typeFuel car params to give out a list of cars with such a type fuel.
     * @return  Collection<CarEntity>.
     */
    Collection<CarEntity> findAllByFuelType(TypeFuel typeFuel);
}
