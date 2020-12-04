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
	public CommandLineRunner initData(RepositoryShip repositoryShipShip , RepositoryPlayer repositoryPlayer,
									  RepositoryGame repositoryGame, RepositoryGamePlayer repositoryGamePlayer,
									  RepositorySalvo repositorySalvo, RepositoryScore repositoryScore) {
		return (args) -> {

			Player p1= new Player("Jack Bauer", "j.bauer@ctu.gov", passwordEncoder().encode("123"));
			Player p2= new Player("Chloe O'Brian ", "O'Brian","1234");
			Player p3= new Player("Kim", "Bauer","1234");
			Player p4= new Player("David", "Palmer","1234");
			Player p5= new Player("Michelle", "Dessler","1234");

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

			List<String> l1= new ArrayList<String>();
			l1.add("H2");
			l1.add("H3");
			l1.add("H4");
			List<String>  l2= new ArrayList<String>();
			l2.add("A2");
			l2.add("A3");
			l2.add("A4");
			List<String>  l3= new ArrayList<String>();
			l3.add("B5");
			l3.add("B6");
			l3.add("B7");

			Ship sp1= new Ship(gp1, "destructor",l1);
            Ship sp2= new Ship(gp1, "destructor",l2);
            Ship sp3= new Ship(gp2, "destructor",l3);

            repositoryShipShip.save(sp1);
			repositoryShipShip.save(sp2);
			repositoryShipShip.save(sp3);

			ArrayList<String> l4= new ArrayList<String>();
			ArrayList<String> l5= new ArrayList<String>();
			l4.add("I3");
			l5.add("E3");
			Salvo s1= new Salvo(gp1, l4, 1);
			Salvo s2= new Salvo(gp2, l5, 1);

			repositorySalvo.save(s1);
			repositorySalvo.save(s2);

			Score sc1= new Score(g1, p1);
			Score sc2= new Score(g1, p2);

			sc1.setFinishDate(1606844770);
			sc1.setScore(0.5);

			sc2.setFinishDate(1606844770);
			sc2.setScore(0.1);

			repositoryScore.save(sc1);
			repositoryScore.save(sc2);
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