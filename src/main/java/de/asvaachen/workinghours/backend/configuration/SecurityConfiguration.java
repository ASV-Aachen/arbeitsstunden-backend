package de.asvaachen.workinghours.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;

import javax.servlet.http.HttpFilter;
import javax.sql.DataSource;
import java.security.Principal;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String AUTHORITY_USER = "USER";
    public static final String AUTHORITY_TAKEL = "TAKEL";
    public static final String ROLE_USER = "ROLE_" + AUTHORITY_USER;
    public static final String ROLE_TAKEL = "ROLE_" + AUTHORITY_TAKEL;


    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private DataSource dataSource;

    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT email as username, password, 1 FROM user_ WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT email as username, role FROM user_ WHERE email = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/seasons").hasAuthority(ROLE_TAKEL)
                    .antMatchers(HttpMethod.GET, "/api/seasons/*/export").hasAuthority(ROLE_TAKEL)
                    .antMatchers(HttpMethod.POST, "/api/members").hasAuthority(ROLE_TAKEL)
                    .antMatchers(HttpMethod.GET, "/api/members").hasAuthority(ROLE_TAKEL)
                    .antMatchers(HttpMethod.GET, "/api/members/takel").hasAuthority(ROLE_TAKEL)
                    .antMatchers(HttpMethod.POST, "/api/member/reduction").hasAuthority(ROLE_TAKEL)
                    .antMatchers(HttpMethod.POST, "/api/projectItems").hasAuthority(ROLE_TAKEL)
                    .antMatchers(HttpMethod.POST, "/api/projects").hasAuthority(ROLE_TAKEL)
                    .antMatchers(HttpMethod.POST, "/api/project").hasAuthority(ROLE_TAKEL)
                    .antMatchers(HttpMethod.POST, "/api/seasons").hasAuthority(ROLE_TAKEL)
                    .anyRequest().authenticated()
                .and()
                    .requestCache()
                    .requestCache(new NullRequestCache())
                .and()
                    .httpBasic();
        http.addFilterBefore(new SecurityServletFilter(), SecurityServletFilter.class)
                .authorizeRequests();
    }

    public boolean isTakel(Principal principal) {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.stream().findFirst().map(SimpleGrantedAuthority::getAuthority).get().equals(ROLE_TAKEL);

    }
}