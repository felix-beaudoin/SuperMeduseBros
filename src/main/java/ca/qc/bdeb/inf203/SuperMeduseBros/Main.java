package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        double w = 350; double h = 480;
        var root = new StackPane();
        Scene scene = new Scene(root, w, h);

        Canvas canvas = new Canvas(w, h);
        GraphicsContext context = canvas.getGraphicsContext2D();




        Partie partie = new Partie();


        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();
            final long startTime = lastTime;
            @Override
            public void handle(long now) {
                double deltaTemps = (now - lastTime) * 1e-9;
                partie.update(deltaTemps);
                partie.draw(context, now - startTime);
                lastTime = now;
            }
        };

        scene.setOnKeyPressed(event -> Input.setKeyPressed(event.getCode(), true));
        scene.setOnKeyReleased(event -> Input.setKeyPressed(event.getCode(), false));

        timer.start();

        root.getChildren().add(canvas);
        stage.setTitle("Super Meduse Bros");
        stage.getIcons().add(new Image("meduse1.png"));
        stage.setScene(scene);
        stage.show();
    }
}