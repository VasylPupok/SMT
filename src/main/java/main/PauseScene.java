package main;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.util.*;
import main.LevelScene;
import main.StartMenuScene;

import javax.sound.sampled.*;
import java.io.*;

public class PauseScene extends Scene {

    private Clip clip;
    private VBox pauseMenu;
    private Label pauseLabel;
    private Button continueButton;
    private Button repeatButton;
    private Button backButton;
    private LevelScene levelScene;
    private Timeline timeline;

    public PauseScene(GridPane pane, int FRAME_WIDTH, int FRAME_HEIGHT, LevelScene levelScene) throws FileNotFoundException {

        super(pane,FRAME_WIDTH,FRAME_HEIGHT);
        this.levelScene = levelScene;

        pauseLabel = new Label("Бойова пауза");
        pauseLabel.setFont(Font.font("Candara", FontWeight.BOLD, 70));
        pauseLabel.setTextFill(Paint.valueOf("WHITE"));

        continueButton = new Button("Продовжити");
        continueButton.setPrefWidth(250);
        continueButton.setPrefHeight(90);
        continueButton.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-text-alignment: center");
        continueButton.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        continueButton.setOnMouseEntered(mouseEvent -> continueButton.setStyle("-fx-background-color: brown; -fx-text-fill: white; -fx-text-alignment: center"));
        continueButton.setOnMouseExited(mouseEvent -> continueButton.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-text-alignment: center"));
        continueButton.setOnMouseClicked(mouseEvent -> {
            getClip().stop();
            StartMenuScene.getStage().setScene((Scene) levelScene);
            levelScene.getClip().start();
        });

        repeatButton = new Button("Спочатку");
        repeatButton.setPrefWidth(250);
        repeatButton.setPrefHeight(90);
        repeatButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center");
        repeatButton.setFont(Font.font("Candara", FontWeight.BOLD, 25));
        repeatButton.setOnMouseEntered(mouseEvent -> repeatButton.setStyle("-fx-background-color: brown; -fx-text-fill: white; -fx-text-alignment: center"));
        repeatButton.setOnMouseExited(mouseEvent -> repeatButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center"));
        repeatButton.setOnMouseClicked(mouseEvent -> {
            getClip().stop();
            levelScene.getLevelButton().runLevel();
        });

        backButton = new Button("До головного меню");
        backButton.setPrefWidth(250);
        backButton.setPrefHeight(90);
        backButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center");
        backButton.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        backButton.setOnMouseEntered(mouseEvent -> backButton.setStyle("-fx-background-color: brown; -fx-text-fill: white; -fx-text-alignment: center"));
        backButton.setOnMouseExited(mouseEvent -> backButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center"));
        backButton.setOnMouseClicked(mouseEvent -> {
            getClip().stop();
            StartMenuScene.getStage().setScene(StartMenuScene.getLoadingScene());
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                StartMenuScene.getStage().setScene(StartMenuScene.getStartMenuScene());
                StartMenuScene.getStartMenuScene().getClip().setMicrosecondPosition(0);
                StartMenuScene.getStartMenuScene().getClip().start();
            }));
            timeline.play();
        });

        pauseMenu = new VBox(pauseLabel,continueButton,repeatButton,backButton);
        pauseMenu.setAlignment(Pos.CENTER);
        pauseMenu.setSpacing(25);

        InputStream backStream = new FileInputStream("resources\\images\\Back.png");
        // these strings sets up size of background appropriate to size of window
        ImageView background = new ImageView(new Image(backStream));
        background.setFitWidth(FRAME_WIDTH);
        background.setFitHeight(FRAME_HEIGHT);

        GridPane winGroup = new GridPane();
        winGroup.getChildren().add(pauseMenu);
        winGroup.setAlignment(Pos.CENTER);

        pane.setPrefWidth(FRAME_WIDTH);
        pane.setPrefHeight(FRAME_HEIGHT);
        pane.getChildren().add(background);
        pane.getChildren().add(winGroup);
//        pane.setBackground(new Background(new BackgroundFill
//                (Paint.valueOf("DARKCYAN"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setAlignment(Pos.CENTER);

        levelScene.getClip().stop();
        playBackMusic();
    }

    private void playBackMusic() {
        try {
            File soundFile = new File("resources/music/ChooseLevel.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

            clip = AudioSystem.getClip();
            clip.open(ais);
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

    public Clip getClip() {
        return clip;
    }
}
