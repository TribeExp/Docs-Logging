package com.basakdm.excartest.service.service_impl;

import com.basakdm.excartest.dao.CarRepositoryDAO;
import com.basakdm.excartest.dto.CarDTO;
import com.basakdm.excartest.entity.CarEntity;
import com.basakdm.excartest.enum_ent.car_enum.*;
import com.basakdm.excartest.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepositoryDAO carRepositoryDAO;
    private List<CarEntity> listCar;

    @Override
    public List<CarEntity> findAll() {
        return carRepositoryDAO.findAll();
    }

    @Override
    public Optional<CarEntity> findById(Long id) {
        return carRepositoryDAO.findById(id);
    }


    @Override
    public CarEntity createCar(CarEntity carEntity) {
        return carRepositoryDAO.saveAndFlush(carEntity);
    }

    @Override
    public void delete(long id) {
        Optional<CarEntity> carOld = findById(id);
        if(carOld.isPresent()) carRepositoryDAO.deleteById(id);
    }

    @Override
    public void update(CarEntity carEntity) {
        Long id = carEntity.getId();
        Optional<CarEntity> carOld = findById(id);
        if(carOld.isPresent()) carRepositoryDAO.save(carEntity);
    }

    @Override
    public Collection<CarEntity> findAllByIsActivatedFalse() {
        return carRepositoryDAO.findAllByIsActivatedFalse();
    }
    @Override
    public Collection<CarEntity> findAllByIsActivatedTrue() {
        return carRepositoryDAO.findAllByIsActivatedTrue();
    }


    @Override
    public Collection<CarEntity> findAllByTransmissionType(Transmission transmission) {
        return carRepositoryDAO.findAllByTransmissionType(transmission);
    }
    @Override
    public Collection<CarEntity> findAllByCarBody(CarBody carBody) {
        return carRepositoryDAO.findAllByCarBody(carBody);
    }
    @Override
    public Collection<CarEntity> findAllByDriveGear(DriveGear driveGear) {
        return carRepositoryDAO.findAllByDriveGear(driveGear);
    }
    @Override
    public Collection<CarEntity> findAllByEngineType(TypeEngine typeEngine) {
        return carRepositoryDAO.findAllByEngineType(typeEngine);
    }
    @Override
    public Collection<CarEntity> findAllByFuelType(TypeFuel typeFuel) {
        return carRepositoryDAO.findAllByFuelType(typeFuel);
    }




    /*@Override
    public void update(long id) {
        CarEntity carEntity = carRepositoryDAO.getOne(id);
        carRepositoryDAO.
    }*/


}
