package com.asie_back.auth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Auth {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Please provide an username")
    private String username;

    @NotEmpty(message = "Please provide a password")
    private String password;

    private String authorities;

    public Auth(){

    }

    public Auth(String username, String password, String authorities){
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public void setAuthorities(String authorities){
        this.authorities = authorities;
    }

    public String getAuthorities(){
        return authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
