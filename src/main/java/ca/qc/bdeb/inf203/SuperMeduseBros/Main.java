package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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


        var menuRoot = new VBox();

        Button startGameButton = new Button("Jouer!");
        menuRoot.getChildren().add(startGameButton);


        Scene menuScene = new Scene(menuRoot);

        startGameButton.setOnAction(event -> {
            startGame(stage);
        });


        stage.setTitle("Super Meduse Bros");
        stage.setResizable(false);
        stage.getIcons().add(new Image("meduse1.png"));
        stage.setScene(menuScene);
        stage.show();
    }

    private void startGame(Stage stage) {
        isLostDisplayed = false;
        var gameRoot = new StackPane();
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

        //debugging shit
        /*canvas=new Canvas(w,h);
        GraphicsContext context2=canvas.getGraphicsContext2D();
        context2.setFill(Color.BLACK);
        context2.fillRect(0,0,w,h);
        gameRoot.setAlignment(Pos.);*/
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
        Text yo = new Text("deez nuts");

        HBox sbeve = new HBox(yo);

        Scene scoreScene = new Scene(sbeve);
        
        stage.setScene(scoreScene);
    }

}