package com.codeoftheweb.salvo.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayerId")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name="locations")
    private List<String> locations;

    private String type;

    public Ship() {locations = new ArrayList<String>();}

    public Ship(GamePlayer gamePlayer, String type, List locations) {
        this.locations= locations;
        this.gamePlayer = gamePlayer;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getShipLocations() {
        return locations;
    }

    public boolean addShipLocations(String shipLocation) {
        return  locations.add(shipLocation) ;
    }

    public void setLocations(List locations) {
        this.locations = locations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}