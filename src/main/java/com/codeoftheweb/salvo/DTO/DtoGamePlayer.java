package com.codeoftheweb.salvo.DTO;

import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Util.Util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DtoGamePlayer {
    private  Map<String, Object> dto;
    private GamePlayer gamePlayer;

    public DtoGamePlayer(){dto= new LinkedHashMap<>();}

    public DtoGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        dto= new LinkedHashMap<>();
    }

    public Map<String, Object> getDto() {
        return dto;
    }

    public void setDto(Map<String, Object> dto) {
        this.dto = dto;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer){
        DtoPlayer dtoPlayer= new DtoPlayer();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", dtoPlayer.makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }

    public Map<String, Object> makeGamePlayerDTO(){
        DtoPlayer dtoPlayer= new DtoPlayer();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.gamePlayer.getId());
        dto.put("player", dtoPlayer.makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }

    public Map<String,  Object> makeGameViewDTO(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> mapHit = new LinkedHashMap<>();
        DtoHit hits= new DtoHit();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getDate());
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers()
                .stream()
                .map(gp -> {
                    DtoGamePlayer gamePlayerDTO = new DtoGamePlayer(gp);
                    return gamePlayerDTO.makeGamePlayerDTO();
                }).collect(Collectors.toList()));
        dto.put("ships",  gamePlayer.getShips().stream().map(ship  -> {
            DtoShip shipDTO = new DtoShip(ship);
            return  shipDTO.makeShipDTO();
        }).collect(Collectors.toList()));
        dto.put("salvoes",  gamePlayer.getGame().getGamePlayers()
                .stream()
                .flatMap(gamePlayer1 -> gamePlayer1.getSalvos()
                        .stream()
                        .map(salvo -> {DtoSalvo dtoSalvo = new DtoSalvo();
                                      return  dtoSalvo.makeSalvoDTO(salvo);
                        }))
                .collect(Collectors.toList()));
        if(gamePlayer.getGame().getGamePlayers().size()==2) {
            mapHit.put("self", hits.makeHitsDTO(gamePlayer));
            mapHit.put("opponent", hits.makeHitsDTO(Util.getOpponent(gamePlayer)));
        }else {
            mapHit.put("self", new ArrayList<>());
            mapHit.put("opponent", new ArrayList<>());
        }
        dto.put("hits", mapHit);
        dto.put("gameState", Util.stateGame(gamePlayer));
        return dto;
    }

    public Map<String,  Object> makeGameViewDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> mapHit = new LinkedHashMap<>();
        DtoHit hits= new DtoHit();
        dto.put("id", this.gamePlayer.getGame().getId());
        dto.put("created", this.gamePlayer.getGame().getDate());
        dto.put("gamePlayers", this.gamePlayer.getGame().getGamePlayers()
                .stream()
                .map(gamePlayer -> {
                    DtoGamePlayer gamePlayerDTO = new DtoGamePlayer(gamePlayer);
                    return gamePlayerDTO.makeGamePlayerDTO();
                }).collect(Collectors.toList()));
        dto.put("ships",  this.gamePlayer.getShips().stream().map(ship  -> {
            DtoShip shipDTO = new DtoShip(ship);
            return  shipDTO.makeShipDTO();
        }).collect(Collectors.toList()));
        dto.put("salvoes",  gamePlayer.getGame().getGamePlayers()
                .stream()
                .flatMap(gamePlayer1 -> gamePlayer1.getSalvos()
                        .stream()
                        .map(salvo -> {DtoSalvo dtoSalvo = new DtoSalvo();
                            return  dtoSalvo.makeSalvoDTO(salvo);
                        }))
                .collect(Collectors.toList()));
        if(gamePlayer.getGame().getGamePlayers().size()==2) {
            mapHit.put("self", hits.makeHitsDTO(gamePlayer));
            mapHit.put("opponent", hits.makeHitsDTO(Util.getOpponent(gamePlayer)));
        }else {
            mapHit.put("self", new ArrayList<>());
            mapHit.put("opponent", new ArrayList<>());
        }
        dto.put("hits", mapHit);
        dto.put("gameState", Util.stateGame(gamePlayer));
        return dto;
    }
}