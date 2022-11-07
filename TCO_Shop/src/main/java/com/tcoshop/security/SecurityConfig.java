package com.tcoshop.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpStatusRequestRejectedHandler;
import org.springframework.security.web.firewall.RequestRejectedHandler;

import com.tcoshop.entity.User;
import com.tcoshop.service.UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }	
    
    @Bean
    RequestRejectedHandler rejectedHandler() {
        return new HttpStatusRequestRejectedHandler();
    }
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(username -> {
//			try {
//				User user = userService.findByUsername(username);
//				String password = passwordUtil.getBCryptPasswordEncoder().encode(user.getPassword());
//				boolean activated = user.getStatus();
//				String role = user.getRole().getId();
//				return org.springframework.security.core.userdetails.User.withUsername(username)
//						.password(password).disabled(!activated).roles(role).build();
//			} catch (NoSuchElementException e) {
//				e.printStackTrace();
//				throw new UsernameNotFoundException(username + " not found!");
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new RuntimeException(e);
//			}
//		});
//	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().disable().csrf().disable();
//		http.authorizeHttpRequests()
//		.antMatchers("/user/update").authenticated()
//		.antMatchers("/checkout/**").authenticated()
//		.anyRequest().permitAll();
//		http.formLogin()
//		.loginPage("/login")
//		.loginProcessingUrl("/login/authenticated")
//		.defaultSuccessUrl("/home", false)
//		.failureUrl("/login/failed")
//		.usernameParameter("username")
//		.passwordParameter("password");
//		http.logout().logoutUrl("/Logout").logoutSuccessUrl("/logout");
//		http.exceptionHandling().accessDeniedHandler(null);
//	}
	
    @Configuration
	@Order(1)
	public static class AdminConfigurationAdapter extends WebSecurityConfigurerAdapter{
	    
	    @Autowired
	    UserService userService;	    	    
	    
	    @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(username -> {
              try {
                  User user = userService.findByUsername(username);
                  String password = encoder().encode(user.getPassword());
                  boolean activated = user.getStatus();
                  String role = user.getRole().getId();
                  return org.springframework.security.core.userdetails.User.withUsername(username)
                          .password(password).disabled(!activated).roles(role).build();
              } catch (UsernameNotFoundException e) {
                  e.printStackTrace();
                  throw new UsernameNotFoundException(username + " not found!");
              } catch (Exception e) {
                  e.printStackTrace();
                  throw new RuntimeException(e);
              }
          });
        }
	    
	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http
	        .antMatcher("/tco-admin/**")
	        .authorizeRequests()
	        .antMatchers("/tco-admin/login")
	        .permitAll()
	        .antMatchers("/tco-admin/**")
	        .hasAnyRole("SADMIN","ADMIN","SHIPPER")
	        
	        .and()
	        .formLogin()
	        .loginPage("/tco-admin/login")
	        .loginProcessingUrl("/tco-admin/admin_login")
	        .failureForwardUrl("/tco-admin/admin_login/failed")
	        .defaultSuccessUrl("/tco-admin/dashboard", true)
	        .usernameParameter("username")
	        .passwordParameter("password")
	        
	        .and()
	        .logout()
	        .logoutUrl("/admin_logout")
	        .logoutSuccessUrl("/tco-admin/admin_logout")
	        .deleteCookies("JSESSIONID")
	       
	        .and()
	        .exceptionHandling()
	        .accessDeniedPage("/403")
	        
	        .and()
	        .cors().disable()
	        .csrf().disable();
	        
	    }
	}
	
    @Configuration
	@Order(2)
	public static class ClientConfigurationAdapter extends WebSecurityConfigurerAdapter{
	    
	    @Autowired
        UserService userService;       
        
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(username -> {
	          try {
	              User user = userService.findByUsername(username);
	              String password = encoder().encode(user.getPassword());
	              boolean activated = user.getStatus();
	              String role = user.getRole().getId();
	              return org.springframework.security.core.userdetails.User.withUsername(username)
	                      .password(password).disabled(!activated).roles(role).build();
	          } catch (UsernameNotFoundException e) {
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
	        http.authorizeHttpRequests()
    	    .antMatchers("/user/update")
    	    .authenticated()
    	    .antMatchers("/checkout/**")
    	    .authenticated()
    	    .anyRequest()
    	    .permitAll()
	        
	        .and()
	        .formLogin()
	        .loginPage("/login")
	        .loginProcessingUrl("/user_login")
	        .defaultSuccessUrl("/home", false)
	        .failureForwardUrl("/login/failed")	        
	        .usernameParameter("username")
	        .passwordParameter("password")
	        
	        .and()
	        .logout()
	        .logoutUrl("/user_logout")
	        .logoutSuccessUrl("/logout")
	        .deleteCookies("JSESSIONID")
	        
	        .and()
	        .exceptionHandling()
	        .accessDeniedPage("/403")
	        
	        .and()
	        .cors().disable()
	        .csrf().disable();
	        
	    }
	}

}
