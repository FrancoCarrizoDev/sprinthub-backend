package com.sprinthub.sprinthub.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public String getProtectedInfo() {
        return "Este es un recurso protegido";
    }
}