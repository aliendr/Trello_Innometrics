package com.example.trello.controllers;


import com.example.trello.entries.Action;
import com.example.trello.entries.Board;
import com.example.trello.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/boards")
    List<Board> getAllBoards() {
        return boardService.findAll();
    }

    @GetMapping("/boards/{id}")
    Optional<Board> getBoard(@PathVariable String id) {
        return boardService.findById(id);
    }

    @GetMapping("/boards/{id}/actions")
    List<Optional> getBoardActions(@PathVariable String id) {
        return boardService.findAllActionsForBoard(id);
    }
}
