package com.codeoftheweb.salvo.Controller;

import com.codeoftheweb.salvo.DTO.*;
import com.codeoftheweb.salvo.Model.Game;
import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Model.Player;
import com.codeoftheweb.salvo.Repository.*;
import com.codeoftheweb.salvo.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.codeoftheweb.salvo.Util.Util;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    RepositoryGamePlayer repositoryGamePlayer;

    @Autowired
    RepositoryPlayer repositoryPlayer;

    @Autowired
    RepositoryGame repositoryGame;

    @RequestMapping("/leaderboard")
    public List<Map<String, Object>> getLeaderboard() {
        DtoPlayer dtoPlayer = new DtoPlayer();
        return repositoryPlayer.findAll().stream()
                .map(p -> dtoPlayer.makePlayerScoreDTO(p)).collect(Collectors.toList());
    }

    @RequestMapping(path = "/game_view/{nn}", method = RequestMethod.GET)
    public ResponseEntity<Object> getGameView(@PathVariable long nn, Authentication authentication) {
        if (Util.isGuest(authentication))
            return new ResponseEntity<>(Util.makeMap("error", "You are not logged in."), HttpStatus.UNAUTHORIZED);
        Player player = repositoryPlayer.findByEmail(authentication.getName());
        if (player == null)
            return new ResponseEntity<>(Util.makeMap("error", "Database error. Player not found."), HttpStatus.INTERNAL_SERVER_ERROR);
        GamePlayer gamePlayer = repositoryGamePlayer.findById(nn).orElse(null);
        if (gamePlayer == null)
            return new ResponseEntity<>(Util.makeMap("error", "Database error. GamePlayer not found."), HttpStatus.INTERNAL_SERVER_ERROR);
        if (player.getId() != gamePlayer.getPlayer().getId())
            return new ResponseEntity<>(Util.makeMap("error", "This is not your game!"), HttpStatus.UNAUTHORIZED);
        DtoGamePlayer dtoGamePlayer = new DtoGamePlayer();
        return new ResponseEntity<>(dtoGamePlayer.makeGamePlayerDTO(gamePlayer), HttpStatus.ACCEPTED);
    }
}