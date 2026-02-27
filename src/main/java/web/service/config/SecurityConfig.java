package web.service.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .addFilterBefore(schemaReplaceFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/actuator").hasAnyAuthority("admin")
                .antMatchers("/**").hasAnyAuthority("admin", "user")
                .antMatchers("/").hasAnyAuthority("admin", "user")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
            .and().csrf().disable()
            .build();
    }

    public Filter schemaReplaceFilter() {
        return new Filter() {

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                HttpServletRequest servletRequest = (HttpServletRequest) request;
                logger.debug("----- LocalName: {}, LocalPort: {}, RemoteAddr: {}, RemoteHost: {}, RemotePort: {}, Scheme: {}, ServerName: {}, ServerPort: {}", 
                        servletRequest.getLocalName(), servletRequest.getLocalPort(), servletRequest.getRemoteAddr(), servletRequest.getRemoteHost(), servletRequest.getRemotePort(), 
                        servletRequest.getScheme(), servletRequest.getServerName(), servletRequest.getServerPort());
                logger.warn("Registered access to login page: requestPath: {}, query: {}, method: {}", 
                        servletRequest.getPathInfo(), servletRequest.getQueryString(), servletRequest.getMethod());
                chain.doFilter(new ServletRequestWrapper(servletRequest), 
                        response);
            }};
    }

}
