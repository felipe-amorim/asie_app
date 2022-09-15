package com.asie_back.config;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final String PREFIX = "Bearer ";
    private static final String SECRET = "mySecretKey";

    public static String getJWTToken(String username, List<GrantedAuthority> grantedAuthorities) {
        String token = Jwts
                .builder()
                .setId("ASIE")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET.getBytes()).compact();

        return "Bearer " + token;
    }

    public static String getNameFromToken(HttpServletRequest request){
        Claims claims = validateToken(request);
        String token = "error";
        return claims.getSubject();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String resetTokenTimer(HttpServletRequest request){
        Claims claims = validateToken(request);
        String token = "error";
        if (claims.get("authorities") != null) {
            List<String> authoritiesList = (List) claims.get("authorities");
            StringBuilder authorities = new StringBuilder();
            for (String a:authoritiesList) {
                authorities.append(a).append(",");
            }
            String name = claims.getSubject();
            token = getJWTToken(name, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities.toString()));
        }
        return token;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (checkJWTToken(request, response)) {
                Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    private static Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    /**
     * Authentication method in Spring flow
     *
     * @param claims
     */
    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
            return false;
        return true;
    }
}
