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
	        .antMatchers("/tco-admin/forgot_password")
	        .permitAll()
	        .antMatchers("/tco-admin/retrieve_password")
	        .permitAll()
	        .antMatchers("/tco-admin/statistics/ordersSold")
	        .hasRole("SADMIN")
	        .antMatchers("/tco-admin/statistics/orderStatus")
	        .hasRole("SADMIN")
	        .antMatchers("/tco-admin/statistics/turnover")
	        .hasRole("SADMIN")
	        .antMatchers("/tco-admin/statistics/usersRegistered")
	        .hasRole("SADMIN")
	        .antMatchers("/tco-admin/user/list/grid")
	        .hasRole("SADMIN")
	        .antMatchers("/tco-admin/user/add")
	        .hasRole("SADMIN")
	        .antMatchers("/tco-admin/user/list")
	        .hasRole("SADMIN")
	        .antMatchers("/tco-admin/user/**")
	        .hasRole("SADMIN")
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
	        .logoutUrl("/tco-admin/admin_logout")
	        .logoutSuccessUrl("/tco-admin/admin_logout_success")
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
    	    .antMatchers("/user/profile")
    	    .authenticated()
    	    .antMatchers("/order/**")
    	    .authenticated()
    	    .antMatchers("/user/change-password")
    	    .authenticated()
    	    .anyRequest()
    	    .permitAll()
	        
	        .and()
	        .formLogin()
	        .loginPage("/login")
	        .loginProcessingUrl("/user_login")
	        .defaultSuccessUrl("/home", true)
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
