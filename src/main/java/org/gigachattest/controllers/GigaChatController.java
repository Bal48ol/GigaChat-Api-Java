package org.gigachattest.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gigachattest.GigaChat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GigaChatController {

    @Value("${gigachat.authUrl}")
    private String auth;

    @Value("${gigachat.rqUID}")
    private String rqUID;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        String hello = "я тебя победю, Гигачат!";
        return ResponseEntity.ok(hello);
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generate(){
        GigaChat gigaChat = new GigaChat(auth, rqUID);

        String question = "What is the capital of France?";
        double temperature = 0.8;

        String response = gigaChat.askQuestion(question, temperature);

        return ResponseEntity.ok(response);
    }
}
