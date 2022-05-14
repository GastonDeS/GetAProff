package ar.edu.itba.paw.webapp.security.services.implementation;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.security.models.PawUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class PawUserDetailsService implements UserDetailsService {

//    @Autowired
    private final UserService userService;

//    public PawUserDetailsService() {
//        this.userSe/rvice = userService;
//    }

    @Autowired
    public PawUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        final Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER_STUDENT"));
        if(Boolean.TRUE.equals(user.isTeacher())) authorities.add(new SimpleGrantedAuthority("USER_TEACHER"));
        final String password = user.getPassword();
        return new PawUser(username, password, authorities, user.getUserid(), user.getName(),
                user.getMail(), user.isTeacher());
    }
}