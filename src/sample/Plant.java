package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import static sample.GameMenu.location_setter;

public abstract class Plant extends Character implements Cloneable {
    long last_addedTime=0;
    String name="Plant";
    private int availability_to_buy_time, price;
    int peaPositionX=0;
    int peaPositionY=0;

    HashMap<Integer, ArrayList<Zombie>> zombieList=new HashMap<Integer,ArrayList<Zombie>>();

    public Plant(LawnPane lp, int hp, int price, int attack_power) {
        super(lp, hp, attack_power);
        this.price = price;
    }

    public abstract void showGif();
    public abstract ImageView getCard();
    public abstract ImageView getIdleGif();
    public void peaTimer(){};
    public abstract int getPrice();

    @Override
    public Plant clone() throws CloneNotSupportedException {
        Plant copy = (Plant) super.clone();
        return copy;
    }

}

class Sunflower extends Plant {
    private static int SUNFLOWER_HP = 400, SUNFLOWER_PRICE = 50, SUN_VALUE = 50, SUNFLOWER_POWER = 0, SUN_GENERATE_TIME;
    protected ImageView sunflower_gif, sun_card, sun;
    protected Pane root = new Pane();
    protected sunBullet spp;

    Sunflower(LawnPane lp){
        super(lp, SUNFLOWER_HP, SUNFLOWER_PRICE, SUNFLOWER_POWER);
        name="sunflower";
    }

    public void showSun() {
        sun = GameMenu.imageGetter("src/sample/images/sun.png");
    }

    public void sunTimer() {
        Sunflower ss=this;
        Timeline repeatTask = new Timeline(new KeyFrame(Duration.millis(10000), event -> {
            spp=new sunBullet(ss,getPosX(),getPosY());
            spp.runIT();
            spp.mouseClicked(root, lp);
        }));
        repeatTask.setCycleCount(Timeline.INDEFINITE);
        repeatTask.play();

        Timeline checker = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(LawnPane.ultimatePause){
                    repeatTask.pause();
                }else{
                    repeatTask.play();
                }
            }
        }));
        checker.setCycleCount(Timeline.INDEFINITE);
        checker.play();
    }

    @Override
    public void showGif() {
        sunflower_gif = GameMenu.imageGetter("src/sample/images/Sunflower.gif");
    }

    @Override
    public ImageView getIdleGif() {
        return sunflower_gif;
    }

    @Override
    public ImageView getCard() {
        return sun_card;
    }

    @Override
    public Sunflower clone() throws CloneNotSupportedException {
        return (Sunflower) (super.clone());
    }
    @Override
    public int getPrice() {
        return SUNFLOWER_PRICE;
    }
    @Override
    public String toString() {
        return Integer.toString(getPosX()) + " " + Integer.toString(getPosY());
    }

    class sunBullet {
        ImageView sun_image;
        PathTransition repeatTaskP;
        int key;
        int posX;
        int posY;
        Sunflower sf;
        AnimationTimer aTimer;


        sunBullet(Sunflower sf, int positionX, int positionY) {
            this.sf = sf;
            int[] err = LawnPane.intPosition(positionX, positionY);
            key = err[1];
            sun_image = GameMenu.imageGetter("src/sample/images/sun.png");
            posX = positionX;
            posY = positionY;
        }

        void runIT() {
//            repeatTaskP = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (!root.getChildren().contains(sun_image) && sf.active) {
                root.getChildren().add(sun_image);
            }
//                posX;
            Line line= new Line();
            line.setStartX(getPosX()); line.setStartY(getPosY());
            line.setEndX(getPosX()-40); line.setEndY(getPosY()+20);
            posX=getPosX()-40;posY=getPosY()+20;
            repeatTaskP = new PathTransition();
            repeatTaskP.setDuration(Duration.seconds(1));
            repeatTaskP.setNode(sun_image);
            repeatTaskP.setPath(line);
            repeatTaskP.play();
        }

        public void mouseClicked(Pane root,LawnPane lp){
            sun_image.setOnMouseClicked(r->{
                repeatTaskP.stop();
                Line line= new Line();
                lp.sunCount+=50;
                lp.label1.setText(Integer.toString(lp.sunCount));
                line.setStartX(posX); line.setStartY(posY);
                line.setEndX(60); line.setEndY(40);
                PathTransition transition = new PathTransition();
                transition.setDuration(Duration.seconds(1));
                transition.setNode(sun_image);
                transition.setPath(line);
                transition.play();
            });

        }


    }
}

