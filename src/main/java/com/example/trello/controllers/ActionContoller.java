package com.example.trello.controllers;

import com.example.trello.entries.Action;
import com.example.trello.services.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ActionContoller {

    @Autowired
    private ActionService actionService;

//    ActionContoller(ActionRepository actionRepository){
//        this.actionRepository=actionRepository;
//    }

    @GetMapping("/actions")
    List<Action> getAllActions() {
        return actionService.findAll();
    }

    @GetMapping("/actions/{id}")
    Optional<Action> getAction(@PathVariable String id) {
        return actionService.findById(id);
    }




}
