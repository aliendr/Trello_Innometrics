import java.util.ArrayList;

public class Board {

    private String boardId;
    private ArrayList<Action> listOfActions;

    Board(String boardId, ArrayList<Action> listOfActions){
        this.boardId=boardId;
        this.listOfActions = listOfActions;
    }

}
