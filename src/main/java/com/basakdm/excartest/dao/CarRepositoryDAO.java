package com.basakdm.excartest.dao;

import com.basakdm.excartest.entity.CarEntity;
import com.basakdm.excartest.enum_ent.car_enum.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CarRepositoryDAO extends JpaRepository<CarEntity, Long> {

    /**
     * search by unactivated cars
     * @return returns a list of unactivated car
     */
    Collection<CarEntity> findAllByIsActivatedFalse();

    /**
     * search by activated cars
     * @return  Collection<CarEntity> - a list of activated car
     */
    Collection<CarEntity> findAllByIsActivatedTrue();

    /**
     * search by type of transmission
     * @param transmission type of transmission, cars that we want to receive
     * @return  Collection<CarEntity> - list with the specified transmission
     */
    Collection<CarEntity> findAllByTransmissionType(Transmission transmission);

    /**
     * search by type of Body
     * @param carBody type of car Body, cars that we want to receive
     * @return  Collection<CarEntity> - list with the specified carBody
     */
    Collection<CarEntity> findAllByCarBody(CarBody carBody);

    /**
     * search by type of drive Gear
     * @param driveGear type of drive Gear, cars that we want to receive
     * @return  Collection<CarEntity> - list with the specified driveGear
     */
    Collection<CarEntity> findAllByDriveGear(DriveGear driveGear);

    /**
     * search by type engine
     * @param typeEngine type of type engine, cars that we want to receive
     * @return  Collection<CarEntity> - list with the specified type Engine
     */
    Collection<CarEntity> findAllByEngineType(TypeEngine typeEngine);

    /**
     * search by type fuel
     * @param typeFuel type of type fuel, cars that we want to receive
     * @return  Collection<CarEntity> - list with the specified typeFuel
     */
    Collection<CarEntity> findAllByFuelType(TypeFuel typeFuel);



}