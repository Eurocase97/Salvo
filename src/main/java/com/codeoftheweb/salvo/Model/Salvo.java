package com.codeoftheweb.salvo.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayerId")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name="locations")
    private List<String>   locations;

    private int turn;

    public Salvo() {
        locations = new ArrayList<String>();
        turn=0;
    }

    public Salvo(GamePlayer gamePlayer, List  locations) {
        this.gamePlayer = gamePlayer;
        this.locations = locations;
        turn = 0;
    }

    public Long getId() {
        return id;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<String> getLocations() {
        return locations;
    }

    public boolean addLocation(String newLocation){
        return locations.add(newLocation);
    }
}