package main;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

import javax.sound.sampled.*;
import java.io.*;

public class StartMenuScene extends Scene {

    private VBox startMenu;
    private Clip clip;
    private Button startButton;
    private LevelButton randomButton;
    private Button exitButton;

    private static Stage stage;
    private static StartMenuScene startMenuScene;
    private static LoadingScene loadingScene;
    private static int width;
    private static int height;

    public StartMenuScene(Pane startMenuPane, int FRAME_WIDTH, int FRAME_HEIGHT, Stage stage) throws IOException {

        super(startMenuPane, FRAME_WIDTH, FRAME_HEIGHT);
        StartMenuScene.stage = stage;
        startMenuScene = this;
        width = FRAME_WIDTH;
        height = FRAME_HEIGHT;
        loadingScene = new LoadingScene(new GridPane(),FRAME_WIDTH,FRAME_HEIGHT);

        Label firstLabel = new Label("Вітаємо в СМТ!");
        firstLabel.setFont(Font.font("Candara", FontWeight.BOLD, 40));
        firstLabel.setTextFill(Paint.valueOf("ORANGE"));

        InputStream stream = new FileInputStream("resources\\images\\Team.png");
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(FRAME_WIDTH/2);
        imageView.setFitHeight(FRAME_HEIGHT/3);

        startButton = new Button("Почати легенду");
        startButton.setPrefWidth(250);
        startButton.setPrefHeight(90);
        startButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center");
        startButton.setFont(Font.font("Candara", FontWeight.BOLD, 25));
        startButton.setOnMouseEntered(mouseEvent -> startButton.setStyle("-fx-background-color: brown; -fx-text-fill: white; -fx-text-alignment: center"));
        startButton.setOnMouseExited(mouseEvent -> startButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center"));

        randomButton = new LevelButton(ChooseLevelScene.getChooseLevelScene(),0,"RED",FRAME_WIDTH,FRAME_HEIGHT);
        randomButton.setPrefWidth(250);
        randomButton.setPrefHeight(90);
        randomButton.setText("Нищівний рівень");
        randomButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center");
        randomButton.setFont(Font.font("Candara", FontWeight.BOLD, 25));
        randomButton.setOnMouseEntered(mouseEvent -> randomButton.setStyle("-fx-background-color: brown; -fx-text-fill: white; -fx-text-alignment: center"));
        randomButton.setOnMouseExited(mouseEvent -> randomButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center"));

        exitButton = new Button("Вийти");
        exitButton.setPrefWidth(250);
        exitButton.setPrefHeight(90);
        exitButton.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-text-alignment: center");
        exitButton.setFont(Font.font("Candara", FontWeight.BOLD, 25));
        exitButton.setOnMouseEntered(mouseEvent -> exitButton.setStyle("-fx-background-color: brown; -fx-text-fill: white; -fx-text-alignment: center"));
        exitButton.setOnMouseExited(mouseEvent -> exitButton.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-text-alignment: center"));

        startMenu = new VBox(20, firstLabel,imageView, startButton,exitButton);
        startMenu.setAlignment(Pos.CENTER);
        startMenu.setPadding(new Insets(4));

        Group startGroup = new Group();
        startGroup.getChildren().add(startMenu);

        InputStream backStream = new FileInputStream("resources\\images\\Back.png");
        ImageView background = new ImageView(new Image(backStream));
        background.setFitWidth(FRAME_WIDTH);
        background.setFitHeight(FRAME_HEIGHT);


        startMenuPane.setPrefWidth(FRAME_WIDTH);
        startMenuPane.setPrefHeight(FRAME_HEIGHT);
        startMenuPane.getChildren().add(background);
        startMenuPane.getChildren().add(startGroup);

        //playBackMusic();
    }

    public void playBackMusic() {
        try {
            File soundFile = new File("resources/music/StartMenu.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
        }
    }

    public Button getStartButton() {
        return startButton;
    }
    public Button getRandomButton() {
        return randomButton;
    }


    public Button getExitButton() {
        return exitButton;
    }

    public Clip getClip() {
        return clip;
    }

    public static Stage getStage(){
        return stage;
    }

    public static StartMenuScene getStartMenuScene() {
        return startMenuScene;
    }

    public static int takeWidth() {
        return width;
    }

    public static int takeHeight(){
        return height;
    }

    public static LoadingScene getLoadingScene() {
        return loadingScene;
    }
}
