package luv2code.springsecurity.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add users for in memory authentication

        User.UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("john").password("test123").roles("EMPLOYEE"))
                .withUser(users.username("mary").password("test123").roles("MANAGER"))
                .withUser(users.username("susan").password("test123").roles("ADMIN"));
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests() // Restrict access based on HttpServletRequest
                .anyRequest().authenticated() // Any request to the app must be authenticated (ie. Logged in)
                .and()
                .formLogin() // Start customizing form login process
                .loginPage("/showMyLoginPage") // Page that shows custom login form
                .loginProcessingUrl("/authenticateTheUser") // Page that processes custom login form submission
                                                            // Spring will handle this routing, you do not need to create
                                                            // a controller request mapping for this.
                                                            // Spring will also check the user id and password automatically
                .permitAll(); // allow anyone to see the login page without logging in first
    }
}
