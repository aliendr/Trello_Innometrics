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

    public Optional<Action> findById(String id) {
        return actionRepository.findById(id);
    }


    public Optional<Action> findByBoardIdAndActionId(String boardId, String actionId) {
        return actionRepository.findByBoardIdAndActionId(boardId,actionId);
    }


    public List<Optional<Action>> findAllByTypeAndBoardId(String type, String boardId){
        return actionRepository.findAllByTypeAndBoardIdOrderByDate(type, boardId);
    }

    public List<Optional<Action>> findAllByTypeAndBoardIdAndDateBefore(String type, String boardId, String before){
        return actionRepository.findAllByTypeAndBoardIdAndDateBeforeOrderByDate(type, boardId, before);
    }

    public List<Optional<Action>> findAllByTypeAndBoardIdAndDateAfter(String type, String boardId, String after){
        return actionRepository.findAllByTypeAndBoardIdAndDateAfterOrderByDate(type, boardId, after);
    }

    public List<Optional<Action>> findAllByTypeAndBoardIdAndDateAfterAndDateBefore(String type, String boardId,String after,String before){
        return actionRepository.findAllByTypeAndBoardIdAndDateAfterAndDateBeforeOrderByDate(type, boardId, after,before);
    }


    public List<Optional<Action>> findByDateBefore(String boardId, String before){
        return actionRepository.findAllByBoardIdAndDateBeforeOrderByDate(boardId, before);
    }

    public List<Optional<Action>> findAllByDateAfter(String boardId, String after){
        return actionRepository.findAllByBoardIdAndDateAfterOrderByDate(boardId, after);
    }


    public List<Optional<Action>> findAllByDateBetween(String boardId, String after, String before){
        return actionRepository.findAllByBoardIdAndDateBetweenOrderByDate(boardId, after,before);
    }

    public List<Optional<Action>> getActionsForBoard(String boardId){
        return actionRepository.findAllByBoardId(boardId);
    }

}
