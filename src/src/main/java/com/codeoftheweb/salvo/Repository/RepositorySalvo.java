package com.codeoftheweb.salvo.Repository;

import com.codeoftheweb.salvo.Model.Salvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorySalvo extends JpaRepository<Salvo, Long> {
}
