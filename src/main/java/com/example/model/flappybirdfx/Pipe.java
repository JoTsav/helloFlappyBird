package com.example.model.flappybirdfx;
import java.util.Random;
import javafx.scene.layout.Pane;

public class Pipe {
    private double xLocation;
    private boolean mustRemove;
    //A pipe will consist of 3 sections.
    private final PipeSection top;
    private final PipeSection middle;
    private final PipeSection bottom;
    private static final Random rand = new Random();
    public static final double PIPE_WIDTH = 50;
    private static final double speed = -3;

    public Pipe(double x, double sceneHeight) {
        xLocation = x;
        double maxTopHeight = sceneHeight /2;
        double topHeight = rand.nextDouble(maxTopHeight);
        top = new PipeSection(false, x, 0, PIPE_WIDTH, topHeight);
        double pipeGapHeight = 200;
        middle = new PipeSection(true, x, topHeight, PIPE_WIDTH, pipeGapHeight);
        double bottomY = topHeight + pipeGapHeight;
        double bottomHeight = sceneHeight - bottomY;
        bottom = new PipeSection(false, x, bottomY, PIPE_WIDTH, bottomHeight);
    }
    public void addToPane(Pane pane) {
        pane.getChildren().addAll(top.getBody(), middle.getBody(), bottom.getBody());// Addition of pipe sections to pane
    }
    public void update() {//updates
        xLocation += speed;
        if(this.xLocation + PIPE_WIDTH < 0) {
            this.mustRemove = true;
            return;
        }
        top.addToXLocation(speed);middle.addToXLocation(speed);bottom.addToXLocation(speed);
    }
    public ECollisionType TestCollision(Entity other) {
        if(top.checkCollision(other) || bottom.checkCollision(other)) { //top/bottom == GAME OVER
            return ECollisionType.GAMEOVER;
            //if it was the middle, spawn a new pipe
        }else if(middle.checkCollision(other)) {//MIDDLE -> new spawn
            return ECollisionType.SPAWN;
        }
        return ECollisionType.NONE;
    }
    public boolean mustRemove() {
        return this.mustRemove;
    }
}
