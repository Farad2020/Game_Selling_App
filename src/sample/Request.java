package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {
    private String code;
    private Game game;
    private Integer id;
    private ArrayList<Game> games = null;
    private User user;
    private ArrayList<User> users = null;
    private ArrayList<String> genres = null;
    private ArrayList<Game_Media> game_media = null;

    public Request() {}

    public Request(String code) {
        this.code = code;
    }

    public Request(String code, int id){
        this.code = code;
        this.id = id;
    }

    public Request(String code, User user) {
        this.code = code;
        this.user = user;
    }

    public Request(ArrayList<User> users, String code) {
        this.code = code;
        this.users = users;
    }

    public Request(String code,int id, User user) {
        this.code = code;
        this.id = id;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
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

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<Game_Media> getGame_media() {
        return game_media;
    }

    public void setGame_media(ArrayList<Game_Media> game_media) {
        this.game_media = game_media;
    }

    @Override
    public String toString() {
        return "Request " +
                "code: '" + code +
                ", game: " + game +
                ", id=" + id;
    }

}
