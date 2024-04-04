package main.views.Cell;
import main.views.City;
import main.views.MapView;
import main.views.PlayersHandler;
import main.ToolPanel;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.*;

/**
 * This is one of three types of cells on the board
 * Mountain cell with it's own image ready to be painted
 *
 * @author Vasia_Pupkin
 * @version 1.0.0
 * @see Cell
 */
public class MountainCell extends Cell {

    private static final String imageURL = "file:resources\\images\\cells\\Mountain.png";//path to image of mountain

    public MountainCell(int length, int x, int y) {
        super(length, x, y, true, true, false);
    }

    public MountainCell(int length) {
        super(length);
    }

    @Override
    protected void clickResponse() throws IOException, InterruptedException {
        super.clickResponse();
        if (isReadyToBuild()) {
            if (getCityWhereBuild().getBuildings().size() < 8) {
                MapView.getMapView().buildResources(takeX(), takeY(), getCityWhereBuild(), ToolPanel.getInstance().getActionsPanel().getBuildingType());
                setReadyToBuild(false);
            }
        } else if (isReadyToMove()) {
            ArmyCell army = getArmyCell();
            getArmyCell().fillFields();
            this.setArmyCellView(army);
            MapView.getMapView().moveArmy(takeX(), takeY(), getArmyCell());
            getArmyCell().setPrevCell(this);
            if(army.getArmy().getHealth() > 0) {
                checkIfCanGotAttack();
            }
            //TODO decide whether bot move 1 army, or all armies
            //PlayersHandler.getPlayersHandler().getPlayer(1).moveAllBotArmies();
            PlayersHandler.getPlayersHandler().getPlayer(1).moveArmy();
            //right mechanics of collecting and spending resources
            for (City i: PlayersHandler.getPlayersHandler().getPlayer(0).getCities()){
                i.collectResources();
                i.spendResources();
            }
            for (City i: PlayersHandler.getPlayersHandler().getPlayer(1).getCities()){
                i.collectResources();
                i.spendResources();
            }
            army.getArmy().getArmyCell().setChosen(false);
            //ToolPanel.getInstance().refresh();
        }
    }
    @Override
    protected void setCellImage() {
        Image mountain = new Image(imageURL);
        this.setFill(new ImagePattern(mountain, super.getX(), super.getY(), super.getWidth(), super.getHeight(), false));
    }

}
