package com.example.demo.Services;

import java.util.Date;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.MyUserDetails;

@Service
public class JwtServices {
	private static final String SECRET = "11111111111111111111111111111111";
    private static final long EXPIRE_TIME = 86400000000L;
	
    public String generateTokenLogin(Authentication authentication) {
        MyUserDetails userPrincipal = (MyUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                 .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        String userName = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }

	public boolean validateJwtToken(String authToken) {
	        try {
	            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
	            return true;
	        } catch (SignatureException e) {
	           e.printStackTrace();
	        } catch (MalformedJwtException e) {
	        	e.printStackTrace();
	        } catch (ExpiredJwtException e) {
	        	e.printStackTrace();
	        } catch (UnsupportedJwtException e) {
	        	e.printStackTrace();;
	        } catch (IllegalArgumentException e) {
	        	e.printStackTrace();
	        }
	        return false;

	    }

}
