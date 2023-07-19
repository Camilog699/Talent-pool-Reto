package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.Role;

import java.util.List;

public interface IRolePersistencePort {
    Role getRole(Long idRole);
}
