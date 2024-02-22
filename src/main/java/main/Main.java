package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private final int FRAME_HEIGHT = 660;
    private final int FRAME_WIDTH = 840;
    private static StartMenuScene startMenuScene;
    private static LoadingScene loadingScene;
    private static FirstLevelScene firstLevelScene;
    private Timeline timeline;

    @Override
    public void start(Stage stage) throws Exception {

        startMenuScene = new StartMenuScene(new Pane(), FRAME_WIDTH, FRAME_HEIGHT, stage);

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
            stage.setScene(new FirstLevelScene(new Group(), FRAME_WIDTH, FRAME_HEIGHT));
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
