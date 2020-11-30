package com.codeoftheweb.salvo.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private  int id;

    private Date date;

   @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
   private Set<GamePlayer> gamePlayers ;

   public Game() {  date = new Date(); gamePlayers= new LinkedHashSet<GamePlayer>(); }

   public int getId() {
        return id;
    }

   public Date getDate() {
        return date;
    }

   public void setDate(Date date) {
        this.date = date;
    }

   @Override
   public String toString() {
        return "Game{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }

   public Set<GamePlayer> getGamePlayers(){
        return gamePlayers;
   }

   public boolean addGamePlayer(GamePlayer newGamePlayer){
        return  gamePlayers.add(newGamePlayer);
   }
}