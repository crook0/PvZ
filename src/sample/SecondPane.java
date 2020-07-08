package sample;

//import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;



public class SecondPane {
    Pane root;
    Scene scene;
    ThirdPane pane3;
    protected Player player;

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
    SecondPane(Stage stage) throws Exception {


        ImageView load = imageGetter("src/sample/images/load.png");
        ImageView load2 = imageGetter("src/sample/images/load2.png");
        ImageView new_game = imageGetter("src/sample/images/new_game.png");
        ImageView new_game2 = imageGetter("src/sample/images/new_game2.png");
        ImageView quit_it = imageGetter("src/sample/images/quit_it.png");
        ImageView quit_it2 = imageGetter("src/sample/images/quit_it2.png");


        ImageView initial = imageGetter("src/sample/images/initial.png");

        ImageView enter = imageGetter("src/sample/images/enter.png");
        location_setter(enter,340,465);
        ImageView enter2 = imageGetter("src/sample/images/enter2.png");
        location_setter(enter2,340,465);
        location_setter(new_game,80,70);
        location_setter(new_game2,80,70);
        location_setter(load,90,220);
        location_setter(load2,90,220);
        location_setter(quit_it,80,355);
        location_setter(quit_it2,80,355);


        //main code after all the image imports

        Pane inroot = new Pane();
        this.root=inroot;  // just so that i can remember the origenal code
        Scene in_s = new Scene(inroot, 800, 600, Color.BLACK);
        this.scene=in_s;
        TextField tf = new TextField("please enter username");
        tf.setLayoutX(340);
        tf.setLayoutY(100);
        enter.setOnMouseEntered(e ->{
            inroot.getChildren().add(enter2);
            player = new Player(tf.getText(), null);
        });
        enter2.setOnMouseExited(e1 ->{
            inroot.getChildren().remove(enter2);
        });
        enter2.setOnMouseClicked(f ->{
            try {
                pane3 = new ThirdPane(stage, player);
                stage.setScene(pane3.scene);
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
        inroot.getChildren().addAll(initial,tf,enter);
    }
}