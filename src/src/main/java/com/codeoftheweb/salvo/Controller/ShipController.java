package com.codeoftheweb.salvo.Controller;

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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShipController {

    @Autowired
    RepositoryShip repositoryShip;

    @Autowired
    RepositoryGamePlayer repositoryGamePlayer;

    @Autowired
    RepositoryPlayer repositoryPlayer;

    @RequestMapping(path = "/games/players/{gamePlayerID}/ships", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> setShips(@PathVariable Long gamePlayerID, @RequestBody List<Ship> ships, Authentication authentication) {
        Player player = repositoryPlayer.findByEmail(authentication.getName());
        GamePlayer gemaGamePlayer= repositoryGamePlayer.findById(gamePlayerID).get();
        if(player.getId()== gemaGamePlayer.getPlayer().getId()){
            if(gemaGamePlayer.getShips().size()+ships.size()<=5){
                setGamePlyerListShips(ships, gemaGamePlayer);
                repositoryGamePlayer.save(gemaGamePlayer);
                return new ResponseEntity("Saved ships", HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(Util.makeMap("Error", "Ships full"), HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>(Util.makeMap("Error", "This no your seccion"), HttpStatus.UNAUTHORIZED);
        }
    }

    private  void setGamePlyerListShips(List<Ship> ships, GamePlayer gamePlayerId) {
        for (Ship ship : ships) {
            ship.setGamePlayer(gamePlayerId);
        }
    }

}


