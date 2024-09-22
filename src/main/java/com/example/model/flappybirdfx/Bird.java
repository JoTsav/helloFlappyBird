package com.example.model.flappybirdfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bird extends Entity{
    private double velocityY;
    private static final double SIZE = 30;
    public Bird(double initialX, double initialY) {
        super(initialX, initialY, SIZE, SIZE);
        this.velocityY = 0;
        this.body.setOpacity(1);
        this.body.setFill(Color.BLUE);
    }
    public void jump() {
        velocityY = -8;
    }
    public void update() {
        double GRAVITY = 0.2;
        velocityY += GRAVITY;
        this.addToYLocation(velocityY);
    }
    public Rectangle getBody() {
        return super.getBody();
    }
}
