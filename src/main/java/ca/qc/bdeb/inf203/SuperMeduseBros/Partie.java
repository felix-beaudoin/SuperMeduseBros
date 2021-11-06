package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Partie {
    double w = 350; double h = 480;

    ArrayList<GameObject> gameObjects = new ArrayList<>();
    Partie(){
        Meduse m = new Meduse(w, h);
        Plateforme p1 = new Plateforme(w, h);
        Plateforme p2= new Plateforme(w, h);
        Plateforme p3 = new Plateforme(w, h);
        Plateforme p4 = new Plateforme(w, h);
        Plateforme p5 = new Plateforme(w, h);
        gameObjects.add(m);
        gameObjects.add(p1);
        gameObjects.add(p2);
        gameObjects.add(p3);
        gameObjects.add(p4);
        gameObjects.add(p5);


    }

    public void update(double deltaTemps){
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTemps, gameObjects);
        }


    }

    public void draw(GraphicsContext context, long now){
        context.clearRect(0, 0, w, h);
        context.setFill(Color.BLUE);
        context.fillRect(0, 0, w, h);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(context, now);
        }
    }
}