class PeaShooter extends Plant {
    private static int PEASHOOTER_ATTACK_POWER = 20, PEASHOOTER_PRICE = 100, PEASHOOTER_HP = 400;
    protected ImageView peashooter_gif, pea_card,pea;
    boolean peaActive=true;
    Pane root=new Pane();
    bulletPea bpp;
    Timeline repeatPeaTask=new Timeline();

    PeaShooter(LawnPane lp) {
        super(lp, PEASHOOTER_HP, PEASHOOTER_PRICE, PEASHOOTER_ATTACK_POWER);
        name="peashooter";
        pea_card = GameMenu.imageGetter("src/sample/images/card_peashooter.png");
    }
    @Override
    public void showGif() {
        peashooter_gif = GameMenu.imageGetter("src/sample/images/peashooterIdle.gif");

    }
    public void showPea(){
        pea=GameMenu.imageGetter("src/sample/images/pea.png");
    }

    @Override
    public ImageView getIdleGif() {
        return peashooter_gif;
    }
    @Override
    public ImageView getCard() {
        return pea_card;
    }
    @Override
    public int getPrice() {
        return PEASHOOTER_PRICE;
    }

    public ImageView getPea() {
        return pea;
    }


    @Override
    public void peaTimer() {
        PeaShooter pp =this;
        Timeline repeatTask = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            if((bpp != null && bpp.posX > 1000 || bpp == null || !root.getChildren().contains(bpp.pea_image)) && zombieList.get(getIntPosY()) != null){
                    bpp=new bulletPea(pp, getPosX(), getPosY(),zombieList);
                if (zombieInLane) {
                    bpp.runIT();
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

    class bulletPea{
        ImageView pea_image;
        int key;
        private ArrayList<Zombie> zombieArrayList;
        Timeline repeatTaskP;
        Zombie nearest_zombie;
        int min;
        int posX;
        int posY;
        PeaShooter ps;
        boolean noZombieInRow;
        bulletPea(PeaShooter ps, int positionX,int positionY,HashMap<Integer,ArrayList<Zombie>> zombieList){
            this.ps = ps;
//            int[] arr=LawnPane.scaledPosition(positionX,positionY);
            int[] err=LawnPane.intPosition(positionX,positionY);
            key=err[1];
            zombieArrayList=zombieList.get(key);
            pea_image = GameMenu.imageGetter("src/sample/images/pea.png");
            posX=positionX;posY=positionY;
            try {
                nearest_zombie = zombieArrayList.get(0);
                zombieInLane = true;
            }
            catch (NullPointerException | IndexOutOfBoundsException e) {
                zombieInLane = false;
            }
            min=100000;
        }
        void runIT(){
            repeatTaskP = new Timeline(new KeyFrame(Duration.millis(2), event -> {
                if (!root.getChildren().contains(pea) && ps.active) {
                    root.getChildren().add(pea);
                }
                posX++;
                location_setter(pea,posX,posY);
                if(getPosX()>1000){
                    repeatTaskP.stop();
                }

                for (Zombie zombie : zombieArrayList) {

                    if ((zombie.getPosX() - getPosX()) < min && zombie.active) {
                        peaActive = true;
                        min = (zombie.getPosX() - getPosX());
                        nearest_zombie = zombie;
                    }
                }

                if(nearest_zombie!=null && nearest_zombie.getPosX()-posX<10 && nearest_zombie.getPosX()>=getPosX()){

                    nearest_zombie.decreaseHealth(20);
                    if (!nearest_zombie.active) {
                        zombieArrayList.remove(nearest_zombie);
                        root.getChildren().remove(nearest_zombie.getIdleGif());
                        nearest_zombie.repeatTask.stop();
                    }
                    repeatTaskP.stop();
                    root.getChildren().remove(pea);
                }
                if (!ps.active) {
                    repeatTaskP.stop();
                }
            }));
            repeatTaskP.setCycleCount(Timeline.INDEFINITE);
            repeatTaskP.play();

            Timeline checker = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(LawnPane.ultimatePause){
                        repeatTaskP.pause();
                    }else{
                        repeatTaskP.play();
                    }
                }
            }));
            checker.setCycleCount(Timeline.INDEFINITE);
            checker.play();
        }
    }
}

