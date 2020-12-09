package com.codeoftheweb.salvo.Controller;

import com.codeoftheweb.salvo.DTO.DtoGame;
import com.codeoftheweb.salvo.DTO.DtoPlayer;
import com.codeoftheweb.salvo.Model.Player;
import com.codeoftheweb.salvo.Repository.RepositoryGame;
import com.codeoftheweb.salvo.Repository.RepositoryPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class GameController {

    static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
    @Autowired
    RepositoryPlayer repositoryPlayer;

    @Autowired
    RepositoryGame repositoryGame;

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public Map<String, Object> getGameAll(Authentication authentication) {
        Map<String,  Object>  dto = new LinkedHashMap<>();

        if(isGuest(authentication)){
            dto.put("player", "Guest");
        }else{
            Player player  = repositoryPlayer.findByEmail(authentication.getName());
            DtoPlayer playerDTO   =   new DtoPlayer(player);
            dto.put("player", playerDTO.makePlayerDTO());
        }
        dto.put("games", repositoryGame.findAll()
                .stream()
                .map(game -> {
                    DtoGame gameDTO =   new DtoGame(game);
                    return  gameDTO.makeGameDTO();
                })
                .collect(Collectors.toList()));
        return dto;
    }

}
