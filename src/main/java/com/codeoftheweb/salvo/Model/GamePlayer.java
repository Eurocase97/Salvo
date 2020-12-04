package com.codeoftheweb.salvo.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gameId")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="playerId")
    private Player player;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    private Set<Ship> ships;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    private Set<Salvo> salvos;

    public  GamePlayer(){ships = new LinkedHashSet<Ship>(); salvos= new LinkedHashSet<Salvo>();};

    public GamePlayer(Game g, Player player){
        this.game=g;
        this.player=player;
        date= new Date();
        ships= new LinkedHashSet<Ship>();
        salvos= new LinkedHashSet<Salvo>();
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public boolean addShip(Ship newShip){ return ships.add(newShip); }

    public Set<Salvo> getSalvos() {return salvos;}

    public boolean addSalvo(Salvo newSalvo){ return salvos.add(newSalvo); }

    public Set<Score> getScore(){ return this.game.getScores(); }
}