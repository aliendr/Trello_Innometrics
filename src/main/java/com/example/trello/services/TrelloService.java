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
            for (Board board : boards) {

                if (board.getBoardUrl().equals(url)) {
                    String request = baseAddress + "boards/" + board.getBoardId() + "/?fields=id&actions=all&actions_limit=1000&action_memberCreator_fields=username" + "&key=" + key + "&token=" + token;
                    String response = com.example.trello.HttpClient.jsonGetRequest(request);
                    fetchActions(response, token, key);
                    try {
                        webhook(token,key, board.getBoardId());
                        board.setWebhook(true);
                        boardRepository.save(board);
                    }
                    catch (IOException e){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "webhook did not succeed");
                    }


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
        System.out.println("listening webhook");
        Action action = getAction(jsonObject.getJSONObject("action"));
        String[] tokenKey = getTokenKeyFromBoardId(action.getBoardId());
        action.setEmailMemberCreator(getEmail(action.getIdMemberCreator(),tokenKey[0],tokenKey[1]));
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

    private void fetchActions(String response, String token, String key){
        JSONObject actionsJson = new JSONObject(response);
        JSONArray actionJsonArray = actionsJson.getJSONArray("actions");
        for (int i = 0; i < actionJsonArray.length(); i++) {
            Action action = getAction(actionJsonArray.getJSONObject(i));
            action.setEmailMemberCreator(getEmail(action.getIdMemberCreator(), token,key));
            actionRepository.save(action);
        }
    }

    private Action getAction(JSONObject actionJson){
        String id = actionJson.getString("id");
        String idMemberCreator = actionJson.getString("idMemberCreator");
        String type = actionJson.getString("type");
        String date = actionJson.getString("date");
        String memberCreatorUsername = actionJson.getJSONObject("memberCreator").getString("username");

        JSONObject data = actionJson.getJSONObject("data");
        String boardId = data.getJSONObject("board").getString("id");
        Action action = new Action(id, boardId, idMemberCreator, type, date, memberCreatorUsername);
        action.setBoardName(data.getJSONObject("board").getString("name"));

        if (data.has("card")){
            JSONObject cardJson = data.getJSONObject("card");
            if (cardJson.has("id"))
                action.setCardId(cardJson.getString("id"));
            if (cardJson.has("name"))
                action.setCardName(cardJson.getString("name"));
            if (cardJson.has("idList"))
                action.setListId(cardJson.getString("idList"));
            if (cardJson.has("closed"))
                action.setCardClosed(cardJson.getString("closed"));
        }

        if (data.has("list")){
            JSONObject listJson = data.getJSONObject("list");
            if (listJson.has("id"))
                action.setListId(listJson.getString("id"));
            if (listJson.has("name"))
                action.setListName(listJson.getString("name"));
        }

        if (data.has("checkItem")){
            JSONObject checkItemJson = data.getJSONObject("checkItem");
            if (checkItemJson.has("id"))
                action.setCheckItemId(checkItemJson.getString("id"));
            if (checkItemJson.has("name"))
                action.setCheckItemName(checkItemJson.getString("name"));
            if (checkItemJson.has("state"))
                action.setCheckItemState(checkItemJson.getString("state"));
        }

        if (data.has("checklist")){
            JSONObject checklistJson = data.getJSONObject("checklist");
            if (checklistJson.has("id"))
                action.setCheckListId(checklistJson.getString("id"));
            if (checklistJson.has("name"))
                action.setCheckListName(checklistJson.getString("name"));
        }

        if (data.has("listAfter")){
            JSONObject listAfterJson = data.getJSONObject("listAfter");
            if (listAfterJson.has("id"))
                action.setListAfterId(listAfterJson.getString("id"));
            if (listAfterJson.has("name"))
                action.setListAfterName(listAfterJson.getString("name"));
        }

        if (data.has("listBefore")){
            JSONObject listBeforeJson = data.getJSONObject("listBefore");
            if (listBeforeJson.has("id"))
                action.setListBeforeId(listBeforeJson.getString("id"));
            if (listBeforeJson.has("name"))
                action.setListBeforeName(listBeforeJson.getString("name"));
        }



        if (data.has("attachment")){
            JSONObject attachmentJson = data.getJSONObject("attachment");
            if (attachmentJson.has("id"))
                action.setAttachmentId(attachmentJson.getString("id"));
            if (attachmentJson.has("name"))
                action.setAttachmentName(attachmentJson.getString("name"));
        }

        if (data.has("member")){
            JSONObject memberJson = data.getJSONObject("member");
            if (memberJson.has("id"))
                action.setMemberAddedDeletedId(memberJson.getString("id"));
            if (memberJson.has("name"))
                action.setMemberAddedDeletedName(memberJson.getString("name"));
        }

        if (data.has("old")){
            action.setOld(data.getJSONObject("old").toString());
        }

        if (data.has("plugin")){
            JSONObject pluginJson = data.getJSONObject("plugin");
            if (pluginJson.has("id"))
                action.setPluginId(pluginJson.getString("id"));
            if (pluginJson.has("name"))
                action.setPluginName(pluginJson.getString("name"));
            if (pluginJson.has("url"))
                action.setPluginUrl(pluginJson.getString("url"));
        }


        return action;
    }

    private String getEmail(String idMemberCreator, String token, String key){
        String request = baseAddress + "members/" + idMemberCreator + "/?fields=email" + "&key=" + key + "&token=" + token;
        String response = com.example.trello.HttpClient.jsonGetRequest(request);
        JSONObject memberJson = new JSONObject(response);
        return memberJson.get("email").toString();
    }

    private String[] getTokenKeyFromBoardId(String boardId){
        String[] tokenKey = new String[2];
        Board board = boardRepository.findAllByBoardId(boardId);
        tokenKey[0]=board.getToken();
        tokenKey[1]=board.getKey();
        return tokenKey;
    }

}