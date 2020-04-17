package com.example.trello.services;

import com.example.trello.HttpClient;
import com.example.trello.entries.Action;
import com.example.trello.entries.Board;
import com.example.trello.repositories.ActionRepository;
import com.example.trello.repositories.BoardRepository;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class TrelloService {

    private String HOST_IP = System.getenv("HOST_IP") + "/trello/hook";

    private String baseAddress = "https://api.trello.com/1/";

    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private BoardRepository boardRepository;


    public void validateTokenKey(String token, String key){
        try {
            String request = baseAddress + "members/me/boards?key="+ key +"&token="+token;
            String response = com.example.trello.HttpClient.jsonGetRequest(request);
            JSONArray boards = new JSONArray(response);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid token");
        }
    }

    public List<Board> addTokenKey(String token, String key){
        try{
            return getBoadsList(token,key);

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid token");
        }
    }

    public Optional<Board> addTokenKeyBoardUrl(String token, String key, String url) throws IOException {
        validateTokenKey(token,key);
        List<Board> boards =getBoadsList(token, key);

        if(!boardRepository.findByTokenAndKeyAndBoardUrl(token, key, url).isPresent()){
            boolean found=false;
            for (Board value : boards) {

                if (value.getBoardUrl().equals(url)) {
                    Board board = value;
                    String request = baseAddress + "boards/" + board.getBoardId() + "/?fields=id&actions=all&actions_limit=1000&action_memberCreator_fields=username" + "&key=" + key + "&token=" + token;
                    String response = com.example.trello.HttpClient.jsonGetRequest(request);
                    ArrayList<String> actionsId = getActionsId(response, board.getBoardId());
                    board.setListOfActions(actionsId);
                    boardRepository.save(board);
                    webhook(token,key,board.getBoardId());
                    found = true;
                    break;
                }
            }


            if(!found)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid board url");
        }


        return boardRepository.findByTokenAndKeyAndBoardUrl(token,key,url);
    }


    public void webhook(String token, String key, String boardId) throws IOException {

        final Collection<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("key", key));
        params.add(new BasicNameValuePair("idModel", boardId));
        params.add(new BasicNameValuePair("callbackURL", HOST_IP));


            final Content postResultForm = Request.Post("https://api.trello.com/1/tokens/"+ token + "/webhooks/")
                    .bodyForm(params, Charset.defaultCharset())
                    .execute().returnContent();
            System.out.println(postResultForm.asString());

    }

    public void listenWebhook(JSONObject jsonObject){
        // add verification

        System.out.println("listening webhook");
        Action action = getAction(jsonObject.getJSONObject("action"));
        actionRepository.save(action);
        System.out.println(action.toString());

    }


    // from trello api
    private List<Board> getBoadsList(String token,String key){
        String request = baseAddress + "members/me/boards?key="+ key +"&token="+token;
        String response = HttpClient.jsonGetRequest(request);
        JSONArray boards = new JSONArray(response);

        List<Board> listOfBoards = new ArrayList<>();
        for (int i = 0; i < boards.length(); i++) {
            JSONObject b = boards.getJSONObject(i);
            String name = b.getString("name");
            String[] urlParts = b.getString("shortUrl").split("/");
            String url = urlParts[urlParts.length-1];
            String boardId = b.getString("id");
            listOfBoards.add(new Board(name,url,boardId, token, key));
        }
        return listOfBoards;
    }

    private ArrayList<String> getActionsId(String response, String boardId){
        JSONObject actionsJson = new JSONObject(response);
        JSONArray actionJsonArray = actionsJson.getJSONArray("actions");
        return fromJsonArrayToList(actionJsonArray,boardId);
    }

    private ArrayList<String> fromJsonArrayToList(JSONArray actionsJson,String boardId){
        ArrayList<String> listOfActions = new ArrayList<>();
        for (int i = 0; i < actionsJson.length(); i++) {
            Action action = getAction(actionsJson.getJSONObject(i));
            listOfActions.add(action.getActionId());
            actionRepository.save(action);
        }
        return listOfActions;
    }

    private Action getAction(JSONObject actionJson){
        String id = actionJson.getString("id");
        String idMemberCreator = actionJson.getString("idMemberCreator");
        String type = actionJson.getString("type");
        String date = actionJson.getString("date");
        String memberCreatorUsername = actionJson.getJSONObject("memberCreator").getString("username");

        String boardId = actionJson.getJSONObject("data").getJSONObject("board").getString("id");
        Action action = new Action(id, boardId, idMemberCreator, type, date, memberCreatorUsername);

//        String listId = data.getJSONObject("list").getString("id");
//        String listName = data.getJSONObject("list").getString("name");
//        JSONObject card = data.getJSONObject("card");
//        String cardId = card.getString("id");
//        String cardName = card.getString("name");


        return action;
    }

}