package com.codeoftheweb.salvo.DTO;

import com.codeoftheweb.salvo.Model.Ship;

import java.util.LinkedHashMap;
import java.util.Map;

public class DtoShip {
    private Map<String, Object> dto;
    private Ship ship;

    public DtoShip(){dto= new LinkedHashMap<>();}

    public DtoShip(Ship ship) {
        this.ship = ship;
        dto= new LinkedHashMap<>();
    }

    public Map<String, Object> getDto() {
        return dto;
    }

    public void setDto(Map<String, Object> dto) {
        this.dto = dto;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Map<String,  Object> makeShipDTO(Ship ship){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", ship.getId());
        dto.put("type",  ship.getType());
        dto.put("locations",  ship.getShipLocations());
        return dto;
    }

    public Map<String,  Object> makeShipDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", ship.getId());
        dto.put("type",  ship.getType());
        dto.put("locations",  ship.getShipLocations());
        return dto;
    }
}