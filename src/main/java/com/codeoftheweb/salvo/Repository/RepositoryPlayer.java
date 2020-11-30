package com.codeoftheweb.salvo.Repository;

import com.codeoftheweb.salvo.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositoryPlayer extends JpaRepository <Player, Long> {

}