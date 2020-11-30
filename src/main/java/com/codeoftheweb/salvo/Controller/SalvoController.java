package com.codeoftheweb.salvo.Controller;

import com.codeoftheweb.salvo.DTO.*;
import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    RepositoryGame repositoryGame;

    @Autowired
    RepositoryGamePlayer repositoryGamePlayer;

    @Autowired
    RepositoryPlayer repositoryPlayer;

    @Autowired
    RepositoryShip repositoryShip;

    @Autowired
    RepositorySalvo repositorySalvo;

    @RequestMapping("/players")
    public List<Map<String, Object>>  getPlayerAll(){
        DtoPlayer dtoPlayer= new DtoPlayer();
        return repositoryPlayer.findAll()
                .stream()
                .map(player -> dtoPlayer.makePlayerDTO(player))
                .collect(Collectors.toList());
    }

    @RequestMapping("/games")
    public List<Map<String, Object>>getGameAll(){
        DtoGame dtoGame= new DtoGame();
        return  repositoryGame.findAll()
                .stream()
                .map(game -> dtoGame.makeGameDTO(game))
                .collect(Collectors.toList());
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

    @RequestMapping("/game_view/{gameId}")
    public Map<String,Object> getGameView(@PathVariable long gameId){
        GamePlayer gamePlayer= repositoryGamePlayer.findById(gameId).get();
        DtoGamePlayer dtoGamePlayer= new DtoGamePlayer(gamePlayer);
    return dtoGamePlayer.makeGameViewDTO();
    }
}