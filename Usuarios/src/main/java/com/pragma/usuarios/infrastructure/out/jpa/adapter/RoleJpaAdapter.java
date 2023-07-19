package com.pragma.usuarios.infrastructure.out.jpa.adapter;

import com.pragma.usuarios.domain.model.Role;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;
import com.pragma.usuarios.infrastructure.exception.NoDataFoundException;
import com.pragma.usuarios.infrastructure.exception.RoleNotFoundException;
import com.pragma.usuarios.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.usuarios.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.usuarios.infrastructure.out.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public Role getRole(Long idRole) {
        return roleEntityMapper.toRole(roleRepository.findById(idRole).orElseThrow(RoleNotFoundException::new));
    }
}