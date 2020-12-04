package com.codeoftheweb.salvo.DTO;

import com.codeoftheweb.salvo.Model.Game;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DtoGame {
    private  Map<String, Object> dto;
    private  Game game;

    public DtoGame(){dto= new LinkedHashMap<>();}

    public DtoGame(Game game) {
        this.game = game;
        dto= new LinkedHashMap<>();
    }

    public Map<String, Object> getDto() {
        return dto;
    }

    public void setDto(Map<String, Object> dto) {
        this.dto = dto;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Map<String,  Object> makeGameDTO(Game game){
       DtoGamePlayer dtoGamePlayer= new DtoGamePlayer();
       Map<String, Object> dto = new LinkedHashMap<>();
       dto.put("id", game.getId());
       dto.put("created",  game.getDate());
       dto.put("gamePlayers",  game.getGamePlayers()
                .stream()
                .map(gamePlayer -> dtoGamePlayer.makeGamePlayerDTO(gamePlayer))
                .collect(Collectors.toList()));
        dto.put("scores", game.getGamePlayers()
                .stream()
                .flatMap(gamePlayer -> gamePlayer.getPlayer().getScores()
                        .stream()
                        .map(score -> {
                            DtoScore scoreDTO = new DtoScore();
                            return scoreDTO.makeScoreDto(score);
                        }))
                .collect(Collectors.toList()));
        return dto;
    }

    public Map<String, Object> makeGameDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.game.getId());
        dto.put("created", this.game.getDate());
        dto.put("gamePlayers", this.game.getGamePlayers()
                .stream()
                .map(gamePlayer -> {
                    DtoGamePlayer gamePlayerDTO = new DtoGamePlayer(gamePlayer);
                    return gamePlayerDTO.makeGamePlayerDTO();})
                .collect(Collectors.toList()));
        dto.put("scores", this.game.getGamePlayers()
                .stream()
                .flatMap(gamePlayer -> gamePlayer.getPlayer().getScores()
                        .stream()
                        .map(score -> {
                            DtoScore scoreDTO = new DtoScore();
                            return scoreDTO.makeScoreDto(score);
                        }))
                .collect(Collectors.toList()));
        return dto;
    }
}