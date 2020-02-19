import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;

public class JsonParser {

    public ArrayList<Action> getActionsArray(String response){
        JSONObject actionsJson = new JSONObject(response);
        JSONArray actionJsonArray = actionsJson.getJSONArray("actions");
        return fromJsonArrayToList(actionJsonArray);
    }

    public Member getMember(String response){
        JSONObject memberJson = new JSONObject(response);
        String id = memberJson.getString("id");
        String username = memberJson.getString("username");
        String boardString = memberJson.get("idBoards").toString();
        ArrayList<String> boards = boardsToArray(boardString);
        return new Member(id, username,boards);
    }

    private ArrayList<String> boardsToArray(String boardString){
        boardString = boardString.substring(1,boardString.length()-1);
        return new ArrayList(Arrays.asList(boardString.split(",")));
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

}