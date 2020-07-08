package sample;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameMenu extends Application {

    protected static Pane outsideRoot = new Pane();

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
    @Override
    public void start(Stage stage) throws Exception{
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("/Users/Asus/IdeaProjects/P/src/sample/images/audio.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        stage.setResizable(false);
        ImageView sun = imageGetter("src/sample/images/sun.png");
        ImageView pea = imageGetter("src/sample/images/pea.png");

        ImageView save_new = imageGetter("src/sample/images/save_new.png");
        ImageView load = imageGetter("src/sample/images/load.png");
        ImageView load2 = imageGetter("src/sample/images/load2.png");
        ImageView new_game = imageGetter("src/sample/images/new_game.png");
        ImageView new_game2 = imageGetter("src/sample/images/new_game2.png");
        ImageView quit_it = imageGetter("src/sample/images/quit_it.png");
        ImageView quit_it2 = imageGetter("src/sample/images/quit_it2.png");



        ImageView enter = imageGetter("src/sample/images/enter.png");
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

        new_game.setOnMouseEntered(e ->{
            final_root.getChildren().add(new_game2);

        });
        new_game2.setOnMouseExited(e1 ->{
            final_root.getChildren().remove(new_game2);
        });
        load.setOnMouseEntered(e ->{
            final_root.getChildren().add(load2);

        });
        load2.setOnMouseExited(e1 ->{
            final_root.getChildren().remove(load2);
        });
        quit_it.setOnMouseEntered(e ->{
            final_root.getChildren().add(quit_it2);

        });
        quit_it2.setOnMouseExited(e1 ->{
            final_root.getChildren().remove(quit_it2);
        });
        quit_it2.setOnMouseClicked(e1 ->{
            System.exit(0);
        });
        (new_game2).setOnMouseClicked(df ->{
            try {
                SecondPane pane2=new SecondPane(stage);
                stage.setScene(pane2.scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        (load2).setOnMouseClicked(df ->{
            try {
                SecondPane pane2=new SecondPane(stage);
                stage.setScene(pane2.scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        final_root.getChildren().addAll(save_new,new_game,load,quit_it);
        stage.setScene(final_scene);
        //=====================
//        Pane pn = new Pane();
//        Scene r1 = new Scene(pn, 1000, 752, Color.BLACK);
//        Lawn curr_lawn = new Lawn();
//        LawnPane lp = new LawnPane(stage,r1, curr_lawn, curr_lawn.getPlantPane(), curr_lawn.getLevel());
//        stage.setScene(lp.scene);
        stage.show();
    }

    public static void fade(Object o) {
        if (o instanceof Sun) {
            FadeTransition fade = new FadeTransition(Duration.seconds(1), ((Sun) o).getImage());
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.play();
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}