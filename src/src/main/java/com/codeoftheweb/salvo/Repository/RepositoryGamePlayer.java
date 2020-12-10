package com.codeoftheweb.salvo.Repository;

import com.codeoftheweb.salvo.Model.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositoryGamePlayer extends JpaRepository<GamePlayer, Long> {

}
