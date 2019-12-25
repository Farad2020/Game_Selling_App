package sample;

import java.io.Serializable;

public class Game_Media  implements Serializable {
    protected int id;
    protected int game_id;
    protected String img_src;

    public Game_Media() {
    }

    public Game_Media(int id, int game_id, String img_src) {
        this.id = id;
        this.game_id = game_id;
        this.img_src = img_src;
    }

    public Game_Media(int game_id, String img_src) {
        this.game_id = game_id;
        this.img_src = img_src;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }
}
