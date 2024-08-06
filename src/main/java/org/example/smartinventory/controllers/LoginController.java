package org.example.smartinventory.controllers;

import org.example.smartinventory.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/api/login")
public class LoginController
{
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService)
    {
        this.loginService = loginService;
    }
}
