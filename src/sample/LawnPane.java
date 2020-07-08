package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class LawnPane {
    protected Player player;
    protected long last_addedTime;
    protected long last_addedTimeSun;
    protected Label label1;
    public ProgressBar pb = new ProgressBar();
    private Sun s = new Sun(100);
    protected Zombie z;
    protected Plant[][] GridArr=new Plant[8][5];
    protected boolean buyFlag = false;
    protected HashMap<Integer,ArrayList<Zombie>> zombieList= new HashMap<>();  ////it is Y then X
    private Pane root;
    protected Pane getRoot1;
    protected Scene scene;
    protected int sunCount;
    private static Pane root1;
    private Lawn curr_lawn;
    private PlantPane plant_pane;
    public Level curr_level;
    private int zombiesEncountered;
    protected ArrayList<LawnMower> lawnMowerArrayList = LawnMower.lawnMowerArrayList;

    static boolean ultimatePause=false;


    ImageView sun = imageGetter("src/sample/images/sun.png");
    ImageView BGimg = imageGetter("src/sample/images/mainBG.png");
    ImageView me = imageGetter("src/sample/images/me.jpg");
    ImageView me2 = imageGetter("src/sample/images/me2.jpg");
    LawnMower lm0;
    LawnMower lm1;
    LawnMower lm2;
    LawnMower lm3;
    LawnMower lm4;
    ImageView save = imageGetter("src/sample/images/sav.jpg");
    ImageView sav_quit = imageGetter("src/sample/images/sav_quit.png");
    ImageView sav_quit2 = imageGetter("src/sample/images/sav_quit2.png");
    ImageView return_to_game = imageGetter("src/sample/images/return_to_game.png");
    ImageView return_to_game2 = imageGetter("src/sample/images/return_to_game2.png");
    ImageView enter = imageGetter("src/sample/images/enter.png");

    LawnPane(Player player, Stage stage, Scene prevScene, Lawn c_l, PlantPane p_p, Level c_ll) throws Exception {
        this.player = player;
        player.lawnPane = this;
        curr_lawn = c_l;
        plant_pane = p_p;
        curr_level = c_ll;
        c_l.root = root1;
        getRoot1=root1;

        label1 = new Label();
        changeSunCount(5000);
        label1.setLayoutX(60);
        label1.setLayoutY(80);

        pb.setLayoutX(600);
        pb.setLayoutY(30);
        pb.setProgress(0);

        root1 = new Pane();
        this.root = root1;
        Scene s1 = new Scene(root1, 1000, 752, Color.BLACK);
        this.scene = s1;

        mouseOnCardGlow();

        location_setter(me, 855, 0);
        location_setter(me2, 855, 0);

        root1.getChildren().addAll(BGimg, label1, me);
        root1.getChildren().add(pb);

        Point p1 = MouseInfo.getPointerInfo().getLocation();

        dragCards();

        //---------------------------------------------------------------

//        BGimg.setOnMouseClicked( e2 ->{
//
//            int[] arr=scaledPosition((int)e2.getSceneX(),(int)e2.getSceneY());
//            int xx=arr[0];
//            int yy=arr[1];
//            int[] err=intPosition(xx,yy);
//            ImageView img=(GridArr[err[0]-1][err[1]]).getIdleGif();
//            root1.getChildren().remove(img);
////            grid.get(yy).remove(0);
//            System.out.printf(Arrays.toString(err));
//        });


        if (curr_level.getLevel() == 1) {
            curr_level.initialiseLevel_1();
            plant_pane.show_cards(root1);
            zombieTimer();
            sunTimer();
        }
        else if (curr_level.getLevel() == 2) {
            curr_level.initialiseLevel_2();
            plant_pane.show_cards(root1);
            zombieTimer();
            sunTimer();
        }

        else if (curr_level.getLevel() == 3) {
            curr_level.initialiseLevel_3();
            plant_pane.show_cards(root1);
            zombieTimer();
            sunTimer();
        }
        else if (curr_level.getLevel() == 4) {
            curr_level.initialiseLevel_4();
            plant_pane.show_cards(root1);
            zombieTimer();
            sunTimer();
        }
        else if (curr_level.getLevel() == 5) {
            curr_level.initialiseLevel_5();
            plant_pane.show_cards(root1);
            zombieTimer();
            sunTimer();
        }

        lm0=new LawnMower(129,root1);
        lm1=new LawnMower(243,root1);
        lm2=new LawnMower(363,root1);
        lm3=new LawnMower(491,root1);
        lm4=new LawnMower(612,root1);


        me.setOnMouseEntered(e2 ->{
            root1.getChildren().add(me2);
        });
        me2.setOnMouseExited(e1 ->{
            root1.getChildren().remove(me2);
        });
        me2.setOnMouseClicked(e1 ->{
            ultimatePause=true;

            Stage stage2=new Stage();
            stage2.initModality(Modality.APPLICATION_MODAL);
            Pane root2 = new Pane();
            Scene s2 = new Scene(root2, 676, 388, Color.BLACK);
            location_setter(sav_quit,150,200);
            location_setter(return_to_game,150,100);
            location_setter(sav_quit2,150,200);
            location_setter(return_to_game2,150,100);
            sav_quit.setOnMouseEntered(ee ->{
                root2.getChildren().add(sav_quit2);
            });
            sav_quit2.setOnMouseExited(e4 ->{
                root2.getChildren().remove(sav_quit2);
            });
            return_to_game.setOnMouseEntered(e4 ->{
                root2.getChildren().add(return_to_game2);
            });
            return_to_game2.setOnMouseExited(e4 ->{
                root2.getChildren().remove(return_to_game2);
            });
            sav_quit2.setOnMouseClicked(e4 ->{
                stage2.close();
                stage.setScene(prevScene);
            });
            return_to_game2.setOnMouseClicked(e4 ->{
                ultimatePause=false;
                stage2.close();
            });
            root2.getChildren().addAll(save,sav_quit,return_to_game);
            stage2.setScene(s2);
            stage2.show();
        });
    }

    public static Pane getCardPane() {
        return root1;
    }

    public static ImageView imageGetter(String s){
        FileInputStream fl= null;
        try {
            fl = new FileInputStream(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image img=new Image(fl);
        ImageView image = new ImageView(img);
        return image;
    }
    public static void location_setter(ImageView image,double x,double y){
        image.setX(x);
        image.setY(y);
    }

    public static void location_setter2(ImageView image,double x,double y){
        image.setX(x);
        image.setY(y);
    }
    //    public static void location_setter3(ImageView image,double x,double y){
//        image.setX(x);
//        image.setY(y);
//    }
    public static int[] scaledPosition(int x,int y){
        int [] reversedArrX=new int[]{860,775,668,562,462,357,256,152};
        int[] reversedY=new int[]{612,491,363,243,129};
        int finalX=-1;
        int finalY=-1;
        for(int i:reversedArrX){
            if(i<=x){
                finalX=i;
                break;
            }
        }
        for(int i:reversedY){
            if(i<=y){
                finalY=i;
                break;
            }
        }
        int[] arr=new int[]{finalX,finalY};
        return arr;

    }

    public static int[] intPosition(int x,int y){
        int [] reversedArrX=new int[]{860,775,668,562,462,357,256,152};
        int[] reversedY=new int[]{612,491,363,243,129};
        int finalX=-1;
        int finalY=-1;
        for(int i=7;i>=0;i--){
            if(reversedArrX[i]==x){
                finalX=7-i;
                break;
            }
        }
        for(int i=4;i>=0;i--){
            if(reversedY[i]==y){
                finalY=4-i;
                break;
            }
        }
        int[] arr=new int[]{finalX,finalY};
        return arr;
    }
    public static int getOriginalY(int y){
        int[] reversedY=new int[]{612,491,363,243,129};
        return reversedY[4-y];
    }
    public static int getOriginalX(int x){
        int [] reversedArrX=new int[]{860,775,668,562,462,357,256,152};
        return reversedArrX[7-x];
    }

    public static boolean final_locaiton_setter(ImageView image, double xx, double yy, Pane root){ ///return true if location is correct
        int x=(int)(xx);
        int y=(int)yy;
        int[] arr=scaledPosition(x,y);
        int finalX=arr[0];
        int finalY=arr[1];
        if(finalX>=0 && finalY>=0){
            location_setter2(image,finalX,finalY);
            return true;
        }
        root.getChildren().remove(image);
        return false;
    }


    public void mouseExitedGlow(ImageView i) {
        Glow g = new Glow();
        g.setLevel(0);
        i.setEffect(g);
    }
    public void mouseEnteredGlow(ImageView i) {
        Glow g = new Glow();
        g.setLevel(0.6);
        i.setEffect(g);
    }

    public void mouseOnCardGlow() {
        plant_pane.sun_card.setOnMouseEntered(e2 -> {
            mouseEnteredGlow(plant_pane.sun_card);

        });
        plant_pane.sun_card.setOnMouseExited(e1 -> {
            mouseExitedGlow(plant_pane.sun_card);
        });

        plant_pane.pea_card.setOnMouseEntered(e3 -> {
            mouseEnteredGlow(plant_pane.pea_card);
        });
        plant_pane.pea_card.setOnMouseExited(e1 -> {
            mouseExitedGlow(plant_pane.pea_card);
        });

        plant_pane.wallnut_card.setOnMouseEntered(e2 -> {
            mouseEnteredGlow(plant_pane.wallnut_card);
        });
        plant_pane.wallnut_card.setOnMouseExited(e1 -> {
            mouseExitedGlow(plant_pane.wallnut_card);
        });

        plant_pane.ice_pea_card.setOnMouseEntered(e4 -> {
            mouseEnteredGlow(plant_pane.ice_pea_card);
        });
        plant_pane.ice_pea_card.setOnMouseExited(e1 -> {
            mouseExitedGlow(plant_pane.ice_pea_card);
        });

        plant_pane.cherry_card.setOnMouseEntered(e2 -> {
            mouseEnteredGlow(plant_pane.cherry_card);
        });
        plant_pane.cherry_card.setOnMouseExited(e1 -> {
            mouseExitedGlow(plant_pane.cherry_card);
        });
    }

    public void onMousePressed(MouseEvent event, Plant p) {
        event.setDragDetect(true);
//        sunflower = new Sunflower();
        //            Sunflower sf=new Sunflower();
        p.showGif();
        root1.getChildren().add(p.getIdleGif());
        location_setter2(p.getIdleGif(),event.getSceneX(),event.getSceneY());
    }

    public void onMouseReleased(MouseEvent event, Plant p) {
//        final_locaiton_setter(p.getIdleGif(), event.getSceneX(), event.getSceneY(), root1);
        int[] arr = scaledPosition((int) event.getSceneX(), (int) event.getSceneY());
        int xx = arr[0];
        int yy = arr[1];
        try {
            int[] err = intPosition(xx, yy);
            if (GridArr[err[0]][err[1]] == null) {
                final_locaiton_setter(p.getIdleGif(), event.getSceneX(), event.getSceneY(), root1);
                Plant copy = p.clone();
                copy.setPosX(xx);
                copy.setPosY(yy);

                GridArr[err[0]][err[1]] = copy; //////this is the main array that contains the plant.
                if (copy.name.equals("peashooter")) {
                    PeaShooter pNow = (PeaShooter) (copy);
                    pNow.root = root1;
                    pNow.zombieList = this.zombieList;
                    pNow.showPea();
                    pNow.peaTimer();
                }
                else if (copy.name.equals("icepeashooter")) {
                    IcePeashooter ipNow = (IcePeashooter) (copy);
                    ipNow.root = root1;
                    ipNow.zombieList = this.zombieList;
                    ipNow.showPea();
                    ipNow.peaTimer();
                }
                else if(copy.name.equals("sunflower")){
                    Sunflower sunNow=(Sunflower)(copy);
                    sunNow.root=root1;
                    sunNow.showSun();
                    sunNow.sunTimer();
                }
                else if (copy.name.equals("cherry")) {
                    CherryBomb cb = (CherryBomb) (copy);
                    cb.lp = this;
                    cb.zombieList = this.zombieList;
                }
            }
            else {
                System.out.println("Cannot plant here");
                root1.getChildren().remove(p.getIdleGif());
            }
        } catch (CloneNotSupportedException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            changeSunCount(p.getPrice());
            root1.getChildren().remove(p.getIdleGif());
        }
    }

    public void dragCards() {
        System.out.println(sunCount);
        Sunflower sunflower = new Sunflower(this);
            plant_pane.sun_card.setOnMousePressed(event -> {
                buyFlag = sunCount >= sunflower.getPrice();
                if (buyFlag) {
                    changeSunCount(-sunflower.getPrice());
                    onMousePressed(event, sunflower);
                }
            });
            plant_pane.sun_card.setOnMouseReleased(event -> {
                if (buyFlag)
                    onMouseReleased(event, sunflower);
            });
            plant_pane.sun_card.setOnMouseDragged(event -> {
                event.setDragDetect(false);
                if (buyFlag)
                    location_setter2(sunflower.getIdleGif(), event.getSceneX() - 20, event.getSceneY() - 20);
            });


        PeaShooter peaShooter = new PeaShooter(this);
            plant_pane.pea_card.setOnMousePressed(event -> {
                buyFlag = sunCount >= peaShooter.getPrice();
                if (buyFlag) {
                    changeSunCount(-peaShooter.getPrice());
                    onMousePressed(event, peaShooter);
                }
            });
            plant_pane.pea_card.setOnMouseReleased(event -> {
                if (buyFlag)
                    onMouseReleased(event, peaShooter);
            });
            plant_pane.pea_card.setOnMouseDragged(event -> {
                event.setDragDetect(false);
                if (buyFlag)
                    location_setter2(peaShooter.getIdleGif(), event.getSceneX() - 20, event.getSceneY() - 20);
            });

        Walnut walnut = new Walnut(this);
            plant_pane.wallnut_card.setOnMousePressed(event -> {
                buyFlag = sunCount >= walnut.getPrice();
                if (buyFlag) {
                    changeSunCount(-walnut.getPrice());
                    onMousePressed(event, walnut);
                }
            });
            plant_pane.wallnut_card.setOnMouseReleased(event -> {
                if (buyFlag)
                    onMouseReleased(event, walnut);
            });
            plant_pane.wallnut_card.setOnMouseDragged(event -> {
                event.setDragDetect(false);
                if (buyFlag)
                    location_setter2(walnut.getIdleGif(), event.getSceneX() - 20, event.getSceneY() - 20);
            });


        IcePeashooter icePeashooter = new IcePeashooter(this);
            plant_pane.ice_pea_card.setOnMousePressed(event -> {
                buyFlag = sunCount >= icePeashooter.getPrice();
                if (buyFlag) {
                    changeSunCount(-icePeashooter.getPrice());
                    onMousePressed(event, icePeashooter);
                }
            });
            plant_pane.ice_pea_card.setOnMouseReleased(event -> {
                if (buyFlag)
                    onMouseReleased(event, icePeashooter);
            });
            plant_pane.ice_pea_card.setOnMouseDragged(event -> {
                event.setDragDetect(false);
                if (buyFlag)
                    location_setter2(icePeashooter.getIdleGif(), event.getSceneX() - 20, event.getSceneY() - 20);
            });

        CherryBomb cherryBomb = new CherryBomb(this);
            plant_pane.cherry_card.setOnMousePressed(event -> {
                buyFlag = sunCount >= cherryBomb.getPrice();
                if (buyFlag) {
                    changeSunCount(-cherryBomb.getPrice());
                    onMousePressed(event, cherryBomb);
                }
            });
            plant_pane.cherry_card.setOnMouseReleased(event -> {
                if (buyFlag)
                    onMouseReleased(event, cherryBomb);
            });
            plant_pane.cherry_card.setOnMouseDragged(event -> {
                event.setDragDetect(false);
                if (buyFlag)
                    location_setter2(cherryBomb.getIdleGif(), event.getSceneX() - 20, event.getSceneY() - 20);
            });
    }

    public void zombieTimer() {
        LawnPane lp = this;
        Timeline repeatTask = new Timeline(new KeyFrame(Duration.millis(5000), event -> {
            if (zombiesEncountered<curr_level.getTotalZombiesInLevel()) {
                Random r = new Random();
                int row = r.nextInt(4);

                int newZombiePick = r.nextInt(curr_level.getLevel());
                if (newZombiePick==0) z = new BaseZombie(lp, root1, row, GridArr);
                else if (newZombiePick==1 || newZombiePick==4) z = new ConeHead(lp, root1, row, GridArr);
                else if (newZombiePick==2) z = new BucketZombie(lp, root1, row, GridArr);
                else if (newZombiePick==3) z = new RunnerZombie(lp, root1, row, GridArr);

                root1.getChildren().add(z.getIdleGif());

                zombieList.computeIfAbsent(row, k -> new ArrayList<Zombie>());
                zombieList.get(row).add(z);
                z.startZombie();
                zombiesEncountered++;
            }
            else if (curr_level.getLevel()==5 && zombiesEncountered==curr_level.getTotalZombiesInLevel()) {
                last_addedTime += 5000;
                Random r = new Random();
                int row = r.nextInt(4);
                z = new Boss(lp, root1, row, GridArr);
                root1.getChildren().add(z.getIdleGif());
                zombieList.computeIfAbsent(row, k -> new ArrayList<Zombie>());
                zombieList.get(row).add(z);
                z.startZombie();
                zombiesEncountered++;
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


    public void sunTimer() {
        LawnPane lp = this;
        last_addedTimeSun=System.currentTimeMillis();
        Random rand=new Random();
        int [] reversedArrX=new int[]{860,775,668,562,462,357,256,152};
        Timeline repeatTaskSun = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            if(System.currentTimeMillis()-last_addedTimeSun>10000){
                last_addedTimeSun+=10000;
                s = new Sun(reversedArrX[rand.nextInt(8)]);
                root1.getChildren().add(s.sun);
                s.mouseClicked(root1,lp);
            }
        }));
        repeatTaskSun.setCycleCount(Timeline.INDEFINITE);
        repeatTaskSun.play();

        Timeline checker = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(LawnPane.ultimatePause){
                    repeatTaskSun.pause();
                }else{
                    repeatTaskSun.play();
                }
            }
        }));
        checker.setCycleCount(Timeline.INDEFINITE);
        checker.play();
    }

    public void changeSunCount(int x) {
        sunCount += x;
        label1.setText(Integer.toString(sunCount));
    }
}