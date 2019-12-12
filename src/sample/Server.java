package sample;

import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Server {

    private Connection conn = null;
    private Connection users_conn = null;
    private ServerSocket ss = null;
    public Server(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/proj_db_test?useUnicode=true&serverTimezone=UTC";
            String user = "root";
            String pass = "";
            conn = DriverManager.getConnection(url,user, pass);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            ss = new ServerSocket(1999);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void start(){
        while(true){
            try{
                System.out.println("waiting for a client...");

                Socket socket = ss.accept();
                ClientHandler ch = new ClientHandler(socket, conn, users_conn);
                System.out.println("client connected " + socket.getInetAddress().getHostAddress());
                ch.start();
            }catch(Exception e){
                System.out.println("What the");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Server sa = new Server();
        sa.start();
    }
}
