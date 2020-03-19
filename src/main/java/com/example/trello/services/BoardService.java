package com.example.trello.services;

import com.example.trello.entries.Action;
import com.example.trello.entries.Board;
import com.example.trello.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ActionService actionService;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Optional<Board> findById(String id) {
        return boardRepository.findById(id);
    }

    public List<Optional> findAllActionsForBoard(String id) {
        Optional<Board> board = boardRepository.findById(id);
        List<String> listOfActionsId = board.get().getListOfActions();
        List<Optional> actions = new ArrayList<>();
        for (int i = 0; i < listOfActionsId.size(); i++) {
            actions.add(actionService.findById(listOfActionsId.get(i)));
        }
        return actions;
    }

}
