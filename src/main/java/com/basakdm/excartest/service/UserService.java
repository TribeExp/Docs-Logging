package com.basakdm.excartest.service;

import com.basakdm.excartest.entity.Role;
import com.basakdm.excartest.entity.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public interface UserService {

    /**
     * Get all user
     * @return collection of users
     */
    Collection<UserEntity> findAll();

    /**
     * Find user by id
     * @param id user unique identifier
     * @return Optional with user, if user was founded. Empty optional in opposite case
     */
    Optional<UserEntity> findById(Long id);

    /**
     * Create user.
     * @return Created user with id.
     */
    UserEntity createUser(String email, String password);

    /**
     * Delete user by id.
     * @param id user params for delete a user.
     * @return  Void.
     */
    void delete(long id);

    /**
     * Update users by id.
     * @param userEntity user params for update a users.
     * @return  Void.
     */
    void update(UserEntity userEntity);

    /**
     * Find Password by id
     * @param userId user unique identifier
     * @return String with Password, if Password was founded. Empty String in opposite case
     */
    String getPasswordById(Long userId);

    /**
     * Find Mail by id
     * @param email user unique identifier
     * @return String with Mail, if Mail was founded. Empty Optional in opposite case
     */
    Optional<UserEntity> findByMail(String email);

    /**
     * Get id Current user
     * @return Long with id.
     */
    long getCurrentUserId() throws Exception;

    /**
     * Get Current user
     * @return Optional with User.
     */
    Optional<UserEntity> getCurrentUser() throws Exception;

    /**
     * Get Roles user
     * @return Set<Role>, where user roles are stored.
     */
    Set<Role> getCurrentUserRoles() throws Exception;

    /**
     * Create user.
     * @param email - email current user
     * @param password - password current user
     * @return UserEntity - Created user with id.
     */
    UserEntity createAdmin(String email, String password);

    /**
     * Get Authority current user
     * @param user - user, which set the property Authority
     * @return Set<SimpleGrantedAuthority> z
     */
    Set<SimpleGrantedAuthority> getAuthority(UserEntity user);
}
