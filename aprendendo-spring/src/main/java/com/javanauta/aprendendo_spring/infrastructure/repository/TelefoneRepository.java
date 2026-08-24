package com.javanauta.aprendendo_spring.infrastructure.repository;

import com.javanauta.aprendendo_spring.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone,Long> {
}
