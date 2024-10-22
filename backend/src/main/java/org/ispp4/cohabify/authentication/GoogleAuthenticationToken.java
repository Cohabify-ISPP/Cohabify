package org.ispp4.cohabify.authentication;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collection;

import org.json.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

public class GoogleAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;
    
    public GoogleAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities, String[] keyStrings) {
        super(authorities);
        JSONObject jsonObject = new JSONObject(principal.toString());
        jsonObject = (JSONObject) jsonObject.get("token");
        String credentials = (String) jsonObject.get("credential");
        this.principal = null;
        try {
            PublicKey publicKey = decryptKey(keyStrings[0]); 
            Claims claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(credentials).getBody();
            String sub = claims.get("sub", String.class); 
            this.principal = sub;
            setAuthenticated(true);

        } catch (SignatureException errorK0){
            try {
                PublicKey publicKey = decryptKey(keyStrings[1]); 
                Claims claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(credentials).getBody();
                String sub = claims.get("sub", String.class); 
                this.principal = sub;
                setAuthenticated(true);
            } catch (SignatureException errorK1) {
                errorK1.printStackTrace();
            }
        }
    }

    public PublicKey decryptKey(String keyString){
        try {
            String certPEM = keyString
                    .replace("-----BEGIN CERTIFICATE-----", "")
                    .replace("-----END CERTIFICATE-----", "")
                    .replaceAll("\\s+", ""); 

            byte[] certBytes = Base64.getDecoder().decode(certPEM);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certBytes));
            return cert.getPublicKey();
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GoogleAuthenticationToken(UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
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