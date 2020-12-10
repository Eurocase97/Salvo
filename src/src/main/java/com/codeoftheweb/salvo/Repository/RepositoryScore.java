package com.codeoftheweb.salvo.Repository;

import com.codeoftheweb.salvo.Model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositoryScore extends JpaRepository<Score, Long> {
}
