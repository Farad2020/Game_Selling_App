package sample;

import java.io.*;
import java.util.ArrayList;

public class File_Manager {
    protected String shop_items_file;
    protected String names_db;
    protected String genres_db;
    protected ArrayList<Game> games;
    //protected String users_file = "src\\users.txt";
    //protected ArrayList<User> users = new ArrayList<>();

    protected FileOutputStream fout ;
    protected ObjectOutputStream oos;
    protected FileInputStream fin;
    protected ObjectInputStream oin;
    public File_Manager(){
        shop_items_file = "src\\games.txt";
        games = new ArrayList<>();
    }
    public File_Manager(String path){
        shop_items_file = path;
        games = new ArrayList<>();
    }

    public void saveGames(ArrayList<Game> games){
        try{
            fout = new FileOutputStream(new File(shop_items_file));
            oos = new ObjectOutputStream(fout);
            oos.writeObject(games);
            oos.close();
            fout.close();
        }catch (FileNotFoundException e){
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("Obj Out Str troubless");
            e.printStackTrace();
        }
    }

    public void loadGames(ArrayList<Game> games) {
        try {
            fin = new FileInputStream(new File(shop_items_file));
            oin = new ObjectInputStream(fin);
            this.games = (ArrayList<Game>) oin.readObject();
            for (int i = 0; i < this.games.size(); i++) {
                games.add(this.games.get(i));
            }
        }catch (IOException | ClassNotFoundException e){
            System.out.println("It was IOException or ClassNotFoundException");
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("file was empty");
        }
    }



}
