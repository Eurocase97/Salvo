package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.Model.*;
import com.codeoftheweb.salvo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(RepositoryShip repositoryShip , RepositoryPlayer repositoryPlayer,
									  RepositoryGame repositoryGame, RepositoryGamePlayer repositoryGamePlayer,
									  RepositorySalvo repositorySalvo, RepositoryScore repositoryScore) {
		return (args) -> {

			Player p1= new Player("Jack Bauer", "j.bauer@ctu.gov", passwordEncoder().encode("123"));
			Player p2= new Player("Chloe O'Brian ", "juan@gmail",passwordEncoder().encode("123"));
			Player p3= new Player("kun", "a@gmail",passwordEncoder().encode("123") );
			repositoryPlayer.save(p1);
			repositoryPlayer.save(p2);
			repositoryPlayer.save(p3);

			Game g1= new Game();
			Game g2= new Game();

			repositoryGame.save(g1);
			repositoryGame.save(g2);

			GamePlayer gp1 =  new GamePlayer(g1, p1);
			GamePlayer gp2 =  new GamePlayer(g1, p2);
			GamePlayer gp3 =  new GamePlayer(g2, p3);


			repositoryGamePlayer.save(gp1);
			repositoryGamePlayer.save(gp2);
			repositoryGamePlayer.save(gp3);

			List<String> l1= new ArrayList<String>();
			l1.add("H2");
			l1.add("H3");
			l1.add("H4");
			l1.add("H5");
			l1.add("H6");
			List<String>  l2= new ArrayList<String>();
			l2.add("A2");
			l2.add("A3");
			l2.add("A4");
			l2.add("A1");
			List<String>  l3= new ArrayList<String>();
			l3.add("i5");
			l3.add("i6");
			l3.add("i7");
			List<String>  l4= new ArrayList<String>();
			l4.add("B5");
			l4.add("B6");
			l4.add("B7");
			List<String>  l5= new ArrayList<String>();
			l5.add("c5");
			l5.add("c6");


			Ship sp1= new Ship(gp1, "carrier",l1);
            Ship sp2= new Ship(gp1, "battleship",l2);
			Ship sp3= new Ship(gp1, "submarine",l3);
			Ship sp4= new Ship(gp1, "destroyer",l4);
			Ship sp5= new Ship(gp1, "patrolboat",l5);


			Ship sp6= new Ship(gp2, "carrier",l1);
			Ship sp7= new Ship(gp2, "battleship",l2);
			Ship sp8= new Ship(gp2, "submarine",l3);
			Ship sp9= new Ship(gp2, "destroyer",l4);
			Ship sp10= new Ship(gp2, "patrolboat",l5);

            repositoryShip.save(sp1);
			repositoryShip.save(sp2);
			repositoryShip.save(sp3);
			repositoryShip.save(sp4);
			repositoryShip.save(sp5);
			repositoryShip.save(sp6);
			repositoryShip.save(sp7);
			repositoryShip.save(sp8);
			repositoryShip.save(sp9);
			repositoryShip.save(sp10);

			/*
			ArrayList<String> l6= new ArrayList<String>();
			ArrayList<String> l7= new ArrayList<String>();
			l6.add("I3");
			l7.add("E3");
			Salvo s1= new Salvo(gp1, l4);

			repositorySalvo.save(s1);
			*/
			/*
			Score sc1= new Score(g1, p1);
			Score sc2= new Score(g1, p2);

			sc1.setFinishDate(1606844770);
			sc1.setScore(0.5);

			sc2.setFinishDate(1606844770);
			sc2.setScore(0.1);

			repositoryScore.save(sc1);
			repositoryScore.save(sc2);

			 */
		};
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	RepositoryPlayer repositoryPlayer;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = repositoryPlayer.findByEmail(inputName);
			if (player != null) {
				return new User(player.getEmail(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	 }
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/web/**").permitAll()
				.antMatchers("/api/game_view/*").hasAuthority("USER")
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/api/games").permitAll();

		http.formLogin()
				.usernameParameter("name")
				.passwordParameter("pwd")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}