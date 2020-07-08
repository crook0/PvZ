package sample;

import java.io.Serializable;

public abstract class Character implements Serializable, Cloneable {
    private static long serialVersionUID = 1L;
    protected LawnPane lp;
    private int attack_power, pos_x, pos_y, current_hp, max_hp;
    protected boolean active = true;
    protected int intPosX;
    protected int intPosY;
    protected boolean zombieInLane;
    protected static int kills;
    int[] err;


    Character(LawnPane lp, int current_hp, int attack_power) {
        this.lp = lp;
        this.current_hp = current_hp;
        this.attack_power = attack_power;
    }

    public abstract void showGif();

    public void decreaseHealth(int damage){
        current_hp-=damage;
        if(current_hp==0){
            active=false;
            if (this instanceof Zombie) {
                kills++;
                System.out.println(((double) kills) / (lp.curr_level.getTotalZombiesInLevel()+lp.curr_level.getLevel()/5));
                lp.pb.setProgress(((double) kills) / (lp.curr_level.getTotalZombiesInLevel()+lp.curr_level.getLevel()/5));
            }
        }
    }

    public int getAttackPower() {
        return attack_power;
    }
    public void setAttackPower(int x) {
        attack_power = x;
    }
    public void setCurrentHp(int x) {
        current_hp = x;
    }
    public int getMaxHp() {
        return max_hp;
    }
    public int getCurrentHp() {
        return current_hp;
    }
    public boolean isDead() {
        return current_hp <= 0;
    }
    public void changeHp(int x) {
        current_hp += x;
    }
    public int getPosX() {
        return pos_x;
    }
    public int getPosY() {
        return pos_y;
    }
    public void setPosX(int x) {
        pos_x = x;
    }
    public void setPosY(int y) {
        pos_y = y;
        setterErr();
    }
    public void setterErr(){
        err=LawnPane.intPosition(getPosX(),getPosY());
        intPosX=err[0];
        intPosY=err[1];

    }
    public int getIntPosX(){return intPosX;}
    public int getIntPosY(){return intPosY;}

}
