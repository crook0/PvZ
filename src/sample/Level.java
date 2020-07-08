package sample;

import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
    private long serialVersionUID = 2L;
    private int curr_level = 5;
    private Lawn lawn;
    private PlantPane plant_pane;
    private ArrayList<Plant> plants;
    private ArrayList<Zombie> zombies;
    private Pane root;
    private LawnPane lp;
    private int totalZombiesInLevel = curr_level * 1;

    Level(LawnPane lp, Lawn lawn, ArrayList<Plant> plants, ArrayList<Zombie> zombies, PlantPane pp, Pane root) {
        this.lp = lp;
        this.root = root;
        this.lawn = lawn;
        this.plants = plants;
        this.zombies = zombies;
        plant_pane = pp;
    }

    public int getLevel() {
        return curr_level;
    }
    public void setLevel(int x){
        curr_level = x;
    }
    public int getTotalZombiesInLevel() {
        return totalZombiesInLevel;
    }
    public void initialiseLevel_1() {
        lawn.clearLawn();
        plant_pane.add(new PeaShooter(lp));
    }
    public void initialiseLevel_2() {
        lawn.clearLawn();
        initialiseLevel_1();
        plant_pane.add(new Sunflower(lp));
    }
    public void initialiseLevel_3() {
        lawn.clearLawn();
        initialiseLevel_2();
        plant_pane.add(new Walnut(lp));
    }
    public void initialiseLevel_4() {
        lawn.clearLawn();
        initialiseLevel_3();
        plant_pane.add(new IcePeashooter(lp));
    }
    public void initialiseLevel_5() {
        lawn.clearLawn();
        initialiseLevel_4();
        plant_pane.add(new CherryBomb(lp));
    }
}
