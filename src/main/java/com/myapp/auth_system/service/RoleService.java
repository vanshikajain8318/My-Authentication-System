package com.myapp.auth_system.service;

import com.myapp.auth_system.dao.RoleDao;
import com.myapp.auth_system.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public void createRole(Role role) throws Exception {
        if (roleDao.findByRoleName(role.getRoleName()) != null) {
            throw new Exception("Role already exists");
        }
        roleDao.save(role);
    }

    public Role getRoleById(Integer id) throws Exception {
        return roleDao.findById(id).orElseThrow(() -> new Exception("Role not found"));
    }

}
