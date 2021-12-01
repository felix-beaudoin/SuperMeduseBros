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
        double w = 350; double h = 480;
        var root = new StackPane();
        Scene scene = new Scene(root);

        Text score = new Text("0px");
        score.setFill(Color.WHITE);
        score.setFont(Font.font(35));

        VBox scoreCol = new VBox();
        scoreCol.getChildren().add(score);
        scoreCol.setAlignment(Pos.TOP_CENTER);

        Canvas canvas = new Canvas(w, h);
        GraphicsContext context = canvas.getGraphicsContext2D();

        Partie partie = new Partie(w,h);

        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();
            final long startTime = lastTime;
            @Override
            public void handle(long now) {
                double deltaTemps = (now - lastTime) * 1e-9;
                partie.update(deltaTemps);
                partie.draw(context, now - startTime);
                lastTime = now;
                score.setText((int) (java.lang.Math.floor(partie.getCamera().getTop() * -1)) + "px");
            }
        };

        scene.setOnKeyPressed(event -> Input.setKeyPressed(event.getCode(), true));
        scene.setOnKeyReleased(event -> Input.setKeyPressed(event.getCode(), false));

        timer.start();

        root.getChildren().addAll(canvas, scoreCol);
        stage.setTitle("Super Meduse Bros");
        stage.setResizable(false);
        stage.getIcons().add(new Image("meduse1.png"));
        stage.setScene(scene);
        stage.show();
    }
}