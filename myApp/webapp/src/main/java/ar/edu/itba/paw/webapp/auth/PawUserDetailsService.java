package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class PawUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> user = us.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("exception.login");
        }
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role r : user.get().getUserRoles()) {
            authorities.add(new SimpleGrantedAuthority(r.getRole()));
        }
        return new org.springframework.security.core.userdetails.User(email, user.get().getPassword(), authorities);
    }
}
