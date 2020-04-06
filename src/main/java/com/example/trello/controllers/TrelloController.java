package com.example.trello.controllers;
import com.example.trello.TrelloRunner;
import com.example.trello.entries.Action;
import com.example.trello.entries.Board;
import com.example.trello.services.ActionService;
import com.example.trello.services.BoardService;
import com.example.trello.services.TrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TrelloController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private TrelloRunner trelloRunner;
    @Autowired
    private ActionService actionService;
    @Autowired
    private TrelloService trelloService;

    @PostMapping("/keytoken")
    public List<Board> addToken(@RequestParam String token, @RequestParam String key) {
        return trelloService.addTokenKey(token, key);
    }

    @PostMapping("/keytoken/boardUrl")
    public Board fetchBoard(@RequestParam String token, @RequestParam String key, @RequestParam String boardUrl) {
//        trelloRunner.run(key, token, boardUrl);
//        return boardService.findByUrl(boardUrl);
        return trelloService.addTokenKeyBoardUrl(token, key, boardUrl);
    }

    @GetMapping("/boards")
    Optional<Board> getBoard(@RequestParam String id) {
        return boardService.findById(id);
    }

    @GetMapping("/boards/action")
    Optional<Action> getAction(@RequestParam String boardId, @RequestParam String actionId) {
        return actionService.findByBoardIdAndActionId(boardId,actionId);
    }


    @GetMapping("/boards/actions")
    List<Optional<Action>> getActionsByType(@RequestParam String boardId, @RequestParam(required = false) String type,
                                            @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        if(type!=null){
            if(from==null){
                if(to==null)
                    return actionService.findAllByTypeAndBoardId(type, boardId);
                else
                    return actionService.findAllByTypeAndBoardIdAndDateBefore(type,boardId,to);
            } else {
                if(to==null)
                    return actionService.findAllByTypeAndBoardIdAndDateAfter(type,boardId,from);
                else
                    return actionService.findAllByTypeAndBoardIdAndDateAfterAndDateBefore(type, boardId, from,to);
            }

        } else {
            if(from==null){
                if(to==null)
                    return boardService.findAllActionsForBoard(boardId);
                else
                    return actionService.findByDateBefore(to);
            } else {
                if(to==null)
                    return actionService.findAllByDateAfter(from);
                else
                    return actionService.findAllByDateBetween(from,to);
            }
        }

    }
}
