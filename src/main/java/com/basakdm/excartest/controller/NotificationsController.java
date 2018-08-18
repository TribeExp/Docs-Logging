package com.basakdm.excartest.controller;

import com.basakdm.excartest.dao.NotificationsRepositoryDAO;
import com.basakdm.excartest.dto.NotificationsDTO;
import com.basakdm.excartest.entity.NotificationsEntity;
import com.basakdm.excartest.service.NotificationsService;
import com.basakdm.excartest.utils.ConverterNotifications;
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
@RequestMapping("/notifications")
@Slf4j
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;
    @Autowired
    private NotificationsRepositoryDAO notificationsRepositoryDAO;

    @GetMapping("/all")
    public Collection<NotificationsDTO> findAll(){
        log.info("(/notifications/all), findAll()");
        return notificationsService.findAll().stream()
                .map(ConverterNotifications::mapNotifyUser)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationsDTO> findCarById(@PathVariable @Positive Long id){
        log.info("(/notifications/{id}), findCarById()");
        return notificationsService.findById(id)
                .map(ConverterNotifications::mapNotifyUser)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody NotificationsEntity notificationsEntity){
        log.info("(/notifications/create), create()");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConverterNotifications.mapNotifyUser(notificationsService.create(notificationsEntity)));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable @Positive Long id){
        log.info("(/notifications/delete), delete()");
        notificationsService.delete(id);
    }

    @PostMapping ("/update")
    public void update(@RequestBody NotificationsEntity notificationsEntity){
        log.info("(/notifications/update), update()");
        notificationsService.update(notificationsEntity);
    }


    @GetMapping(value = "/getTextNotifyById/{notifyId}")
    public String getTextNotifyById(@PathVariable @Positive Long notifyId){
        log.info("(/notifications/getTextNotifyById/{notifyId}), getTextNotifyById()");
        return notificationsService.findById(notifyId).get().getTextNotify();
    }

    @GetMapping(value = "/getFromWhomIdById/{notifyId}")
    public Long getFromWhomIdById(@PathVariable @Positive Long notifyId){
        log.info("(/notifications/getFromWhomIdById/{notifyId}), getFromWhomIdById()");
        return notificationsService.findById(notifyId).get().getFromWhomId();
    }
    @GetMapping(value = "/getToWhomIdById/{notifyId}")
    public Long getToWhomIdById(@PathVariable @Positive Long notifyId){
        log.info("(/notifications/getToWhomIdById/{notifyId}), getToWhomIdById()");
        return notificationsService.findById(notifyId).get().getToWhomId();
    }

    @GetMapping(value = "/getOrderIdById/{notifyId}")
    public Long getOrderIdById(@PathVariable @Positive Long notifyId){
        log.info("(/notifications/getOrderIdById/{notifyId}), getOrderIdById()");
        return notificationsService.findById(notifyId).get().getOrderId();
    }
    /*@PostMapping(value = "/setOrderIdById/{notifyId}/{orderId}")
    public void setOrderIdById(@RequestBody @PathVariable @Positive Long notifyId, @PathVariable @Positive Long orderId){

        Optional<NotificationsEntity> optionalNotificationsEntity = notificationsService.findById(notifyId);
        NotificationsEntity notificationsEntity = optionalNotificationsEntity.get();
        notificationsEntity.setOrderId(orderId);

        notificationsRepositoryDAO.saveAndFlush(notificationsEntity);
    }*/

}
