package com.example.trello.controllers;
import com.example.trello.entries.Action;
import com.example.trello.entries.Board;
import com.example.trello.services.ActionService;
import com.example.trello.services.BoardService;
import com.example.trello.services.TrelloService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@RestController
@RequestMapping("/api")
public class TrelloController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private TrelloService trelloService;



    @ApiOperation(
            value = "get list of borads belonging to that token and key"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = Board.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })

    @PostMapping("/keytoken")
    public List<Board> addToken(@RequestParam String token, @RequestParam String key) {
        return trelloService.addTokenKey(token, key);
    }



    @ApiOperation(
            value = "fetch a borad",
            notes = "Method collects actions from given board and connects webhook for it "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = Board.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = IOException.class
            )
    })
    @PostMapping("/keytoken/boardUrl")
    public Optional<Board> fetchBoard(@RequestParam String token, @RequestParam String key, @RequestParam String boardUrl) throws IOException {
        return trelloService.addTokenKeyBoardUrl(token, key, boardUrl);
    }



    @ApiOperation(
            value = "get fetched borad by url",
            notes = "Method returns a board if it is already fetched"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = Board.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @GetMapping("/board")
    Optional<Board> getBoard(@RequestParam String token, @RequestParam String key,
                               @RequestParam String boardUrl) {
        Optional<Board> board = boardService.getBoardByUrl(token,key,boardUrl);
        if(board.isPresent())
            return board;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid board url");
    }



    @ApiOperation(
            value = "get fetched action",
            notes = "Method returns action if it`s board is fetched and there exists such action "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = Action.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @GetMapping("/boards/action")
    Optional<Action> getAction(@RequestParam String token, @RequestParam String key,
                               @RequestParam String boardUrl, @RequestParam String actionId) {
        Optional<Board> board = boardService.getBoardByUrl(token,key,boardUrl);
        if(board.isPresent())
            return actionService.findByBoardIdAndActionId(board.get().getBoardId(),actionId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid board url");
    }




    @ApiOperation(
            value = "get actions for a fetched board using search filters",
            notes = "You can set type of action or date from/to"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = Action.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })

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



    @ApiOperation(
            value = "listens webhook",
            notes = "Method recieves all actions that triggered by trello server webhook"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })

    @PostMapping("/trello/hook")
    void webhook(@RequestBody String request){

        //webhookService.validateWebhook(request);
        JSONObject jsonObject = new JSONObject(request);
        System.out.println(jsonObject);
        System.out.println("webhook entering");
        trelloService.listenWebhook(jsonObject);
    }




    @ApiOperation(
            value = "greeting method",
            notes = "Trello server ensures that agent is alive and is able to recieve webhook body "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = HttpEntity.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @RequestMapping(value = "/trello/hook", method = {RequestMethod.GET})
    public HttpEntity<String> handleTestRequest () {


        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("test-header", Arrays.asList("test-header-value"));

        HttpEntity<String> responseEntity = new HttpEntity<>("test body", headers);
        System.out.println("HEAD request came");
        return responseEntity;
    }



    @ApiOperation(
            value = "get active webhooks for token",
            notes = "Method returns active webhooks for token"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = String.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })

    @GetMapping(value = "/trello/hooks")
    public String getWebhooksForToken(@RequestParam String token, @RequestParam String key){
        String request = "https://api.trello.com/1/tokens/"+token+"/webhooks?key="+key;
        String response = com.example.trello.HttpClient.jsonGetRequest(request);
        return new JSONObject(response).toString();
    }



    @ApiOperation(
            value = "delete a webhook",
            notes = "Method stops listening a webhook"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = IOException.class
            )
    })

    @RequestMapping(value = "/trello/hook", method = {RequestMethod.DELETE})
    public void deleteWebhook (@RequestParam String token, @RequestParam String key, @RequestParam String idWebhook) throws IOException {

        final Content deleteResultForm = Request.Delete("https://api.trello.com/1//webhooks/"+ idWebhook + "?key="+ key +"&token=" + token)
                .execute().returnContent();
        System.out.println(deleteResultForm.asString());

    }




}
