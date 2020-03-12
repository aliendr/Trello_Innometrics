package com.example.trello.controllers;

import com.example.trello.entries.Action;
import com.example.trello.repositories.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ActionContoller {

    @Autowired
    private ActionRepository actionRepository;

//    ActionContoller(ActionRepository actionRepository){
//        this.actionRepository=actionRepository;
//    }

    @GetMapping("/actions")
    List<Action> getAllActions() {
        return actionRepository.findAll();
    }





}
