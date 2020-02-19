import org.json.JSONObject;

public class Action {

    private String id;
    private String idMemberCreator;
    private String type;
    private String date;
    private JSONObject data;

    Action(String id, String idMemberCreator, String type, String date, JSONObject data) {
        this.id = id;
        this.idMemberCreator = idMemberCreator;
        this.type = type;
        this.date = date;
        this.data = data;
    }

    public String getId(){
        return this.id;
    }

    public String getIdMemberCreator(){
        return this.idMemberCreator;
    }

    public String getType(){
        return this.type;
    }

    public String getDate(){
        return this.date;
    }

    public String getData(){
        return data.toString(2);
    }
}