package com.example.trello.services;

import com.example.trello.entries.Action;
import com.example.trello.entries.Board;
import com.example.trello.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ActionService actionService;

    public Optional<List<Board>> getAllBoards(String token, String key){
        return boardRepository.findAllByTokenAndKey(token,key);
    }

    public Optional<Board> getBoardByUrl(String token, String key, String url){
        return boardRepository.findByTokenAndKeyAndBoardUrl(token, key,url);
    }

    public List<Optional<Action>> findAllActionsForBoard(String token, String key, String url) {
        Optional<Board> board = boardRepository.findByTokenAndKeyAndBoardUrl(token, key,url);
        if(board.isPresent()){
            return actionService.getActionsForBoard(board.get().getBoardId());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid board url");
    }

}
