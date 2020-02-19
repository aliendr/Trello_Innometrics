import org.json.JSONArray;

import java.util.ArrayList;

public class Member {

    private String id;
    private String username;
    private ArrayList<String> boards;

    Member(String id, String username, ArrayList<String> boards){
        this.id = id;
        this.username = username;
        this.boards = boards;
    }


    public String getId(){
        return this.id;
    }

    public ArrayList<String> getBoards(){
        return this.boards;
    }

    public String getUsername(){
        return this.username;
    }
}
