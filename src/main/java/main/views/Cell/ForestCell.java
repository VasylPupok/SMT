package main.views.Cell;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.*;

public class ForestCell extends Cell implements EmptyCell {

    private static final String imageURL = "file:resources\\images\\cells\\Forest.png";//path to image of forest

    public ForestCell(int length, int x, int y) {
        super(length, x, y, true, true, false);
    }



    @Override
    protected void setCellImage() {
        Image mountain = new Image(imageURL);
        this.setFill(new ImagePattern(mountain, super.getX(), super.getY(), super.getWidth(), super.getHeight(), false));
    }

}