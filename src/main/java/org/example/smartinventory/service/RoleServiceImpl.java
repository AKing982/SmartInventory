package org.example.smartinventory.service;

import org.example.smartinventory.entities.RoleEntity;
import org.example.smartinventory.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService
{
    private RoleRepository roleRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, JdbcTemplate jdbcTemplate)
    {
        this.roleRepository = roleRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void save(RoleEntity roleEntity) {
        roleRepository.save(roleEntity);
    }

    @Override
    public void delete(RoleEntity roleEntity) {
        roleRepository.delete(roleEntity);
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Collection<RoleEntity> findAllById(Iterable<Long> ids) {
        return roleRepository.findAllById(ids);
    }

    @Override
    public Set<String> getUserRoles(Long userId){
        String sql = "SELECT r.role FROM roles r JOIN user_roles ur ON r.roleid = ur.roleid WHERE ur.userid = ?";
        return new HashSet<>(jdbcTemplate.queryForList(sql, String.class, userId));
    }

    @Override
    public Set<String> getUserRolePermissions(Long userId){
        String sql = "SELECT DISTINCT rp.permission FROM role_permissions rp " +
                "JOIN user_roles ur ON rp.role_id = ur.roleid WHERE ur.userid = ?";
        return new HashSet<>(jdbcTemplate.queryForList(sql, String.class, userId));
    }

    @Override
    public void addRoleToUser(Long userId, Integer roleId){
        String sql = "INSERT INTO user_roles (userid, roleid) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, roleId);
    }

    @Override
    public void removeRoleFromUser(Long userId, Integer roleId){
        String sql = "DELETE FROM user_roles WHERE user_id = ? AND role_id = ?";
        jdbcTemplate.update(sql, userId, roleId);
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return roleRepository.findByName(name);
    }

}
