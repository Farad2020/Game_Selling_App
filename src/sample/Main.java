package sample;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/proj_db_test?useUnicode=true&serverTimezone=UTC";
            String user = "root";
            String pass = "";
            Connection conn = DriverManager.getConnection(url,user, pass);
            if (conn != null){
                System.out.println("Sucsess");
            }
            String query = "select * from items";
            var statement = conn.prepareStatement(query);
            ResultSet r = statement.executeQuery();
            while( r.next() ){
                int id = r.getInt("id");
                double price = r.getFloat("price");
                String model = r.getString("model");
                int sold = r.getInt("sold");
                String genre = r.getString("genre");
                String publisher = r.getString("publisher");
                String developer = r.getString("developer");
                Date date = r.getDate("release_date");
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String release_date = df.format(date);
                System.out.printf("%-10s%-20f%-20s%-20s%-10s%-20s%-10s%-10s", id, price, model, sold, genre, publisher, developer, release_date);
                System.out.println();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
