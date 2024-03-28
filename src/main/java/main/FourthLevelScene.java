package main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.views.MapArrView;
import main.views.MapView;
import main.views.Player;
import main.views.PlayersHandler;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FourthLevelScene extends Scene implements LevelScene {

    private LevelButton levelButton;
    private Clip clip;
    private MapView fourthLevel;

    public FourthLevelScene(Group group, int FRAME_WIDTH, int FRAME_HEIGHT, LevelButton levelButton) {

        super(group, FRAME_WIDTH, FRAME_HEIGHT);
        this.levelButton = levelButton;

        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        borderPane.setCenter(gridPane);
        PlayersHandler.getPlayersHandler().clearPlayers();
        PlayersHandler.getPlayersHandler().addPlayer(new Player());
        PlayersHandler.getPlayersHandler().addPlayer(new Player());
        fourthLevel = MapView.getMapView(FRAME_WIDTH, 472, gridPane, 23, 23, this, 4);
        borderPane.setBottom(ToolPanel.getInstance());
        ToolPanel.getInstance().initMapPanel();
        group = new Group();
        group.getChildren().add(borderPane);


        this.setRoot(group);

        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    if (fourthLevel.getMapLU().y > 0) {
                        fourthLevel.getMapLU().y--;
                        fourthLevel.getMapRB().y--;
                    }
                }
                case DOWN -> {
                    if (fourthLevel.getMapRB().y < MapArrView.getMapArrView().getRowsNumber()) {
                        fourthLevel.getMapLU().y++;
                        fourthLevel.getMapRB().y++;
                    }
                }
                case LEFT -> {
                    if (fourthLevel.getMapLU().x > 0) {
                        fourthLevel.getMapLU().x--;
                        fourthLevel.getMapRB().x--;
                    }
                }
                case RIGHT -> {
                    if (fourthLevel.getMapRB().x < MapArrView.getMapArrView().getColumnsNumber()) {
                        fourthLevel.getMapLU().x++;
                        fourthLevel.getMapRB().x++;
                    }
                }
                case ESCAPE -> {
//                    try {
//                        StartMenuScene.getStage().setScene(new PauseScene(new GridPane(),FRAME_WIDTH,FRAME_HEIGHT,this));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                case SPACE -> {
                    this.getLevelButton().setPassed(true);
                    if (this.getLevelButton().isPassed()) {
                        this.getLevelButton().changeBackground();
                        this.getMapView().getLevelScene().getClip().stop();
                        this.getMapView().getLevelScene().getClip().setMicrosecondPosition(0);
//                        WinScene winScene = null;
//                        try {
//                            winScene = new WinScene(new GridPane(), StartMenuScene.takeWidth(), StartMenuScene.takeHeight(),this);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        StartMenuScene.getStage().setScene(winScene);
                    }
                }
                case SHIFT -> {
                    this.getMapView().getLevelScene().getClip().stop();
                    this.getMapView().getLevelScene().getClip().setMicrosecondPosition(0);
//                    LoseScene loseScene = null;
//                    try {
//                        loseScene = new LoseScene(new GridPane(), StartMenuScene.takeWidth(), StartMenuScene.takeHeight(),this);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    StartMenuScene.getStage().setScene(loseScene);
                }
            }
            MiniMap.getMiniMap().drawMiniMap();
            fourthLevel.drawMap();
        });

        playBackMusic();
    }

    private void playBackMusic() {
        try {
            File soundFile = new File("resources/music/Game.wav");
            ;
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

            clip = AudioSystem.getClip();
            clip.open(ais);

            clip.setFramePosition(0);
            clip.start();

            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public LevelButton getLevelButton() {
        return levelButton;
    }

    @Override
    public Clip getClip() {
        return clip;
    }

    public MapView getMapView() {
        return fourthLevel;
    }

}
