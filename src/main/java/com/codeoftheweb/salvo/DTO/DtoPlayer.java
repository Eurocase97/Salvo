package com.codeoftheweb.salvo.DTO;

import com.codeoftheweb.salvo.Model.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class DtoPlayer {
    private  Map<String, Object> dto;
    private  Player player;

    public DtoPlayer(){dto= new LinkedHashMap<>();}

    public DtoPlayer(Player player) {
        this.player = player;
        dto= new LinkedHashMap<>();
    }

    public Map<String, Object> getDto() {
        return dto;
    }

    public void setDto(Map<String, Object> dto) {
        this.dto = dto;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map<String, Object> makePlayerDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email",  player.getEmail());
        dto.put("nombre",  player.getUserName());
        return  dto;
    }

    public Map<String, Object> makePlayerDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.player.getId());
        dto.put("email", this.player.getEmail());
        dto.put("nombre",  this.player.getUserName());
        return  dto;
    }

    public Map<String, Object> makePlayerScoreDTO(Player p){
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> score = new LinkedHashMap<>();
        dto.put("id", p.getId());
        dto.put("email", p.getEmail());
        score.put("total", p.getTotalScore());
        score.put("won", p.getWonScore());
        score.put("lost", p.getLostScore());
        score.put("tied", p.getTiedScore());
        dto.put("score", score);
      return dto;
    }

}