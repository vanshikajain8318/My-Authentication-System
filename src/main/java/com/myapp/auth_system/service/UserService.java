package com.myapp.auth_system.service;

import com.myapp.auth_system.dao.RoleDao;
import com.myapp.auth_system.dao.UserDao;
import com.myapp.auth_system.entity.Role;
import com.myapp.auth_system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("USER");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setEmail("admin@gmail.com");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
        userDao.save(adminUser);

    }

    public void registerUser(User user) throws Exception {
        if (userDao.findByUserName(user.getUserName()) != null) {
            throw new Exception("Username already exists");
        }

        Role userRole = roleDao.findByRoleName("USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setRoleName("USER");
            roleDao.save(userRole);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        userDao.save(user);
    }

}
