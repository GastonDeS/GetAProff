package ar.edu.itba.paw.webapp.security.api.models;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BasicAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;
    private AuthenticationTokenDetails tokenDetails;

    public BasicAuthenticationToken(Object principal, Object credentials,
                                    Collection<? extends GrantedAuthority> authorities,
                                    AuthenticationTokenDetails tokenDetails) {
        super(principal, credentials, authorities);
        this.tokenDetails = tokenDetails;
    }


    public BasicAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return (String)super.getPrincipal();
    }

    @Override
    public String getCredentials() {
        return (String)super.getCredentials();
    }

    @Override
    public Object getDetails() {
        return this.tokenDetails;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }
}
