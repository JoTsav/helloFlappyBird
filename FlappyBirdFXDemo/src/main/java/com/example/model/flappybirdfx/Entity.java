package com.example.model.flappybirdfx;

import javafx.scene.shape.Rectangle;

/**
 * Abstraction for Bird and Pipe entities
 */
public abstract class Entity {
    protected Rectangle body;

    /**
     * Entity constructor
     * @param xLocation
     * @param yLocation
     * @param width
     * @param height
     */
    public Entity(double xLocation, double yLocation, double width, double height) {
        super();
        this.body = new Rectangle(xLocation, yLocation, width, height);
    }
    public boolean checkCollision(Entity entity) {
        return this.body.getBoundsInParent().intersects(entity.body.getBoundsInParent());
    }
    public void addToXLocation(double increment) {//increment across plane
        double newValue = increment+this.body.getX();
        this.body.setX(newValue);
    }
    public void addToYLocation(double increment) {
        double newValue = increment+this.body.getY();
        this.body.setY(newValue);
    }
    public Rectangle getBody(){
        return body;
    }
}
