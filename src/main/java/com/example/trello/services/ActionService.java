package com.example.trello.services;

import com.example.trello.entries.Action;
import com.example.trello.repositories.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    public List<Action> findAll(){
        return actionRepository.findAll();
    }

    public Optional<Action> findById(String id) {
        return actionRepository.findById(id);
    }

}
