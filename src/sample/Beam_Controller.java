package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Beam_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button main_Beam_btn;

    @FXML
    private Button open_cart_btn;

    @FXML
    private Button save_changes_btn;

    @FXML
    private Button enter_admin_btn;

    @FXML
    private Button sign_in_btn;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_bar_field;

    @FXML
    private AnchorPane main_shop_page;

    @FXML
    private ListView<String> categories_listview;

    @FXML
    private Button choose_category_btn;

    @FXML
    private Button buy_item_btn;

    @FXML
    private AnchorPane item_page;

    @FXML
    private Button add_to_cart_btn;

    @FXML
    private Button return_btn;

    @FXML
    private Label item_page_msg_lbl;

    @FXML
    private Label item_page_title_lbl;

    @FXML
    private Label item_page_gnr_lbl;

    @FXML
    private Label item_page_dev_lbl;

    @FXML
    private Label item_page_publish_lbl;

    @FXML
    private Label item_page_release_lbl;

    @FXML
    private Label item_page_price_lbl;

    @FXML
    private Label purchase_cost_lbl;

    @FXML
    private ScrollPane shop_content_scroll_pane;

    @FXML
    private AnchorPane shop_content_anchor_pane;

    @FXML
    private AnchorPane sign_in_and_Up_page;

    @FXML
    private TabPane sign_page_tablepane;

    @FXML
    private Tab sign_in_tab;

    @FXML
    private TextField sign_in_username_field;

    @FXML
    private PasswordField sign_in_password_field;

    @FXML
    private Label sign_in_msg_label;

    @FXML
    private Button submit_sign_in_btn;

    @FXML
    private Tab sign_up_tab;

    @FXML
    private TextField sign_up_username_field;

    @FXML
    private PasswordField sign_up_password1_field;

    @FXML
    private PasswordField sign_up_password2_field;

    @FXML
    private Label sign_up_msg_label;


    @FXML
    private Button submit_sign_up_btn;

    @FXML
    private AnchorPane cart_page;

    @FXML
    private ListView<String> cart_items_listview;

    @FXML
    private Button delete_item_from_cart_btn;

    @FXML
    private Button buy_cart_items_btn;

    @FXML
    private AnchorPane admin_page;

    @FXML
    private ListView<String> all_items_admin_listview;

    @FXML
    private Button delete_item_from_stock_btn;

    @FXML
    private Button create_new_item_in_stock_btn;

    @FXML
    private AnchorPane add_item_page;

    @FXML
    private ListView<String> items_admin_listview;

    @FXML
    private TextField create_game_title_field;

    @FXML
    private ComboBox<String> create_game_genre_field;

    @FXML
    private TextField create_game_developer_field;

    @FXML
    private TextField create_game_publisher_field;

    @FXML
    private TextField create_game_price_field;

    @FXML
    private DatePicker create_game_rel_date_field;

    @FXML
    private Button add_item_btn;

    @FXML
    private Label create_item_page_label;
//////////////////////////////
    @FXML
    private ListView<String> filtered_games_listview;
