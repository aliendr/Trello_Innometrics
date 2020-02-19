import java.util.ArrayList;

public class Board {

    private String boardId;
    private String baseAddress = "https://api.trello.com/1/boards/";
    private String req = "/?fields=id&actions=all&actions_limit=10&action_memberCreator_fields=username";
    private String apiToken;
    private String apiKey;

    Board(String boardId, String apiKey, String apiToken){
        this.boardId=boardId;
        this.apiKey = apiKey;
        this.apiToken = apiToken;
    }

    public ArrayList<Action> getAllActions(){
        String request = baseAddress + this.boardId + req + "&key=" + apiKey +"&token=" + apiToken;
        HttpClient httpClient = new HttpClient();
        String response = HttpClient.jsonGetRequest(request);
        JsonParser jsonParser = new JsonParser();
        return jsonParser.getActionsArray(response);
    }



}
