package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;

public class Lawn implements Serializable {
    private ArrayList<Plant> plants = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private Level level;
    private Player player;
    private PlantPane plant_pane;
    protected Pane root;
    Plant[][] GridArr=new Plant[8][5];
    protected LawnPane lp;

    Lawn(LawnPane lp) {
        this.lp = lp;
        plant_pane = new PlantPane();
        level = new Level(lp, this, plants, zombies, plant_pane, root);
    }

    public void setPlayer(Player p) {
        player = p;
    }
    public Player getPlayer() {
        return player;
    }
    public Level getLevel() { return level; }
    public PlantPane getPlantPane() { return plant_pane; }

    public void addCharacter(Character x) {
        if (x instanceof Plant) {
            plants.add((Plant) x);
        }
        else {
            zombies.add((Zombie) x);
        }
    }
    public ArrayList<Plant> getPlants() {
        return plants;
    }
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }
    public void clearLawn() {
        plants = new ArrayList<>();
    }
}

class LawnMower {
    private boolean isActive;
    protected ImageView LawnMower_img;
    protected int posX;
    protected int posY;
    Pane root;
    Timeline repeatTask;
    protected static ArrayList<LawnMower> lawnMowerArrayList = new ArrayList<>();

    LawnMower(int posY,Pane root){
        LawnMower_img=GameMenu.imageGetter("src/sample/images/LM.png");
        this.posY=posY;
        this.posX=10;
        GameMenu.location_setter(LawnMower_img,this.posX,this.posY);
        this.root=root;
        isActive=true;
        root.getChildren().add(LawnMower_img);
        lawnMowerArrayList.add(this);
    }

    public boolean getStatus() {
        return isActive;
    }
    public void moveMowerGif() {
        if(posX>1000){
            isActive = false;
        }
        repeatTask = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                posX++;
                GameMenu.location_setter(LawnMower_img,posX,posY);
            }
        }));
        repeatTask.setCycleCount(Timeline.INDEFINITE);
        repeatTask.play();
    }
    public void stopMowerGif(){
        repeatTask.stop();
    }
//    void removeLawnmover(){
//        root.getChildren().remove(LawnMower_img);
//        isActive=false;
//    }
}