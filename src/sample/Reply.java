package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Reply implements Serializable {
    private String code;
    private ArrayList<Game> games;

    public Reply() {
        games = new ArrayList<>();
    }

    public Reply(ArrayList<Game> games){
        this.games = games;
    }

    public Reply(String code) {
        this.code = code;
    }

    public void addGame(Game g){
        games.add(g);
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
