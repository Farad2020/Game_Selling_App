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

    public void saveGameChanges(ArrayList<Game> games){
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

    public ArrayList<Game_Media> getAllGameMedia(){
        ArrayList<Game_Media> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from game_media");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int game_id = rs.getInt("game_id");
                String img_src = rs.getString("game_img_source");
                list.add(new Game_Media(game_id, img_src));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveGameMediaChanges(ArrayList<Game_Media> medias){
        try{
            for(Game_Media media : medias){
                PreparedStatement ps = conn.prepareStatement("UPDATE game_media SET game_id = ?, game_img_source = ? WHERE id=?");
                ps.setInt(1, media.getGame_id());
                ps.setString(2,media.getImg_src());
                ps.setInt(3, media.getId());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGameMedia(Game_Media gm){
        try {
            PreparedStatement ps = conn.prepareStatement("select * from game_media where game_id = ?");
            ps.setInt(1, gm.getGame_id());
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                ps = conn.prepareStatement("INSERT INTO game_media (id, game_id, game_img_source) VALUES(NULL, ?, ?)");
                ps.setInt(1, gm.getGame_id());
                ps.setString(2,gm.getImg_src());
                ps.executeUpdate();
                ps.close();
            }else{
                ps = conn.prepareStatement("UPDATE game_media SET game_img_source = ? WHERE game_id = ?");
                ps.setString(1, gm.getImg_src());
                ps.setInt(2,gm.getGame_id());
                ps.executeUpdate();
                ps.close();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void removeGameMedia(int id){
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM game_media where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String getGameImgSrc(int game_id){
        String src = "";
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from game_media where game_id=?");
            ps.setInt(1, game_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                src = rs.getString("game_img_source");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return src;
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
                int balance = rs.getInt("balance");
                list.add(new User(id, login, username, password, is_moder,balance));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addUser(User user){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (id, login, username, password, is_moder, balance) VALUES(NULL, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getLogin());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getPassword());
            ps.setBoolean(4, user.isIs_moder());
            ps.setString(5, Double.toString(user.getBalance()));
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
                PreparedStatement ps = conn.prepareStatement("UPDATE users SET login = ?, username = ?, password = ?, is_moder = ?, balance = ? WHERE id=?");
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.setBoolean(4, user.isIs_moder());
                ps.setDouble(5, user.getBalance());
                ps.setInt(6, user.getId());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getAllGenres(){
        ArrayList<String> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from genres");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String genre = rs.getString("genre_name");
                list.add(genre);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<String> getUserTransHistory(int user_id){
        ArrayList<String> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from user_transaction_history where user_id=?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                String date = df.format(rs.getDate("purchase_time"));
                String cost = Double.toString(rs.getDouble("cost"));
                String bought_game = "";
                ArrayList<Game> games = getAllGames();
                for( Game g : games){
                    if(g.getId() == rs.getInt("game_id")){
                        bought_game = g.getGameInfo();
                        break;
                    }
                }

                String transaction = date + " " +cost + "\n" + bought_game ;
                list.add(transaction);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<String> getUserGamesLibrary(int user_id){
        ArrayList<String> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from user_transaction_history where user_id=?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            int i = 1;  //as listing method
            String bought_game = "";
            String game_owner = "";
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String date, cost;
            while(rs.next()){
                date = df.format(rs.getDate("purchase_time"));
                cost = Double.toString(rs.getDouble("cost"));
                game_owner = "";
                bought_game = "";
                ArrayList<Game> games = getAllGames();
                for( Game g : games){
                    if(g.getId() == rs.getInt("game_id")){
                        bought_game = g.getGameInfo();
                        break;
                    }
                }
                ArrayList<User> users = getAllUsers();
                for( User u : users){
                    if(u.getId() == rs.getInt("user_id")){
                        game_owner = u.getLogin();
                        break;
                    }
                }

                String transaction = i + " " + bought_game + " " + game_owner + " " + date + " " + cost ;
                i++;
                list.add(transaction);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Game> getUserGames(int user_id){
        ArrayList<Game> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from user_transaction_history where user_id=?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ArrayList<Game> games = getAllGames();
                for( Game g : games){
                    if(g.getId() == rs.getInt("game_id")){
                        list.add(g);
                        break;
                    }
                }
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void make_transaction(User user, ArrayList<Game> games){
        for (Game g : games){
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO user_transaction_history (id, user_id, game_id, purchase_time, cost) VALUES(NULL, ?, ?, ?, ?)");
                ps.setInt(1, user.getId());
                ps.setInt(2, g.getId());

                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                ps.setDate(3, sqlDate);

                ps.setDouble(4 , g.getPrice());
                ps.executeUpdate();
                ps.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getAllTransactions(){
        ArrayList<String> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from user_transaction_history");
            ResultSet rs = ps.executeQuery();
            int i = 1;  //as listing method
            while(rs.next()){
                String bought_game = "";
                ArrayList<Game> games = getAllGames();
                for( Game g : games){
                    if(g.getId() == rs.getInt("game_id")){
                        bought_game = g.getGameInfo();
                        break;
                    }
                }

                String transaction = i + " " + bought_game ;
                i++;
                list.add(transaction);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void run(){
        Request req = null;
        while(true){
            try {
                req = (Request)ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Transactions

            if(req.getCode().equals("MAKE_TRANSACTION")){
                make_transaction(req.getUser(), req.getGames());
                System.out.println("Transaction successful");
            }

            if(req.getCode().equals("USER_TRANS_HISTORY")){
                Reply rep = new Reply();
                ArrayList<String> history = getUserTransHistory(req.getId());
                rep.setStrings(history);
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("GET_USER_GAMES")){
                Reply rep = new Reply();
                ArrayList<Game> lib = getUserGames(req.getId());
                rep.setGames(lib);
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if(req.getCode().equals("GET_ALL_TRANSACTIONS")){
                Reply rep = new Reply();
                ArrayList<String> history = getAllTransactions();
                rep.setStrings(history);
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("USER_GAMES_LIBRARY")){
                Reply rep = new Reply();
                ArrayList<String> library = getUserGamesLibrary(req.getId());
                rep.setStrings(library);
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Genres

            if(req.getCode().equals("GET_ALL_GENRES")){
                Reply rep = new Reply();
                ArrayList<String> genres = getAllGenres();

                rep.setGenres(genres);

                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //User interactions

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

                rep.setUsers(users);
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("SAVE_ALL_USERS")){
                saveUserChanges(req.getUsers());

                Reply rep = new Reply("USERS SAVED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Game Medias
            if(req.getCode().equals("PREP_IMGS")){
                Reply rep = new Reply();
                ArrayList<Game_Media> game_media = getAllGameMedia();

                rep.setGame_media(game_media);

                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("GET_IMG_SRC")){
                Reply rep = new Reply();
                rep.setCode(getGameImgSrc(req.getId()));

                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("REMOVE_IMG")){
                removeGameMedia(req.getId());
                System.out.println("IMG Removed Successfully");
            }

            if(req.getCode().equals("UPDATE_IMG")){
                saveGameMediaChanges(req.getGame_medias());

                Reply rep = new Reply("GAMES UPDATED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("ADD_IMG")){
                addGameMedia(req.getGame_medium());

                Reply rep = new Reply("IMG ADDED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Game interactions

            if(req.getCode().equals("REMOVE")){
                removeGame(req.getId());
                System.out.println("Removed Successfully");
            }

            if(req.getCode().equals("UPDATE")){
                updateGame(req.getId(), req.getGame());

                Reply rep = new Reply("GAMES UPDATED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("ADD")){
                addGame(req.getGame());

                Reply rep = new Reply("GAME ADDED SUCCESSFULLY");
                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("GET_ALL")){
                Reply rep = new Reply();
                ArrayList<Game> games = getAllGames();

                rep.setGames(games);

                try {
                    oos.writeObject(rep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(req.getCode().equals("SAVE_ALL_GAMES")){
                saveGameChanges(req.getGames());

                Reply rep = new Reply("GAMES SAVED SUCCESSFULLY");
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
