package univpm.advprog.aule.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import univpm.advprog.aule.services.UserServiceDefault;

//Classe in cui andranno impostati i permessi dei vari utenti
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().
			antMatchers("/login").permitAll().
			antMatchers("/").permitAll().
			antMatchers("/instruments/**").hasAnyRole("ADMIN").
			antMatchers("/**").hasAnyRole("USER").
				and().formLogin().loginPage("/login").defaultSuccessUrl("/")
					.failureUrl("/login?error=true").permitAll().
				and().logout().logoutSuccessUrl("/") // NB se commentiamo
														// questa riga,
														// viene richiamata
														// /login?logout
					.invalidateHttpSession(true).permitAll().
			and().csrf().disable();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserServiceDefault();
	};

	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(this.passwordEncoder);
	}

}
