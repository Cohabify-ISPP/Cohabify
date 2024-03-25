package org.ispp4.cohabify.authentication;

import java.util.Collection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class GoogleAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;
    
    public GoogleAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        
        Claims claims = Jwts.parserBuilder().build().parseClaimsJws(principal.toString()).getBody();
        JSONParser parser = new JSONParser();
        String credentialsJson = claims.get("credentials", String.class);   
        JSONObject credentialsObject = (JSONObject) parser.parse(credentialsJson);
    
    //  Obtener el valor de sub del objeto credentials
        String sub = (String) credentialsObject.get("sub");

        this.principal = sub;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}