package com.facens.devopsproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facens.devopsproject.config.Generated;

@RestController
@Generated
public class HelloController {
    
    @GetMapping
    public String helloWorld() {
        return "Hello, World!";
    }

}
