package com.asie_back.auth;

import com.asie_back.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class AuthController {
    @Autowired
    private AuthRepository repository;

    private String getUserAuthorities(Auth auth){
        List<Auth> auths = repository.findAll();
        String authorities = "";
        for (Auth existingAuth:auths) {
            if(existingAuth.getUsername().equalsIgnoreCase(auth.getUsername())&&existingAuth.getPassword().equals(auth.getPassword())){
                authorities = existingAuth.getAuthorities();
                break;
            }
        }
        return authorities;
    }

    @PostMapping("/auth")
    ResponseEntity authenticateUser(@Valid @RequestBody Auth auth) {
        String userAuth = getUserAuthorities(auth);
        if(userAuth.length()>0){
            HashMap<String, String> map = new HashMap<>();
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(userAuth);
            map.put("token", TokenRepository.getNewUserToken(auth.getUsername(), grantedAuthorities));
            return ResponseEntity.status(HttpStatus.OK).body(map);
        }
        throw new ResponseStatusException(NOT_FOUND, "Username or password incorrect");
    }





}
