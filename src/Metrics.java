import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Metrics {
    private String baseAddress = "https://api.trello.com/1/";
    private String apiKey;
    private String apiToken;
    private Member member;

    Metrics(String apiKey, String apiToken){
        this.apiKey = apiKey;
        this.apiToken = apiToken;
    }

    boolean initialise(){
        String request = baseAddress + "members/me?boardBackgrounds=none&boardsInvited_fields=name%2Cclosed%2CidOrganization&boardStars=false&cards=" +
                "none&customBoardBackgrounds=none&customEmoji=none&customStickers=none&fields=email%2Cusername%2CidBoards&notifications=" +
                "none&organizations=none&organization_fields=none&organization_paid_account=false&organizationsInvited=none&organizationsInvited_fields=" +
                "none&paid_account=false&savedSearches=false&tokens=none&key="+ apiKey+"&token="+apiToken;

        HttpClient httpClient = new HttpClient();
        String response = httpClient.jsonGetRequest(request);
        Member member = getMember(response);

        // for now just variable
        String myBoardId = member.getBoards().get(0);
        request = baseAddress + "boards/" + myBoardId+ "/?fields=id&actions=all&actions_limit=10&action_memberCreator_fields=username" + "&key=" + apiKey +"&token=" + apiToken;
        response = HttpClient.jsonGetRequest(request);
        ArrayList<Action> actionsArray = getActionsArray(response);
        Board board = new Board(myBoardId,actionsArray);

        return true;
    }


    public ArrayList<Action> getActionsArray(String response){
        JSONObject actionsJson = new JSONObject(response);
        JSONArray actionJsonArray = actionsJson.getJSONArray("actions");
        return fromJsonArrayToList(actionJsonArray);
    }


    private ArrayList<Action> fromJsonArrayToList(JSONArray actionsJson){
        ArrayList<Action> listOfActions = new ArrayList<>();
        for (int i = 0; i < actionsJson.length(); i++) {
            Action action = getAction(actionsJson.getJSONObject(i));
            listOfActions.add(action);
        }
        return listOfActions;
    }

    private Action getAction(JSONObject actionJson){
        String id = actionJson.getString("id");
        String idMemberCreator = actionJson.getString("idMemberCreator");
        String type = actionJson.getString("type");
        String date = actionJson.getString("date");
        JSONObject data = actionJson.getJSONObject("data");
        return new Action(id, idMemberCreator, type, date, data);
    }


    private Member getMember(String response){
        JSONObject memberJson = new JSONObject(response);
        String id = memberJson.getString("id");
        String username = memberJson.getString("username");
        String boardString = memberJson.get("idBoards").toString();
        ArrayList<String> boards = boardsToArray(boardString);
        return new Member(id, username,boards);
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
