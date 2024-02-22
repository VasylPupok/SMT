package main;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import java.io.*;

public class LoadingScene extends Scene {

    private VBox loadingMenu;
    private String teamImageURL = "resources\\images\\Team.png";
    private String loadingImageURL = "resources\\images\\Loading.gif";
    private ColorAdjust colorAdjust;

    public LoadingScene(GridPane pane, int FRAME_WIDTH, int FRAME_HEIGHT) throws FileNotFoundException {

        super(pane, FRAME_WIDTH, FRAME_HEIGHT);

        InputStream teamStream = new FileInputStream(teamImageURL);
        Image teamImage = new Image(teamStream);
        ImageView teamImageView = new ImageView();
        teamImageView.setImage(teamImage);
        teamImageView.setFitWidth(400);
        teamImageView.setFitHeight(200);

        InputStream loadingStream = new FileInputStream(loadingImageURL);
        Image loadingImage = new Image(loadingStream);
        ImageView loadingImageView = new ImageView();
        loadingImageView.setImage(loadingImage);
        loadingImageView.setFitWidth(100);
        loadingImageView.setFitHeight(100);

        colorAdjust = new ColorAdjust();
        Label fix = new Label("Fix colorAdjust whole scene");
        fix.setVisible(false);
        fix.setManaged(false);

        loadingMenu = new VBox(teamImageView,loadingImageView);
        loadingMenu.setAlignment(Pos.CENTER);
        loadingMenu.setSpacing(30);

        GridPane loadingGroup = new GridPane();
        loadingGroup.getChildren().add(loadingMenu);
        loadingGroup.setAlignment(Pos.CENTER);

        pane.setPrefWidth(FRAME_WIDTH);
        pane.setPrefHeight(FRAME_HEIGHT);
        pane.getChildren().add(loadingGroup);
        pane.setBackground(new Background(new BackgroundFill
                (Paint.valueOf("BLACK"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setAlignment(Pos.CENTER);
        pane.setEffect(colorAdjust);
    }

    public ColorAdjust getColorAdjust() {
        return colorAdjust;
    }
}
