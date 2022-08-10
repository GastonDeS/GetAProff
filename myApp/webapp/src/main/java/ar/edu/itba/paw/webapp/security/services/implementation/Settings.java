package ar.edu.itba.paw.webapp.security.services.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class Settings {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.clockSkew}")
    private Long clockSkew;

    @Value("${jwt.audience}")
    private String audience;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.claimNames.authorities}")
    private String authoritiesClaimName;


    public String getSecret() {
        return secret;
    }

    public Long getClockSkew() {
        return clockSkew;
    }

    public String getAudience() {
        return audience;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getAuthoritiesClaimName() {
        return authoritiesClaimName;
    }

}
