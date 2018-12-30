package com.football.gateway.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by IntelliJ IDEA.
 * User: truongnq
 * Date: 2018-12-16
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public class LoginRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
