package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
public class ThirdPane {

    protected Pane root;
    protected Scene scene;
    private int sunCount = 0, lan_count=0;
    private static Pane root1;
    private LawnPane paneLawn;
    private Lawn curr_lawn = new Lawn(paneLawn);
    private PlantPane plant_pane = curr_lawn.getPlantPane();
    private Level curr_level = curr_lawn.getLevel();
    protected Player player;

    ImageView sun = imageGetter("src/sample/images/sun.png");
    ImageView pea = imageGetter("src/sample/images/pea.png");

    ImageView save_new = imageGetter("src/sample/images/save_new.png");
    ImageView load = imageGetter("src/sample/images/load.png");
    ImageView load2 = imageGetter("src/sample/images/load2.png");
    ImageView new_game = imageGetter("src/sample/images/new_game.png");
    ImageView new_game2 = imageGetter("src/sample/images/new_game2.png");
    ImageView quit_it = imageGetter("src/sample/images/quit_it.png");
    ImageView quit_it2 = imageGetter("src/sample/images/quit_it2.png");
    ImageView sel_level = imageGetter("src/sample/images/sel_level.png");
    ImageView sel_level2 = imageGetter("src/sample/images/sel_level2.png");

    ImageView initial = imageGetter("src/sample/images/initial.png");
    ImageView image = imageGetter("src/sample/images/menu.jpg");
    ImageView image1 = imageGetter("src/sample/images/adv.png");
    ImageView image2 = imageGetter("src/sample/images/adv2.png");
    ImageView BGimg = imageGetter("src/sample/images/mainBG.png");

    ImageView me = imageGetter("src/sample/images/me.jpg");
    ImageView me2 = imageGetter("src/sample/images/me2.jpg");
    ImageView quit = imageGetter("src/sample/images/quit.png");
    ImageView quit2 = imageGetter("src/sample/images/quit2.png");
    ImageView help = imageGetter("src/sample/images/help.png");
    ImageView help2 = imageGetter("src/sample/images/help2.png");
    ImageView instructions = imageGetter("src/sample/images/inst.jpg");
    ImageView lm = imageGetter("src/sample/images/LM.png");
    ImageView lm1 = imageGetter("src/sample/images/LM.png");
    ImageView lm2 = imageGetter("src/sample/images/LM.png");
    ImageView lm3 = imageGetter("src/sample/images/LM.png");
    ImageView lm4 = imageGetter("src/sample/images/LM.png");


    ImageView save = imageGetter("src/sample/images/sav.jpg");
    ImageView sav_quit = imageGetter("src/sample/images/sav_quit.png");
    ImageView sav_quit2 = imageGetter("src/sample/images/sav_quit2.png");
    ImageView return_to_game = imageGetter("src/sample/images/return_to_game.png");
    ImageView return_to_game2 = imageGetter("src/sample/images/return_to_game2.png");

    ImageView enter = imageGetter("src/sample/images/enter.png");

