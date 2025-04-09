package ru.kata.spring.boot_security.demo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class); //удалить потом
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles;

    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);

    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getRolesByIds(List<Long> ids) {
        return roleRepository.findByIds(ids);
    }

    @Transactional
    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
}
