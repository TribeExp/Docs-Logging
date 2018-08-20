package com.basakdm.excartest.controller;

import com.basakdm.excartest.dao.NotificationsRepositoryDAO;
import com.basakdm.excartest.dto.NotificationsDTO;
import com.basakdm.excartest.entity.NotificationsEntity;
import com.basakdm.excartest.service.NotificationsService;
import com.basakdm.excartest.utils.ConverterNotifications;
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

@RestController
@RequestMapping("/notifications")
@Slf4j
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;
    @Autowired
    private NotificationsRepositoryDAO notificationsRepositoryDAO;

    /**
     * Get all Notifications.
     * @return collection of NotificationsEntity.
     */
    @ApiOperation(value = "Get all Notifications.", notes = "")
    @GetMapping("/all")
    public Collection<NotificationsDTO> findAll(){
        log.info("(/notifications/all), findAll()");
        return notificationsService.findAll().stream()
                .map(ConverterNotifications::mapNotifyUser)
                .collect(Collectors.toList());
    }

    /**
     * Find notifications by id
     * @param id notification unique identifier.
     * @return Optional with notifications, if notifications was founded. Empty optional in opposite case.
     */
    @ApiOperation(value = "Find notifications by id.", notes = "")
    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationsDTO> findCarById(@PathVariable @Positive @ApiParam("id notification unique identifier")Long id){
        log.info("(/notifications/{id}), findCarById()");
        return notificationsService.findById(id)
                .map(ConverterNotifications::mapNotifyUser)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create notification.
     * @param notificationsEntity params for create a new notification.
     * @return Created notification with id.
     */
    @ApiOperation(value = "Create notification.", notes = "")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @ApiParam("notificationsEntity params for create a new notification") NotificationsEntity notificationsEntity){
        log.info("(/notifications/create), create()");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConverterNotifications.mapNotifyUser(notificationsService.create(notificationsEntity)));
    }

    /**
     * Delete notification by id.
     * @param id notification params for delete a notification.
     * @return  Void.
     */
    @ApiOperation(value = "Delete notification by id.", notes = "")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable @Positive @ApiParam("id notification params for delete a notification") Long id){
        log.info("(/notifications/delete), delete()");
        notificationsService.delete(id);
    }

    /**
     * Update notification by id.
     * @param notificationsEntity notification params for update a notifications.
     * @return  Void.
     */
    @ApiOperation(value = "Update notification by id.", notes = "")
    @PostMapping ("/update")
    public void update(@RequestBody @ApiParam("notificationsEntity notification params for update a notifications") NotificationsEntity notificationsEntity){
        log.info("(/notifications/update), update()");
        notificationsService.update(notificationsEntity);
    }

    /**
     * Get text notify by id.
     * @param notifyId notification params for find a text.
     * @return  String.
     */
    @ApiOperation(value = "Get text notify by id.", notes = "")
    @GetMapping(value = "/getTextNotifyById/{notifyId}")
    public String getTextNotifyById(@PathVariable @Positive @ApiParam("notifyId notification params for find a text") Long notifyId){
        log.info("(/notifications/getTextNotifyById/{notifyId}), getTextNotifyById()");
        return notificationsService.findById(notifyId).get().getTextNotify();
    }

    /**
     * Get the ID of the person who sent this message.
     * @param notifyId notification params for find a FromWhomId.
     * @return  Long.
     */
    @ApiOperation(value = "Get the ID of the person who sent this message.", notes = "")
    @GetMapping(value = "/getFromWhomIdById/{notifyId}")
    public Long getFromWhomIdById(@PathVariable @Positive @ApiParam("notifyId notification params for find a FromWhomId") Long notifyId){
        log.info("(/notifications/getFromWhomIdById/{notifyId}), getFromWhomIdById()");
        return notificationsService.findById(notifyId).get().getFromWhomId();
    }

    /**
     * Get the id of the person who received the message.
     * @param notifyId notification params for find a toWhomId.
     * @return  Long.
     */
    @ApiOperation(value = "Get the id of the person who received the message.", notes = "")
    @GetMapping(value = "/getToWhomIdById/{notifyId}")
    public Long getToWhomIdById(@PathVariable @Positive @ApiParam("notifyId notification params for find a toWhomId") Long notifyId){
        log.info("(/notifications/getToWhomIdById/{notifyId}), getToWhomIdById()");
        return notificationsService.findById(notifyId).get().getToWhomId();
    }

    /**
     * Get an order object by ID, from which you can then take any field.
     * @param notifyId notification params for find a order.
     * @return  Long.
     */
    @ApiOperation(value = "Get an order object by ID, from which you can then take any field.", notes = "")
    @GetMapping(value = "/getOrderIdById/{notifyId}")
    public Long getOrderIdById(@PathVariable @Positive @ApiParam("notifyId notification params for find a order") Long notifyId){
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