    ThirdPane(Stage stage, Player player) throws Exception {
        this.player = player;
        location_setter(enter,340,465);
        ImageView enter2 = imageGetter("src/sample/images/enter2.png");
        location_setter(enter2,340,465);

        Pane final_root=new Pane();
        Scene final_scene=new Scene(final_root,636,502);
        location_setter(new_game,80,70);
        location_setter(new_game2,80,70);
        location_setter(load,90,220);
        location_setter(load2,90,220);
        location_setter(quit_it,80,355);
        location_setter(quit_it2,80,355);

        Pane root = new Pane();
        this.root=root;
        Scene s = new Scene(root, 1012, 785, Color.BLACK);
        this.scene=s;
        Label label1 = new Label(Integer.toString(sunCount));
        label1.setTextFill(Color.web("#ff0000", 0.8));
        label1.setLayoutX(60);
        label1.setLayoutY(80);

        location_setter(image1,510,100);
        location_setter(image2,510,100);
        location_setter(quit,882,567);
        location_setter(quit2,882,567);
        location_setter(help,797,545);
        location_setter(help2,797,545);

        location_setter(lm,45,120);
        location_setter(lm1,45,250);
        location_setter(lm2,45,360);
        location_setter(lm3,45,480);
        location_setter(lm4,45,600);

//        Listener for MouseClick
        image1.setOnMouseEntered(e ->{
            root.getChildren().add(image2);

        });
        image2.setOnMouseExited(e1 ->{
            root.getChildren().remove(image2);
        });
        image2.setOnMouseClicked(e -> {
            try {
                if(paneLawn==null){
                    paneLawn = new LawnPane(player, stage, this.scene, curr_lawn, plant_pane, curr_level);
                    stage.setScene(paneLawn.scene);
                }else{
                    stage.setScene(paneLawn.scene);
                    LawnPane.ultimatePause=false;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        quit.setOnMouseEntered(e ->{
            root.getChildren().add(quit2);
        });
        quit2.setOnMouseExited(e1 ->{
            root.getChildren().remove(quit2);
        });
        quit2.setOnMouseClicked(e2 ->{
//            stage.set
            Stage stage2=new Stage();
            Pane root2 = new Pane();
            Scene s2 = new Scene(root2, 676, 388, Color.BLACK);
            location_setter(sav_quit,150,200);
            location_setter(return_to_game,150,100);
            location_setter(sav_quit2,150,200);
            location_setter(return_to_game2,150,100);

            sav_quit.setOnMouseEntered(e ->{
                root2.getChildren().add(sav_quit2);
            });
            sav_quit2.setOnMouseExited(e1 ->{
                root2.getChildren().remove(sav_quit2);
            });
            return_to_game.setOnMouseEntered(e ->{
                root2.getChildren().add(return_to_game2);
            });
            return_to_game2.setOnMouseExited(e1 ->{
                root2.getChildren().remove(return_to_game2);
            });
            sav_quit2.setOnMouseClicked(e ->{
                stage2.close();
                stage.close();
            });
            return_to_game2.setOnMouseClicked(e ->{
                stage2.close();
            });
            root2.getChildren().addAll(save,sav_quit,return_to_game);
            stage2.setScene(s2);
            stage2.show();
        });
        help.setOnMouseEntered(e ->{
            root.getChildren().add(help2);
        });
        help2.setOnMouseExited(e1 ->{
            root.getChildren().remove(help2);
        });

        help2.setOnMouseClicked(e2 ->{
//            stage.set
            Stage stage2=new Stage();
            Pane root2 = new Pane();
            Scene s2 = new Scene(root2, 960, 640, Color.BLACK);
            location_setter(sav_quit,150,200);
            location_setter(return_to_game,150,100);

            instructions.setOnMouseClicked(e ->{
                stage2.close();
            });
            root2.getChildren().addAll(instructions);
            stage2.setScene(s2);
            stage2.show();
        });
        TextField level_text=new TextField("type a num bw 1 to 5");
        level_text.setLayoutX(150);
        level_text.setLayoutY(300);

        location_setter(sel_level,350,300);
        location_setter(sel_level2,350,300);


        sel_level.setOnMouseEntered(e ->{
            root.getChildren().add(sel_level2);

        });
        sel_level2.setOnMouseExited(e1 ->{
            root.getChildren().remove(sel_level2);
        });
        sel_level2.setOnMouseClicked(e1 ->{
//                int gh=Integer.parseInt(level_text.getText());
            try{
                int gh=Integer.parseInt(level_text.getText());
                if (gh<=5) {
                    curr_level.setLevel(gh);
                    level_text.setText("level "+gh+" selected");
                } else {
                    level_text.setText("select a valid level");
                }

            } catch (NumberFormatException e){
                level_text.setText("select a valid level");
            } catch (NullPointerException n){
                System.out.println(curr_level.getLevel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        root.getChildren().addAll( image,image1,quit,help,sel_level,level_text);
    }

    public static Pane getCardPane() {
        return root1;
    }

    public static ImageView imageGetter(String s) throws Exception{
        FileInputStream fl=new FileInputStream(s);
        Image img=new Image(fl);
        ImageView image = new ImageView(img);
        return image;
    }
    public static void location_setter(ImageView image,double x,double y){
        image.setX(x);
        image.setY(y);
    }
}