package com.asie_back.token;

import com.asie_back.config.JWTAuthorizationFilter;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TokenRepository {

    private static final LinkedHashMap<String, String> usersSessions = new LinkedHashMap<>();

    public static String getNewUserToken(String username, List<GrantedAuthority> grantedAuthorities){
        String token = JWTAuthorizationFilter.getJWTToken(username, grantedAuthorities);
        usersSessions.put(username, token);
        return token;
    }

    public static String refreshToken(HttpServletRequest request){
        String username = JWTAuthorizationFilter.getNameFromToken(request);
        String token = JWTAuthorizationFilter.resetTokenTimer(request);
        usersSessions.put(username, token);
        return token;
    }

    public static boolean validateUserToken(String token){
        boolean valid = false;
        for (Map.Entry<String, String> sessionEntry:usersSessions.entrySet()) {
            if(sessionEntry.getValue().equals(token)){
                valid = true;
                break;
            }
        }
        return valid;
    }
}
