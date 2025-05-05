package com.ironhack.spring_security.repositories;

import com.ironhack.spring_security.models.ERole;
import com.ironhack.spring_security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
