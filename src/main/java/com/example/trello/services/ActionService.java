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


    public List<Optional<Action>> findByTypeOfAction(String type){
        return actionRepository.findAllByTypeOrderByDate(type);
    }

    public List<Optional<Action>> findAllByTypeAndBoardId(String type, String boardId){
        return actionRepository.findAllByTypeAndBoardIdOrderByDate(type, boardId);
    }

    public List<Optional<Action>> findAllByTypeAndBoardIdAndDateBefore(String type, String boardId, String before){
        return actionRepository.findAllByTypeAndBoardIdAndDateBefore(type, boardId, before);
    }

    public List<Optional<Action>> findAllByTypeAndBoardIdAndDateAfter(String type, String boardId, String after){
        return actionRepository.findAllByTypeAndBoardIdAndDateAfter(type, boardId, after);
    }

    public List<Optional<Action>> findAllByTypeAndBoardIdAndDateAfterAndDateBefore(String type, String boardId,String after,String before){
        return actionRepository.findAllByTypeAndBoardIdAndDateAfterAndDateBefore(type, boardId, after,before);
    }



    public List<Optional<Action>> findAllByDateBetween(String after, String before){
        return actionRepository.findAllByDateBetweenOrderByDate(after,before);
    }

    public List<Optional<Action>> findAllByDateAfter(String after){
        return actionRepository.findAllByDateAfterOrderByDate(after);
    }

    public List<Optional<Action>> findByDateBefore(String before){
        return actionRepository.findAllByDateBeforeOrderByDate(before);
    }

    public Optional<Action> findByBoardIdAndActionId(String boardId, String actionId) {
        return actionRepository.findByBoardIdAndActionId(boardId,actionId);
    }

}
