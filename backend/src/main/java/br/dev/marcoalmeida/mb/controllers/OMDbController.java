package br.dev.marcoalmeida.mb.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OMDbController {

    @GetMapping("/private")
    public String home(){
        return "Hello World !!";
    }

    @GetMapping("/public")
    public String publicResource(){
        return "Access allowed to all !!";
    }
}
