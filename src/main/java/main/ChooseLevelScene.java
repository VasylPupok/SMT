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
import javafx.util.*;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class ChooseLevelScene extends Scene {

    private static Stage stage;
    private static ChooseLevelScene chooseLevelScene;
    private Clip clip;
    private Button backButton;
    private static ArrayList<LevelButton> levelButtons = new ArrayList<>();

    private LevelButton firstLevelButton;
    private LevelButton secondLevelButton;
    private LevelButton thirdLevelButton;
    private LevelButton fourthLevelButton;
    private LevelButton fifthLevelButton;

    private Timeline timeline;

    public ChooseLevelScene(Group group, int FRAME_WIDTH, int FRAME_HEIGHT, Stage stage) throws IOException {

        super(group, FRAME_WIDTH, FRAME_HEIGHT);
        ChooseLevelScene.stage = stage;
        chooseLevelScene = this;

        Label chooseLevelLabel = new Label("Оберіть рівень!");
        chooseLevelLabel.setFont(Font.font("Candara", FontWeight.BOLD, 70));
        chooseLevelLabel.setTextFill(Paint.valueOf("ORANGE"));

        firstLevelButton = new LevelButton(this, 1, "GREEN", FRAME_WIDTH, FRAME_HEIGHT);
        secondLevelButton = new LevelButton(this, 2, "BLUE", FRAME_WIDTH, FRAME_HEIGHT);
        thirdLevelButton = new LevelButton(this, 3, "PURPLE", FRAME_WIDTH, FRAME_HEIGHT);
        fourthLevelButton = new LevelButton(this, 4, "GREY", FRAME_WIDTH, FRAME_HEIGHT);
        fifthLevelButton = new LevelButton(this, 5, "BROWN", FRAME_WIDTH, FRAME_HEIGHT);

        HBox levelButtons = new HBox(firstLevelButton, secondLevelButton, thirdLevelButton, fourthLevelButton, fifthLevelButton);
        levelButtons.setSpacing(15);

        backButton = new Button("Назад");
        backButton.setPrefWidth(300);
        backButton.setPrefHeight(100);
        backButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center");
        backButton.setFont(Font.font("Candara", FontWeight.BOLD, 40));
        backButton.setOnMouseEntered(mouseEvent -> backButton.setStyle("-fx-background-color: brown; -fx-text-fill: white; -fx-text-alignment: center"));
        backButton.setOnMouseExited(mouseEvent -> backButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-text-alignment: center"));

        VBox chooseLevelMenu = new VBox(chooseLevelLabel, levelButtons, backButton);
        chooseLevelMenu.setAlignment(Pos.CENTER);
        chooseLevelMenu.setPadding(new Insets(120, 0, 0, 15));
        chooseLevelMenu.setSpacing(30);

        InputStream backStream = new FileInputStream("resources\\images\\Back.gif");
        // these strings sets up size of background appropriate to size of window
        ImageView background = new ImageView(new Image(backStream));
        background.setFitWidth(FRAME_WIDTH);
        background.setFitHeight(FRAME_HEIGHT);

        group.getChildren().add(chooseLevelMenu);

//        choosePane.setPrefWidth(FRAME_WIDTH);
//        choosePane.setPrefHeight(FRAME_HEIGHT);
//        choosePane.getChildren().add(background);
//        choosePane.getChildren().add(chooseGroup);

        this.setRoot(group);
        //playBackMusic();
    }

    public void playBackMusic() {
        try {
            File soundFile = new File("resources/music/ChooseLevel.wav");
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

    protected void openLevel(Scene levelScene) {
        for (LevelButton button : levelButtons) {
            if (button.isClicked()) {
                stage.setScene(StartMenuScene.getLoadingScene());
                timeline = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                    stage.setScene(levelScene);
                    button.setClicked(false);
                }));
                timeline.play();
                break;
            }
        }
    }

    public Clip getClip() {
        return clip;
    }

    public Button getBackButton() {
        return backButton;
    }

    public static ArrayList<LevelButton> getLevelButtons() {
        return levelButtons;
    }

    public static Stage getStage() {
        return stage;
    }

    public static ChooseLevelScene getChooseLevelScene() {
        return chooseLevelScene;
    }

    public LevelButton getFirstLevelButton() {
        return firstLevelButton;
    }

    public LevelButton getSecondLevelButton() {
        return secondLevelButton;
    }

    public LevelButton getThirdLevelButton() {
        return thirdLevelButton;
    }

    public LevelButton getFourthLevelButton() {
        return fourthLevelButton;
    }

    public LevelButton getFifthLevelButton() {
        return fifthLevelButton;
    }
}
