package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final double w = 350;
    private final double h = 480;

    @Override
    public void start(Stage stage) {

        //utliser pour manager les scenes
        GameManager gameManager = new GameManager(stage, w, h);
        gameManager.displayMenu();

        //show the stage
        stage.setTitle("Super Meduse Bros");
        stage.setResizable(false);
        stage.show();
    }
}