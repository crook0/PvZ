package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import static sample.GameMenu.location_setter;
import java.util.Random;

public class Sun {
    protected ImageView sun = GameMenu.imageGetter("src/sample/images/sun.png");
    AnimationTimer repeatTaskP;
    int[] reversedY=new int[]{612,491,363,243,129};
    private boolean sunFall;
    private int sunFadeCountDown, sunValue;
//    final double[] y;
    long startNanoTime1;
    long posX, posY;
    boolean flag=false;
    protected boolean removeSun = false;

    Sun(int xx){
        Random rand=new Random();
//        y = new double[]{0};
        posY=0;
        startNanoTime1 = System.nanoTime();
        int endPosition=reversedY[rand.nextInt(5)];
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                repeatTaskP=this;
                long t = (currentNanoTime - startNanoTime1) / 20000000;
                posX = xx;
                if(posY<endPosition && !flag){
                    posY = t;
                    location_setter(sun, posX,posY);
//                    System.out.println("before"+posY);
                }
                if(posY>=endPosition && !flag){
                    flag=true;
                    PauseTransition pause = new PauseTransition(Duration.seconds(10));
                    pause.setOnFinished(event -> GameMenu.fade(this));
                    pause.play();
                }
            }
        }.start();
    }

    public void mouseClicked(Pane root,LawnPane lp){
        sun.setOnMouseClicked(r->{
            repeatTaskP.stop();
            Line line= new Line();
            lp.sunCount+=50;
            System.out.println(lp.sunCount);
            lp.label1.setText(Integer.toString(lp.sunCount));
            line.setStartX(posX); line.setStartY(posY);
            line.setEndX(60); line.setEndY(40);
            PathTransition transition = new PathTransition();
            transition.setDuration(Duration.seconds(1));
            transition.setNode(sun);
            transition.setPath(line);
            transition.play();

        });
    }
    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }
    public ImageView getImage() {
        return sun;
    }
}