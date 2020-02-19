import java.util.ArrayList;

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
        JsonParser jsonParser = new JsonParser();
        this.member = jsonParser.getMember(response);

//        JSONObject memberJson = new JSONObject(response);
//        System.out.println(memberJson.toString(2));

        return true;
    }

    ArrayList<Action> getActionsfromBoard(String boardId){
        Board board = new Board(boardId, apiKey, apiToken);
        ArrayList<Action> listOfActions = board.getAllActions();
        return listOfActions;
    }






}