class Walnut extends Plant {
    private static int WALNUT_HP = 400, WALNUT_PRICE = 50, WALNUT_ATTACK_POWER = 0;
    protected static ImageView walnut_gif, walnut_card;

    Walnut(LawnPane lp) {
        super(lp, WALNUT_HP, WALNUT_PRICE, WALNUT_ATTACK_POWER);
        name="walnut";
        walnut_card = GameMenu.imageGetter("src/sample/images/card_wallnut.png");
    }
    @Override
    public void showGif() {
        try {
            walnut_gif = GameMenu.imageGetter("src/sample/images/WalnutIdle.gif");
            GameMenu.location_setter(walnut_gif,155,480);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ImageView getIdleGif() {
        return walnut_gif;
    }
    @Override
    public ImageView getCard() {
        return walnut_card;
    }
    @Override
    public int getPrice() {
        return WALNUT_PRICE;
    }
}

class IcePeashooter extends Plant {
    private static int ICEPEASHOOTER_ATTACK_POWER = 20, ICEPEASHOOTER_PRICE = 175, ICEPEASHOOTER_HP = 400;
    protected ImageView icepeashooter_gif, ice_pea_card,icePea;
    boolean icePeaActive=true;
    public Pane root=new Pane();
    IceBulletPea iBpp;
    Timeline repeatPeaTask=new Timeline();

    IcePeashooter(LawnPane lp) {
        super(lp, ICEPEASHOOTER_HP, ICEPEASHOOTER_PRICE, ICEPEASHOOTER_ATTACK_POWER);
        name="icepeashooter";
        ice_pea_card = GameMenu.imageGetter("src/sample/images/card_freezepeashooter.png");
    }
    @Override
    public void showGif() {
        icepeashooter_gif = GameMenu.imageGetter("src/sample/images/IcePeashooterIdle.png");

    }
    public void showPea(){
        icePea=GameMenu.imageGetter("src/sample/images/icePea.png");
    }

    @Override
    public ImageView getIdleGif() {
        return icepeashooter_gif;
    }
    @Override
    public ImageView getCard() {
        return ice_pea_card;
    }
    @Override
    public int getPrice() {
        return ICEPEASHOOTER_PRICE;
    }

    public ImageView getPea() {
        return icePea;
    }


    @Override
    public void peaTimer() {
        IcePeashooter ipp =this;
        Timeline repeatTask = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if((iBpp!=null && iBpp.posX>1000 || iBpp==null || !root.getChildren().contains(iBpp.ice_pea_image)&& iBpp!=null) && zombieList.get(getIntPosY())!=null) {
                    iBpp = new IceBulletPea(ipp, getPosX(), getPosY(), zombieList);
                    if (zombieInLane) {
                        iBpp.runIT();
                    }
                }
            }
        }));
        repeatTask.setCycleCount(Timeline.INDEFINITE);
        repeatTask.play();

        Timeline checker = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(LawnPane.ultimatePause){
                    repeatTask.pause();
                }else{
                    repeatTask.play();
                }
            }
        }));
        checker.setCycleCount(Timeline.INDEFINITE);
        checker.play();
    }

    class IceBulletPea {
        ImageView ice_pea_image;
        int key;
        private ArrayList<Zombie> zombieArrayList;
        Timeline repeatTaskP;
        Zombie nearest_zombie;
        int min;
        int posX;
        int posY;
        IcePeashooter ips;
        boolean noZombieInRow;
        IceBulletPea(IcePeashooter ips, int positionX,int positionY,HashMap<Integer,ArrayList<Zombie>> zombieList){
            this.ips = ips;
//            int[] arr=LawnPane.scaledPosition(positionX,positionY);
            int[] err=LawnPane.intPosition(positionX,positionY);
            key=err[1];
            zombieArrayList=zombieList.get(key);
            ice_pea_image = GameMenu.imageGetter("src/sample/images/pea.png");
            posX=positionX;posY=positionY;
            try {
                nearest_zombie = zombieArrayList.get(0);
                zombieInLane = true;
            }
            catch (NullPointerException | IndexOutOfBoundsException e) {
                zombieInLane = false;
            }
            min=100000;
        }
        void runIT(){
            repeatTaskP = new Timeline(new KeyFrame(Duration.millis(2), event -> {
                if (!root.getChildren().contains(icePea) && ips.active) {
                    root.getChildren().add(icePea);
                }
                posX++;
                location_setter(icePea,posX,posY);
                if(getPosX()>1000){
                    repeatTaskP.stop();
                }

                for (Zombie zombie : zombieArrayList) {

                    if ((zombie.getPosX() - getPosX()) < min && zombie.active) {
                        icePeaActive = true;
                        min = (zombie.getPosX() - getPosX());
                        nearest_zombie = zombie;
                    }
                }

                if(nearest_zombie!=null && nearest_zombie.getPosX()-posX<10 && nearest_zombie.getPosX()>=getPosX()){
                    nearest_zombie.decreaseHealth(20);
                    nearest_zombie.makeSlow();
                    if (!nearest_zombie.active) {
                        zombieArrayList.remove(nearest_zombie);
                        root.getChildren().remove(nearest_zombie.getIdleGif());
                        nearest_zombie.repeatTask.stop();
                    }
                    repeatTaskP.stop();
                    root.getChildren().remove(icePea);
                }
                if (!ips.active) {
                    repeatTaskP.stop();
                }
            }));
            repeatTaskP.setCycleCount(Timeline.INDEFINITE);
            repeatTaskP.play();

            Timeline checker = new Timeline(new KeyFrame(Duration.millis(200), event -> {
                if(LawnPane.ultimatePause){
                    repeatTaskP.pause();
                }else{
                    repeatTaskP.play();
                }
            }));
            checker.setCycleCount(Timeline.INDEFINITE);
            checker.play();
        }
    }
}