//////////////////////////////

    private static File_Manager filer = new File_Manager();
    public ArrayList<Game> games_in_store = new ArrayList<>();
    private ArrayList<Game> games_in_cart = new ArrayList<>();
    ArrayList<String> genres = new ArrayList<>(Arrays.asList("Platformer","RPG","FPS","Strategy","Stealth-Action",  "All") );
    //create auto filling genres through getting them from txt

    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    @FXML
    void initialize() {
        try {
            socket = new Socket("127.0.0.1", 1999);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set items for -> categories_listview.
        //prep_genres();
        prepare_main_page_listview();


        search_btn.setOnAction(event -> {
            main_Beam_btn.fire();
            search_item();
        });

        //Buy and add to cart

        buy_item_btn.setOnAction(event -> {
            open_item_page();
        });

        add_to_cart_btn.setOnAction(event -> {
            addItemToCart();
        });

        return_btn.setOnAction(event -> {
            main_Beam_btn.fire();
        });

        //use listview as display

        main_Beam_btn.setOnAction(event -> {
            prepare_main_page_listview();
            offLayouts();
            main_shop_page.setVisible(true);
        });

        //Cart page interactions

        open_cart_btn.setOnAction(event -> {
            prepCartPage();
            offLayouts();
            cart_page.setVisible(true);
        });


        delete_item_from_cart_btn.setOnAction(event -> {
            deleteFromCart();
        });


        buy_cart_items_btn.setOnAction(event -> {
            makePurchase();
        });


        //Sign up
        sign_in_btn.setOnAction(event -> {
            offLayouts();
            sign_in_and_Up_page.setVisible(true);
        });


        enter_admin_btn.setOnAction(event -> {
            offLayouts();
            prepAdminPage();
            admin_page.setVisible(true);
        });



        // Observable list

        choose_category_btn.setOnAction(event -> {
            if(categories_listview.getSelectionModel().getSelectedIndex() >= 0){
                for (int i = 0; i < filtered_games_listview.getItems().size(); i++) {
                    filtered_games_listview.getItems().remove(0);
                }
                String genre = categories_listview.getSelectionModel().getSelectedItem();
                for (int i = 0; i < games_in_store.size(); i++) {
                    if (games_in_store.get(i).getGenre().equals(genre) || genre.equals("All")) {
                        filtered_games_listview.getItems().add(games_in_store.get(i).showDetails());
                    }
                }
            }
        });

        //Admin interactions

        create_new_item_in_stock_btn.setOnAction(event -> {
            offLayouts();
            prepAddItemPage();
            reset_creation_page();
            add_item_page.setVisible(true);
        });


        delete_item_from_stock_btn.setOnAction(event -> {
            deleteItemFromShop();
        });


        add_item_btn.setOnAction(event -> {
            create_new_item();
            save_changes_btn.fire();
        });

        save_changes_btn.setOnAction(event -> {
            save_changes();
        });

/*
        // BYE when controller shuts down
        try{
            Request req = new Request("BYE");
            oos.writeObject(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }



    public void search_item(){
        if( !(search_bar_field.equals("") || search_bar_field.equals(null)) ){
            filtered_games_listview.getItems().clear();
        for (int i = 0; i < games_in_store.size(); i++) {
                if( games_in_store.get(i).getTitle().contains(search_bar_field.getText()) || games_in_store.get(i).getDeveloper().contains(search_bar_field.getText()) ){
                    filtered_games_listview.getItems().add(games_in_store.get(i).showDetails());
                }
            }
        }
    }


    public void open_item_page(){
        if(filtered_games_listview.getSelectionModel().getSelectedIndex() != -1){
            //Checking if user selected something and filling the page
            for (int i = 0; i < games_in_store.size(); i++) {
                if(games_in_store.get(i).showDetails().equals(filtered_games_listview.getSelectionModel().getSelectedItem())){
                    item_page_title_lbl.setText(games_in_store.get(i).getTitle());
                    item_page_dev_lbl.setText(games_in_store.get(i).getDeveloper());
                    item_page_publish_lbl.setText(games_in_store.get(i).getPublisher());
                    item_page_gnr_lbl.setText(games_in_store.get(i).getGenre());
                    item_page_release_lbl.setText(games_in_store.get(i).getRelease_date_string());
                    item_page_price_lbl.setText(""+games_in_store.get(i).getPrice());
                    offLayouts();
                    item_page.setVisible(true);
                    break;
                }
            }
        }
    }


    //What if you store user selection in variable instead searching it again?
    //Interactions with main
    public void addItemToCart(){
        int user_selected_item;
        boolean repeated_request=false;
        for (int i = 0; i < games_in_store.size(); i++) {
            if(games_in_store.get(i).getTitle().equals(item_page_title_lbl.getText())){
                //Checking if  games_in_cart doesn't already have this item
                for (int j = 0; j < games_in_cart.size(); j++) {
                    if(games_in_cart.get(i).getTitle().equals(games_in_store.get(i).getTitle())){
                        repeated_request = true;
                        break;
                    }
                }
                if(!repeated_request) {
                    games_in_cart.add(games_in_store.get(i));
                    item_page_msg_lbl.setText("This Game has been added to your cart. You can check it any time.");
                }
                break;
            }
        }
    }

    public void update_games_in_store(){
        try{
            Request req = new Request("GET_ALL");
            oos.writeObject(req);
            Reply rep = (Reply)ois.readObject();
            games_in_store.clear();
            for(Game g:rep.getGames())
                games_in_store.add(g);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void prepare_main_page_listview(){
        if(games_in_store.size() == 0){
            try{
                Request req = new Request("GET_ALL");
                oos.writeObject(req);
                Reply rep = (Reply)ois.readObject();
                games_in_store = rep.getGames();
                if(categories_listview.getItems().size() == 0){
                    categories_listview.getItems().addAll(genres);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void save_changes(){
        try{
            Request req = new Request("SAVE_ALL", games_in_store);
            oos.writeObject(req);
            Reply rep = (Reply)ois.readObject();
            System.out.println(rep.getCode());
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Cart interactions
    public void setPurchaseCost(){
        double totalPrice = 0;
        for (Game game:games_in_cart){
            totalPrice += game.getPrice();
        }
        purchase_cost_lbl.setText("You'll make a purchase for: " + totalPrice);
    }


    public void prepCartPage(){
        cart_items_listview.getItems().clear();
        for (Game game:games_in_cart){
            cart_items_listview.getItems().add(game.showDetails());
        }
        setPurchaseCost();
    }


    public void deleteFromCart(){
        if(cart_items_listview.getSelectionModel().getSelectedIndex() != -1){
            for (Game game:games_in_cart){
                if(game.showDetails().equals(cart_items_listview.getSelectionModel().getSelectedItem())){
                    games_in_cart.remove(game);
                    break;
                }
            }
        }
        prepCartPage();
    }


    public void makePurchase(){
        for (Game game:games_in_cart){
            game.setSold( game.getSold()+1 );
        }
        purchase_cost_lbl.setText("Your purchase was confirmed");
        games_in_cart.clear();
        save_changes_btn.fire();
        prepCartPage();
    }

    //Admin interactions
    public void prepAdminPage(){
        all_items_admin_listview.getItems().clear();
        for (Game game:games_in_store){
            all_items_admin_listview.getItems().add(game.showDetails());
        }
    }

    public void deleteItemFromShop(){
        for (Game game:games_in_store){
            if( game.showDetails().equals(all_items_admin_listview.getSelectionModel().getSelectedItem()) ){
                try {
                    Request req = new Request("REMOVE", game.getId());
                    oos.writeObject(req);
                    games_in_store.remove(game);
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        save_changes_btn.fire();
        prepAdminPage();
    }


    public void prepAddItemPage(){
        create_game_genre_field.getItems().clear();
        create_game_genre_field.getItems().addAll(genres);
        //"Platformer","RPG","FPS","Strategy","Stealth-Action"
        items_admin_listview.getItems().clear();
        for (int i = 0; i < games_in_store.size(); i++) {
            items_admin_listview.getItems().add( games_in_store.get(i).showDetails() + " \nSold: " + games_in_store.get(i).getSold() );
        }
    }


    public void create_new_item(){
        create_item_page_label.setText("You'll get the message for responding actions!");
        String title = create_game_title_field.getText();
        String developer = create_game_developer_field.getText();
        String publisher = create_game_publisher_field.getText();
        String genre = create_game_genre_field.getSelectionModel().getSelectedItem();
        String date = create_game_rel_date_field.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int sold = 0;
        double price = 0;
        try{
            price = Double.parseDouble(create_game_price_field.getText());
        }catch (java.lang.NumberFormatException e){
            create_item_page_label.setText("You need to fill all of the following fields correctly: price");
            return;
        }
        if(check_is_creator_filled(title, genre, publisher, developer, date)){
            System.out.println(date);
            Game g = new Game(null, price, title, sold, genre, publisher, developer, date);
            try {
                /*
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date date = sd.parse(rdate);
                java.sql.Date sqlRelDate = new java.sql.Date(date.getTime());
                */
                Request req = new Request("ADD", g);
                oos.writeObject(req);
                Reply rep = (Reply)ois.readObject();
                System.out.println(rep.getCode());
            }catch(Exception e){
                e.printStackTrace();
            }
            update_games_in_store();
            prepAddItemPage();
            reset_creation_page();
        }
    }


    public void reset_creation_page(){
        create_item_page_label.setText("You'll get the message for responding actions!");
        create_game_title_field.setText(null);
        create_game_developer_field.setText(null);
        create_game_publisher_field.setText(null);
        create_game_genre_field.getSelectionModel().select(null);
        create_game_rel_date_field.setValue(null);
        create_game_price_field.setText(null);
    }


    public boolean check_is_creator_filled( String title, String genre, String publisher, String developer, String release_date){
        String error_msg = "You need to fill next fields: ";
        boolean has_error = false;
        if(!(title.matches(".*[A-Z,a-z,0-9].*"))){
            has_error = true;
            error_msg += " title ";
        }

        if(!(genre!=null)){
            has_error = true;
            error_msg += " genre ";
        }
        if(!(publisher.matches(".*[A-Z,a-z,0-9].*"))){
            has_error = true;
            error_msg += " publisher ";
        }
        if(!(developer.matches(".*[A-Z,a-z,0-9].*"))){
            has_error = true;
            error_msg += " developer ";
        }
        if(!(release_date.matches(".*[0-9].*"))){
            has_error = true;
            error_msg += " release_date ";
        }

        for (int i = 0; i < games_in_store.size(); i++) {
            if(games_in_store.get(i).getTitle().equals(title)){
                has_error=true;
                error_msg += " title must be unique ";
                break;
            }
        }

        if(has_error)
            create_item_page_label.setText(error_msg);


        return !has_error;
    }

    public void offLayouts(){
        main_shop_page.setVisible(false);
        add_item_page.setVisible(false);
        admin_page.setVisible(false);
        cart_page.setVisible(false);
        sign_in_and_Up_page.setVisible(false);
        item_page.setVisible(false);
    }
}

//admin works if admin signed in