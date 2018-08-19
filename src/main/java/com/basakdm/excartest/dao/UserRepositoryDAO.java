package com.basakdm.excartest.dao;

import com.basakdm.excartest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryDAO extends JpaRepository<UserEntity, Long> {

    /**
     * Returns user by email
     * @param email to be searched
     * @return Optional<UserEntity>
     */
    Optional<UserEntity> findByMailEquals(String email);
}
