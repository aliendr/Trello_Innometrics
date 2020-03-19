package com.example.trello;

import com.example.trello.entries.Action;
import com.example.trello.entries.Board;
import com.example.trello.entries.Member;
import com.example.trello.repositories.ActionRepository;
import com.example.trello.repositories.AuthenticationRepository;
import com.example.trello.repositories.BoardRepository;
import com.example.trello.repositories.MemberRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TrelloRunner{
    private String baseAddress = "https://api.trello.com/1/";
//    private String apiKey = "ffce8063806eb2065f6d792bc0793ece";
//    private String apiToken = "af80fef990571cc1916e003ca274a25c56822b200a99604a47d482e8f2338c38";

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;

//    @Override
    public void run(String apiKey, String apiToken){
        String request = baseAddress + "members/me?boardBackgrounds=none&boardsInvited_fields=name%2Cclosed%2CidOrganization&boardStars=false&cards=" +
                "none&customBoardBackgrounds=none&customEmoji=none&customStickers=none&fields=email%2Cusername%2CidBoards&notifications=" +
                "none&organizations=none&organization_fields=none&organization_paid_account=false&organizationsInvited=none&organizationsInvited_fields=" +
                "none&paid_account=false&savedSearches=false&tokens=none&key="+ apiKey+"&token="+apiToken;

        HttpClient httpClient = new HttpClient();
        String response = httpClient.jsonGetRequest(request);
        Member member = getMember(response);
        memberRepository.save(member);

        for (int i = 0; i < member.getBoards().size(); i++) {
            String boardId = member.getBoards().get(i);
            Board board = new Board(boardId);
            request = baseAddress + "boards/" + boardId+ "/?fields=id&actions=all&actions_limit=1000&action_memberCreator_fields=username" + "&key=" + apiKey +"&token=" + apiToken;
            response = HttpClient.jsonGetRequest(request);
            ArrayList<String> actionsId = getActionsId(response, boardId);
            board.setListOfActions(actionsId);

            request = baseAddress + "boards/"  + boardId + "/?fields=name" + "&key=" + apiKey +"&token=" + apiToken;
            response = HttpClient.jsonGetRequest(request);
            JSONObject boardsJson = new JSONObject(response);
            board.setName(boardsJson.getString("name"));
            boardRepository.save(board);
        }
    }

    private ArrayList<String> getActionsId(String response, String boardId){
        JSONObject actionsJson = new JSONObject(response);
        JSONArray actionJsonArray = actionsJson.getJSONArray("actions");
        return fromJsonArrayToList(actionJsonArray,boardId);
    }

    private ArrayList<String> fromJsonArrayToList(JSONArray actionsJson,String boardId){
        ArrayList<String> listOfActions = new ArrayList<>();
        for (int i = 0; i < actionsJson.length(); i++) {
            Action action = getAction(actionsJson.getJSONObject(i), boardId);
            listOfActions.add(action.getActionId());
            actionRepository.save(action);
        }
        return listOfActions;
    }

    private Action getAction(JSONObject actionJson,String boardId){
        String id = actionJson.getString("id");
        String idMemberCreator = actionJson.getString("idMemberCreator");
        String type = actionJson.getString("type");
        String date = actionJson.getString("date");
        JSONObject data = actionJson.getJSONObject("data");
        return new Action(id, boardId, idMemberCreator, type, date);
    }

    private Member getMember(String response) {
        JSONObject memberJson = new JSONObject(response);
        String id = memberJson.getString("id");
        String username = memberJson.getString("username");
        String boardString = memberJson.get("idBoards").toString();
        ArrayList<String> boardsId = boardsToArray(boardString);
//        ArrayList<Board> boardList = new ArrayList<Board>();
//        for (int i = 0; i < boardsId.size(); i++) {
//            boardList.add(new Board(boardsId.get(i)));
//        }
        return new Member(id,username,boardsId);
    }

    private ArrayList<String> boardsToArray(String boardString){
        boardString = boardString.substring(1,boardString.length()-1);
        String[] boards = boardString.split(",");
        for (int i = 0; i < boards.length; i++) {
            boards[i] = boards[i].substring(1,boards[i].length()-1);
        }
        return new ArrayList(Arrays.asList(boards));
    }
}