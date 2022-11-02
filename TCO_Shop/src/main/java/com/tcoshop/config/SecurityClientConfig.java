package com.tcoshop.config;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tcoshop.entity.User;
import com.tcoshop.service.UserService;
import com.tcoshop.util.PasswordUtil;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityClientConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserService userService;
	@Autowired
	PasswordUtil passwordUtil;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				User user = userService.findByUsername(username);
				String password = passwordUtil.getBCryptPasswordEncoder().encode(user.getPassword());
				boolean activated = user.getStatus();
				String role = user.getRole().getId();
				return org.springframework.security.core.userdetails.User.withUsername(username)
						.password(password).disabled(!activated).roles(role).build();
			} catch (NoSuchElementException e) {
				e.printStackTrace();
				throw new UsernameNotFoundException(username + " not found!");
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable().csrf().disable();
		http.authorizeHttpRequests()
		.antMatchers("/user/update").authenticated()
		.antMatchers("/checkout/**").authenticated()
		.anyRequest().permitAll();
		http.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/login/authenticated")
		.defaultSuccessUrl("/home", false)
		.failureUrl("/login/failed")
		.usernameParameter("username")
		.passwordParameter("password");
		http.logout().logoutUrl("/Logout").logoutSuccessUrl("/logout");
		http.exceptionHandling().accessDeniedHandler(null);
	}
}
