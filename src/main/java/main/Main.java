package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    private final int FRAME_HEIGHT = 660;
    private final int FRAME_WIDTH = 840;
    private static ChooseLevelScene chooseLevelScene;
    private static StartMenuScene startMenuScene;
    private static LoadingScene loadingScene;
    private static FirstLevelScene firstLevelScene;
    private GameScene gameScene;
    private Timeline timeline;

    @Override
    public void start(Stage stage) throws Exception {

        startMenuScene = new StartMenuScene(new Pane(), FRAME_WIDTH, FRAME_HEIGHT, stage);
        chooseLevelScene = new ChooseLevelScene(new Group(),FRAME_WIDTH, FRAME_HEIGHT,stage);
        loadingScene = StartMenuScene.getLoadingScene();

        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
        stage.setTitle("SMT");
        stage.setResizable(false);
        stage.setScene(loadingScene);
        stage.setMinWidth(FRAME_WIDTH);
        stage.setMinHeight(FRAME_HEIGHT);
        stage.show();
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
            stage.setScene(startMenuScene);
            startMenuScene.playBackMusic();
        }));
        timeline.play();

        startMenuScene.getStartButton().setOnMouseClicked(mouseEvent -> {
            startMenuScene.getClip().stop();
            stage.setScene(loadingScene);
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                stage.setScene(chooseLevelScene);
                chooseLevelScene.playBackMusic();
            }));
            timeline.play();
            chooseLevelScene.getBackButton().setOnMouseClicked(newMouseEvent -> {
                stage.setScene(loadingScene);
                chooseLevelScene.getClip().stop();
                timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                    stage.setScene(startMenuScene);
                    startMenuScene.getClip().start();
                }));
                timeline.play();
            });
            chooseLevelScene.getFirstLevelButton().setOnMouseClicked(newMouseEvent -> chooseLevelScene.getFirstLevelButton().runLevel());
            chooseLevelScene.getSecondLevelButton().setOnMouseClicked(newMouseEvent -> chooseLevelScene.getSecondLevelButton().runLevel());
            chooseLevelScene.getThirdLevelButton().setOnMouseClicked(newMouseEvent -> chooseLevelScene.getThirdLevelButton().runLevel());
            chooseLevelScene.getFourthLevelButton().setOnMouseClicked(newMouseEvent -> chooseLevelScene.getFourthLevelButton().runLevel());
            chooseLevelScene.getFifthLevelButton().setOnMouseClicked(newMouseEvent -> chooseLevelScene.getFifthLevelButton().runLevel());
        });

        startMenuScene.getRandomButton().setOnMouseClicked(mouseEvent -> {
            startMenuScene.getClip().stop();
            startMenuScene.getClip().setMicrosecondPosition(0);
            stage.setScene(loadingScene);
            timeline = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                gameScene = new GameScene(new Group(),FRAME_WIDTH,FRAME_HEIGHT);
                stage.setScene(gameScene);
            }));
            timeline.play();
        });


        startMenuScene.getExitButton().setOnMouseClicked(mouseEvent -> {
            stage.setScene(loadingScene);
            timeline = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                stage.close();
                startMenuScene.getClip().stop();
                Platform.exit();
                System.exit(0);
            }));
            timeline.play();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static StartMenuScene getStartMenuScene(){
        return startMenuScene;
    }

}
