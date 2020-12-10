package com.codeoftheweb.salvo.DTO;

import com.codeoftheweb.salvo.Model.Salvo;
import com.codeoftheweb.salvo.Model.Ship;

import java.util.LinkedHashMap;
import java.util.Map;

public class DtoSalvo {
    private Map<String, Object> dto;
    private Salvo salvo;

    public DtoSalvo(){dto= new LinkedHashMap<>();}

    public DtoSalvo(Salvo salvo) {
        this.salvo = salvo;
        dto= new LinkedHashMap<>();
    }

    public Map<String, Object> getDto() {
        return dto;
    }

    public Salvo getSalvo() {
        return salvo;
    }

    public void setSalvo(Salvo salvo) {
        this.salvo = salvo;
    }

    public Map<String,  Object> makeSalvoDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", salvo.getTurn());
        dto.put("player",  salvo.getGamePlayer().getPlayer().getId());
        dto.put("locations",  salvo.getLocations());
        return dto;
    }

    public Map<String,  Object> makeSalvoDTO(Salvo salvo){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", salvo.getTurn());
        dto.put("player",  salvo.getGamePlayer().getPlayer().getId());
        dto.put("locations",  salvo.getLocations());
        return dto;
    }
}