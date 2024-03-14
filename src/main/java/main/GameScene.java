package main;

import javafx.scene.*;
import javafx.scene.layout.*;
import main.views.MapArrView;
import main.views.MapView;
import main.views.Player;
import main.views.PlayersHandler;

import javax.sound.sampled.*;
import java.io.*;

public class GameScene extends Scene implements LevelScene{

    private MapView mapView;
    private Clip clip;
    private LevelButton levelButton;

    public GameScene(Group group, int FRAME_WIDTH, int FRAME_HEIGHT) {


        super(group, FRAME_WIDTH, FRAME_HEIGHT);
        levelButton = new LevelButton(ChooseLevelScene.getChooseLevelScene(),0, "RED", FRAME_WIDTH, FRAME_HEIGHT);
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        borderPane.setCenter(gridPane);
        PlayersHandler.getPlayersHandler().clearPlayers();
        PlayersHandler.getPlayersHandler().addPlayer(new Player());
        PlayersHandler.getPlayersHandler().addPlayer(new Player());
        mapView = MapView.getMapView(FRAME_WIDTH, 472, gridPane,30,30, this);
        group = new Group();
        group.getChildren().add(borderPane);

        this.setRoot(group);

        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    if (mapView.getMapLU().y > 0) {
                        mapView.getMapLU().y--;
                        mapView.getMapRB().y--;
                    }
                }
                case DOWN -> {
                    if (mapView.getMapRB().y < MapArrView.getMapArrView().getRowsNumber()) {
                        mapView.getMapLU().y++;
                        mapView.getMapRB().y++;
                    }
                }
                case LEFT -> {
                    if (mapView.getMapLU().x > 0) {
                        mapView.getMapLU().x--;
                        mapView.getMapRB().x--;
                    }
                }
                case RIGHT -> {
                    if (mapView.getMapRB().x < MapArrView.getMapArrView().getColumnsNumber()) {
                        mapView.getMapLU().x++;
                        mapView.getMapRB().x++;
                    }
                }

                case SPACE -> {
//                    this.getLevelButton().setPassed(true);
//                    if(this.getLevelButton().isPassed()){
//                        this.getLevelButton().changeBackground();
//                        mapView.getLevelScene().getClip().stop();
//                        mapView.getLevelScene().getClip().setMicrosecondPosition(0);
//                        WinScene winScene = null;
//                        try {
//                            winScene = new WinScene(new GridPane(), StartMenuScene.takeWidth(), StartMenuScene.takeHeight(),this);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        StartMenuScene.getStage().setScene(winScene);
//                    }
                }
                case SHIFT -> {
//                    mapView.getLevelScene().getClip().stop();
//                    mapView.getLevelScene().getClip().setMicrosecondPosition(0);
//                    LoseScene loseScene = null;
//                    try {
//                        loseScene = new LoseScene(new GridPane(), StartMenuScene.takeWidth(), StartMenuScene.takeHeight(),this);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    StartMenuScene.getStage().setScene(loseScene);
                }
            }
            //MiniMap.getMiniMap().drawMiniMap();
            mapView.drawMap();
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

        } catch (IOException exc) {
            exc.printStackTrace();
        } catch (UnsupportedAudioFileException exc) {
            exc.printStackTrace();
        } catch (LineUnavailableException exc) {
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
}
