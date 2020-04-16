package com.example.trello.controllers;
import com.example.trello.entries.Action;
import com.example.trello.entries.Board;
import com.example.trello.services.ActionService;
import com.example.trello.services.BoardService;
import com.example.trello.services.TrelloService;
import com.example.trello.services.WebhookService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class TrelloController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private TrelloService trelloService;
//    @Autowired
//    private WebhookService webhookService;

    @PostMapping("/keytoken")
    public List<Board> addToken(@RequestParam String token, @RequestParam String key) {
        return trelloService.addTokenKey(token, key);
    }

    @PostMapping("/keytoken/boardUrl")
    public Optional<Board> fetchBoard(@RequestParam String token, @RequestParam String key, @RequestParam String boardUrl) throws IOException {
        return trelloService.addTokenKeyBoardUrl(token, key, boardUrl);
    }

    @GetMapping("/boards/action")
    Optional<Action> getAction(@RequestParam String token, @RequestParam String key,
                               @RequestParam String boardUrl, @RequestParam String actionId) {
        Optional<Board> board = boardService.getBoardByUrl(token,key,boardUrl);
        if(board.isPresent())
            return actionService.findByBoardIdAndActionId(board.get().getBoardId(),actionId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid board url");
    }


    @GetMapping("/boards/actions")
    List<Optional<Action>> getActionsByType(@RequestParam String token, @RequestParam String key,
                                            @RequestParam String boardUrl, @RequestParam(required = false) String type,
                                            @RequestParam(required = false) String from, @RequestParam(required = false) String to) {

        Optional<Board> board = boardService.getBoardByUrl(token,key,boardUrl);
        if(board.isPresent()){
            String boardId = board.get().getBoardId();

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
                        return actionService.getActionsForBoard(boardId);
                    else
                        return actionService.findByDateBefore(boardId,to);
                } else {
                    if(to==null)
                        return actionService.findAllByDateAfter(boardId, from);
                    else
                        return actionService.findAllByDateBetween(boardId, from,to);
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid board url");

    }


    @PostMapping("/trello/hook")
    void webhook(@RequestBody String request){

        //webhookService.validateWebhook(request);

        JSONObject jsonObject = new JSONObject(request);
        System.out.println(jsonObject);
        trelloService.listenWebhook(jsonObject);
    }


    @RequestMapping(value = "/trello/hook", method = {RequestMethod.GET})
    public HttpEntity<String> handleTestRequest () {

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("test-header", Arrays.asList("test-header-value"));

        HttpEntity<String> responseEntity = new HttpEntity<>("test body", headers);

        return responseEntity;
    }
}
