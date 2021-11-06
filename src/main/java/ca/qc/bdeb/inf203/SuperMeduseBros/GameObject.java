package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class GameObject {

    double x, y, vx, vy, ax, ay, w, h;
    double WIDTH, HEIGHT;
    Color color;

    GameObject(double w, double h){
        WIDTH = w;
        HEIGHT = h;
    }

    public void update(double deltaTemps, ArrayList<GameObject> gameObjects) {
        updatePhysique(deltaTemps);
    }

    public void updatePhysique(double deltaTemps) {
        vx += ax * deltaTemps;
        vy += ay * deltaTemps;

        x += vx * deltaTemps;
        y += vy * deltaTemps;
    }

    public double getHaut() {
        return y;
    }
    public double getBas() {
        return y + h;
    }
    public double getGauche() {
        return x;
    }
    public double getDroite() {
        return x + w;
    }

    public abstract void draw(GraphicsContext context, long now);


}
