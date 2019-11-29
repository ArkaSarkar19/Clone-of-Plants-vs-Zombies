package sample;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class Pea extends GameObject{
    protected int  attack;
    protected int speed;
    protected int lane;
    protected int xPos;
    protected boolean stop;
    protected ImageView peaImage;
    protected TranslateTransition tt;
    protected static Timeline timeline;
    protected GameScreen gameScreen;
    protected ArrayList<Zombie> ZombieLane;
    protected int[] pos;
    public Pea(int[] pos,int position,int lane, GameScreen gameScreen){
        this.xPos = position;
        this.pos = pos;
        this.lane = lane;
        this.attack = 25;
        this.speed = 25;
        if (lane ==0){
            ZombieLane = gameScreen.getLaneZombie_1();
        }
        else if (lane ==1){
            ZombieLane = gameScreen.getLaneZombie_2();
        }
        else if (lane ==2){
            ZombieLane = gameScreen.getLaneZombie_3();
        }
        else if (lane ==3){
            ZombieLane = gameScreen.getLaneZombie_4();
        }
        else{
            ZombieLane = gameScreen.getLaneZombie_5();
        }
        Image z = new Image(Pea.class.getResource("resources/spritesNStuff/Pea.png").toString());
        peaImage = new ImageView(z);
        tt = new TranslateTransition();
        tt.setNode(peaImage);
        this.gameScreen = gameScreen;
        gameScreen.lawngrid.add(this.getPeaImage(),pos[0]+1,pos[1]);
    }

    public int getAttack() {
        return attack;
    }

    public int getLane() {
        return lane;
    }

    public int getPosition() {
        return xPos;
    }

    public int getSpeed() {
        return speed;
    }
    protected void  attack(Zombie zomb){
       ;
    }

    public ImageView getPeaImage() {
        return peaImage;
    }

    protected void move(){
        tt.setDuration(Duration.millis(100));
        peaImage.setX(xPos);
        tt.setByX(+speed);
        tt.play();
        tt.setOnFinished(e->{
            peaImage.setX(peaImage.getX()+speed);
            if(peaImage.getX()>= 830){
                gameScreen.getLawngrid().getChildren().remove(this.peaImage);
                peaImage= null;
                tt.stop();
                stop=true;
                tt=null;

                System.gc();
                return;
            }
            for (int i=0;i<ZombieLane.size();i++){
                if ((ZombieLane.get(i).getZombieImage().getBoundsInLocal().getMinX() -50 <= this.peaImage.getX()) && ((ZombieLane.get(i).getZombieImage().getBoundsInLocal().getMaxX()+25 >= this.peaImage.getX()))){
                    System.out.println("HIT HUA HAI");

                    gameScreen.getLawngrid().getChildren().remove(this.peaImage);
                    attack(ZombieLane.get(i));
                    peaImage= null;
                    tt.stop();
                    stop=true;
                    System.gc();
                    break;
                }
            }
            if (!stop){
                tt.playFromStart();
            }
        });
    }

    public static void setTimeline(Timeline timeline) {
        Pea.timeline = timeline;
    }
}

class NormalPea extends Pea{
    public NormalPea(int[] pos,int position, int lane, GameScreen gameScreen){
        super(pos, position,lane,gameScreen);
    }
    @Override
    protected void  attack(Zombie zomb){
        zomb.peaAttack(attack);
    }
}