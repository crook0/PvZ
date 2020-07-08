package sample;

import java.io.IOException;
import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    protected LawnPane lawnPane;
    Player(String name, LawnPane lp) {
        this.name = name;
        lawnPane = lp;
    }

    public String getName() {
        return name;
    }
    public int getNumSunsAvailable() {
        return lawnPane.sunCount;
    }
//    public class Seriazlize() throws IOException {
//
//    }
}
