package com.basakdm.excartest.service.service_impl;

import com.basakdm.excartest.dao.RoleRepositoryDAO;
import com.basakdm.excartest.dao.UserRepositoryDAO;
import com.basakdm.excartest.dto.UserDTO;
import com.basakdm.excartest.entity.CarEntity;
import com.basakdm.excartest.entity.Role;
import com.basakdm.excartest.entity.UserEntity;
import com.basakdm.excartest.service.UserService;
import com.basakdm.excartest.utils.ConverterUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoryDAO userRepositoryDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepositoryDAO roleRepositoryDAO;

    @Override
    public UserEntity createUser(String email, String password) {
        log.info("email = " + email + " password " + password + ", createUser()");
        Role user = new Role();
        if (roleRepositoryDAO.findByRole("USER") == null){
            log.info("if (roleRepositoryDAO.findByRole(USER) == null) == true");
            user.setRole("USER");
            user.setId(2);
            roleRepositoryDAO.save(user);
            log.info("setRole(User), setId(2), save(user)");
        }
        if (roleRepositoryDAO.findByRole("ADMIN") == null){
            log.info("if (roleRepositoryDAO.findByRole(USER) == null) == true");
            user.setRole("ADMIN");
            user.setId(1);
            roleRepositoryDAO.saveAndFlush(user);
            log.info("setRole(ADMIN), setId(1), save(user)");
        }

        UserEntity newUser = new UserEntity();
        Role userRole = roleRepositoryDAO.findByRole("USER");
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setMail(email);
        newUser.setActive(true);
        newUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        return userRepositoryDAO.saveAndFlush(newUser);
    }

    @Override
    public UserEntity createAdmin(String email, String password) {
        log.info("email = " + email + " password " + password + ", createAdmin()");
        Role user = new Role();
        if (roleRepositoryDAO.findByRole("USER") == null){
            log.info("if (roleRepositoryDAO.findByRole(USER) == null) == true");
            user.setRole("USER");
            user.setId(2);
            roleRepositoryDAO.save(user);
            log.info("setRole(USER), setId(2), save(user)");
        }
        if (roleRepositoryDAO.findByRole("ADMIN") == null){
            log.info("if (roleRepositoryDAO.findByRole(ADMIN) == null) == true");
            user.setRole("ADMIN");
            user.setId(1);
            roleRepositoryDAO.saveAndFlush(user);
            log.info("setRole(ADMIN), setId(2), save(user)");
        }

        UserEntity newUser = new UserEntity();
        Role userRole = roleRepositoryDAO.findByRole("ADMIN");
        log.info("userRole" + userRole);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setMail(email);
        newUser.setActive(true);
        newUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        log.info("setPassword, setMail, setActive, setRoles " + email);
        return userRepositoryDAO.saveAndFlush(newUser);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepositoryDAO.findAll();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepositoryDAO.findById(id);
    }

    @Override
    public void delete(long id) {
        Optional<UserEntity> userOld = findById(id);
        if(userOld.isPresent()) userRepositoryDAO.deleteById(id);
    }

    @Override
    public void update(UserEntity userEntity) {
        Long id = userEntity.getId();
        Optional<UserEntity> userOld = findById(id);
        if(userOld.isPresent()) userRepositoryDAO.save(userEntity);
    }

    @Override
    public String getPasswordById(Long userId) {
        log.info("getPasswordById()");
        return bCryptPasswordEncoder.encode(findById(userId).get().getPassword());
    }

    @Override
    public Optional<UserEntity> findByMail(String email) {
        log.info("findByMail()");
        return userRepositoryDAO.findByMailEquals(email);
    }

    @Override
    public long getCurrentUserId() throws Exception {
        log.info("getCurrentUserId()");
        UserEntity userEntity = getCurrentUser()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("userEntity " + userEntity);
        return userEntity.getId();
    }

    @Override
    public Optional<UserEntity> getCurrentUser() throws Exception {
        log.info("getCurrentUser()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth " + auth);
        if (null == auth) {
            throw new Exception("Authentication context not found");
        }

        Object obj = auth.getPrincipal();
        String username = "";
        log.info("auth.getPrincipal() = " + obj);

        if (obj instanceof UserDetails) {
            username = ((UserDetails) obj).getUsername();
            log.info("if (obj instanceof UserDetails) == true ");
            log.info("username = " + username);
        } else {
            log.info("if (obj instanceof UserDetails) == false");
            username = obj.toString();
            log.info("username = " + username);
        }

        return userRepositoryDAO.findByMailEquals(username);
    }

    @Override
    public Set<Role> getCurrentUserRoles() throws Exception{
        log.info("getCurrentUserRoles()");
        UserEntity userEntity = getCurrentUser()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("userEntity = " + userEntity);
        log.info("userEntity.getRoles = " + userEntity.getRoles());
        return userEntity.getRoles();
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
        log.info("getAuthority()");
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        });
        log.info("authorities " + authorities);
        return authorities;
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
