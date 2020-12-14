package com.codeoftheweb.salvo.Util;

import com.codeoftheweb.salvo.Model.GamePlayer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Util {

    public static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }

    public static GamePlayer getOpponent(GamePlayer myself){
        GamePlayer opponent= new GamePlayer();
        for( GamePlayer g :myself.getGame().getGamePlayers()){
            if(g.getId()!=myself.getId()){
                opponent=g;
            }
        }
        return opponent;
    }


      public static List<String> getLocatiosByType(String type, GamePlayer self){
        return  self.getShips().size()  ==  0 ? new ArrayList<>() : self.getShips().stream().filter(ship -> ship.getType().equals(type)).findFirst().get().getShipLocations();
    }

    public static Map<String, Integer> shipTypes = Stream.of(
            new Object[][]{
                    {"carrier", 5},
                    {"battleship", 4},
                    {"submarine", 3},
                    {"destroyer", 3},
                    {"patrolboat", 2}
            }).collect(toMap(data -> (String)data[0], data -> (Integer)data[1]));
}