class CherryBomb extends Plant {
    private static int CHERRYBOMB_ATTACK_POWER = 180, CHERRYBOMB_PRICE = 150, CHERRYBOMB_HP = Integer.MAX_VALUE;
    protected static ImageView c1, c25, cherry_card;

    CherryBomb(LawnPane lp) {
        super(lp, CHERRYBOMB_HP, CHERRYBOMB_PRICE, CHERRYBOMB_ATTACK_POWER);
        name="cherry";
        cherry_card = GameMenu.imageGetter("src/sample/images/card_cherrybomb.png");
        c25=GameMenu.imageGetter("src/sample/images/cherryBomb25.jpeg");
        c1=GameMenu.imageGetter("src/sample/images/cherryBomb1.jpeg");
    }
    public void cherryBomberGif(int x,int y){
        Timeline cherr = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(!lp.getRoot1.getChildren().contains(c1)) {
                    lp.getRoot1.getChildren().remove(c25);
                    lp.getRoot1.getChildren().add(c1);
                    location_setter(c1,x,y);
                }
                else if(!lp.getRoot1.getChildren().contains(c25)){
                    lp.getRoot1.getChildren().remove(c1);
                    lp.getRoot1.getChildren().add(c25);
                    location_setter(c25,x,y);
                }
            }
        }));

        cherr.setCycleCount(Timeline.INDEFINITE);
        cherr.play();
    }
    public void setCherrybombGifRemove() {
        lp.getRoot1.getChildren().remove(c1);
        lp.getRoot1.getChildren().remove(c25);
    }

    @Override
    public void showGif() {
//        try {
//            cherryBomb_gif = GameMenu.imageGetter("src/sample/images/CherryBombIdle.gif");
//            GameMenu.location_setter(cherryBomb_gif,265,360);
//        }
//        catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
    }
    @Override
    public ImageView getCard() {
        return cherry_card;
    }
    @Override
    public ImageView getIdleGif() {
        return c1;
    }
    @Override
    public int getPrice() {
        return CHERRYBOMB_PRICE;
    }

}