package com.asie_back.token;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class TokenController {

    @PostMapping("/token/refresh")
    ResponseEntity validateSession(@Valid @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        String sessionId = "Token not found or expired";
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        HashMap<String, String> map = new HashMap<>();
        boolean valid = TokenRepository.validateUserToken(token);
        if(!valid){
            throw new ResponseStatusException(NOT_FOUND, sessionId);
        }
        map.put("token", TokenRepository.refreshToken(request));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
