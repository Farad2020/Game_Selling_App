package sample;

import java.io.Serializable;

public class User implements Serializable {
    protected Integer id;
    protected String login;
    protected String username;
    protected String password;
    protected boolean is_moder;
    protected double balance;

    public User() {
        is_moder = false;
    }

    public User(Integer id, String login, String username, String password) {
        this.id = id;
        this.login = login;
        this.username = username;
        this.password = password;
        is_moder = false;
    }

    public User(String login, String username, String password, boolean is_moder) {
        this.login = login;
        this.username = username;
        this.password = password;
        this.is_moder = is_moder;
    }

    public User(Integer id, String login, String username, String password, boolean is_moder, double balance) {
        this.id = id;
        this.login = login;
        this.username = username;
        this.password = password;
        this.is_moder = is_moder;
        this.balance = balance;
    }

    public String showDetails(){
        String data = "";
        data ="id: " + id +", login: " + login + '\n' +
                "username: " + username  +", password: " + password + '\n' +
                "is_moder: " + is_moder;
        return data;
    }

    public User(User u) {
        this.id = u.id;
        this.login = u.login;
        this.username = u.username;
        this.password = u.password;
        this.is_moder = u.is_moder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_moder() {
        return is_moder;
    }

    public void setIs_moder(boolean is_moder) {
        this.is_moder = is_moder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", is_moder=" + is_moder +
                '}';
    }
}
