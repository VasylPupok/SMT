package main;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

public class LevelButton extends Button {

    private int level;
    private boolean isClicked;
    private boolean isPassed;
    private Scene levelScene;
    private ChooseLevelScene chooseLevelScene;

    private int FRAME_WIDTH;
    private int FRAME_HEIGHT;

    public LevelButton(ChooseLevelScene chooseLevelScene,int level,String levelButtonColour, int FRAME_WIDTH, int FRAME_HEIGHT){

        this.chooseLevelScene = chooseLevelScene;
        this.level = level;
        this.isClicked = false;
        this.isPassed = false;
        this.FRAME_WIDTH = FRAME_WIDTH;
        this.FRAME_HEIGHT = FRAME_HEIGHT;

        this.setText(String.valueOf(level));
        this.setFont(Font.font("Candara", FontWeight.BOLD, 30));
        this.setBackground(new Background(new BackgroundFill(Paint.valueOf(levelButtonColour), CornerRadii.EMPTY, Insets.EMPTY)));
        this.setStyle("-fx-text-fill: white; -fx-text-alignment: center");
        this.setPrefWidth(150);
        this.setPrefHeight(100);
        chooseLevelScene.getLevelButtons().add(this);
        this.setOnMouseEntered(mouseEvent -> {
            setBackground(new Background(new BackgroundFill(Paint.valueOf("WHITE"), CornerRadii.EMPTY, Insets.EMPTY)));
            setStyle("-fx-text-fill: black");
        });
        this.setOnMouseExited(mouseEvent -> {
            if(!isPassed){
                setBackground(new Background(new BackgroundFill(Paint.valueOf(levelButtonColour), CornerRadii.EMPTY, Insets.EMPTY)));
                setStyle("-fx-text-fill: white");
            }
            else{
                this.setStyle("-fx-background-color: magenta; -fx-text-fill: yellow");
            }
        });
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public int getLevel(){
        return level;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public ChooseLevelScene getChooseLevelScene() {
        return chooseLevelScene;
    }

    public void changeBackground(){
        if(this.isPassed){
            this.setStyle("-fx-background-color: magenta; -fx-text-fill: yellow");
        }
    }

    public void runLevel(){
        chooseLevelScene.getClip().stop();
        int n = getLevel();
        switch(n){
            case 0 -> levelScene = new GameScene(new Group(),FRAME_WIDTH,FRAME_HEIGHT);
            case 1 -> levelScene = new FirstLevelScene(new Group(),FRAME_WIDTH,FRAME_HEIGHT,this);
            //default -> levelScene = new GameScene(new Group(),FRAME_WIDTH,FRAME_HEIGHT);
        }
        isClicked = true;
        chooseLevelScene.openLevel(levelScene);
    }

    public void endLevel(){

    }

}
