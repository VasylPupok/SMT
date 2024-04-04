package main.views.Cell;

import javafx.scene.image.*;
import javafx.scene.paint.*;
import main.ToolPanel;
import main.views.MapView;
import main.views.PlayersHandler;

import javax.sound.sampled.*;
import java.io.*;


public class GoldmineCell extends Cell implements BuildingCell{

    private static final String imageURL = "file:resources\\images\\city\\buildings\\Goldmine.png";//path to image of goldmine
    private static final String imageEnemyURL = "file:resources\\images\\city\\buildings\\EnemyGoldmine.png";
    private Clip clip;
    private String sound = "resources\\music\\Goldmine.wav";
    private boolean isOurs;

    public GoldmineCell(int length, int x, int y) {
        super(length,x,y,false, true, false);
    }

    @Override
    protected void clickResponse() throws IOException, InterruptedException {
        super.clickResponse();
        if(isReadyToMove()){
            getCityWhereBuild().deleteBuilding(this);
            ArmyCell army = getArmyCell();
            getArmyCell().fillFields();
            this.setArmyCellView(army);
            MapView.getMapView().moveArmy(takeX(), takeY(), getArmyCell());
            getArmyCell().setPrevCell(null);
            if(army.getArmy().getHealth() > 0) {
                checkIfCanGotAttack();
            }
            PlayersHandler.getPlayersHandler().getPlayer(1).moveArmy();
        }  else if (ToolPanel.getInstance().getActionsPanel().isReadyToDelete()) {
            getCityWhereBuild().deleteBuilding(this);
        }
        playSound();
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
