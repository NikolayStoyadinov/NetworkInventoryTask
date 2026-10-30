
package group.networkinventorytask.company.inventory.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class Security {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth

                        // Admin can perform all operations
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN")

                        // Normal users can only perform GET requests
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole("USER", "ADMIN")

                        // All other operations require admin privileges
                        .requestMatchers("/api/v1/**").hasRole("ADMIN")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsService(
            DataSource dataSource) {

        JdbcUserDetailsManager manager =
                new JdbcUserDetailsManager(dataSource);

        // READ USERS
        manager.setUsersByUsernameQuery(
                "SELECT username, password, enabled " +
                        "FROM dbo.api_users WHERE username = ?"
        );

        // READ AUTHORITIES
        manager.setAuthoritiesByUsernameQuery(
                "SELECT username, authority " +
                        "FROM dbo.api_authorities WHERE username = ?"
        );

        // CHECK IF USER EXISTS
        manager.setUserExistsSql(
                "SELECT COUNT(*) FROM dbo.api_users WHERE username = ?");

        // CREATE USER
        manager.setCreateUserSql(
                "INSERT INTO dbo.api_users " +
                        "(username, password, enabled) VALUES (?, ?, ?)"
        );

        // CREATE USER AUTHORITY
        manager.setCreateAuthoritySql(
                "INSERT INTO dbo.api_authorities " +
                        "(username, authority) VALUES (?, ?)"
        );

        //DELETE USER
        manager.setDeleteUserAuthoritiesSql(
                "DELETE FROM dbo.api_authorities WHERE username = ?");

        manager.setDeleteUserSql(
                "DELETE FROM dbo.api_users WHERE username = ?");

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        }
    }

