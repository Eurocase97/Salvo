package com.codeoftheweb.salvo.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers ;

    public Player(){}

    public Player(String userName, String email) { this.name = userName;this.email = email; gamePlayers=new LinkedHashSet<GamePlayer>(); }

    public Long getId() { return id; }

    public String getUserName() {
        return name;
    }

    public void setUserName(String userName) {
        this.name = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return name + " " + email;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public boolean addGamePlayer(GamePlayer newGamePlayer){
        return gamePlayers.add(newGamePlayer);
    }
}