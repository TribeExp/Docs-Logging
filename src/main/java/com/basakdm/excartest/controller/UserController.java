package com.basakdm.excartest.controller;

import com.basakdm.excartest.dao.UserRepositoryDAO;
import com.basakdm.excartest.dto.UserDTO;
import com.basakdm.excartest.entity.UserEntity;
import com.basakdm.excartest.request_models.user_models.UserIdAndCarId;
import com.basakdm.excartest.service.UserService;
import com.basakdm.excartest.utils.ConverterUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepositoryDAO userRepositoryDAO;

    /**
     * Get all user
     *
     * @return collection of users
     */
    @GetMapping("/all")
    public Collection<UserDTO> findAll(){
        return userService.findAll().stream()
                .map(ConverterUsers::mapUser)
                .collect(Collectors.toList());
    }

    /**
     * Find car by id
     *
     * @param userId user unique identifier
     * @return Optional with user, if user was founded. Empty optional in opposite case
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable @Positive Long userId){
        return userService.findById(userId)
                .map(ConverterUsers::mapUser)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete user by id.
     * @param id user params for delete a user.
     */
    @PostMapping ("/delete/{id}")
    public void delete(@PathVariable @Positive Long id){
        userService.delete(id);
    }

    /**
     * Update users by id.
     * @param userEntity user params for update a users.
     */
    @PostMapping ("/update")
    public void update(@RequestBody UserEntity userEntity){
        userService.update(userEntity);
    }

    /**
     * Get user password by id
     * @param userId user id
     * @return password
     */
    @GetMapping(value = "/getPasswordById/{userId}")
    public String getPasswordById(@PathVariable @Positive Long userId){
        return userService.getPasswordById(userId);
    }

    /**
     * Get user Email by id
     * @param userId user id for find Email
     * @return String
     */
    @GetMapping(value = "/getEmailById/{userId}")
    public String getEmailById(@PathVariable @Positive Long userId){
        return userService.findById(userId).get().getMail();
    }

    /**
     * Get user Photo by id
     * @param userId user id for find Photo
     * @return String
     */
    @GetMapping(value = "/getPhoto/{userId}")
    public String getPhotoById(@PathVariable @Positive Long userId){
        return userService.findById(userId).get().getPhoto();
    }

    /**
     * Get user Phone by id
     * @param userId user id for find Phone
     * @return String
     */
    @GetMapping(value = "/getPhone/{userId}")
    public String getPhoneById(@PathVariable @Positive Long userId){
        return userService.findById(userId).get().getPhoneNum();
    }

    /**
     * Get SetCar by id
     * @param userId user id for find set car
     * @return HashSet<Long>
     */
    @GetMapping(value = "/getterSetCar/{userId}")
    public HashSet<Long> getSetCarById(@PathVariable @Positive Long userId){
        return userService.findById(userId).get().getSetIdCar();
    }

    /**
     * Set SetCar by id, add to the set of cars of the landlord, one more car
     * @param userIdAndCarId user id
     * @return HashSet<Long> - set of auto(id)
     */
    @PostMapping(value = "/setNewIdCar")
    public Boolean setSetCarById(@RequestBody UserIdAndCarId userIdAndCarId){
        UserEntity foundUser = userService.findById(userIdAndCarId.getUserId()).get();
        HashSet<Long> set = foundUser.getSetIdCar();
        set.add(userIdAndCarId.getCarId());
        foundUser.setSetIdCar(set);
        return userRepositoryDAO.saveAndFlush(foundUser) != null;
    }

    /**
     * We get a field that shows whether a new notification has arrived or not (True / False)
     * @param userId id of the user of which we look at the field
     * @return Boolean
     */
    @GetMapping(value = "/getNotifyById/{userId}")
    public Boolean getNotifyById(@PathVariable @Positive Long userId){
        return userService.findById(userId).get().getNotify();
    }

    /**
     * Filter find
     * @param search settings find for findAdd
     * @return List<UserEntity>
     */
    //НЕ КОПИРУЙ ЭТОТ МЕТОД
    @GetMapping("/users")
    public List<UserEntity> search(@RequestParam(value = "search") String search) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<UserEntity> spec = builder.build();
        return userRepositoryDAO.findAll(spec);
    }
}
