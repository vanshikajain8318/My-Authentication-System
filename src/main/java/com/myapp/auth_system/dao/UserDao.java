package com.myapp.auth_system.dao;

import com.myapp.auth_system.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    User findByUserName(String userName);
}
