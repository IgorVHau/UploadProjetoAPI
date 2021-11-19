package org.serratec.ecommerce.config;

//import org.serratec.ecomerce.servico.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
//	@Autowired
//	CustomUserDetailsService customUserDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("user").password(passwordEncoder().encode("123456")).roles("usuario")
		.and()
		.withUser("admin").password(passwordEncoder().encode("321")).roles("admin")
		//role = admin -> ROLE_admin
		.and()
		.withUser("master").password(passwordEncoder().encode("abc")).roles("admin");	
//		
//		auth.userDetailsService(customUserDetailsService)
//		.passwordEncoder(passwordEncoder());

	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//		.httpBasic()
//		.and()
//		.authorizeRequests()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.csrf().disable()
//		.formLogin().disable()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
	//
	//Criar cliente e visualizar todos os produtos pelo nome são os únicos métodos
	//que não necessitam de métodos. .antMatchers().authenticated()
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        .authorizeRequests()
        .antMatchers("/")
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/console/**")
        .permitAll();
        httpSecurity
        .csrf()
        .disable();
        httpSecurity
        .headers()
        .frameOptions()
        .disable();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
