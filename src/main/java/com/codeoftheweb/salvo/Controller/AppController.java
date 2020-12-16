package com.codeoftheweb.salvo.Controller;


import com.codeoftheweb.salvo.DTO.*;
import com.codeoftheweb.salvo.Model.Game;
import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Model.Player;
import com.codeoftheweb.salvo.Model.Score;
import com.codeoftheweb.salvo.Repository.*;
import com.codeoftheweb.salvo.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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

    @RequestMapping(path = "/game_view/{ID}", method = RequestMethod.GET )
    public ResponseEntity<Map<String, Object>> getGamePlayerView(@PathVariable long ID, Authentication authentication) {
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error", "Not Logged in"), HttpStatus.UNAUTHORIZED);
        }
        Long playerLogged = repositoryPlayer.findByEmail(authentication.getName()).getId();
        Long playerCheck = repositoryGamePlayer.getOne(ID).getPlayer().getId();

        if (playerLogged != playerCheck){
            return new ResponseEntity<>(Util.makeMap("error", "This is not your game"), HttpStatus.FORBIDDEN);
        }

        DtoGamePlayer dtoGame_View = new DtoGamePlayer();
        GamePlayer gamePlayer = repositoryGamePlayer.getOne(ID);
        if(Util.stateGame(gamePlayer) == "WON"){
            if(gamePlayer.getGame().getScores().size()<2) {
                Set<Score> scores = new HashSet<>();
                Score score1 = new Score();
                score1.setPlayer(gamePlayer.getPlayer());
                score1.setGame(gamePlayer.getGame());
                score1.setFinishDate(Date.from(Instant.now()));
                score1.setScore(1D);
                repositoryScore.save(score1);
                Score score2 = new Score();
                score2.setPlayer(Util.getOpponent(gamePlayer).getPlayer());
                score2.setGame(gamePlayer.getGame());
                score2.setFinishDate(Date.from(Instant.now()));
                score2.setScore(0D);
                repositoryScore.save(score2);
                scores.add(score1);
                scores.add(score2);
                Util.getOpponent(gamePlayer).getGame().setScores(scores);
            }
        }
        if(Util.stateGame(gamePlayer) == "TIE"){
            if(gamePlayer.getGame().getScores().size()<2) {
                Set<Score> scores = new HashSet<Score>();
                Score score1 = new Score();
                score1.setPlayer(gamePlayer.getPlayer());
                score1.setGame(gamePlayer.getGame());
                score1.setFinishDate(Date.from(Instant.now()));
                score1.setScore(0.5D);
                repositoryScore.save(score1);
                Score score2 = new Score();
                score2.setPlayer(Util.getOpponent(gamePlayer).getPlayer());
                score2.setGame(gamePlayer.getGame());
                score2.setFinishDate(Date.from(Instant.now()));
                score2.setScore(0.5D);
                repositoryScore.save(score2);
                scores.add(score1);
                scores.add(score2);
                Util.getOpponent(gamePlayer).getGame().setScores(scores);
            }
        }
        return new ResponseEntity<>(dtoGame_View.makeGameViewDTO(repositoryGamePlayer.getOne(ID)), HttpStatus.ACCEPTED);
    }
}