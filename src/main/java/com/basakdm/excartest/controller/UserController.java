package com.basakdm.excartest.controller;

import com.basakdm.excartest.dao.UserRepositoryDAO;
import com.basakdm.excartest.dto.UserDTO;
import com.basakdm.excartest.entity.UserEntity;
import com.basakdm.excartest.request_models.user_models.UserIdAndCarId;
import com.basakdm.excartest.service.UserService;
import com.basakdm.excartest.utils.ConverterUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepositoryDAO userRepositoryDAO;

    @GetMapping("/all")
    public Collection<UserDTO> findAll(){
        log.info("(users/all), findAll()");
        return userService.findAll().stream()
                .map(ConverterUsers::mapUser)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable @Positive Long userId){
        log.info("(users/{userId}), findUserById()");
        return userService.findById(userId)
                .map(ConverterUsers::mapUser)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping ("/delete/{id}")
    public void delete(@PathVariable @Positive Long id){
        log.info("(users/delete/{id}), delete()");
        userService.delete(id);
    }
    @PostMapping ("/update")
    public void update(@RequestBody UserEntity userEntity){
        log.info("(users/update), update()");
        userService.update(userEntity);
    }
    @GetMapping(value = "/getPasswordById/{userId}")
    public String getPasswordById(@PathVariable @Positive Long userId){
        log.info("(users/getPasswordById/{userId}), getPasswordById()");
        return userService.getPasswordById(userId);
    }
    @GetMapping(value = "/getEmailById/{userId}")
    public String getEmailById(@PathVariable @Positive Long userId){
        log.info("(users/getEmailById/{userId}), getEmailById()");
        return userService.findById(userId).get().getMail();
    }
    @GetMapping(value = "/getPhoto/{userId}")
    public String getPhotoById(@PathVariable @Positive Long userId){
        log.info("(users/getPhoto/{userId}), getPhotoById()");
        return userService.findById(userId).get().getPhoto();
    }
    @GetMapping(value = "/getPhone/{userId}")
    public String getPhoneById(@PathVariable @Positive Long userId){
        log.info("(users/getPhone/{userId}), getPhoneById()");
        return userService.findById(userId).get().getPhoneNum();
    }
    @GetMapping(value = "/getterSetCar/{userId}")
    public HashSet<Long> getSetCarById(@PathVariable @Positive Long userId){
        log.info("(users/getterSetCar/{userId}), getSetCarById()");
        return userService.findById(userId).get().getSetIdCar();
    }
    @PostMapping(value = "/setNewIdCar")
    public Boolean setSetCarById(@RequestBody UserIdAndCarId userIdAndCarId){
        log.info("(users//setNewIdCar), setSetCarById()");
        UserEntity foundUser = userService.findById(userIdAndCarId.getUserId()).get();
        HashSet<Long> set = foundUser.getSetIdCar();
        set.add(userIdAndCarId.getCarId());
        foundUser.setSetIdCar(set);
        log.info("saveAndFlush(foundUser)");
        return userRepositoryDAO.saveAndFlush(foundUser) != null;
    }
    @GetMapping(value = "/getNotifyById/{userId}")
    public Boolean getNotifyById(@PathVariable @Positive Long userId){
        log.info("(users/getNotifyById/{userId}), getNotifyById()");
        return userService.findById(userId).get().getNotify();
    }
}
