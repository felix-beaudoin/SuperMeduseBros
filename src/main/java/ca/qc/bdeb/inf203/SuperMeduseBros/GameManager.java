package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;

public class GameManager {

    private final Stage stage;
    private final Menu menuPrincipal;
    private final MenuScore menuScore;
    private final GameWindow game;
    private final double w, h;

    public GameManager(Stage stage, double w, double h) {
        this.stage = stage;
        this.w = w;
        this.h = h;

        menuPrincipal = new Menu();
        menuScore = new MenuScore();
        game = new GameWindow();
    }


    public class Menu {

        private final Scene menuPrincipalScene;

        Menu() {
            menuPrincipalScene = buildMenuScene();
        }

        public Scene getMenuScene() {
            return menuPrincipalScene;
        }

        private Scene buildMenuScene() {
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
            startGameButton.setOnAction(event -> displayGame());

            Button showScore = new Button("Meilleurs scores");
            showScore.setFont(Font.font(15));
            showScore.setOnAction(event -> displayScores(null));

            VBox buttons = new VBox(startGameButton, showScore);
            buttons.setAlignment(Pos.BOTTOM_CENTER);
            buttons.setPadding(new Insets(25));
            buttons.setSpacing(25);


            //si escape alors exit
            menuScene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    Platform.exit();
                }
            });

            //display les elements
            menuRoot.getChildren().addAll(canvas, buttons);

            return menuScene;
        }
    }

    public void displayMenu() {
        stage.setScene(menuPrincipal.getMenuScene());
    }

    public class MenuScore {

        private final String fileName = "scores.bin";
        private Scene menuScore;
        private final ListView<String> listScores = new ListView<>();

        MenuScore() {
            menuScore = buildScoreScene(null);
        }

        public Scene getMenuScoreScene() {
            return menuScore;
        }

        public Scene getMenuScoreScene(Score score) {
            if (score == null) return getMenuScoreScene();

            menuScore = buildScoreScene(score);
            return menuScore;
        }

        /**
         * Construit la scene du menu de score et la retourne
         *
         * @param newScore nouveau score (null si on vient du menu principal)
         * @return la scene du menu score
         */
        private Scene buildScoreScene(Score newScore) {
            BorderPane rootScore = new BorderPane();
            Scene scoreScene = new Scene(rootScore, w, h);

            Text titre = new Text("Meilleurs scores");
            titre.setFont(Font.font(35));

            Button acceuil = new Button("Retourner à l'accueil");
            acceuil.setOnAction(e -> displayMenu());

            BorderPane.setAlignment(titre, Pos.CENTER);
            rootScore.setTop(titre);

            if (newScore != null) {
                TextField nameField = new TextField();
                nameField.setPromptText("Votre nom");
                nameField.setText("Anonymous");

                Button saveScore = new Button("Sauvergarder votre score!");
                saveScore.setOnAction(e -> {
                    newScore.setNom(nameField.getText());
                    addScore(newScore);
                    nameField.setDisable(true);
                    saveScore.setDisable(true);
                });

                VBox bottomNodes = new VBox(nameField, saveScore, acceuil);
                bottomNodes.setAlignment(Pos.CENTER);
                bottomNodes.setSpacing(10);

                BorderPane.setAlignment(bottomNodes, Pos.CENTER);
                rootScore.setBottom(bottomNodes);
            } else {
                BorderPane.setAlignment(acceuil, Pos.CENTER);
                rootScore.setBottom(acceuil);
            }

            BorderPane.setAlignment(listScores, Pos.CENTER);
            BorderPane.setMargin(listScores, new Insets(10, 0, 10, 0));
            rootScore.setCenter(listScores);

            rootScore.setPadding(new Insets(10));

            //si escape alors exit
            scoreScene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    displayMenu();
                }
            });

            return scoreScene;
        }

        public void addScore(Score score) {

            ObjectOutputStream scoreFile;
            try {
                if (!new File(fileName).exists()) {
                    scoreFile = new ObjectOutputStream(new FileOutputStream(fileName, true)); //avec header
                } else {
                    scoreFile = new MyObjectOutputStream(new FileOutputStream(fileName, true)); //sans header
                }

                scoreFile.writeObject(score);
                scoreFile.close();
                scoreFile.flush();
            } catch (Exception e) {
                System.out.println("Erreur lors de l'écriture au fichier");
            }
            refreshScores();
        }

        public void refreshScores() {
            //get scores from file
            LinkedList<Score> scores = getScores();

            //sort scores
            Collections.sort(scores);

            //reverse scores to have the best score at the top
            Collections.reverse(scores);

            //display scores
            listScores.getItems().clear();

            //fill the listView
            int MAX_SCORES = 15;
            for (int i = 0; i < MAX_SCORES; i++) {
                if (i < scores.size())
                    listScores.getItems().add("#" + (i + 1) + " : " + scores.get(i).getNom() + " - " + scores.get(i).getScore() + "px");
                else
                    listScores.getItems().add("");
            }

            //display scores
            listScores.refresh();
        }

        private LinkedList<Score> getScores() {
            LinkedList<Score> scores = new LinkedList<>();

            try (ObjectInputStream scoreFile = new ObjectInputStream(new FileInputStream(fileName))) {
                //weird tehcnique, mais apparement c la seule manière de lire un fichier d'objets (https://stackoverflow.com/a/33137586)
                while (true) {
                    try {
                        scores.add((Score) scoreFile.readObject());
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Fichier introuvable. Aucun score?");
            } catch (Exception e) {
                System.out.println("Erreur lors de la lecture du fichier");
                e.printStackTrace();
            }
            return scores;
        }

        //https://www.geeksforgeeks.org/how-to-fix-java-io-streamcorruptedexception-invalid-type-code-in-java/
        private class MyObjectOutputStream extends ObjectOutputStream {
            public MyObjectOutputStream(OutputStream out) throws IOException {
                super(out);
            }
            public void writeStreamHeader() {
            }
        }
    }

    public void displayScores(Score newScore) {
        stage.setScene(menuScore.getMenuScoreScene(newScore));
        menuScore.refreshScores();
    }

    public class GameWindow {
        private boolean isLostDisplayed;
        private AnimationTimer timer;
        private final double DURATION_LOST_TEXT = 3;

        /**
         * Construit la scene du jeu et la retourne
         * <p>
         * Note: on reconstruit la scene à chaque fois car on ne peut pas reprendre une meme partie
         *
         * @return la scene du jeu
         */
        public Scene getGameScene() {
            return buildGameScene();
        }

        private Scene buildGameScene() {
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
                    partie.update(deltaTemps, now - startTime);
                    partie.draw(context, now - startTime);

                    //score
                    score.setText(partie.getScore() + "px");

                    //debug info
                    position.setText(partie.getPositionInfo());
                    vitesse.setText(partie.getVitesseInfo());
                    acceleration.setText(partie.getAccelerationInfo());
                    standingOnPlat.setText(partie.getStandingOnPlateformInfo());


                    //gameLost
                    if (partie.isGameLost()) {
                        if (!isLostDisplayed) {
                            gameRoot.getChildren().add(getLostText());
                            isLostDisplayed = true;
                            timeOfLost = now * 1e-9;
                        }

                        if (now * 1e-9 >= DURATION_LOST_TEXT + timeOfLost) {
                            timer.stop();
                            displayScores(new Score(partie.getScore()));
                        }
                    }
                    lastTime = now;
                }
            };

            //quand on appuie sur escape on va au menu
            gameScene.setOnKeyPressed(event -> {
                Input.setKeyPressed(event.getCode(), true);
                if (event.getCode() == KeyCode.ESCAPE) {
                    timer.stop();
                    displayMenu();
                }
            });

            //setting key press
            gameScene.setOnKeyReleased(event -> Input.setKeyPressed(event.getCode(), false));

            //adding elements to the gameRoot
            gameRoot.getChildren().addAll(canvas, scoreCol, debugInfo);

            return gameScene;
        }

        private void startGame() {
            timer.start();
        }

        private Text getLostText() {
            Text gameOver = new Text("Partie Terminée");
            gameOver.setFill(Color.RED);
            gameOver.setFont(Font.font(35));
            return gameOver;
        }
    }

    public void displayGame() {
        stage.setScene(game.getGameScene());
        game.startGame();
    }
}
