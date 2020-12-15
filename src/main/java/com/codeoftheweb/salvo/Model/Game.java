package com.codeoftheweb.salvo.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date date;

   @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
   private Set<GamePlayer> gamePlayers ;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<Score>  scores ;

   public Game() {
        date = new Date();
        gamePlayers= new LinkedHashSet<GamePlayer>();
        scores= new LinkedHashSet<Score>();
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

    public Set<Score> getScores() { return scores;}

    public boolean addGameScore(Score newScore){
        return  scores.add(newScore);
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }
}