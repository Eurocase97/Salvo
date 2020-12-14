package com.codeoftheweb.salvo.Controller;

import com.codeoftheweb.salvo.DTO.DtoGame;
import com.codeoftheweb.salvo.DTO.DtoPlayer;
import com.codeoftheweb.salvo.Model.Game;
import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Model.Player;
import com.codeoftheweb.salvo.Repository.RepositoryGame;
import com.codeoftheweb.salvo.Repository.RepositoryGamePlayer;
import com.codeoftheweb.salvo.Repository.RepositoryPlayer;
import com.codeoftheweb.salvo.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    RepositoryPlayer repositoryPlayer;

    @Autowired
    RepositoryGame repositoryGame;

    @Autowired
    RepositoryGamePlayer repositoryGamePlayer;

    @RequestMapping(path = "/games",   method = RequestMethod.GET)
    public Map<String, Object> getGameAll(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        if (Util.isGuest(authentication)) {
            dto.put("player", "Guest");
        } else {
            Player player = repositoryPlayer.findByEmail(authentication.getName());
            DtoPlayer playerDTO = new DtoPlayer(player);
            dto.put("player", playerDTO.makePlayerDTO());
        }
        dto.put("games", repositoryGame.findAll()
                .stream()
                .map(game -> {
                    DtoGame gameDTO = new DtoGame(game);
                    return gameDTO.makeGameDTO();
                })
                .collect(Collectors.toList()));
        return dto;
    }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createGame(Authentication authentication) {
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
        }
        Player player = repositoryPlayer.findByEmail(authentication.getName());
        if (player == null) {
            return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
        }
        Game game = new Game();
        repositoryGame.save(game);
        GamePlayer gamePlayer = repositoryGamePlayer.save(new GamePlayer(game, player));
        return new ResponseEntity<>(Util.makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/game/{idGame}/players",   method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable long idGame, Authentication authentication) {
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error", "Is guest"), HttpStatus.UNAUTHORIZED);
        }

        Player player = repositoryPlayer.findByEmail(authentication.getName());

        Game gameToJoin = repositoryGame.getOne(idGame);

        if (gameToJoin == null) {
            return new ResponseEntity<>(Util.makeMap("error", "No such game."), HttpStatus.FORBIDDEN);
        }

        long gamePlayersCount = gameToJoin.getGamePlayers().size();

        if (gamePlayersCount == 1) {

            GamePlayer gamePlayer = repositoryGamePlayer.save(new GamePlayer(gameToJoin, player));
            return new ResponseEntity<>(Util.makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(Util.makeMap("error", "Game is full!"), HttpStatus.FORBIDDEN);
        }
    }
}


