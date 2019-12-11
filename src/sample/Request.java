package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {
    private String code;
    private Game game;
    private Integer id;
    private ArrayList<Game> games = null;

    public Request() {}

    public Request(String code) {
        this.code = code;
    }

    public Request(String code, int id){
        this.code = code;
        this.id = id;
    }

    public Request(String code, Game game) {
        this.code = code;
        this.game = game;
    }

    public Request(String code, ArrayList<Game> games) {
        this.code = code;
        this.games = games;
    }

    public Request(String code,int id, Game game) {
        this.code = code;
        this.id = id;
        this.game = game;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Request " +
                "code: '" + code +
                ", game: " + game +
                ", id=" + id;
    }
}
