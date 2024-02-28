package main.views.Cell;

import javafx.scene.image.*;
import javafx.scene.paint.*;
import main.views.*;

import javax.sound.sampled.*;
import java.io.*;

public class ArmyCell extends Cell implements BuildingCell, Attackable {

    private static final String imageURL = "file:resources\\images\\city\\Knight.png";//path to image of city
    private static final String imageEnemyURL = "file:resources\\images\\city\\EnemyKnight.png";
    private Army army;
    private Cell prevCell;
    private Clip clip;
    private String sound = "resources\\music\\Army.wav";

    public ArmyCell(int length, int x, int y) {
        super(length, x, y, false, false, true);
    }



    private boolean isProperCell(Cell cell) {
        for (City city : PlayersHandler.getPlayersHandler().getPlayer(0).getCities()) {
            if (cell instanceof BuildingCell) {
                if (city.hasBuilding((BuildingCell) cell))
                    return false;
            }
            if (city.getCityCell() == cell)
                return false;
        }
        return true;
    }

    public Army getArmy() {
        return army;
    }

    public void setArmy(Army army) {
        this.army = army;
    }

    public Cell getPrevCell() {
        return prevCell;
    }

    public void setPrevCell(Cell prevCell) {
        this.prevCell = prevCell;
    }

    @Override
    protected void setCellImage() {
        if (PlayersHandler.getPlayersHandler().getPlayer(0).hasCity(getCityWhereBuild())) {
            fillCell(imageURL);
        } else {
            fillCell(imageEnemyURL);
        }
    }

    private void playSound() {
        try {
            File soundFile = new File(sound);
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();

        } catch (IOException exc) {
            exc.printStackTrace();
        } catch (UnsupportedAudioFileException exc) {
            exc.printStackTrace();
        } catch (LineUnavailableException exc) {
            exc.printStackTrace();
        }
    }
}