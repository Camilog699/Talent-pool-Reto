package com.pragma.usuarios.domain.api;

import com.pragma.usuarios.domain.model.Role;

import java.util.List;

public interface IRoleServicePort {
    Role getRole(Long idRole);
}
