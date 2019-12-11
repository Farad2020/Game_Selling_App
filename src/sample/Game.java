package sample;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

// Selling physical copies
public class Game extends Good {
    protected String genre;
    protected String publisher;
    protected String developer;
    protected Date release_date;

    public Game(){     }

    public Game(Integer id, double price, String model, int sold, String genre, String publisher, String developer, Date release_date) {
        super(id, price, model, sold);
        this.genre = genre;
        this.publisher = publisher;
        this.developer = developer;
        this.release_date = release_date;
    }

    public Game(Integer id, double price, String model, int sold, String genre, String publisher, String developer, String release_date) {
        super(id, price, model, sold);
        this.genre = genre;
        this.publisher = publisher;
        this.developer = developer;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = sd.parse(release_date);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            this.release_date = sqlDate;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String showDetails(){
        return "Title: " + title + "\n" +
                "Genre: " + genre + "\n" +
                "Developer: " + developer + "\n" +
                "Publisher: " + publisher + "\n" +
                "Price: " + price;
    }

    @Override
    public String toString() {
        return "Game " +
                "id: " + id +
                ",genre: " + genre +
                ", publisher: " + publisher +
                ", developer: " + developer +
                ", release_date: " + release_date +
                ", price: " + price +
                ", title: " + title +
                ", sold: " + sold;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public String getRelease_date_string() {
        Date date = this.release_date;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String rdate = df.format(date);

        return rdate;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public void setRelease_date_from_str(String release_date) {
        try {
            SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = sd.parse(release_date);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            this.release_date = sqlDate;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
