package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.Role;

public interface IRolePersistencePort {
    Role getRole(Long idRole);
}
