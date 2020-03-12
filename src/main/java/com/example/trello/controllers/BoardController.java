package com.example.trello.controllers;


import com.example.trello.entries.Board;
import com.example.trello.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/boards")
    List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
}
