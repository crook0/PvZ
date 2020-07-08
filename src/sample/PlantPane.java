package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;

public class PlantPane implements Serializable {
    private long serialVersionUID = 3L;
    protected ImageView cherry_card, pea_card, ice_pea_card, sun_card, wallnut_card, cherry_card2, pea_card2, ice_pea_card2, sun_card2, wallnut_card2;
    private ArrayList<ImageView> curr_cards = new ArrayList<>();

    PlantPane() {
        try {
            cherry_card = GameMenu.imageGetter("src/sample/images/card_cherrybomb.png");
            pea_card = GameMenu.imageGetter("src/sample/images/card_peashooter.png");
            ice_pea_card = GameMenu.imageGetter("src/sample/images/card_freezepeashooter.png");
            sun_card = GameMenu.imageGetter("src/sample/images/card_sunflower.png");
            wallnut_card = GameMenu.imageGetter("src/sample/images/card_wallnut.png");
            cherry_card2 = GameMenu.imageGetter("src/sample/images/card_cherrybomb2.png");
            pea_card2 = GameMenu.imageGetter("src/sample/images/card_peashooter2.png");
            ice_pea_card2 = GameMenu.imageGetter("src/sample/images/card_freezepeashooter2.png");
            sun_card2 = GameMenu.imageGetter("src/sample/images/card_sunflower2.png");
            wallnut_card2 = GameMenu.imageGetter("src/sample/images/card_wallnut2.png");

            GameMenu.location_setter(pea_card, 110,10);
            GameMenu.location_setter(sun_card, 200,10);
            GameMenu.location_setter(wallnut_card, 290,10);
            GameMenu.location_setter(ice_pea_card, 380,10);
            GameMenu.location_setter(cherry_card, 470,10);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void add(Plant p) {
        if (p instanceof Sunflower) {
            curr_cards.add(sun_card);
        }
        if (p instanceof PeaShooter) {
            curr_cards.add(pea_card);
        }
        if (p instanceof Walnut) {
            curr_cards.add(wallnut_card);
        }
        if (p instanceof IcePeashooter) {
            curr_cards.add(ice_pea_card);
        }
        if (p instanceof CherryBomb) {
            curr_cards.add(cherry_card);
        }
    }

    public void show_cards(Pane root) {
        for(ImageView i : curr_cards) {
            if (!root.getChildren().contains(i))
                root.getChildren().add(i);
        }
    }
}