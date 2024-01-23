package com.spring.jwt2.service;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private JwtEncoder jwtEncoder;
    public String generateToken(Authentication auth){
        Instant now = Instant.now();
        Instant expirationTime = now.plusSeconds(30);
        String scope = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet
                                    .builder()
                                    .issuer("self")
                                    .issuedAt(now)
                                    .expiresAt(expirationTime)
                                    .subject(auth.getName())
                                    .claim("roles", scope)
                                    .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

    }
}
