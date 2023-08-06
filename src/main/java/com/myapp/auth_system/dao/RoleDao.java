package com.myapp.auth_system.dao;

import com.myapp.auth_system.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
