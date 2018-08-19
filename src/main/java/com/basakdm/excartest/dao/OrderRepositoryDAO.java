package com.basakdm.excartest.dao;

import com.basakdm.excartest.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface OrderRepositoryDAO extends JpaRepository<OrderEntity, Long> {

   /**
    * Returns orders for id car that are associated with it
    * @param idCar id of the car that is associated with the desired order
    * @return  Optional<OrderEntity>
    */
   Optional<OrderEntity> findByIdCar(Long idCar);
}
