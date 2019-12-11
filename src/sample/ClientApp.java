package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args){
        try {
            Socket socket = new Socket("127.0.0.1", 1999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            Scanner in = new Scanner(System.in);
            while(true){
                System.out.println("1. add game");
                System.out.println("2. view all");
                System.out.println("3. remove game");
                System.out.println("4. update title");
                System.out.println("5. exit");

                int choice = in.nextInt();

                if(choice == 1){
                    System.out.print("title: ");
                    String title = in.next();
                    System.out.print("price: ");
                    Double price = in.nextDouble();
                    System.out.print("genre: ");
                    String genre = in.next();
                    System.out.print("publisher: ");
                    String publisher = in.next();
                    System.out.print("developer: ");
                    String developer = in.next();
                    System.out.print("release date: ");
                    String rdate = in.next();
                    int sold = 0;
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                        java.util.Date date = sd.parse(rdate);
                        java.sql.Date sqlRelDate = new java.sql.Date(date.getTime());

                        Game g = new Game(null , price, title, sold, genre, publisher, developer, sqlRelDate);
                        Request req = new Request("ADD", g);

                        oos.writeObject(req);

                        Reply rep = (Reply)ois.readObject();
                        System.out.println(rep.getCode());
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }

                if(choice == 2){
                    Request req = new Request("GET_ALL");
                    oos.writeObject(req);

                    Reply rep = (Reply)ois.readObject();

                    ArrayList<Game> list = rep.getGames();

                    for(Game g : list){
                        System.out.println(g);
                    }
                }

                if(choice == 3){
                    System.out.println("enter id to remove: ");
                    Integer id = in.nextInt();

                    Request req = new Request("REMOVE", id);
                    oos.writeObject(req);
                }

                if(choice == 4){
                    System.out.print("id: ");
                    Integer game_id = in.nextInt();
                    System.out.print("title: ");
                    String title = in.next();
                    System.out.print("price: ");
                    Double price = in.nextDouble();
                    System.out.print("genre: ");
                    String genre = in.next();
                    System.out.print("publisher: ");
                    String publisher = in.next();
                    System.out.print("developer: ");
                    String developer = in.next();
                    System.out.print("release date: ");
                    String rdate = in.next();
                    int sold = 0;
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                        java.util.Date date = sd.parse(rdate);
                        java.sql.Date sqlRelDate = new java.sql.Date(date.getTime());

                        Game g = new Game(null , price, title, sold, genre, publisher, developer, sqlRelDate);
                        Request req = new Request("UPDATE", game_id, g);

                        oos.writeObject(req);

                        Reply rep = (Reply)ois.readObject();
                        System.out.println(rep.getCode());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                if(choice == 5){
                    Request req = new Request("BYE");
                    oos.writeObject(req);
                    break;
                }
            }

            oos.close();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
