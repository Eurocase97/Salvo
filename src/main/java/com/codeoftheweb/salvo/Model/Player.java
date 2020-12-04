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

    private String password;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers ;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Score>  scores ;

    public Player(){
        scores= new LinkedHashSet<Score>();
        gamePlayers=new LinkedHashSet<GamePlayer>();
    }

    public Player(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password=password;
        gamePlayers=new LinkedHashSet<GamePlayer>();

    }

    public Player(String email, String password) {
        this.email = email;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public boolean addGamePlayer(GamePlayer newGamePlayer){
        return gamePlayers.add(newGamePlayer);
    }

    public Set<Score> getScores() { return scores;}

    public boolean addGameScore(Score newScore){
        return  scores.add(newScore);
    }

    public long getWonScore(){
        return this.getScores().stream().
                filter(score -> score.getScore()==1.0).count();
    }

    public long getLostScore(){
        return this.getScores().stream().
                filter(score -> score.getScore()==0.0).count();
    }

    public long getTiedScore(){
        return this.getScores().stream().
                filter(score -> score.getScore()==0.5).count();
    }

    public double getTotalScore(){
        return  getWonScore() + getLostScore() + getTiedScore() ;
    }



}