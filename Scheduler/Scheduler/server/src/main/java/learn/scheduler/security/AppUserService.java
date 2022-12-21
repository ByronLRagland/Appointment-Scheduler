package learn.scheduler.security;

import learn.scheduler.data.AppUserJdbcTemplateRepository;
import learn.scheduler.model.AppUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnWebApplication
public class AppUserService implements UserDetailsService {
    private final AppUserJdbcTemplateRepository repository;

    public AppUserService(AppUserJdbcTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = repository.findByUsername(username);

        if (user == null || !user.isEnabled()) {
            throw new UsernameNotFoundException("username " + username + " not found.");
        }

        return user;
    }
}