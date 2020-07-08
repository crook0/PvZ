package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public abstract class Zombie extends Character {
    protected int normalSpeed = 30;
    protected int normalDamage = 100;
    protected boolean invadeHouse = true;
    protected double move_speed;
    private int zombie_count, level, name;
    AnimationTimer timer1;
    Pane root;
    int speed=10;
    Timeline repeatTask;
    Plant[][] GridArr;

    Zombie(LawnPane lp, Pane root, int Yposition, Plant[][] GridArr) {
        super(lp, 5000, 100);
        this.GridArr=GridArr;
        this.root=root;
        showGif();
        setPosX(880);
        setPosY(LawnPane.getOriginalY(Yposition));
    }
    Zombie(LawnPane lp, Pane root, int Yposition, Plant[][] GridArr, int bossHp) {
        super(lp, bossHp, 100);
        this.GridArr=GridArr;
        this.root=root;
        showGif();
        setPosX(880);
        setPosY(LawnPane.getOriginalY(Yposition));
    }

    public void makeSlow() {
        normalSpeed = 50;
    }

    public void startZombie() {
        repeatTask = new Timeline(new KeyFrame(Duration.millis(normalSpeed), event -> {
            GameMenu.location_setter(getIdleGif(), getPosX(),getPosY());
            int[] err=lp.intPosition(getPosX(),getPosY());

            if (active) {
                if (err[0] >= 0 && err[1] >= 0) {
                    if (GridArr[err[0]][err[1]] != null) {
                        if (GridArr[err[0]][err[1]].active) {
                            GridArr[err[0]][err[1]].decreaseHealth(100);
                            if (GridArr[err[0]][err[1]].getCurrentHp() <= 0) {
                                GridArr[err[0]][err[1]].active = false;
                            }

                        } else {
                            root.getChildren().remove(GridArr[err[0]][err[1]].getIdleGif());
                            if (GridArr[err[0]][err[1]].name.equals("peashooter")) {
                                PeaShooter pp = (PeaShooter) (GridArr[err[0]][err[1]]);
                                root.getChildren().remove(pp.getPea());
                            }
                            else if (GridArr[err[0]][err[1]].name.equals("icepeashooter")) {
                                IcePeashooter pp = (IcePeashooter) (GridArr[err[0]][err[1]]);
                                root.getChildren().remove(pp.getPea());
                            }
                            setPosX(getPosX() - 1);
                        }
                    } else {
                        setPosX(getPosX() - 1);
                    }
                } else {
                    setPosX(getPosX() - 1);
                }
                for(LawnMower l : LawnMower.lawnMowerArrayList) {
                    if (l.getStatus()) {
                        if (l.posX >= getPosX() && l.posY == getPosY()) {
                            l.moveMowerGif();
                            root.getChildren().remove(getIdleGif());
                            invadeHouse = false;
                            break;
                        }

                    }
                    else if (!l.getStatus() && invadeHouse && getPosX()<0) {
//                        System.exit(0);
                    }
                }
            }
        }));
        repeatTask.setCycleCount(Timeline.INDEFINITE);
        repeatTask.play();

        Timeline checker = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            if(LawnPane.ultimatePause){
                repeatTask.pause();
            }else{
                repeatTask.play();
            }
        }));
        checker.setCycleCount(Timeline.INDEFINITE);
        checker.play();

    }

    public abstract void showGif();
    public abstract void changeMoveSpeed(double x);
    public abstract ImageView getIdleGif();

    public double getMoveSpeed() {
        return move_speed;
    }
    public static void showZombies(ArrayList<Zombie> zombies) {
        for(Zombie i : zombies) {
            i.showGif();
        }
    }
}

class BaseZombie extends Zombie {
    private String BASIC_NAME;
    private double BASIC_SPEED;
    private int BASIC_HIT;
    protected ImageView base_zombie_idle_gif;
    int damage=1;

    BaseZombie(LawnPane lp, Pane root, int Yposition, Plant[][] GridArr){
        super(lp, root, Yposition, GridArr);
        base_zombie_idle_gif = GameMenu.imageGetter("src/sample/images/BaseZombieIdle.gif");
    }

    @Override
    public void showGif() {

    }
    @Override
    public void changeMoveSpeed(double x) {

    }
    @Override
    public ImageView getIdleGif() {
        return base_zombie_idle_gif;
    }
}

class ConeHead extends Zombie {
    private String CONE_NAME;
    private double CONE_SPEED;
    private int CONE_HIT;
    protected ImageView conehead_zombie_idle_gif;

    ConeHead(LawnPane lp, Pane root, int Yposition, Plant[][] GridArr){
        super(lp, root, Yposition, GridArr);
        conehead_zombie_idle_gif = GameMenu.imageGetter("src/sample/images/ConeHeadZombieIdle.gif");
    }

    @Override
    public void showGif() {

    }
    @Override
    public void changeMoveSpeed(double x) {
        move_speed += x;
    }
    @Override
    public ImageView getIdleGif() {
        return conehead_zombie_idle_gif;
    }
}



class BucketZombie extends Zombie {
    private String BUCKET_NAME;
    private double BUCKET_SPEED;
    private int BUCKET_HIT;
    protected ImageView bucket_zombie_idle_gif;

    BucketZombie(LawnPane lp, Pane root, int Yposition, Plant[][] GridArr){
        super(lp, root, Yposition, GridArr);
        bucket_zombie_idle_gif = GameMenu.imageGetter("src/sample/images/BucketZombie.gif");
    }

    @Override
    public void showGif() {

    }
    @Override
    public void changeMoveSpeed(double x) {
        move_speed += x;
    }
    @Override
    public ImageView getIdleGif() {
        return bucket_zombie_idle_gif;
    }
}



class RunnerZombie extends Zombie {
    private boolean runningOn;
    private String RUNNER_NAME;
    private double RUNNER_SPEED;
    private int RUNNER_HIT = normalDamage;
    protected static ImageView runner_zombie_idle_gif;

    RunnerZombie(LawnPane lp, Pane root, int Yposition, Plant[][] GridArr){
        super(lp, root, Yposition, GridArr);
        runner_zombie_idle_gif = GameMenu.imageGetter("src/sample/images/RunnerZombie.gif");
    }

    @Override
    public void showGif() {

    }
    @Override
    public void changeMoveSpeed(double x) {
        move_speed += x;
    }
    @Override
    public ImageView getIdleGif() {
        return runner_zombie_idle_gif;
    }
}



class Boss extends Zombie {
    private boolean BOSS_NAME;
    private double BOSS_SPEED;
    private int BOSS_HIT;
    protected static ImageView boss_zombie_idle_gif;

    Boss(LawnPane lp, Pane root, int Yposition, Plant[][] GridArr){
        super(lp, root, Yposition, GridArr, 10000);
        boss_zombie_idle_gif = GameMenu.imageGetter("src/sample/images/Boss.gif");
    }

    @Override
    public void showGif() {

    }
    @Override
    public void changeMoveSpeed(double x) {
        move_speed += x;
    }
    @Override
    public ImageView getIdleGif() {
        return boss_zombie_idle_gif;
    }
}