package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Partie {
    double w = 350; double h = 480;

    ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    Partie(){
        Meduse m = new Meduse();
        gameObjects.add(m);
    }

    public void update(double deltaTemps){
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTemps);
        }
    }

    public void draw(GraphicsContext context){
        context.setFill(Color.BLUE);
        context.fillRect(0, 0, w, h);
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(context);
        }
    }
}
