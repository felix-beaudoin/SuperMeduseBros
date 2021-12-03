package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Timer;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private AnimationTimer timer;
    private double w = 350;
    private double h = 480;
    private boolean isLostDisplayed;

    @Override
    public void start(Stage stage) {
        displayMenu(stage);
    }

    private void displayMenu(Stage stage){
        StackPane menuRoot = new StackPane();
        Scene menuScene = new Scene(menuRoot);

        Canvas canvas = new Canvas(w, h);
        GraphicsContext context = canvas.getGraphicsContext2D();

        //background et image
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, w, h);
        context.drawImage(new Image("accueil.png"), 0, 25);



        //button pour jouer et afficher score
        Button startGameButton = new Button("Jouer!");
        startGameButton.setOnAction(event -> {
            startGame(stage);
        });

        Button showScore = new Button("Meilleurs scores");
        showScore.setOnAction(event -> {
            displayScore(false, stage);
        });

        VBox buttons = new VBox(startGameButton, showScore);
        buttons.setAlignment(Pos.CENTER);



        //si escape alors exit
        menuScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE){
                Platform.exit();
            }
        });

        //display les elements
        menuRoot.getChildren().addAll(canvas, buttons);
        stage.setResizable(false);
        stage.setWidth(w);
        stage.setHeight(h);
        stage.setScene(menuScene);
        stage.show();
    }


    private void startGame(Stage stage) {
        isLostDisplayed = false;
        StackPane gameRoot = new StackPane();
        Scene gameScene = new Scene(gameRoot);
        Partie partie = new Partie(w, h);

        //canvas
        Canvas canvas = new Canvas(w, h);
        GraphicsContext context = canvas.getGraphicsContext2D();

        //score
        Text score = new Text("0px");
        score.setFill(Color.WHITE);
        score.setFont(Font.font(35));

        //score alignment
        VBox scoreCol = new VBox();
        scoreCol.getChildren().add(score);
        scoreCol.setAlignment(Pos.TOP_CENTER);

        //debug info
        Text position = new Text();
        position.setFill(Color.WHITE);
        Text vitesse = new Text();
        vitesse.setFill(Color.WHITE);
        Text acceleration = new Text();
        acceleration.setFill(Color.WHITE);
        Text standingOnPlat = new Text();
        standingOnPlat.setFill(Color.WHITE);

        VBox debugInfo = new VBox(position, vitesse, acceleration, standingOnPlat);

        timer = new AnimationTimer() {
            long lastTime = System.nanoTime();
            final long startTime = lastTime;
            double timeOfLost;

            @Override
            public void handle(long now) {
                double deltaTemps = (now - lastTime) * 1e-9;


                // update la partie + dessiner
                partie.update(deltaTemps, now - startTime, lastTime);
                partie.draw(context, now - startTime);

                //score
                score.setText((int) (java.lang.Math.floor(partie.getCamera().getTop() * -1)) + "px");

                //debug info
                position.setText(partie.getPositionInfo());
                vitesse.setText(partie.getVitesseInfo());
                acceleration.setText(partie.getAccelerationInfo());
                standingOnPlat.setText(partie.getStandingOnPlateformInfo());


                //gameLost
                if (partie.isGameLost()) {
                    if (!isLostDisplayed) {
                        displayLost(gameRoot);
                        timeOfLost = now * 1e-9;
                    }

                    if (now * 1e-9 >= 3 + timeOfLost){
                        displayScore(true, stage);
                    }
                }
                lastTime = now;
            }
        };

        gameScene.setOnKeyPressed(event -> Input.setKeyPressed(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> Input.setKeyPressed(event.getCode(), false));

        timer.start();

        gameRoot.getChildren().addAll(canvas, scoreCol, debugInfo);

        stage.setScene(gameScene);
    }

    private void displayLost(StackPane gameRoot) {
        if (!isLostDisplayed) {
            Text gameOver = new Text("Partie Terminée");
            gameOver.setFill(Color.RED);
            gameOver.setFont(Font.font(35));
            gameRoot.getChildren().add(gameOver);
            System.out.println("lost displayed");
        }
        isLostDisplayed = true;
    }

    public void displayScore(Boolean fromGame, Stage stage){
        System.out.println("yoooo");
        Text yo = new Text("h");

        HBox hboxers = new HBox(yo);

        Scene scoreScene = new Scene(hboxers);
        
        stage.setScene(scoreScene);
    }

}