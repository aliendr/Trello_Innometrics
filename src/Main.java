import java.util.ArrayList;

public class Main {
    private static String token = "af80fef990571cc1916e003ca274a25c56822b200a99604a47d482e8f2338c38";
    private static String key = "ffce8063806eb2065f6d792bc0793ece";
    public String BASE = "https://api.trello.com/1/";
    public static void main(String[] args) {


        Metrics metrics = new Metrics(key,token);
        metrics.initialise();

        ArrayList<Action> list = metrics.getActionsfromBoard("5e32cc1031e2f91141ee1077");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
            System.out.println(list.get(i).getDate());
            System.out.println(list.get(i).getIdMemberCreator());
            System.out.println(list.get(i).getType());
            System.out.println(list.get(i).getData());
            System.out.println();
            System.out.println();

        }


    }



}
