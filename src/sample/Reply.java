package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Reply implements Serializable {
    private String code;
    private ArrayList<Game> games;
    private ArrayList<User> users;
    private ArrayList<String> genres;
    private ArrayList<String> strings;
    private ArrayList<Game_Media> game_media;


    public Reply() {
        games = new ArrayList<>();
        users = new ArrayList<>();
        genres = new ArrayList<>();
        strings = new ArrayList<>();
    }

    public Reply(ArrayList<Game> games){
        this.games = games;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
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

    public ArrayList<String> getStrings() {
        return strings;
    }

    public void setStrings(ArrayList<String> history) {
        this.strings = history;
    }

    public ArrayList<Game_Media> getGame_media() {
        return game_media;
    }

    public void setGame_media(ArrayList<Game_Media> game_media) {
        this.game_media = game_media;
    }
}
