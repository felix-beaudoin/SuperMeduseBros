package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


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

    private void displayMenu(Stage stage) {
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
        startGameButton.setFont(Font.font(15));
        startGameButton.setOnAction(event -> {
            startGame(stage);
        });

        Button showScore = new Button("Meilleurs scores");
        showScore.setFont(Font.font(15));
        showScore.setOnAction(event -> {
            displayScore(false, stage, 0);
        });

        VBox buttons = new VBox(startGameButton, showScore, new Text(""));
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        buttons.setSpacing(25);


        //si escape alors exit
        menuScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
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

        //sick.txt alignment
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

                //sick.txt
                score.setText(partie.getScore() + "px");

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

                    if (now * 1e-9 >= 3 + timeOfLost) {

                        displayScore(true, stage, partie.getScore());
                        timer.stop();
                    }
                }

                lastTime = now;
            }
        };


        gameScene.setOnKeyPressed(event -> {
            Input.setKeyPressed(event.getCode(), true);
            if (event.getCode() == KeyCode.ESCAPE) {
                timer.stop();
                displayMenu(stage);
            }
        });
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
        }
        isLostDisplayed = true;
    }

    public void displayScore(Boolean fromGame, Stage stage, int scoreActuel) {

        Text titre = new Text("Meilleurs scores");
        titre.setFont(Font.font(35));

        LinkedList<Score> scoreTriable = new LinkedList<>();

        GridPane scores = new GridPane();
        try { // source : https://www.w3schools.com/java/java_files_read.asp

            File scoreFile = new File("score.txt");

            Scanner sc = new Scanner(scoreFile);

            int i = 0;

            while (sc.hasNextLine()) {

                String[] nextLine = sc.nextLine().split(";");
                scores.add(new Text("#" + (i + 1) + " -- " + nextLine[0] + " -- " + nextLine[1] + "px"), 0, i);
                i++;
                scoreTriable.add(new Score(nextLine[0], Integer.parseInt(nextLine[1])));
            }

            for (int j = 0; i < 15; i++) {
                scores.add(new Text(""), 0, i);
            }
            sc.close();

        } catch (Exception e) {
            scores.add(new Text("Fichier des scores non-fonctionnel"), 0, 0);
            System.out.println(e);
        }


        VBox col = new VBox(titre, scores);

        if (fromGame) {
            HBox inputfield = new HBox();
            inputfield.setAlignment(Pos.CENTER);

            Text name = new Text("Nom:");
            TextField textField = new TextField();
            Button save = new Button("Sauvegarder!");
            save.setOnAction(event -> {

                try {
                    FileWriter pw = new FileWriter(new File("score.txt"));

                    scoreTriable.add(new Score(textField.getText(), scoreActuel));

                    sortScoreList(scoreTriable);

                    for (int i = 0; i < scoreTriable.size(); i++) {
                        pw.write(textField.getText() + ";" + scoreActuel);
                    }


                    pw.close();
                } catch (IOException e) {
                    textField.setText("Fichier pas trouvé");
                }
                displayMenu(stage);
            });
            inputfield.getChildren().addAll(name, textField, save);
            col.getChildren().add(inputfield);
        }


        Button acceuil = new Button("Retourner à l'accueil");
        acceuil.setOnAction(event -> displayMenu(stage));


        col.getChildren().add(acceuil);
        col.setAlignment(Pos.CENTER);
        col.setSpacing(15);

        Scene scoreScene = new Scene(col);

        //si escape alors exit
        scoreScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                displayMenu(stage);
            }
        });

        stage.setScene(scoreScene);
    }

    private void sortScoreList(LinkedList<Score> scoreNonTrie) {


    }

}