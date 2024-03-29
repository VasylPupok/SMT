package main.views.Cell;

import javafx.scene.image.*;
import javafx.scene.paint.*;

import javax.sound.sampled.*;
import java.io.*;


public class GoldmineCell extends Cell {

    private static final String imageURL = "file:resources\\images\\city\\buildings\\Goldmine.png";//path to image of goldmine
    private static final String imageEnemyURL = "file:resources\\images\\city\\buildings\\EnemyGoldmine.png";
    private Clip clip;
    private String sound = "resources\\music\\Goldmine.wav";
    private boolean isOurs;

    public GoldmineCell(int length, int x, int y) {
        super(length,x,y,false, true, false);
    }


    @Override
    protected void setCellImage() {
            fillCell(imageEnemyURL);
    }

    private void playSound(){
        try {
            File soundFile = new File(sound);
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();

        } catch (IOException  exc) {
            exc.printStackTrace();
        } catch (UnsupportedAudioFileException exc) {
            exc.printStackTrace();
        } catch (LineUnavailableException exc) {
            exc.printStackTrace();
        }
    }
}
