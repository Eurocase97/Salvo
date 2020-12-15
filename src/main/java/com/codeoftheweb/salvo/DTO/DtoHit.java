package com.codeoftheweb.salvo.DTO;

import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Model.Salvo;
import com.codeoftheweb.salvo.Util.Util;

import java.util.*;

public class DtoHit {
    public DtoHit() {}

    public List<Map<String, Object>> makeHitsDTO(GamePlayer gamePlayer) {
        List<Map<String, Object>>hits = new ArrayList<>();
        List<String>carrierLocations= new ArrayList<String>();
        List<String>battleshipLocations = new ArrayList<>();
        List<String>submarineLocations = new ArrayList<>();
        List<String>destroyerLocations = new ArrayList<>();
        List<String>patrolBoatLocations = new ArrayList<>();

        carrierLocations= Util.getLocatiosByType("carrier", gamePlayer);
        battleshipLocations=Util.getLocatiosByType("battleship", gamePlayer);
        submarineLocations=Util.getLocatiosByType("submarine", gamePlayer);
        destroyerLocations=Util.getLocatiosByType("destroyer",gamePlayer);
        patrolBoatLocations=Util.getLocatiosByType("patrolboat", gamePlayer);

        long carrierDamage=0;
        long battleShipDamage=0;
        long submarineDamage=0;
        long destroyerDamage=0;
        long patrolBoatDamage=0;

        for(Salvo salvoShot: Util.getOpponent(gamePlayer).getSalvos()){
            Map<String, Object>hitsMapPerTurn = new LinkedHashMap<>();
            Map<String, Object>damages = new LinkedHashMap<>();
            List<String>hitCellsList = new ArrayList<>();
            long missedShots = salvoShot.getLocations().size();
            long carrierHitsInTurn=0;
            long battleShipHitsInTurn=0;
            long submarineHitPerTurn=0;
            long destroyerHitPerTurn=0;
            long patrolBoatHitPerTurn=0;

            for(String location: salvoShot.getLocations()){
                if (carrierLocations.contains(location)){
                    hitCellsList.add(location);
                    missedShots--;
                    carrierHitsInTurn++;
                    carrierDamage++;
                }
                if (battleshipLocations.contains(location)){
                    hitCellsList.add(location);
                    missedShots--;
                    battleShipHitsInTurn++;
                    battleShipDamage++;
                }
                if (submarineLocations.contains(location)){
                    hitCellsList.add(location);
                    missedShots--;
                    submarineHitPerTurn++;
                    submarineDamage++;
                }
                if (destroyerLocations.contains(location)){
                    hitCellsList.add(location);
                    missedShots--;
                    destroyerHitPerTurn++;
                    destroyerDamage++;
                }
                if (patrolBoatLocations.contains(location)){
                    hitCellsList.add(location);
                    missedShots--;
                    patrolBoatHitPerTurn++;
                    patrolBoatDamage++;
                }
            }

            damages.put("carrierHits", carrierHitsInTurn);
            damages.put("battleshipHits", battleShipHitsInTurn);
            damages.put("submarineHits", submarineHitPerTurn);
            damages.put("destroyerHits", destroyerHitPerTurn);
            damages.put("patrolboatHits", patrolBoatHitPerTurn);
            damages.put("carrier", carrierDamage);
            damages.put("battleship", battleShipDamage);
            damages.put("submarine", submarineDamage);
            damages.put("destroyer", destroyerDamage);
            damages.put("patrolboat", patrolBoatDamage);

            hitsMapPerTurn.put("turn", salvoShot.getTurn());
            hitsMapPerTurn.put("hitLocations", hitCellsList);
            hitsMapPerTurn.put("damages", damages);
            hitsMapPerTurn.put("missed", missedShots);
            hits.add(hitsMapPerTurn);
        }
        return hits;
    }

    public int makeDagame(GamePlayer gamePlayer){
        List<String>carrierLocations= new ArrayList<String>();
        List<String>battleshipLocations = new ArrayList<>();
        List<String>submarineLocations = new ArrayList<>();
        List<String>destroyerLocations = new ArrayList<>();
        List<String>patrolBoatLocations = new ArrayList<>();

        carrierLocations= Util.getLocatiosByType("carrier", gamePlayer);
        battleshipLocations=Util.getLocatiosByType("battleship", gamePlayer);
        submarineLocations=Util.getLocatiosByType("submarine", gamePlayer);
        destroyerLocations=Util.getLocatiosByType("destroyer",gamePlayer);
        patrolBoatLocations=Util.getLocatiosByType("patrolboat", gamePlayer);

        int countImpact=0;

        for(Salvo salvoShot: Util.getOpponent(gamePlayer).getSalvos()){

            for(String location: salvoShot.getLocations()){
                if (carrierLocations.contains(location)){
                    countImpact++;
                }
                if (battleshipLocations.contains(location)){
                    countImpact++;
                }
                if (submarineLocations.contains(location)){
                    countImpact++;
                }
                if (destroyerLocations.contains(location)){
                    countImpact++;
                }
                if (patrolBoatLocations.contains(location)){
                    countImpact++;
                }
            }
        }
        return countImpact++;
    }
}