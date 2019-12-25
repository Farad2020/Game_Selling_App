package sample;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Randomizer {
    protected ArrayList<String> genres = new ArrayList<>(Arrays.asList("Platformer","RPG","FPS","Strategy","Stealth-Action",  "All") );
    protected ArrayList<String> names = new ArrayList<>();
    protected ArrayList<String> rating = new ArrayList<>();


    public static int getRandNum(int n){
        Random rand = new Random(System.currentTimeMillis());
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        for(int i=0; i < 100; i++){
            System.out.println(getRandNum(a));
            System.out.println(getRandNum(a));
        }
        // if process happense on same millisecond output is the same
        return rand.nextInt(n);
    }

}
