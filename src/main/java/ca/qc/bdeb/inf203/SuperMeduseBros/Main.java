package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        double w = 350;
        double h = 480;
        var root = new StackPane();
        Scene scene = new Scene(root);

        //score
        Text score = new Text("0px");
        score.setFill(Color.WHITE);
        score.setFont(Font.font(35));

        //score alignment
        VBox scoreCol = new VBox();
        scoreCol.getChildren().add(score);
        scoreCol.setAlignment(Pos.TOP_CENTER);

        //canvas
        Canvas canvas = new Canvas(w, h);
        GraphicsContext context = canvas.getGraphicsContext2D();

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


        Partie partie = new Partie(w, h);


        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();
            final long startTime = lastTime;

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



                lastTime = now;
            }
        };

        scene.setOnKeyPressed(event -> Input.setKeyPressed(event.getCode(), true));
        scene.setOnKeyReleased(event -> Input.setKeyPressed(event.getCode(), false));

        timer.start();

        root.getChildren().addAll(canvas, scoreCol, debugInfo);
        stage.setTitle("Super Meduse Bros");
        stage.setResizable(false);
        stage.getIcons().add(new Image("meduse1.png"));
        stage.setScene(scene);
        stage.show();
    }
}