package com.example.model.flappybirdfx;

import javafx.scene.paint.Color;

public class PipeSection extends Entity{
    public PipeSection(boolean isTrigger, double xLocation, double yLocation, double width, double height) {
        super(xLocation, yLocation, width, height);
        if(isTrigger) {//determine if you are a trigger to load more pipes or simply an obstacle
            this.body.setOpacity(0);
        }else {
            this.body.setOpacity(1);
            this.body.setFill(Color.GREEN);
        }
    }
}
