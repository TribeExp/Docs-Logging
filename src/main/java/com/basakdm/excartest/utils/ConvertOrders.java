package com.basakdm.excartest.utils;

import com.basakdm.excartest.dto.OrderDTO;
import com.basakdm.excartest.entity.OrderEntity;
import com.basakdm.excartest.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
public class ConvertOrders {

    @Autowired
    private static OrderService orderService;

    public static OrderDTO mapOrder(OrderEntity orderEntity) {

        log.info("orderEntity = " + orderEntity + " mapOrder()");
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(orderEntity.getId());
        orderDTO.setPriceAdd(orderEntity.getPriceAdd());
        orderDTO.setFinPrice(orderEntity.getFinPrice());
        orderDTO.setFromWhatDate(orderEntity.getFromWhatDate());
        orderDTO.setIdCar(orderEntity.getIdCar());
        orderDTO.setIdUser(orderEntity.getIdUser());
        orderDTO.setIdOwner(orderEntity.getIdOwner());
        orderDTO.setAmountOfDays(orderEntity.getAmountOfDays());
        log.info("orderDTO = " + orderDTO + " mapOrder()");

        return orderDTO;
    }

    public static OrderEntity mapOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity;
        Optional<OrderEntity> optionalOrderEntity = orderService.findById(orderDTO.getId());

        if (optionalOrderEntity.isPresent()) orderEntity = optionalOrderEntity.get();
        else throw new RuntimeException("Incorrect ID of car");

        log.info("orderDTO = " + orderDTO + " mapOrder()");
        orderEntity.setId(orderDTO.getId());
        orderEntity.setPriceAdd(orderDTO.getPriceAdd());
        orderEntity.setFinPrice(orderDTO.getFinPrice());
        orderEntity.setFromWhatDate(orderDTO.getFromWhatDate());
        orderEntity.setIdCar(orderDTO.getIdCar());
        orderEntity.setIdUser(orderDTO.getIdUser());
        orderEntity.setIdOwner(orderDTO.getIdOwner());
        orderEntity.setAmountOfDays(orderDTO.getAmountOfDays());
        log.info("orderEntity = " + orderEntity + " mapOrder()");

        return orderEntity;
    }
}
