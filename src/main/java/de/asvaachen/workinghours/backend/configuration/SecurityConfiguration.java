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

import javax.sql.DataSource;
import java.security.Principal;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String ROLE_USER = "USER";
    public static final String ROLE_TAKEL = "TAKEL";

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
                .antMatchers(HttpMethod.POST, "/api/seasons").hasRole(ROLE_TAKEL)
                .antMatchers(HttpMethod.POST, "/api/members").hasRole(ROLE_TAKEL)
                .antMatchers(HttpMethod.GET, "/api/members").hasRole(ROLE_TAKEL)
                .antMatchers(HttpMethod.POST, "/api/member/reduction").hasRole(ROLE_TAKEL)
                .antMatchers(HttpMethod.POST, "/api/projectItems").hasRole(ROLE_TAKEL)
                .antMatchers(HttpMethod.POST, "/api/projects").hasRole(ROLE_TAKEL)
                .antMatchers(HttpMethod.POST, "/api/project").hasRole(ROLE_TAKEL)
                .antMatchers(HttpMethod.POST, "/api/seasons").hasRole(ROLE_TAKEL)
                .anyRequest().authenticated()
                .and()
                .requestCache()
                .requestCache(new NullRequestCache())
                .and()
                .httpBasic();
    }

    public boolean isTakel(Principal principal) {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.stream().findFirst().map(SimpleGrantedAuthority::getAuthority).get().equals("ROLE_" + ROLE_TAKEL);

    }
}