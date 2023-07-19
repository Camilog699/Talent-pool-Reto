package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.api.IRoleServicePort;
import com.pragma.usuarios.domain.model.Role;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;

public class RoleUseCase implements IRoleServicePort{
    private final IRolePersistencePort rolPersistencePort;
    public RoleUseCase(IRolePersistencePort rolPersistencePort){ this.rolPersistencePort = rolPersistencePort;}

    @Override
    public Role getRole(Long rolId) {
        return rolPersistencePort.getRole(rolId);
    }
}