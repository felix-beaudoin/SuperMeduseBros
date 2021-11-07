package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class GameObject {

    double x, y;
    double vx=0, vy=0, ax=0, ay=0;
    double width, height;
    Color color;
    protected final Partie partie;

    GameObject(double x, double y, double width, double height, Partie partie) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.partie = partie;
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
        return y + height;
    }
    public double getGauche() {
        return x;
    }
    public double getDroite() {
        return x + width;
    }

    public abstract void draw(GraphicsContext context, long now);
}
