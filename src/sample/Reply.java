package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Reply implements Serializable {
    private String code;
    private ArrayList<Game> games;
    private ArrayList<User> users;

    public Reply() {
        games = new ArrayList<>();
        users = new ArrayList<>();
    }

    public Reply(ArrayList<Game> games){
        this.games = games;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Reply(String code) {
        this.code = code;
    }

    public void addGame(Game g){
        games.add(g);
    }

    public void addUser(User u){
        users.add(u);
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
