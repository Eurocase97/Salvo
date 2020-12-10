package com.codeoftheweb.salvo.Controller;


import com.codeoftheweb.salvo.DTO.*;
import com.codeoftheweb.salvo.Model.Game;
import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Model.Player;
import com.codeoftheweb.salvo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    RepositoryShip repositoryShip;

    @Autowired
    RepositorySalvo repositorySalvo;

    @Autowired
    RepositoryScore repositoryScore;


    static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }


    @RequestMapping("/gamePlayers")
    public List<Map<String, Object>>getGamePlayersAll(){
        DtoGamePlayer  dtoGamePlayer = new DtoGamePlayer();
        return  repositoryGamePlayer.findAll()
                .stream()
                .map(gamePlayer -> dtoGamePlayer.makeGamePlayerDTO(gamePlayer))
                .collect(Collectors.toList());
    }

    @RequestMapping("/ships")
    public List<Map<String,Object>>getShipAll(){
        DtoShip dtoShip = new DtoShip();
        return repositoryShip.findAll()
                .stream()
                .map(ship -> dtoShip.makeShipDTO(ship))
                .collect(Collectors.toList());
    }

    @RequestMapping("/salvoes")
    public List<Map<String,Object>>getSalvoAll(){
        DtoSalvo dtoSalvo = new DtoSalvo();
        return repositorySalvo.findAll()
                .stream()
                .map(salvo -> dtoSalvo.makeSalvoDTO(salvo))
                .collect(Collectors.toList());
    }

    @RequestMapping("/leaderboard")
    public List<Map<String,Object>> getLeaderboard(){
        DtoPlayer dtoPlayer = new DtoPlayer();
        return repositoryPlayer.findAll().stream()
                .map(p -> dtoPlayer.makePlayerScoreDTO(p)).collect(Collectors.toList());
    }

    @RequestMapping("/game_view/{gameId}")
    public Map<String,Object> getGameView(@PathVariable long gameId){
        GamePlayer gamePlayer= repositoryGamePlayer.findById(gameId).get();
        DtoGamePlayer dtoGamePlayer= new DtoGamePlayer(gamePlayer);
    return dtoGamePlayer.makeGameViewDTO();
    }
}