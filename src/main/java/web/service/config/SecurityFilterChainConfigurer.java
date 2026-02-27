package web.service.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessEventPublishingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
public class SecurityFilterChainConfigurer {

    public SecurityFilterChainConfigurer(SecurityFilterChain chain) {
        LogoutFilter logoutFilter = null;
        for (Filter c : chain.getFilters()) {
            if (c instanceof AbstractAuthenticationProcessingFilter) {
                UsernamePasswordAuthenticationFilter f = (UsernamePasswordAuthenticationFilter) c;
                SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler() {};
                handler.setRedirectStrategy(new RedirectStrategyWrapper());
                handler.setDefaultFailureUrl("/login?error");
                f.setAuthenticationFailureHandler(handler);
                SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler() {};
                successHandler.setRedirectStrategy(new RedirectStrategyWrapper());
                successHandler.setDefaultTargetUrl("/");
                f.setAuthenticationSuccessHandler(successHandler);
            }
            if (c instanceof LogoutFilter) {
                logoutFilter = (LogoutFilter) c;
            }
        };
        if (logoutFilter != null) {
            chain.getFilters().remove(logoutFilter);
            SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
            logoutSuccessHandler.setRedirectStrategy(new RedirectStrategyWrapper());
            chain.getFilters().add(new LogoutFilter(logoutSuccessHandler, 
                    new CompositeLogoutHandler(new SecurityContextLogoutHandler(), new LogoutSuccessEventPublishingLogoutHandler())));
        }
    }
}
