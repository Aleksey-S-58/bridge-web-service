package web.service.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.warn("----- Atempt to enter with username: not blank - {}", StringUtils.isNotBlank(username));
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("username is blank!");
        }
        List<User> users = new ArrayList<>();
        // users.add(new User("admin", passwordEncoder.encode("admin"), "admin")); // TODO remove it!
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Login wasn't found in the repository!");
        }
        User user = users.get(0);
        return new org.springframework.security.core.userdetails.User(
                    user.getName(), user.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }

}
