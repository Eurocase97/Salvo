package com.codeoftheweb.salvo.Controller;

import com.codeoftheweb.salvo.DTO.DtoShip;
import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Model.Player;
import com.codeoftheweb.salvo.Model.Ship;
import com.codeoftheweb.salvo.Repository.RepositoryGame;
import com.codeoftheweb.salvo.Repository.RepositoryGamePlayer;
import com.codeoftheweb.salvo.Repository.RepositoryPlayer;
import com.codeoftheweb.salvo.Repository.RepositoryShip;
import com.codeoftheweb.salvo.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ShipController {

    @Autowired
    RepositoryShip repositoryShip;

    @Autowired
    RepositoryGamePlayer repositoryGamePlayer;

    @Autowired
    RepositoryPlayer repositoryPlayer;
    /*  NO SIRVE SE USA EN GEMEVIEW
    @RequestMapping(path = "api/games/players/gamePlayerID/ships", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getShips(@PathVariable Long gamePlayerID){
        GamePlayer gamePlayer= repositoryGamePlayer.findById(gamePlayerID).get();
        if(gamePlayer != null){
            DtoShip dtoShip = new DtoShip();
            Map<String, Object> dto = new LinkedHashMap<>();
            for(Ship s : gamePlayer.getShips()){
                dto.put("ship", dtoShip.makeShipDTO(s));
            }
            return  new ResponseEntity<>(Util.makeMap("Code","ok"), HttpStatus.ACCEPTED);
        }else {
           return  new ResponseEntity<>(Util.makeMap("error","Not find id"), HttpStatus.UNAUTHORIZED);
        }
    }*/

    @RequestMapping(path = "/games/players/{gamePlayerID}/ships", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> setShips(@PathVariable Long gamePlayerID, @RequestBody List<Ship> ships, Authentication authentication) {
        Player player = repositoryPlayer.findByEmail(authentication.getName());
        GamePlayer gameGamePlayer= repositoryGamePlayer.findById(gamePlayerID).get();
        if(player.getId()== gameGamePlayer.getPlayer().getId()){
            if(gameGamePlayer.getShips().size()+ships.size()<=5){
                setGamePlyerListShips(ships, gameGamePlayer);
                repositoryShip.saveAll(ships);
                return new ResponseEntity(Util.makeMap("OK", "ok"), HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(Util.makeMap("error", "Ships full"), HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>(Util.makeMap("error", "This no your seccion"), HttpStatus.UNAUTHORIZED);
        }
    }

    private  void setGamePlyerListShips(List<Ship> ships, GamePlayer gamePlayerId) {
        for (Ship ship : ships) {
            ship.setGamePlayer(gamePlayerId);
        }
    }
}