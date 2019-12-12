package sample;
import java.io.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ClientHandler extends Thread{

    private Socket socket;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Connection conn;

    public ClientHandler(Socket socket, Connection conn){
        this.conn = conn;
        this.socket = socket;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Game> getAllGames(){
        ArrayList<Game> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from items");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                double price = rs.getFloat("price");
                String model = rs.getString("model");
                int sold = rs.getInt("sold");
                String genre = rs.getString("genre");
                String publisher = rs.getString("publisher");
                String developer = rs.getString("developer");
                Date release_date = rs.getDate("release_date");
                /*
                Date date = rs.getDate("release_date");
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String release_date = df.format(date);*/
                list.add(new Game(id, price, model, sold, genre, publisher, developer, release_date));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addGame(Game game){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO items (id, price, model, sold, genre, publisher, developer, release_date) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)");
            ps.setDouble(1, game.getPrice());
            ps.setString(2, game.getTitle());
            ps.setInt(3, game.getSold());
            ps.setString(4,game.getGenre());
            ps.setString(5,game.getPublisher());
            ps.setString(6, game.getDeveloper());
            ps.setDate(7, game.getRelease_date());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeGame(int id){
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM items where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateGame(int game_id,Game game){
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE items SET price = ?, model = ?, sold = ?, genre = ?, publisher = ?, developer = ?, release_date = ?  WHERE id=?");
            ps.setDouble(1, game.getPrice());
            ps.setString(2, game.getTitle());
            ps.setInt(3, game.getSold());
            ps.setString(4,game.getGenre());
            ps.setString(5,game.getPublisher());
            ps.setString(6, game.getDeveloper());
            ps.setDate(7, game.getRelease_date());
            ps.setInt(8, game_id);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void saveChanges(ArrayList<Game> games){
        try{
            for(Game game: games){
                PreparedStatement ps = conn.prepareStatement("UPDATE items SET price = ?, model = ?, sold = ?, genre = ?, publisher = ?, developer = ?, release_date = ?  WHERE id=?");
                ps.setDouble(1, game.getPrice());
                ps.setString(2, game.getTitle());
                ps.setInt(3, game.getSold());
                ps.setString(4,game.getGenre());
                ps.setString(5,game.getPublisher());
                ps.setString(6, game.getDeveloper());
                ps.setDate(7, game.getRelease_date());
                ps.setInt(8, game.getId());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void remove_user(int id){
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateUser(int user_id,User user){
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET login = ?, username = ?, password = ?, is_moder = ? WHERE id=?");
            ps.setString(1, user.getLogin());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getPassword());
            ps.setBoolean(4, user.isIs_moder());
            ps.setInt(5, user_id);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String login = rs.getString("login");
                String username = rs.getString("username");
                String password = rs.getString("password");
                boolean is_moder = rs.getBoolean("is_moder");
                list.add(new User(id, login, username, password, is_moder));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addUser(User user){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (id, login, username, password, is_moder) VALUES(NULL, ?, ?, ?, ?)");
            ps.setString(1, user.getLogin());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getPassword());
            ps.setBoolean(4, user.isIs_moder());
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void saveUserChanges(ArrayList<User> users){
        try{
            for(User user: users){
                PreparedStatement ps = conn.prepareStatement("UPDATE items SET login = ?, username = ?, password = ?, is_moder = ? WHERE id=?");
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.setBoolean(4, user.isIs_moder());
                ps.setInt(5, user.getId());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void run(){
        Request req = null;
        while(true){
            try {
                req = (Request)ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(req.getCode().equals("REMOVE_USER")){
                remove_user(req.getId());
                System.out.println("Removed Successfully");
            }

            if(req.getCode().equals("UPDATE_USER")){
                updateUser(req.getId(), req.getUser());

                Reply rep = new Reply("UPDATED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("ADD_USER")){
                addUser(req.getUser());

                Reply rep = new Reply("ADDED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("GET_ALL_USERS")){
                Reply rep = new Reply();
                ArrayList<User> users = getAllUsers();

                for(User u : users){
                    rep.addUser(u);
                }

                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("SAVE_ALL_USERS")){
                saveUserChanges(req.getUsers());

                Reply rep = new Reply("SAVED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("REMOVE")){
                removeGame(req.getId());
                System.out.println("Removed Successfully");
            }

            if(req.getCode().equals("UPDATE")){
                updateGame(req.getId(), req.getGame());

                Reply rep = new Reply("UPDATED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("ADD")){
                addGame(req.getGame());

                Reply rep = new Reply("ADDED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("GET_ALL")){
                Reply rep = new Reply();
                ArrayList<Game> games = getAllGames();

                for(Game g : games){
                    rep.addGame(g);
                }

                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("SAVE_ALL")){
                saveChanges(req.getGames());

                Reply rep = new Reply("SAVED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("BYE")){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
