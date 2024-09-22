package com.example.model.flappybirdfx;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Game extends Pane {
    private AnimationTimer timer;
    private final Canvas canvas;
    private Bird bird;
    private final List<Pipe> pipes;
    private boolean gameOver;
    private final BorderPane layout;
    private  final double WIDTH;
    private  final double HEIGHT;
    private static final int PIPE_SPACING = 400;
    private double lastPipeXLocation = 0;
    private boolean gameStarted = false;
    private boolean isBusy=false;

    private long lastUpdate = 0;
    private long frameTimes = 0;
    private long frameCount = 0;
    private double fps = 0;
    private Pipe lastCollidedPipe = null; // Store the last collided pipe
    private final Stage mainStage;
    private int score;

    public Game(Stage primaryStage, double width, double height) {
        WIDTH = width;
        HEIGHT = height;
        canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK); // Set canvas background to black
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        bird = new Bird(WIDTH / 10, HEIGHT / 2);
        pipes = new ArrayList<>();
        gameOver = false;
        layout = new BorderPane();
        setupUI();
        layout.getChildren().add(bird.getBody());// Add the bird directly to the layout

        this.mainStage = primaryStage;
    }
    private void setupUI() {
        Button resetButton = new Button("ResetGame");
        resetButton.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                if (!isGameStarted()) {
                    startGame();  // Method to start the game if not started
                } else if (!isGameOver()) {
                    getBird().jump();  // Make the bird jump if the game is active
                }else {
                    resetGame();
                }}});

        layout.setCenter(canvas);
        layout.setTop(resetButton);
        getChildren().add(layout);
    }
    public void startGame() {
        if (!gameStarted) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update(now);
                    render();
                }
            };
            timer.start();
            gameStarted = true;
            spawnInitialPipes(5);
        }
    }
    private void spawnInitialPipes(int numPipes) {
        if(isBusy)return;
        isBusy = true;
        for (int i = 0; i < numPipes; i++) {
            spawnPipe();
        }
        isBusy = false;
    }
    private void spawnPipe() {

        lastPipeXLocation += Pipe.PIPE_WIDTH + PIPE_SPACING;
        Pipe newPipe = new Pipe(lastPipeXLocation, HEIGHT);
        pipes.add(newPipe);
        newPipe.addToPane(layout);
    }
    private void update(long now) {
        if (!gameOver) {
            bird.update();
            if (bird.getBody().getY() > HEIGHT || bird.getBody().getY() < 0) {
                gameOver = true;
                timer.stop();
            }

            pipes.forEach(Pipe::update);
            pipes.removeIf(Pipe::mustRemove);
            checkCollisions();

            calculateFPS(now);
        }
    }

    private void render() {//No need to redraw since all entities use Rectangle that form part of the scree
        this.mainStage.setTitle(String.format("FPS: %.2f\t\t\tScore: %s", fps, score));

    }
    private void checkCollisions() {
        int numPipesToSpawn = 0;
        boolean canStopSearching = false;
        for (Pipe pipe : pipes) {
            ECollisionType collisionType = pipe.TestCollision(bird);
            switch (collisionType) {
                case GAMEOVER:
                    System.out.println("GameOver");
                    gameOver = true;
                    timer.stop();
                    canStopSearching = true;
                    break;
                case SPAWN:
                    if (lastCollidedPipe != pipe) {
                        score += 1;
                        System.out.println(score);
                        numPipesToSpawn += 2;
                        canStopSearching=true;
                        lastCollidedPipe = pipe; // Update last collided pipe
                    }
                    break;
                default:
                    break;
            }
            if(canStopSearching) {
                break;
            }
        }
        spawnInitialPipes(numPipesToSpawn);
    }
    public Bird getBird() {
        return bird;
    }
    private void resetGame() {//Resets configs
        bird = new Bird(WIDTH / 10, HEIGHT / 2);
        layout.getChildren().removeIf(node -> node instanceof Rectangle); // This removes all pipes.
        pipes.clear();
        lastPipeXLocation = WIDTH / 2;
        gameOver = false;
        gameStarted = false;
        score = 0;
        layout.getChildren().add(bird.getBody());
        startGame();
    }
    private void calculateFPS(long nanoDuration) {//only do the calculations when the animation loop has run 1 iteration
        if(lastUpdate>0) {
            long deltaNanos = nanoDuration - lastUpdate;//obtain difference
            frameTimes += deltaNanos;//accumulated time difference
            frameCount++;//each time this loop runs an iteration it is considered 1 frame
            if(frameCount >= 60) {//only calculate the FPS once every 60 frames, so we do not tie down processing.
                fps = 1e9 /((double) frameTimes / frameCount);//frames per second
                //reset our accumulators for the next 60 frames
                frameCount = 0;
                frameTimes = 0;
            }
        }
        lastUpdate = nanoDuration;//track the animation time
    }
    public boolean isGameStarted() {
        return gameStarted;
    }
    public boolean isGameOver() {
        return gameOver;
    }
}
