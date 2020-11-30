package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.Model.*;
import com.codeoftheweb.salvo.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(RepositoryShip repositoryShipShip , RepositoryPlayer repositoryPlayer,
									  RepositoryGame repositoryGame, RepositoryGamePlayer repositoryGamePlayer,
									  RepositorySalvo repositorySalvo) {
		return (args) -> {

			Player p1= new Player("Jack Bauer ", "j.bauer@ctu.gov ");
			Player p2= new Player("Chloe O'Brian ", "O'Brian");
			Player p3= new Player("Kim", "Bauer");
			Player p4= new Player("David", "Palmer");
			Player p5= new Player("Michelle", "Dessler");

			repositoryPlayer.save(p1);
			repositoryPlayer.save(p2);
			repositoryPlayer.save(p3);
			repositoryPlayer.save(p4);
			repositoryPlayer.save(p5);

			Game g1= new Game();
			Game g2= new Game();
			Game g3= new Game();
			Game g4= new Game();

			repositoryGame.save(g1);
			repositoryGame.save(g2);
			repositoryGame.save(g3);
			repositoryGame.save(g4);

			GamePlayer gp1 =  new GamePlayer(g1, p1);
			GamePlayer gp2 =  new GamePlayer(g1, p2);
			GamePlayer gp3 = new GamePlayer(g2, p3);
			GamePlayer gp4 = new GamePlayer(g2, p1);

			repositoryGamePlayer.save(gp1);
			repositoryGamePlayer.save(gp2);
			repositoryGamePlayer.save(gp3);
			repositoryGamePlayer.save(gp4);

			List l1= new ArrayList<String>();
			l1.add("h2");
			l1.add("h3");
			l1.add("h4");
			List l2= new ArrayList<String>();
			l2.add("a2");
			l2.add("a3");
			l2.add("a4");
			List l3= new ArrayList<String>();
			l3.add("b5");
			l3.add("b6");
			l3.add("b7");

			Ship sp1= new Ship(gp1, "destructor",l1);
            Ship sp2= new Ship(gp1, "destructor",l2);
            Ship sp3= new Ship(gp2, "destructor",l3);

            repositoryShipShip.save(sp1);
			repositoryShipShip.save(sp2);
			repositoryShipShip.save(sp3);

			ArrayList<String> l4= new ArrayList<String>();
			ArrayList<String> l5= new ArrayList<String>();
			l4.add("i3");
			l5.add("e3");
			Salvo s1= new Salvo(gp1, l4, 1);
			Salvo s2= new Salvo(gp2, l5, 1);

			repositorySalvo.save(s1);
			repositorySalvo.save(s2);
		};
	}
}