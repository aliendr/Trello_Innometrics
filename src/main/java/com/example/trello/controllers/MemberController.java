package com.example.trello.controllers;

import com.example.trello.TrelloRunner;
import com.example.trello.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @Autowired
    private TrelloRunner trelloRunner;
//    @Autowired
//    private MemberService memberService;

    @PostMapping("/keytoken")
    public void addToken(@RequestParam String token, @RequestParam String key) {
//        memberService.setToken(token, key);
        trelloRunner.run(key, token);


    }
}
