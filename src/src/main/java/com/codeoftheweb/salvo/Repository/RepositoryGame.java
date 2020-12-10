package com.codeoftheweb.salvo.Repository;

import com.codeoftheweb.salvo.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositoryGame extends JpaRepository<Game, Long> {

}
