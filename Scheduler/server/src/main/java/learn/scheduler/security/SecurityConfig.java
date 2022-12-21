package learn.scheduler.security;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {
    private final JwtConverter converter;
    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {
        http.csrf().disable();
        http.cors();

        http.authorizeRequests()

                 .antMatchers(HttpMethod.GET, "/api/appointment").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.GET, "/api/appointment/find/customer/*").hasAnyAuthority("admin","customer")
                .antMatchers(HttpMethod.GET, "/api/appointment/find/*").hasAnyAuthority("admin","employee","customer")
                .antMatchers(HttpMethod.GET, "/api/appointment/*").hasAnyAuthority("admin","employee","customer")
                 .antMatchers(HttpMethod.GET, "/api/services/*").permitAll()
                 .antMatchers(HttpMethod.GET, "/api/services").permitAll()
                 .antMatchers(HttpMethod.GET, "/api/services/find/*").permitAll()
                 .antMatchers(HttpMethod.GET, "/api/appuser/usertype/*").hasAnyAuthority("admin",
                        "customer","employee")
                 .antMatchers(HttpMethod.GET, "/api/appuser/users/customers/enabled").hasAnyAuthority("admin")
                 .antMatchers(HttpMethod.GET, "/api/appuser/users/employees/enabled").hasAnyAuthority("admin","customer")
                .antMatchers(HttpMethod.GET, "/api/appuser/users/customers/disabled").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.GET, "/api/appuser/users/employees/disabled").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.GET, "/api/appuser/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/appuser").hasAnyAuthority("admin")
                 .antMatchers(HttpMethod.POST, "/api/appointment").hasAnyAuthority("admin",
                         "employee")
                 .antMatchers(HttpMethod.POST, "/api/services").hasAnyAuthority("admin")
                 .antMatchers(HttpMethod.POST, "/api/appuser/customer").permitAll()
                 .antMatchers(HttpMethod.POST, "/api/appuser/employee").hasAnyAuthority("admin")
                 .antMatchers(HttpMethod.POST, "/api/appuser").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/api/appointment/cancel/*").hasAnyAuthority("admin",
                        "employee","customer")
                 .antMatchers(HttpMethod.PUT, "/api/appointment/*").hasAnyAuthority("admin",
                         "employee","customer")
                 .antMatchers(HttpMethod.PUT, "/api/services/enable/*").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/api/services/disable/*").hasAnyAuthority("admin")
                 .antMatchers(HttpMethod.PUT, "/api/services/*").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/api/appuser/enable/*").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/api/appuser/disable/*").hasAnyAuthority("admin")
                 .antMatchers(HttpMethod.PUT, "/api/appuser/customer/*").permitAll()
                 .antMatchers(HttpMethod.PUT, "/api/appuser/*").authenticated()
                 .antMatchers(HttpMethod.DELETE, "/api/appointment/*").hasAnyAuthority("admin",
                         "employee")
                 .antMatchers(HttpMethod.DELETE, "/api/services/*").hasAnyAuthority("admin")
                 .antMatchers(HttpMethod.DELETE, "/api/appuser/*").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/encode/*").permitAll()
                .antMatchers(HttpMethod.POST, "/refresh").authenticated()
                 .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(manager(config), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}