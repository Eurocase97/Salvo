package com.codeoftheweb.salvo.Util;

import com.codeoftheweb.salvo.DTO.DtoHit;
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

    public static String stateGame(GamePlayer gamePlayer){
        if(gamePlayer.getGame().getGamePlayers().size()==2) {
            DtoHit dtoHit= new DtoHit();
           int mySelfImpact= dtoHit.makeDagame(gamePlayer);
           int opponentImpact= dtoHit.makeDagame(getOpponent(gamePlayer));
            if(mySelfImpact==17 && opponentImpact==17){
                return  "TIE";
            }else if(mySelfImpact==17){
                return "LOSE";
            }else if(opponentImpact==17){
                return "WON";
            }
        }
        if (gamePlayer.getShips().isEmpty()) {
            return "PLACESHIPS";
        }else if( (gamePlayer.getGame().getGamePlayers().size()==1) || getOpponent(gamePlayer).getShips().size()==0 ){
            return "WAITINGFOROPP";
        }else if(gamePlayer.getGame().getGamePlayers().size()==2  && gamePlayer.getSalvos().size()>getOpponent(gamePlayer).getSalvos().size()) {
            return "WAIT";
        }else{
            return "PLAY";
        }
    }
}