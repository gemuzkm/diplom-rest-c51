package by.tms.diplomrestc51.configuration.security;

import by.tms.diplomrestc51.configuration.security.jwt.JwtConfiguration;
import by.tms.diplomrestc51.configuration.security.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@PropertySource("classpath:endpoint.properties")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${ADMIN_ENDPOINT}")
    private String ADMIN_ENDPOINT;

    @Value("${USER_ENDPOINT}")
    private String USER_ENDPOINT;

    @Value("${AUTH_ENDPOINT}")
    private String AUTH_ENDPOINT;

    @Value("${DB_H2_ENDPOINT}")
    private String DB_H2_ENDPOINT;
    @Value("#{'${PUBLIC_URLS}'.split(',')}")
    private String[] PUBLIC_URLS;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN")
                .antMatchers(USER_ENDPOINT).hasAuthority("USER")
                .antMatchers(DB_H2_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfiguration(jwtTokenProvider));
        http
                .headers().frameOptions().sameOrigin();
    }
}
