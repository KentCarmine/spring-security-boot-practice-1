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

    private static final String ROLE_EMPLOYEE = "EMPLOYEE";
    private static final String ROLE_MANAGER = "MANAGER";
    private static final String ROLE_ADMIN = "ADMIN";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add users for in memory authentication

        User.UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("john").password("test123").roles(ROLE_EMPLOYEE))
                .withUser(users.username("mary").password("test123").roles(ROLE_EMPLOYEE, ROLE_MANAGER))
                .withUser(users.username("susan").password("test123").roles(ROLE_EMPLOYEE, ROLE_ADMIN));
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests() // Restrict access based on HttpServletRequest
                .antMatchers("/").hasRole(ROLE_EMPLOYEE) // allow Employees to see '/' page
                .antMatchers("/leaders/**").hasRole(ROLE_MANAGER) // allow Managers to see '/leaders' pages
                .antMatchers("/systems/**").hasRole(ROLE_ADMIN) // allow Admins to see '/systems' pages
                .anyRequest().authenticated() // Any request to the app must be authenticated (ie. Logged in)
                .and()
                .formLogin() // Start customizing form login process
                .loginPage("/showMyLoginPage") // Page that shows custom login form
                .loginProcessingUrl("/authenticateTheUser") // Page that processes custom login form submission
                                                            // Spring will handle this routing, you do not need to create
                                                            // a controller request mapping for this.
                                                            // Spring will also check the user id and password automatically
                .permitAll() // allow anyone to see the login page without logging in first
                .and()
                .logout().permitAll() // Expose default /logout URL and permit any to access it
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied"); // Use custom page for 403 forbidden errors
    }
}
