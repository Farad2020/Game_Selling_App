package sample;

import java.io.Serializable;

public abstract class Good implements Serializable {
    protected Integer id;
    protected double price;
    protected String title;
    protected int sold;

    public Good() {    }

    public Good(Integer id, double price, String title, int sold) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.sold = sold;
    }

    public abstract String showDetails();

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
