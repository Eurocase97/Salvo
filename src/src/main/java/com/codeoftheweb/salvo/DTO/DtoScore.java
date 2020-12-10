package com.codeoftheweb.salvo.DTO;

import com.codeoftheweb.salvo.Model.Score;

import java.util.LinkedHashMap;
import java.util.Map;

public class DtoScore {
    private Map<String, Object> dto;
    private Score score;

    public DtoScore() {
        dto= new LinkedHashMap<>();
    }

    public DtoScore(Score score) {
        this.score = score;
        dto= new LinkedHashMap<>();
    }

    public Map<String, Object> makeScoreDto(Score score){
        dto.put("player", score.getPlayer().getId());
        dto.put("score", score.getScore());
        dto.put("finishScore", score.getFinishDate());
        return dto;
    }
}