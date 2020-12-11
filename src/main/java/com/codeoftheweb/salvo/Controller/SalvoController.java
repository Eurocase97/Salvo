package com.codeoftheweb.salvo.Controller;

import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Model.Player;
import com.codeoftheweb.salvo.Model.Salvo;
import com.codeoftheweb.salvo.Model.Ship;
import com.codeoftheweb.salvo.Repository.RepositoryGamePlayer;
import com.codeoftheweb.salvo.Repository.RepositoryPlayer;
import com.codeoftheweb.salvo.Repository.RepositorySalvo;
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
public class SalvoController {
    @Autowired
    RepositoryShip repositoryShip;

    @Autowired
    RepositoryGamePlayer repositoryGamePlayer;

    @Autowired
    RepositoryPlayer repositoryPlayer;

    @Autowired
    RepositorySalvo repositorySalvo;

    @RequestMapping(path = "/games/players/{gamePlayerID}/salvoes", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> setSalvoes(@PathVariable Long gamePlayerID, @RequestBody Salvo salvo, Authentication authentication) {
        Player p1=repositoryPlayer.findByEmail(authentication.getName());
        if(p1!=null){
            GamePlayer gp= repositoryGamePlayer.getOne(gamePlayerID);
            if(p1.getId()==gp.getPlayer().getId()){
                try {
                    GamePlayer opponent = getOpponent(gp);
                    if(opponent.getSalvos().size() >= gp.getSalvos().size()){
                        salvo.setTurn(gp.getSalvos().size()+1);
                        salvo.setGamePlayer(gp);
                        repositorySalvo.save(salvo);
                        return new ResponseEntity<>(Util.makeMap("OK", "You did it, you fired"), HttpStatus.CREATED);
                    }else{
                        return new ResponseEntity<>(Util.makeMap("error", "this not your turn"), HttpStatus.FORBIDDEN);
                    }
                }catch (Exception e){
                    return new ResponseEntity<>(Util.makeMap("error", "You have not opponent"), HttpStatus.UNAUTHORIZED);
                }
            }else {
                return new ResponseEntity<>(Util.makeMap("error", "This no your seccion"), HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(Util.makeMap("error", "This no your seccion"), HttpStatus.UNAUTHORIZED);
        }
    }

    private GamePlayer getOpponent(GamePlayer myself)throws Exception {
        GamePlayer opponent=null;
        for( GamePlayer g :myself.getGame().getGamePlayers()){
            if(g.getId()!=myself.getId()){
                opponent=g;
            }
        }
        return opponent;
    }
}