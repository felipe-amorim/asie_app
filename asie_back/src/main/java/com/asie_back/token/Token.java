package com.asie_back.token;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Token {
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    public void setToken(String session) {
        this.token = session;
    }

    public String getToken() {
        return token;
    }
}
