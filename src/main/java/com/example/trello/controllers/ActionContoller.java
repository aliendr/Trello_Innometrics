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

    @GetMapping("/actions")
    List<Action> getAllActions() {
        return actionService.findAll();
    }

    @GetMapping("/actions/{id}")
    Optional<Action> getAction(@PathVariable String id) {
        return actionService.findById(id);
    }

    @GetMapping("/actions/type/{type}")
    List<Optional<Action>> getActionsByType(@PathVariable String type) { return actionService.findByTypeOfAction(type); }

    @GetMapping("actions/date/fromTo/{from}/{to}")
    List<Optional<Action>> getActionsByDateFromTo(@PathVariable String from, @PathVariable String to){
        return actionService.findAllByDateBetween(from,to);
    }

    @GetMapping("actions/date/to/{to}")
    List<Optional<Action>> getActionsByDateTo(@PathVariable String to){
        return actionService.findByDateBefore(to);
    }

    @GetMapping("actions/date/from/{from}")
    List<Optional<Action>> getActionsByDateFrom(@PathVariable String from){
        return actionService.findAllByDateAfter(from);
    }
}